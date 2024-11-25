package br.edu.ibmec.cloud.ecommerce.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.azure.spring.data.cosmos.repository.CosmosRepository;

import br.edu.ibmec.cloud.ecommerce.entity.Order;

@Repository
public interface OrderRepository extends CosmosRepository<Order, String> {
    String countByUsuarioId(String usuarioId);
    Order findByOrderId(String orderId);
    List<Order> findByProductId(String productId);
    List<Order> findByUsuarioId(String usuarioId);
    List<Order> findByNumeroCartao(String numeroCartao);
}