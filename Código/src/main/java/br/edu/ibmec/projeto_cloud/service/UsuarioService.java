package br.edu.ibmec.projeto_cloud.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.edu.ibmec.projeto_cloud.model.Cartao;
import br.edu.ibmec.projeto_cloud.model.Usuario;
import br.edu.ibmec.projeto_cloud.repository.CartaoRepository;
import br.edu.ibmec.projeto_cloud.repository.UsuarioRepository;
import br.edu.ibmec.projeto_cloud.model.Transacao;


@Service
public class UsuarioService{
  @Autowired
  private UsuarioRepository usuarioRepository;


  @Autowired
  private CartaoRepository cartaoRepository;

  private static List<Usuario> database = new ArrayList<>();

  public List<Usuario>getAllItems(){
    return database;
  }

  public Usuario getItem(int id){
    for (Usuario usuario:database){
      if (usuario.getId()==(id)){
        return usuario;
      }
    }
    return null;
  }

  public Usuario criarUsuario(int id, String nome, String cpf, LocalDateTime dataNascimento, String email, String endereco) throws Exception{
    //Validar CPF
    if (!validarCpf(cpf)){
      throw new Exception("CPF inválido!");
    }
    
    Usuario usuario = new Usuario();
    usuario.setCpf(cpf);
    //usuario.setId(UUID.randomUUID());
    usuario.setNome(nome);
    usuario.setCpf(cpf);
    usuario.setDataNascimento(dataNascimento);
    usuario.setEmail(email);
    usuario.setEndereco(endereco);
    
    database.add(usuario);
    return usuario;
  }

  public Usuario criarUsuario(Usuario usuario) throws Exception {
    return criarUsuario(
        usuario.getId(),
        usuario.getNome(),
        usuario.getCpf(),
        usuario.getDataNascimento(),
        usuario.getEmail(),
        usuario.getEndereco()
    );
  }

  public Usuario buscarUsuario(int id){
    return this.findUsuario(id);
  }

  public void associarCartao (Cartao cartao, int id, Transacao transacao) throws Exception{

    //Buscar usuario
    Usuario usuario = this.findUsuario(id);

    //Validar se encontrou o usuario
    if (usuario == null){
      throw new Exception("Usuário não encontrado");
    }

    if (!cartao.getAtivo()){
      throw new Exception("Cartao inativo. Impossível realizar transação.");
    }

    validarLimiteCredito(cartao, transacao.getValor());
    // validarFrequenciaTransacoes(usuario);
    // validarTransacaoDuplicada(usuario, transacao);

    usuario.associarCartao(cartao);
  }
// cria cartão
  cartaoRepository.save(cartao);
//atualiza o cartao
  usuarioRepository.save(usuario);


  private Usuario findUsuario(int id){
    Optional<Usuario> usuario = usuarioRepository.findById(id);

    if (usuario.isEmpty())
      return null;

    return usuario.get();
  }


public boolean validarCpf(String cpf) {
  return cpf != null && cpf.matches("\\d{11}");
}

public void validarLimiteCredito(Cartao cartao, double valorTransacao) throws Exception {
  if (cartao.getLimiteCredito() < valorTransacao) {
    throw new Exception("Limite insuficiente");
  }
}

// public void validarFrequenciaTransacoes(Usuario usuario) throws Exception {
//   List<Transacao> transacoesRecentes = usuario.getTransacoes()
//       .stream()
//       .filter(t -> t.getDataTransacao().isAfter(LocalDateTime.now().minusMinutes(2)))
//       .collect(Collectors.toList());

//   if (transacoesRecentes.size() >= 3) {
//     throw new Exception("Alta-frequência de transações em pequeno intervalo");
//   }
// }

// public void validarTransacaoDuplicada(Usuario usuario, Transacao novaTransacao) throws Exception {
//   long transacoesSemelhantes = usuario.getTransacoes()
//       .stream()
//       .filter(t -> t.getDataTransacao().isAfter(LocalDateTime.now().minusMinutes(2)))
//       .filter(t -> t.getValor() == novaTransacao.getValor() && t.getEstabelecimento().equals(novaTransacao.getEstabelecimento()))
//       .count();

//   if (transacoesSemelhantes >= 2) {
//     throw new Exception("Transação duplicada");
//   }
// }
}

