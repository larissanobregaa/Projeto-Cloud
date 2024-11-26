package br.edu.ibmec.cloud.ecommerce.entity;

import org.springframework.data.annotation.Id;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;

@Data
@Container(containerName = "extratos")
public class Extrato {

    @Id
    private String extratoId;

    @PartitionKey
    @NotBlank(message = "O ID do cliente não pode estar vazio")
    private String clienteId;

    private Date dataTransacao;

    @NotBlank(message = "A descrição da transação não pode estar vazia")
    private String descricao;

    private double valor;

    private String tipoTransacao; // Exemplo: "Crédito" ou "Débito"
}
