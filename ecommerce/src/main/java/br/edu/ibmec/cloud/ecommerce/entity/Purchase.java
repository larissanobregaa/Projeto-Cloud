package br.edu.ibmec.cloud.ecommerce.entity;

import java.time.LocalDate;
import java.util.List;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@Container(containerName = "purchase")
public class Purchase {

    @Id
    private String purchaseId;

    @PartitionKey
    private String usuarioId;

    private double totalSpent;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate lastPurchase;

    private List<Extract> extractList;
    @Data
    @NoArgsConstructor
    public class Extract {
        private String transactionId;

        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate date;

        private String description;
        private double amount;
    }
}