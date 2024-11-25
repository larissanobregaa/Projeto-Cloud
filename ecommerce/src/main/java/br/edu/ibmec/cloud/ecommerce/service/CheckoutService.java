package br.edu.ibmec.cloud.ecommerce.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.edu.ibmec.cloud.ecommerce.config.TransactionProperties;
import br.edu.ibmec.cloud.ecommerce.entity.Order;
import br.edu.ibmec.cloud.ecommerce.entity.Product;
import br.edu.ibmec.cloud.ecommerce.repository.OrderRepository;

@Service
@EnableConfigurationProperties(TransactionProperties.class)
public class CheckoutService {

    @Autowired
    private RestTemplate restTemplate;

    private final String baseUrl = "https://projetocloud-cartao-credito-api-cqb9bmaudufzg9ht.centralus-01.azurewebsites.net/";
    private final String merchant = "BOT-COMMERCE";

    @Autowired
    private OrderRepository orderRepository;

    public Order checkout(Product product, String idUsuario, String numeroCartao) throws Exception {
        try {
            TransacaoResponse response = this.autorizar(product, numeroCartao);

            if (response == null || response.equals("")) {
                throw new Exception("Compra não realizada.");
            }

            Order order = new Order();
            order.setOrderId(UUID.randomUUID().toString());
            order.setDataOrder(LocalDateTime.now());
            order.setProductId(product.getProductId());
            order.setProductName(product.getProductName());
            order.setUsuarioId(idUsuario);
            order.setStatus("Seu produto foi comprado com sucesso.");
            this.orderRepository.save(order);
            return order;
        } catch (Exception e) {
            throw new Exception("A sua compra não foi realizada.", e);
        }
    }

    private TransacaoResponse autorizar(Product product, String numeroCartao) {
        String url = baseUrl + "transacao/autorizar";

        TransacaoRequest requestBody = new TransacaoRequest();
        requestBody.setNumeroCartao(numeroCartao);
        requestBody.setEstabelecimento(merchant);
        requestBody.setValor(product.getPrice());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<TransacaoRequest> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<TransacaoResponse> response = restTemplate.postForEntity(url, request, TransacaoResponse.class);

        return response.getBody();
    }
}