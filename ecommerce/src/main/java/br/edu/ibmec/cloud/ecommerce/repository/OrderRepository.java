package br.edu.ibmec.cloud.ecommerce.repository;

import com.azure.spring.data.cosmos.repository.CosmosRepository;

import br.edu.ibmec.cloud.ecommerce.entity.Order;

public interface OrderRepository extends CosmosRepository<Order, String> {

    Order findByOrderId(String orderId);
}