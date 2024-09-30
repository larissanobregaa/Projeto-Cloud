package br.edu.ibmec.projeto_cloud.model;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;

import java.util.List;
import lombok.Data;


@Data
@Entity
public class Usuario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  
  @Column
  @NotBlank(message = "Campo nome é obrigatório")
  private String nome;


  @Column
  @NotBlank(message = "Campo CPF é obrigatório")
  private String cpf;


  @Column
  @NotBlank(message = "Campo endereço é obrigatório")
  private String endereco;


  @Column
  @NotBlank(message = "Campo email é obrigatório")
  @Email(message = "Campo email não está no formato correto.")
  private String email;


  @Column
  @NotNull(message = "campo obrigatorio")
  private LocalDate dataNascimento;

  private Boolean ativo;

  @OneToMany
  @JoinColumn(referencedColumnName = "id", name = "usuario_id")
  private List<Cartao> cartoes = new ArrayList<>();

  public void associarCartao (Cartao cartao){
    this.cartoes.add(cartao);
  }

  public Boolean getAtivo() {
    return ativo;
}

public void setAtivo(Boolean ativo) {
    this.ativo = ativo;
}
}
