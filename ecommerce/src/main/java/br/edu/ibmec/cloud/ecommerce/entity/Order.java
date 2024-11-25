package br.edu.ibmec.cloud.ecommerce.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;

import lombok.Data;

@Data
@Container(containerName = "ordens")
public class Order {
    @Id
    private String orderId;
    @PartitionKey
    private String productId;
    private String productName;
    private String idUsuario;
    private LocalDateTime dataOrder;
    private String status;
    
}
