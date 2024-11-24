package br.edu.ibmec.cloud.ecommerce.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.azure.spring.data.cosmos.repository.CosmosRepository;

import br.edu.ibmec.cloud.ecommerce.entity.Order;

@Repository
public interface OrderRepository extends CosmosRepository<Order, String> {
    int countByUserId(int userId); // contar compras por cliente
    List<Order> findByProductId(String productId);
    List<Order> findByUserId(int userId);
    List<Order> findByCartao(String numeroCartao);
}