package br.edu.ibmec.projeto_cloud.request;

import lombok.Data;

@Data
public class TransacaoRequest {
    private int idUsuario;
    private String numeroCartao;
    private String comerciante;
    private Double valor;
}