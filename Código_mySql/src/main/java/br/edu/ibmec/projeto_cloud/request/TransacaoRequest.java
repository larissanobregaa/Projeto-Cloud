package br.edu.ibmec.projeto_cloud.request;

import lombok.Data;

@Data
public class TransacaoRequest {
    private int idUsuario;
    private String numeroCartao;
<<<<<<< HEAD
    private String estabelecimento;
=======
    private String establecimento;
>>>>>>> 5674f7c8b2681f6a32173327b7688cfae0a31e58
    private Double valor;
}