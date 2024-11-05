package br.edu.ibmec.projeto_cloud.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
public class Cartao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private boolean desativadoPermanentemente = false;

    @NotBlank(message = "Insira um número de cartão válido.")
    private String numeroCartao;

    private LocalDate dataValidade;

    private Double limiteCredito = 0.0;

    private boolean ativo = true; // Inicialize o ativo aqui

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "cartao") // Mapeie corretamente o relacionamento
    private List<Transacao> transacoes = new ArrayList<>();

    // Métodos de acesso
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public double getLimiteCredito() {
        return limiteCredito != null ? limiteCredito : 0.0;
    }

    public void setLimiteCredito(double limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    public boolean isAtivo() { // Use isAtivo ao invés de getAtivo
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
