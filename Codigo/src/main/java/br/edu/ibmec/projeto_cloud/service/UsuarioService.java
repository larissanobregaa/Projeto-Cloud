package br.edu.ibmec.projeto_cloud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ibmec.projeto_cloud.exception.UsuarioException;
import br.edu.ibmec.projeto_cloud.model.Cartao;
import br.edu.ibmec.projeto_cloud.model.Endereco; // Importando a classe Endereco
import br.edu.ibmec.projeto_cloud.model.Usuario;
import br.edu.ibmec.projeto_cloud.repository.CartaoRepository;
import br.edu.ibmec.projeto_cloud.repository.UsuarioRepository;

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
  public Usuario getItem(int id) throws Exception {
    return findUsuario(id);
  }

  // Cria um novo usuário
  public Usuario criarUsuario(Usuario usuario) throws UsuarioException {
    // Validar CPF
    if (!validarCpf(usuario.getCpf())) {
      throw new UsuarioException("CPF inválido!");
    }

    // Verifica se o CPF já está cadastrado
    if (usuarioRepository.findUsuarioByCpf(usuario.getCpf()).isPresent()) {
      throw new UsuarioException("Usuário com o CPF informado já cadastrado");
    }

    // Insere na Base de Dados
    return usuarioRepository.save(usuario);
  }

  // Atualiza um usuário existente
  public Usuario atualizarUsuario(int id, Usuario usuarioAtualizado) throws UsuarioException {
    // Buscar o usuário existente pelo ID
    Usuario usuarioExistente = findUsuario(id);

    // Validar CPF se for atualizado
    if (!validarCpf(usuarioAtualizado.getCpf())) {
      throw new UsuarioException("CPF inválido!");
    }

    // Atualizar os dados do usuário
    usuarioExistente.setNome(usuarioAtualizado.getNome());
    usuarioExistente.setCpf(usuarioAtualizado.getCpf());
    usuarioExistente.setDataNascimento(usuarioAtualizado.getDataNascimento());
    usuarioExistente.setEmail(usuarioAtualizado.getEmail());
    //usuarioExistente.setEndereco(usuarioAtualizado.getEndereco());

    // Salvar as alterações no repositório
    return usuarioRepository.save(usuarioExistente);
  }

  public void deletarUsuario(int id) throws UsuarioException {
    // Buscar o usuário existente pelo ID
    Usuario usuarioExistente = findUsuario(id);
    usuarioRepository.delete(usuarioExistente);
  }

  // Associa um cartão ao usuário e valida transação
  public void associarCartao(Cartao cartao, int id, double valorTransacao) throws UsuarioException {
    // Buscar usuário
    Usuario usuario = findUsuario(id);

    // Verifica se o cartão está ativo
    if (!cartao.getAtivo()) {
      throw new UsuarioException("Cartão inativo. Impossível realizar transação.");
    }

    // Validar limite de crédito
    validarLimiteCredito(cartao, valorTransacao); // Passando o valor da transação diretamente.

    // Associar cartão ao usuário
    usuario.associarCartao(cartao);

    // Salvar as alterações no cartão e no usuário
    cartaoRepository.save(cartao);
    usuarioRepository.save(usuario);
  }

  public List<Cartao> verCartoesAssociados(int id) throws UsuarioException {
    // Buscar o usuário pelo ID
    Usuario usuario = findUsuario(id);
    return usuario.getCartoes();
  }

  // Método para associar um endereço ao usuário
  public void associarEndereco(Endereco endereco, int id) throws UsuarioException {
    // Buscar usuário
    Usuario usuario = findUsuario(id);

    // Associar o endereço ao usuário
    usuario.getEnderecos().add(endereco);

    // Salvar o usuário com o endereço associado
    usuarioRepository.save(usuario);
  }

  // Método para listar os endereços associados ao usuário
  public List<Endereco> verEnderecosAssociados(int id) throws UsuarioException {
    // Buscar o usuário pelo ID
    Usuario usuario = findUsuario(id);
    return usuario.getEnderecos();
  }

  // Busca usuário por ID
  private Usuario findUsuario(int id) throws UsuarioException {
    return usuarioRepository.findById(id)
            .orElseThrow(() -> new UsuarioException("Usuário não encontrado"));
  }

  // Valida o CPF
  public boolean validarCpf(String cpf) {
    return cpf != null && cpf.matches("\\d{11}");
  }

  // Valida o limite de crédito do cartão
  public void validarLimiteCredito(Cartao cartao, double valorTransacao) throws UsuarioException {
    if (cartao.getLimiteCredito() < valorTransacao) {
      throw new UsuarioException("Limite insuficiente");
    }
  }
}
