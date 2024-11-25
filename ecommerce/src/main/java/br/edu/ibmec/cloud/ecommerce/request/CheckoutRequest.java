package br.edu.ibmec.cloud.ecommerce.request;

import lombok.Data;

@Data
public class CheckoutRequest {
    private String idUsuario;
    private String productId;
    private String numeroCartao;
}