package br.edu.ibmec.projeto_cloud.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
<<<<<<< HEAD
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

=======
  public LocalDate dataValidade;
  
  @Column
  public Double limiteCredito = 0.0;
  
  
  @Column
  public Boolean ativo = false;
  
  @ManyToOne
  @JsonIgnore
  @JoinColumn(name = "usuario_id") 
  private Usuario usuario;

  @OneToMany
  @JoinColumn(referencedColumnName = "id", name = "cartao_id")
  public List<Transacao> transacoes = new ArrayList<>();

  public Usuario getUsuario() {
    return usuario;
  }

  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
  }

  public double getLimiteCredito(){
    return limiteCredito != null ? limiteCredito : 0.0;
  }
>>>>>>> main

  public void setLimiteCredito(double limiteCredito){
    this.limiteCredito = limiteCredito;
  }

  public boolean getAtivo(){
    return ativo != null ? ativo : false;
  }

  public void setAtivo(boolean ativo){
    this.ativo = ativo;
  }
<<<<<<< HEAD

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
=======
  
>>>>>>> main
}
