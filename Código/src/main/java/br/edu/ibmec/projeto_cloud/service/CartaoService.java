package br.edu.ibmec.projeto_cloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ibmec.projeto_cloud.model.Cartao;
import br.edu.ibmec.projeto_cloud.model.Transacao;
import br.edu.ibmec.projeto_cloud.model.Usuario;
import br.edu.ibmec.projeto_cloud.repository.CartaoRepository;
import br.edu.ibmec.projeto_cloud.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CartaoService {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Criar um novo cartão associado a um usuário
    public Cartao criarCartao(int usuarioId, Cartao cartao) throws Exception {
        // Buscar o usuário pelo ID
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioId);

        if (!usuarioOptional.isPresent()) {
            throw new Exception("Usuário não encontrado");
        }

        // Associar o cartão ao usuário
        cartao.setUsuario(usuarioOptional.get());

        // Salvar o cartão no repositório
        return cartaoRepository.save(cartao);
    }

    // Buscar um cartão pelo ID
    public Cartao buscarCartaoPorId(int cartaoId) {
        Optional<Cartao> cartaoOptional = cartaoRepository.findById(cartaoId);
        return cartaoOptional.orElse(null);
    }

    public Cartao associarCartao(int usuarioId, Cartao cartao) {
        // Busque o usuário pelo ID (caso você tenha um repositório de usuários)
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Associe o cartão ao usuário
        cartao.setUsuario(usuario);
        
        // Caso tenha transações no cartão, associe-as
        if (cartao.getTransacoes() != null) {
            for (Transacao transacao : cartao.getTransacoes()) {
                transacao.setCartao(cartao);
            }
        }

        // Salve o cartão no banco de dados
        return cartaoRepository.save(cartao);
    }

    // Atualizar os dados de um cartão
    public Cartao atualizarCartao(int cartaoId, Cartao cartaoAtualizado) throws Exception {
        Optional<Cartao> cartaoOptional = cartaoRepository.findById(cartaoId);

        if (!cartaoOptional.isPresent()) {
            throw new Exception("Cartão não encontrado");
        }

        Cartao cartaoExistente = cartaoOptional.get();
        cartaoExistente.setNumeroCartao(cartaoAtualizado.getNumero());
        cartaoExistente.setDataValidade(cartaoAtualizado.getValidade());
        cartaoExistente.setLimiteCredito(cartaoAtualizado.getLimiteCredito());
        cartaoExistente.setAtivo(cartaoAtualizado.getAtivo());

        return cartaoRepository.save(cartaoExistente);
    }

    // Deletar um cartão pelo ID
    public void deletarCartao(int cartaoId) throws Exception {
        Optional<Cartao> cartaoOptional = cartaoRepository.findById(cartaoId);

        if (!cartaoOptional.isPresent()) {
            throw new Exception("Cartão não encontrado");
        }

        cartaoRepository.delete(cartaoOptional.get());
    }

    // Listar todos os cartões associados a um usuário
    public List<Cartao> listarCartoesDoUsuario(int usuarioId) throws Exception {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioId);

        if (!usuarioOptional.isPresent()) {
            throw new Exception("Usuário não encontrado");
        }

        Usuario usuario = usuarioOptional.get();

        return usuario.getCartoes(); // Supondo que o modelo `Usuario` tenha uma lista de cartões
    }
}