package br.edu.ibmec.projeto_cloud.service;

import br.edu.ibmec.projeto_cloud.model.Transacao;
import br.edu.ibmec.projeto_cloud.model.Usuario;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.List;
public class TransacaoService {
  public void validarFrequenciaTransacoes(Usuario usuario) throws Exception {
  List<Transacao> transacoesRecentes = usuario.getCartoes()  // Acessa os cartões do usuário
      .stream()
      .flatMap(cartao -> cartao.getTransacoes().stream())  // Mapeia para as transações dos cartões
      .filter(t -> t.getDataTransacao().isAfter(LocalDateTime.now().minusMinutes(2)))
      .collect(Collectors.toList());

  if (transacoesRecentes.size() >= 3) {
    throw new Exception("Alta-frequência de transações em pequeno intervalo");
  }
}

public void validarTransacaoDuplicada(Usuario usuario, Transacao novaTransacao) throws Exception {
  long transacoesSemelhantes = usuario.getCartoes()  // Acessa os cartões do usuário
      .stream()
      .flatMap(cartao -> cartao.getTransacoes().stream())  // Mapeia para as transações dos cartões 
      .filter(t -> t.getDataTransacao().isAfter(LocalDateTime.now().minusMinutes(2)))
      .filter(t -> Double.compare(t.getValor(), novaTransacao.getValor()) == 0  // Compara valores usando Double.compare
              && t.getEstabelecimento().equals(novaTransacao.getEstabelecimento()))
      .count();

  if (transacoesSemelhantes >= 2) {
    throw new Exception("Transação duplicada");
  }
}
  
}
