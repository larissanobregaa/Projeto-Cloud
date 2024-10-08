package br.edu.ibmec.projeto_cloud.dto;

import br.edu.ibmec.projeto_cloud.model.Cartao;
import br.edu.ibmec.projeto_cloud.model.Transacao;

public class CartaoTransacaoDTO {

    private Cartao cartao;
    private Transacao transacao;

    // Construtor padrão
    public CartaoTransacaoDTO() {}

    // Construtor com parâmetros
    public CartaoTransacaoDTO(Cartao cartao, Transacao transacao) {
        this.cartao = cartao;
        this.transacao = transacao;
    }

    // Getters e Setters
    public Cartao getCartao() {
        return cartao;
    }

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }

    public Transacao getTransacao() {
        return transacao;
    }

    public void setTransacao(Transacao transacao) {
        this.transacao = transacao;
    }
}
