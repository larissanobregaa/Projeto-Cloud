package br.edu.ibmec.projeto_cloud.service;

public class TransacaoService {
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
