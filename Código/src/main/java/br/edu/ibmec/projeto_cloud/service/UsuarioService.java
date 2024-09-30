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

  // Cria um novo usuário
  public Usuario criarUsuario(Usuario usuario) throws Exception {
    // Validar CPF
    if (!validarCpf(usuario.getCpf())) {
      throw new Exception("CPF inválido!");
    }

    // Verifica se o CPF já está cadastrado
    Optional<Usuario> opUsuario = this.usuarioRepository.findUsuarioByCpf(usuario.getCpf());
    if (opUsuario.isPresent()) {
      throw new UsuarioException("Usuário com o CPF informado já cadastrado");
    }

    // Insere na Base de Dados
    return usuarioRepository.save(usuario);
  }

  // Busca um usuário por ID
  public Usuario buscarUsuario(int id) {
    return this.findUsuario(id);
  }

  public Usuario atualizarUsuario(int id, Usuario usuarioAtualizado) throws Exception {
    // Buscar o usuário existente pelo ID
    Usuario usuarioExistente = this.findUsuario(id);
    
    if (usuarioExistente == null) {
        throw new Exception("Usuário não encontrado");
    }
    
    // Validar CPF se for atualizado
    if (!validarCpf(usuarioAtualizado.getCpf())) {
        throw new Exception("CPF inválido!");
    }

    // Atualizar os dados do usuário
    usuarioExistente.setNome(usuarioAtualizado.getNome());
    usuarioExistente.setCpf(usuarioAtualizado.getCpf());
    usuarioExistente.setDataNascimento(usuarioAtualizado.getDataNascimento());
    usuarioExistente.setEmail(usuarioAtualizado.getEmail());
    usuarioExistente.setEndereco(usuarioAtualizado.getEndereco());

    // Salvar as alterações no repositório
    return usuarioRepository.save(usuarioExistente);
  }


  public void deletarUsuario(int id) throws Exception {
    // Buscar o usuário existente pelo ID
    Usuario usuarioExistente = this.findUsuario(id);

    if (usuarioExistente == null) {
        throw new Exception("Usuário não encontrado");
    }

    // Remover o usuário do repositório
    usuarioRepository.delete(usuarioExistente);
  }

  
  // Associa um cartão ao usuário e valida transação
  public void associarCartao(Cartao cartao, int id, Transacao transacao) throws Exception {
    // Buscar usuário
    Usuario usuario = this.findUsuario(id);

    // Validar se encontrou o usuário
    if (usuario == null) {
      throw new Exception("Usuário não encontrado");
    }

    // Verifica se o cartão está ativo
    if (!cartao.getAtivo()) {
      throw new Exception("Cartão inativo. Impossível realizar transação.");
    }

    // Validar limite de crédito
    validarLimiteCredito(cartao, transacao.getValor());

    // Associar cartão ao usuário
    usuario.associarCartao(cartao);

    // Salvar as alterações no cartão e no usuário
    cartaoRepository.save(cartao);
    usuarioRepository.save(usuario);
  }


  public List<Cartao> verCartoesAssociados(int id) throws Exception {
    // Buscar o usuário pelo ID
    Usuario usuario = this.findUsuario(id);

    if (usuario == null) {
        throw new Exception("Usuário não encontrado");
    }

    // Retornar a lista de cartões associados ao usuário
    return usuario.getCartoes();
  }

  
  // Busca usuário por ID
  private Usuario findUsuario(int id) {
    Optional<Usuario> usuario = usuarioRepository.findById(id);
    return usuario.orElse(null);
  }

  // Valida o CPF
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
