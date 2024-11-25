package br.edu.ibmec.cloud.ecommerce.entity;

import java.time.LocalDate;
import java.util.List;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;

import lombok.Data;
import nonapi.io.github.classgraph.json.Id;

@Data
@Container(containerName = "compras")
public class Purchase {

  @Data
  public static class  Extract {
    private String transactionId;
    private String date;
    private String description;
    private double amount;
    
  }

  @Id
  private String purchaseId;

  @PartitionKey
  private String idUsuario;
  private double totalSpent;
  private LocalDate lastPurchase;
  private List<Extract> extractList;
  
}
