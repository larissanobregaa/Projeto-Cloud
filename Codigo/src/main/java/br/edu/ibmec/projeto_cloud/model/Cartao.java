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
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
@Entity
public class Cartao {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public int id;

  @NotBlank(message = "Insira um número de cartão válido.")
  @Column
  public String numeroCartao;

  @Column
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

  public void setLimiteCredito(double limiteCredito){
    this.limiteCredito = limiteCredito;
  }

  public boolean getAtivo(){
    return ativo != null ? ativo : false;
  }

  public void setAtivo(boolean ativo){
    this.ativo = ativo;
  }
  
}
