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
  public LocalDateTime dataValidade;
  
  @Column
  public Double limiteCredito;
  
  
  @Column
  public Boolean ativo;
  
  
  @OneToMany
  @JoinColumn(referencedColumnName = "id", name = "cartao_id")
  public List<Transacao> transacoes = new ArrayList<>();

  public double getLimiteCredito(){
    return limiteCredito;
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
  

  

}
