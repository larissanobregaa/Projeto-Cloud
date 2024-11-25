package br.edu.ibmec.cloud.ecommerce.request;

import lombok.Data;

@Data
public class CheckoutRequest {
    private String usuarioId;
    private String productId;
    private String numeroCartao;
}