package br.edu.ibmec.cloud.ecommerce.service;

import br.edu.ibmec.cloud.ecommerce.config.TransactionProperties;
import br.edu.ibmec.cloud.ecommerce.entity.Ordem;
import br.edu.ibmec.cloud.ecommerce.entity.Produto;
import br.edu.ibmec.cloud.ecommerce.errorHandler.CheckoutException;
import br.edu.ibmec.cloud.ecommerce.repository.OrdemRepository;
import br.edu.ibmec.cloud.ecommerce.request.TransacaoRequest;
import br.edu.ibmec.cloud.ecommerce.request.TransacaoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@EnableConfigurationProperties(TransactionProperties.class)
public class CheckoutService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TransactionProperties transactionProperties;

    @Autowired
    private OrdemRepository ordemRepository;

    public Ordem checkout( String usuarioId, Produto produto, String cartaoId, LocalDateTime dataTransacao) throws Exception{
        try {
            TransacaoResponse response = this.autorizar(usuarioId, produto, cartaoId, dataTransacao);

            if (response.getStatus().equals("APROVADO") == false) {
                throw new CheckoutException("A sua compra foi reprovada: " + response.getErro());
            }

            Ordem ordem = new Ordem();
            ordem.setOrdemId(UUID.randomUUID().toString());
            ordem.setDataTransacao(LocalDateTime.now());
            ordem.setProdutoId(produto.getProdutoId());
            ordem.setUsuarioId(usuarioId);
            ordem.setStatus("Produto Comprado");
            this.ordemRepository.save(ordem);
            return ordem;
            
        } catch (CheckoutException e) {
            throw e;
        } catch (Exception e) {
            String detailedMessage = extractErrorMessage(e.getMessage());
            throw new CheckoutException("Erro ao processar a compra: " + detailedMessage);
        }
    }

    private TransacaoResponse autorizar(String usuarioId, Produto produto, String cartaoId, LocalDateTime dataTransacao) {
        String url = transactionProperties.getTransactionUrl();
        TransacaoRequest request = new TransacaoRequest();

        request.setEstabelecimento(transactionProperties.getMerchant());
        request.setUsuarioId(usuarioId);
        request.setCartaoId(cartaoId);
        request.setValor(produto.getPreco());
        request.setDataTransacao(dataTransacao);
        ResponseEntity<TransacaoResponse> response = this.restTemplate.postForEntity(url, request, TransacaoResponse.class);
        return response.getBody();
    }

    public static String extractErrorMessage(String response) {
        try {
            System.out.println("Response: " + response);
            // Localizar o início do JSON na string
            int jsonStartIndex = response.indexOf("{");
            if (jsonStartIndex == -1) {
                return "Formato inválido. Não foi encontrado um JSON.";
            }

            // Isolar a parte do JSON
            String jsonString = response.substring(jsonStartIndex);

            // Usar o ObjectMapper para processar o JSON
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(jsonString);

            // Navegar até "errors" e pegar o primeiro "message"
            JsonNode errors = root.path("errors");
            if (errors.isArray() && errors.size() > 0) {
                JsonNode firstError = errors.get(0);
                return firstError.path("message").asText();
            }

            return "Nenhuma mensagem encontrada em 'errors'.";
        } catch (Exception e) {
            return "Erro ao processar a mensagem: " + e.getMessage();
        }
    }
}
