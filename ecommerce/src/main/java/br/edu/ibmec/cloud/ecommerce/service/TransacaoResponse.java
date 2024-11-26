package br.edu.ibmec.cloud.ecommerce.service;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class TransacaoResponse {

    private LocalDateTime dataTransacao;
    private double valor;
    private String estabelecimento;
    private String status;
    private List<Cartao> cartao;

    @Data
    public static class Cartao {
        private int id;
        private String ativo;
        private String limite;
        private String numero;
        private List<String> transacoes;
    }
}