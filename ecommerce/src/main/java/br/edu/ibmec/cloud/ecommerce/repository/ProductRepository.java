package br.edu.ibmec.cloud.ecommerce.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.azure.spring.data.cosmos.repository.CosmosRepository;

import br.edu.ibmec.cloud.ecommerce.entity.Product;

@Repository
public interface ProductRepository extends CosmosRepository<Product, String> {
    // Buscar todos os produtos
    List<Product> findAll(); 

    // Buscar produtos por categoria
    List<Product> findByProductCategory(String category);

    // Buscar produtos por nome contendo uma string 
    List<Product> findByProductNameContainingIgnoreCase(String productName);
}
