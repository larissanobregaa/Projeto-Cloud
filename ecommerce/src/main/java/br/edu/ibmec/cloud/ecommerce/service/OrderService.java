package br.edu.ibmec.cloud.ecommerce.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.azure.cosmos.models.PartitionKey;

import br.edu.ibmec.cloud.ecommerce.entity.Order;
import br.edu.ibmec.cloud.ecommerce.repository.OrderRepository;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public Order findByOrderId(String orderId) {
        return this.orderRepository.findByOrderId(orderId);
    }

    public List<Order> getAllOrders() {
        Iterable<Order> iterable = this.orderRepository.findAll();
        return StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
    }

    public void save(Order order) {
        order.setOrderId(UUID.randomUUID().toString());
        this.orderRepository.save(order);
    }

    public void delete(String orderId) throws Exception {

        Optional<Order> optOrder = this.orderRepository.findById(orderId);

        if(optOrder.isPresent() == false) {
            throw new Exception("Não encontrei o pedido a ser excluido");
        }

        this.orderRepository.deleteById(orderId, new PartitionKey(optOrder.get().getOrderId()));
    }

}
