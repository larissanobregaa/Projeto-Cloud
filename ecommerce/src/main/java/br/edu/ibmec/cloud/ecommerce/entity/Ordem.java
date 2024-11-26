package br.edu.ibmec.cloud.ecommerce.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;

import lombok.Data;

@Data
@Container(containerName = "ordens")
public class Ordem {
    @Id
    private String ordemId;
    @PartitionKey
    private String produtoId;
    private String usuarioId;
    private LocalDateTime dataTransacao;
    private String status;
}