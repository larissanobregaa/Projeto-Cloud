package br.edu.ibmec.projeto_cloud.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import br.edu.ibmec.projeto_cloud.model.Cartao;
import br.edu.ibmec.projeto_cloud.model.Usuario;
import br.edu.ibmec.projeto_cloud.model.Transacao;


@Service
public class UsuarioService{
  private static List<Usuario> database = new ArrayList<>();

  public List<Usuario>getAllItems(){
    return database;
  }

  @SuppressWarnings("unlikely-arg-type")
  public Usuario getItem(org.hibernate.validator.constraints.UUID id){
    for (Usuario usuario:database){
      if (usuario.getId().equals(id)){
        return usuario;
      }
    }
    return null;
  }

  public Usuario criarUsuario(UUID id, String nome, String cpf, LocalDateTime dataNascimento, String email, String endereco) throws Exception{
    //Validar CPF
    if (!validarCpf(cpf)){
      throw new Exception("CPF inválido!");
    }
    
    Usuario usuario = new Usuario();
    usuario.setCpf(cpf);
    usuario.setId(UUID.randomUUID());
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

  public Usuario buscarUsuario(UUID id){
    return this.findUsuario(id);
  }

  public void associarCartao (Cartao cartao, UUID id, Transacao transacao) throws Exception{

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
    validarFrequenciaTransacoes(usuario);
    validarTransacaoDuplicada(usuario, transacao);

    usuario.associarCartao(cartao);
  }

  private Usuario findUsuario(UUID id){
    for (Usuario item : database){
      if (item.getId()==id){
        return item;
      }
  }

  return null;
}

public boolean validarCpf(String cpf) {
  return cpf != null && cpf.matches("\\d{11}");
}

public void validarLimiteCredito(Cartao cartao, double valorTransacao) throws Exception {
  if (cartao.getLimiteCredito() < valorTransacao) {
    throw new Exception("Limite insuficiente");
  }
}

public void validarFrequenciaTransacoes(Usuario usuario) throws Exception {
  List<Transacao> transacoesRecentes = usuario.getTransacoes()
      .stream()
      .filter(t -> t.getDataTransacao().isAfter(LocalDateTime.now().minusMinutes(2)))
      .collect(Collectors.toList());

  if (transacoesRecentes.size() >= 3) {
    throw new Exception("Alta-frequência de transações em pequeno intervalo");
  }
}

public void validarTransacaoDuplicada(Usuario usuario, Transacao novaTransacao) throws Exception {
  long transacoesSemelhantes = usuario.getTransacoes()
      .stream()
      .filter(t -> t.getDataTransacao().isAfter(LocalDateTime.now().minusMinutes(2)))
      .filter(t -> t.getValor() == novaTransacao.getValor() && t.getEstabelecimento().equals(novaTransacao.getEstabelecimento()))
      .count();

  if (transacoesSemelhantes >= 2) {
    throw new Exception("Transação duplicada");
  }
}
}

