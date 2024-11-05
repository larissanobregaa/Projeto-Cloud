package br.edu.ibmec.projeto_cloud.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ibmec.projeto_cloud.model.Cartao;
import br.edu.ibmec.projeto_cloud.model.Transacao;
import br.edu.ibmec.projeto_cloud.model.Usuario;
import br.edu.ibmec.projeto_cloud.repository.CartaoRepository;
import br.edu.ibmec.projeto_cloud.repository.UsuarioRepository;

@Service
public class CartaoService {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Cartao criarCartao(int usuarioId, Cartao cartao) throws Exception {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioId);

        if (!usuarioOptional.isPresent()) {
            throw new Exception("Usuário não encontrado");
        }

        cartao.setUsuario(usuarioOptional.get());
        return cartaoRepository.save(cartao);
    }

    public Cartao associarCartao(int usuarioId, Cartao cartao) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        cartao.setUsuario(usuario);
        
        if (cartao.getTransacoes() != null) {
            for (Transacao transacao : cartao.getTransacoes()) {
                transacao.setCartao(cartao);
            }
        }

        return cartaoRepository.save(cartao);
    }

    public Cartao atualizarCartao(int cartaoId, Cartao cartaoAtualizado) throws Exception {
        Cartao cartaoExistente = buscarCartaoPorId(cartaoId);

        cartaoExistente.setNumeroCartao(cartaoAtualizado.getNumeroCartao());
        cartaoExistente.setDataValidade(cartaoAtualizado.getDataValidade());
        cartaoExistente.setLimiteCredito(cartaoAtualizado.getLimiteCredito());
        cartaoExistente.setAtivo(cartaoAtualizado.isAtivo());

        return cartaoRepository.save(cartaoExistente);
    }

    public void deletarCartao(int cartaoId) throws Exception {
        Cartao cartao = buscarCartaoPorId(cartaoId);
        cartaoRepository.delete(cartao);
    }

    public List<Cartao> listarCartoesDoUsuario(int usuarioId) throws Exception {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new Exception("Usuário não encontrado"));

        return usuario.getCartoes();
    }

    public void ativarCartao(int cartaoId) {
        Cartao cartao = buscarCartaoPorId(cartaoId);

        if (!cartao.isAtivo() && cartao.isDesativadoPermanentemente()) {
            throw new IllegalStateException("Cartão desativado permanentemente. Não pode ser reativado.");
        }

        cartao.setAtivo(true);
        cartaoRepository.save(cartao);
    }

    public void desativarCartao(int cartaoId) {
        Cartao cartao = buscarCartaoPorId(cartaoId);
        cartao.setAtivo(false);
        cartao.setDesativadoPermanentemente(true);
        cartaoRepository.save(cartao);
    }

    public Cartao buscarCartaoPorId(int cartaoId) {
        return cartaoRepository.findById(cartaoId)
            .orElseThrow(() -> new IllegalArgumentException("Cartão não encontrado."));
    }
}
