package br.edu.ibmec.cloud.ecommerce.request;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CheckoutRequest {
    private String usuarioId;
    private String produtoId;
    private String cartaoId;
    private LocalDateTime dataTransacao;
}