package br.edu.ibmec.todo.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class Todo {
    private int id;

    @NotBlank (message = "Campo do nome é obrgatório")
    private String name;
    @NotBlank (message = "Campo responsável é obrigatório")
    private String owner;
    @NotBlank (message = "Campo status é obrigatório")
    private String status;
    @NotBlank (message = "Campo descrição é obrigatório")
    private String description;
    @NotBlank (message = "Campo email é obrigatório")
    @Email(message = "Campo email nhão está em um formato correto")
    private String email;
}
