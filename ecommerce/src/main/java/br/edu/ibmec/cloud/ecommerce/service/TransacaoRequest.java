package br.edu.ibmec.cloud.ecommerce.service;

import lombok.Data;

@Data
public class TransacaoRequest {
    private String idUsuario;
    private String numeroCartao;
    private String estabelecimento;
    private Double valor;

}
