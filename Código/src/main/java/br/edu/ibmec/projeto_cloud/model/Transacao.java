package br.edu.ibmec.projeto_cloud.model;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
@Entity
public class Transacao {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public int id;
  
  @Column
  @NotBlank(message = "Insira um valor válido")
  public Double valor;
  
  
  @Column
  public String estabelecimento;
  
  
  @Column
  public LocalDateTime dataTransacao;  

  @ManyToOne
  @JoinColumn(name = "cartao_id", nullable = false)  // O cartão não pode ser nulo
  public Cartao cartao;
}
