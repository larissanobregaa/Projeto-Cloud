package br.edu.ibmec.projeto_cloud.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
@Entity
public class Transacao {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public int id;
  @Column
  @NotNull
  public Double valor;
  
  
  @Column
  public String estabelecimento;
  
  
  @Column
  public LocalDateTime dataTransacao;

  @ManyToOne
  @JoinColumn(name = "cartao_id")
  @JsonIgnore
  private Cartao cartao;

}
