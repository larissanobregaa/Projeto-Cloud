package br.edu.ibmec.cloud.ecommerce.entity;

import com.azure.spring.data.cosmos.core.mapping.Container;

import lombok.Data;

@Data
@Container(containerName = "compras")
public class Purchase {

  @Data
  public static class  Extract {
    private String transactionId;
  
    
  }
  
}
