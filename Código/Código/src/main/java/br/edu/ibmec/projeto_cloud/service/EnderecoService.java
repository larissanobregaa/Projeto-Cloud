package br.edu.ibmec.projeto_cloud.service;

import br.edu.ibmec.projeto_cloud.exception.UsuarioException;
import br.edu.ibmec.projeto_cloud.model.Endereco;
import br.edu.ibmec.projeto_cloud.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    // Busca todos os endereços
    public List<Endereco> getAllEnderecos() {
        return enderecoRepository.findAll();
    }

    // Busca um endereço por ID
    public Endereco getEnderecoById(int id) throws UsuarioException {
        return enderecoRepository.findById(id)
                .orElseThrow(() -> new UsuarioException("Endereço não encontrado"));
    }

    // Cria um novo endereço
    public Endereco criarEndereco(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }

    // Atualiza um endereço existente
    public Endereco atualizarEndereco(int id, Endereco enderecoAtualizado) throws UsuarioException {
        Endereco enderecoExistente = getEnderecoById(id);
        enderecoExistente.setRua(enderecoAtualizado.getRua());
        enderecoExistente.setNumero(enderecoAtualizado.getNumero());
        enderecoExistente.setBairro(enderecoAtualizado.getBairro());
        enderecoExistente.setCidade(enderecoAtualizado.getCidade());
        enderecoExistente.setEstado(enderecoAtualizado.getEstado());
        enderecoExistente.setCep(enderecoAtualizado.getCep());
        return enderecoRepository.save(enderecoExistente);
    }

    // Deleta um endereço por ID
    public void deletarEndereco(int id) throws UsuarioException {
        Endereco enderecoExistente = getEnderecoById(id);
        enderecoRepository.delete(enderecoExistente);
    }
}
