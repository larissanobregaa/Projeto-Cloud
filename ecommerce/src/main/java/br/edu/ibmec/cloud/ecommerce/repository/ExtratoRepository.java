package br.edu.ibmec.cloud.ecommerce.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.azure.spring.data.cosmos.repository.CosmosRepository;

import br.edu.ibmec.cloud.ecommerce.entity.Extrato;

@Repository
public interface ExtratoRepository extends CosmosRepository<Extrato, String> {
    List<Extrato> findByClienteId(String clienteId);
}