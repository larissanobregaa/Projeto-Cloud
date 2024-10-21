package br.edu.ibmec.projeto_cloud.model;

import java.time.LocalDate;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
<<<<<<< HEAD:Código/src/main/java/br/edu/ibmec/projeto_cloud/model/Usuario.java
import jakarta.validation.constraints.Pattern;
=======

import java.util.ArrayList;

import java.util.List;

>>>>>>> 12809c6147131880ffe65102ce2907d95a5348cf:Codigo/src/main/java/br/edu/ibmec/projeto_cloud/model/Usuario.java

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Usuario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column
  @NotBlank(message = "Campo nome é obrigatório")
  private String nome;

<<<<<<< HEAD:Código/src/main/java/br/edu/ibmec/projeto_cloud/model/Usuario.java
  @Column(unique = true)
=======

  @Column
>>>>>>> 12809c6147131880ffe65102ce2907d95a5348cf:Codigo/src/main/java/br/edu/ibmec/projeto_cloud/model/Usuario.java
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

  @Column
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario", orphanRemoval = true)
  private List<Endereco> enderecos = new ArrayList<>();

  @OneToMany
  @JoinColumn(name = "usuario_id")
  private List<Cartao> cartoes = new ArrayList<>();

  public void associarCartao(Cartao cartao) {
    this.cartoes.add(cartao);
  }

  // Adicione um método para verificar se o usuário é maior de idade
  public boolean isMaiorDeIdade() {
    return LocalDate.now().minusYears(18).isAfter(dataNascimento);
  }
}
