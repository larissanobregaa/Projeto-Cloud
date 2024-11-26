package br.edu.ibmec.cloud.ecommerce.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import br.edu.ibmec.cloud.ecommerce.entity.Ordem;
import br.edu.ibmec.cloud.ecommerce.repository.OrdemRepository;

@Service
public class OrdemService {

    @Autowired
    private OrdemRepository ordemRepository;

    public Ordem findByOrdemId(String id) {
        return this.ordemRepository.findByOrdemId(id);
    }

    public java.util.Optional<Ordem> findById(String id) {
        return this.ordemRepository.findById(id);
    }

    public List<Ordem> findOrdensByIdProduto() {
        return (List<Ordem>) ordemRepository.findAll();
    }

    public void save(Ordem ordem) {
        ordem.setOrdemId(UUID.randomUUID().toString());
        this.ordemRepository.save(ordem);
    }
}