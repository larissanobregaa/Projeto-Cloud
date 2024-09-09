package br.edu.ibmec.projeto_cloud.model;

import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class Transacao {
  public UUID id;
  
  @NotBlank(message = "Insira um valor válido")
  public Double valor;
  public String estabelecimento;
  public LocalDateTime dataTransacao;  
}
