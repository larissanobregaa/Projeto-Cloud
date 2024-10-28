package br.edu.ibmec.projeto_cloud.model;

import java.time.LocalDate;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.ArrayList;

import java.util.List;


import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Usuario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
<<<<<<< HEAD:Código/src/main/java/br/edu/ibmec/projeto_cloud/model/Usuario.java
  
<<<<<<< HEAD
=======
=======
>>>>>>> main:Codigo/src/main/java/br/edu/ibmec/projeto_cloud/model/Usuario.java

>>>>>>> main
  @Column
  @NotBlank(message = "Campo nome é obrigatório")
  private String nome;


  @Column
  @NotBlank(message = "Campo CPF é obrigatório")
  @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "CPF deve seguir o padrão XXX.XXX.XXX-XX")
  private String cpf;

  @Column
  @NotBlank(message = "Campo email é obrigatório")
  @Email(message = "Campo email não está no formato correto.")
  private String email;

  @Column
  @NotNull(message = "Campo data de nascimento é obrigatório!")
  private LocalDate dataNascimento;

<<<<<<< HEAD:Código/src/main/java/br/edu/ibmec/projeto_cloud/model/Usuario.java
  private Boolean ativo;
=======
  @Column
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario", orphanRemoval = true)
  private List<Endereco> enderecos = new ArrayList<>();
>>>>>>> main:Codigo/src/main/java/br/edu/ibmec/projeto_cloud/model/Usuario.java

  @OneToMany
  @JoinColumn(name = "usuario_id")
  private List<Cartao> cartoes = new ArrayList<>();

  public void associarCartao(Cartao cartao) {
    this.cartoes.add(cartao);
  }

<<<<<<< HEAD:Código/src/main/java/br/edu/ibmec/projeto_cloud/model/Usuario.java
  public Boolean getAtivo() {
    return ativo;
}

public void setAtivo(Boolean ativo) {
    this.ativo = ativo;
}
=======
  // Adicione um método para verificar se o usuário é maior de idade
  public boolean isMaiorDeIdade() {
    return LocalDate.now().minusYears(18).isAfter(dataNascimento);
  }
>>>>>>> main:Codigo/src/main/java/br/edu/ibmec/projeto_cloud/model/Usuario.java
}
