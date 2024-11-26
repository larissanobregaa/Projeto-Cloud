package br.edu.ibmec.cloud.ecommerce.request;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class TransacaoRequest {
    private String usuarioId;
    private String cartaoId;
    private String estabelecimento;
    private Double valor;
    private LocalDateTime dataTransacao;
}
