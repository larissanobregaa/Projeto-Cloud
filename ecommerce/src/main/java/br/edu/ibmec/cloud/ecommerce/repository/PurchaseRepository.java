package br.edu.ibmec.cloud.ecommerce.repository;

import com.azure.spring.data.cosmos.repository.CosmosRepository;

import br.edu.ibmec.cloud.ecommerce.entity.Purchase;

public interface PurchaseRepository extends CosmosRepository<Purchase, String> {

  Purchase findByUserId(String idUsuario);  
} 
