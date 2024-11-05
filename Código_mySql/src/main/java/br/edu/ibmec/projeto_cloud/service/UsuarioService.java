package br.edu.ibmec.projeto_cloud.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ibmec.projeto_cloud.exception.UsuarioException;
import br.edu.ibmec.projeto_cloud.model.Cartao;
import br.edu.ibmec.projeto_cloud.model.Usuario;
import br.edu.ibmec.projeto_cloud.repository.CartaoRepository;
import br.edu.ibmec.projeto_cloud.repository.UsuarioRepository;
import br.edu.ibmec.projeto_cloud.model.Transacao;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    // Busca todos os usuários
    public List<Usuario> getAllItems() {
        return usuarioRepository.findAll();
    }

    // Busca um usuário por ID
    public Usuario getItem(int id) {
        return findUsuario(id);
    }

    // Cria um novo usuário com validação de duplicidade
    public Usuario criarUsuario(Usuario usuario) throws Exception {
        validarCpfEEmail(usuario.getCpf(), usuario.getEmail(), false);
        return usuarioRepository.save(usuario);
    }

    // Busca um usuário por ID
    public Usuario buscarUsuario(int id) {
        return this.findUsuario(id);
    }

    // Atualiza um usuário existente com validação de duplicidade
    public Usuario atualizarUsuario(int id, Usuario usuarioAtualizado) throws Exception {
        Usuario usuarioExistente = findUsuario(id);
        if (usuarioExistente == null) {
            throw new Exception("Usuário não encontrado");
        }

        validarCpfEEmail(usuarioAtualizado.getCpf(), usuarioAtualizado.getEmail(), true);

        // Atualizar os dados do usuário
        usuarioExistente.setNome(usuarioAtualizado.getNome());
        usuarioExistente.setCpf(usuarioAtualizado.getCpf());
        usuarioExistente.setDataNascimento(usuarioAtualizado.getDataNascimento());
        usuarioExistente.setEmail(usuarioAtualizado.getEmail());
        usuarioExistente.setEndereco(usuarioAtualizado.getEndereco());

        return usuarioRepository.save(usuarioExistente);
    }

    // Deleta um usuário por ID
    public void deletarUsuario(int id) throws Exception {
        Usuario usuarioExistente = findUsuario(id);
        if (usuarioExistente == null) {
            throw new Exception("Usuário não encontrado");
        }
        usuarioRepository.delete(usuarioExistente);
    }

    // Associa um cartão ao usuário e valida a transação
    public void associarCartao(Cartao cartao, int id, Transacao transacao) throws Exception {
        Usuario usuario = findUsuario(id);
        if (usuario == null) {
            throw new Exception("Usuário não encontrado");
        }

        if (!cartao.isAtivo()) {
            throw new Exception("Cartão inativo. Impossível realizar transação.");
        }

        validarLimiteCredito(cartao, transacao.getValor());

        usuario.associarCartao(cartao);
        cartaoRepository.save(cartao);
        usuarioRepository.save(usuario);
    }

    // Ver cartões associados ao usuário
    public List<Cartao> verCartoesAssociados(int id) throws Exception {
        Usuario usuario = findUsuario(id);
        if (usuario == null) {
            throw new Exception("Usuário não encontrado");
        }
        return usuario.getCartoes();
    }

    // Busca usuário por ID (método privado)
    private Usuario findUsuario(int id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.orElse(null);
    }

    // Valida o CPF e o Email (verifica duplicidade)
    private void validarCpfEEmail(String cpf, String email, boolean isUpdate) throws UsuarioException {
        if (!validarCpf(cpf)) {
            throw new UsuarioException("CPF inválido!");
        }

        // Verificar duplicidade de CPF
        Optional<Usuario> usuarioCpf = usuarioRepository.findUsuarioByCpf(cpf);
        if (usuarioCpf.isPresent() && !isUpdate) {
            throw new UsuarioException("Usuário com o CPF informado já está cadastrado.");
        }

        // Verificar duplicidade de Email
        Optional<Usuario> usuarioEmail = usuarioRepository.findUsuarioByEmail(email);
        if (usuarioEmail.isPresent() && !isUpdate) {
            throw new UsuarioException("Usuário com o email informado já está cadastrado.");
        }
    }

    // Valida o CPF (somente formato)
    public boolean validarCpf(String cpf) {
        return cpf != null && cpf.matches("\\d{11}");
    }

    // Valida o limite de crédito do cartão
    public void validarLimiteCredito(Cartao cartao, double valorTransacao) throws Exception {
        if (cartao.getLimiteCredito() < valorTransacao) {
            throw new Exception("Limite insuficiente");
        }
    }
}
