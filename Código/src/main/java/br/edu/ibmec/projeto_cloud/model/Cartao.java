package br.edu.ibmec.projeto_cloud.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class Cartao {
  public UUID id;

  @NotBlank(message = "Insira um número de cartão válido.")
  public String numeroCartao;

  public LocalDateTime dataValidade;
  public Double limiteCredito;
  public Boolean ativo;
  public List<Transacao> transacoes;

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
