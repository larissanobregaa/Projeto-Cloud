package br.edu.ibmec.cloud.ecommerce.repository;

import org.springframework.stereotype.Repository;

import com.azure.spring.data.cosmos.repository.CosmosRepository;

import br.edu.ibmec.cloud.ecommerce.entity.Ordem;

@Repository
public interface OrdemRepository extends CosmosRepository<Ordem, String> {
    Ordem findByOrdemId(String ordemId);
}
