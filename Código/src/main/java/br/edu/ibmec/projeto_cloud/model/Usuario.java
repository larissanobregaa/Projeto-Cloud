package br.edu.ibmec.projeto_cloud.model;

import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import lombok.Data;


@Data
public class Usuario {
  private UUID id;
  
  @NotBlank(message = "Campo nome é obrigatório")
  private String nome;

  @NotBlank(message = "Campo CPF é obrigatório")
  private String cpf;

  @NotBlank(message = "Campo endereço é obrigatório")
  private String endereco;

  @NotBlank(message = "Campo email é obrigatório")
  @Email(message = "Campo email não está no formato correto.")
  private String email;

  @NotBlank(message = "Campo data de nascimento é obrigatório")
  private LocalDateTime dataNascimento;
  
  private List<Cartao> cartoes;
  private List<Transacao> transacoes;

  public void associarCartao (Cartao cartao){
    this.cartoes.add(cartao);
  }
}
