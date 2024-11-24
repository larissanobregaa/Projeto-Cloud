package br.edu.ibmec.cloud.ecommerce.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.edu.ibmec.cloud.ecommerce.config.TransactionProperties;
import br.edu.ibmec.cloud.ecommerce.entity.Order;
import br.edu.ibmec.cloud.ecommerce.entity.Product;
import br.edu.ibmec.cloud.ecommerce.errorHandler.CheckoutException;
import br.edu.ibmec.cloud.ecommerce.repository.OrderRepository;

@Service
@EnableConfigurationProperties(TransactionProperties.class)
public class CheckoutService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TransactionProperties transactionProperties;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private FraudDetectionService fraudDetectionService; // Serviço de detecção de fraude

    public Order checkout(Product product, int idUsuario, String numeroCartao) throws Exception {
        // Verifica limite de compras
        int comprasCliente = orderRepository.countByUserId(idUsuario);
        if (comprasCliente >= 5) { // Limite de 5 compras
            throw new CheckoutException("Limite de compras excedido para este cliente.");
        }

        // Verifica disponibilidade de estoque
        if (product.getStock() <= 0) {
            throw new CheckoutException("Produto indisponível no estoque.");
        }

        // Proteção contra fraudes
        if (fraudDetectionService.isFraudulentTransaction(idUsuario, product.getPrice())) {
            throw new CheckoutException("Transação suspeita de fraude.");
        }

        // Autoriza a transação
        TransacaoResponse response = this.autorizar(product, idUsuario, numeroCartao);
        if (!"APROVADO".equals(response.getStatus())) {
            throw new CheckoutException("Não consegui realizar a compra.");
        }

        // Atualiza estoque
        product.setStock(product.getStock() - 1);

        // Cria e salva o pedido
        Order order = new Order();
        order.setOrderId(UUID.randomUUID().toString());
        order.setDataOrder(LocalDateTime.now());
        order.setProductId(product.getProductId());
        order.setUserId(idUsuario);
        order.setStatus("Produto Comprado");
        this.orderRepository.save(order);

        return order;
    }

    private TransacaoResponse autorizar(Product product, int idUsuario, String numeroCartao) {
        String url = transactionProperties.getTransactionUrl();
        TransacaoRequest request = new TransacaoRequest();
        request.setEstabelecimento(transactionProperties.getMerchant());
        request.setIdUsuario(idUsuario);
        request.setNumeroCartao(numeroCartao);
        request.setValor(product.getPrice());

        ResponseEntity<TransacaoResponse> response =
                this.restTemplate.postForEntity(url, request, TransacaoResponse.class);

        return response.getBody();
    }
}