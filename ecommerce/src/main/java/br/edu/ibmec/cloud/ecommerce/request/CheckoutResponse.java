package br.edu.ibmec.cloud.ecommerce.request;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CheckoutResponse {
    private String produtoId;
    private String status;
    private LocalDateTime dataTransacao;
    private String erro;
    private String ordemId;

}
