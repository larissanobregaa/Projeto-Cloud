package br.edu.ibmec.cloud.ecommerce.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.azure.cosmos.models.PartitionKey;

import br.edu.ibmec.cloud.ecommerce.entity.Extrato;
import br.edu.ibmec.cloud.ecommerce.repository.ExtratoRepository;

@Service
public class ExtratoService {

    @Autowired
    private ExtratoRepository extratoRepository;

    public void save(Extrato extrato) {
        extrato.setExtratoId(UUID.randomUUID().toString());
        this.extratoRepository.save(extrato);
    }

    public Optional<Extrato> findById(String extratoId) {
        return this.extratoRepository.findById(extratoId);
    }

    public List<Extrato> findByClienteId(String clienteId) {
        return this.extratoRepository.findByClienteId(clienteId);
    }

    public void delete(String extratoId) throws Exception {

        Optional<Extrato> optExtrato = this.extratoRepository.findById(extratoId);

        if (!optExtrato.isPresent())
            throw new Exception("Extrato não encontrado para exclusão");

        this.extratoRepository.deleteById(extratoId, new PartitionKey(optExtrato.get().getClienteId()));
    }
}
