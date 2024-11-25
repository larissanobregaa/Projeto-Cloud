package br.edu.ibmec.cloud.ecommerce.service;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class TransacaoResponse {

    private class Cartao {
        private int id;
        private String ativo;
        private String limite;
        private String numero;
        private List<String> transacoes;
    }

    private LocalDateTime dataTransacao;
    private double valor;
    private String comerciante;
    private List<Cartao> cartao;
}
