package br.edu.ibmec.projeto_cloud.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
public class Cartao {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @NotBlank(message = "Insira um número de cartão válido.")
  @Column
  @Pattern(regexp = "\\d{16}", message = "Insira um número de cartão válido.")
  private String numeroCartao;

  @Column
  private LocalDateTime dataValidade;
  
  @Column
  private Double limiteCredito;
  
  @Column
  private Boolean ativo;
  
  @OneToMany(mappedBy = "cartao")
  private List<Transacao> transacoes = new ArrayList<>();

  @ManyToOne
  @JoinColumn(name = "usuario_id")
  private Usuario usuario;

  // Métodos existentes
  public Double getLimiteCredito() {
    return limiteCredito != null ? limiteCredito : 0.0;
}


  public void setLimiteCredito(double limiteCredito){
    this.limiteCredito = limiteCredito;
  }

  public boolean getAtivo(){
    return ativo;
  }

  public void setAtivo(boolean ativo){
    this.ativo = ativo;
  }

  // Novos métodos
  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
  }

  public String getNumero() {
    return numeroCartao;
  }

  public LocalDateTime getValidade() {
    return dataValidade;
  }
}
