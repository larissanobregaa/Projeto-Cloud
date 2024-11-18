package br.edu.ibmec.projeto_cloud.service;

import br.edu.ibmec.projeto_cloud.model.Cartao;
import br.edu.ibmec.projeto_cloud.model.Transacao;
import br.edu.ibmec.projeto_cloud.model.Usuario;
import br.edu.ibmec.projeto_cloud.repository.CartaoRepository;
import br.edu.ibmec.projeto_cloud.repository.TransacaoRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransacaoService {
  @Autowired
  private TransacaoRepository transacaoRepository;

  @Autowired
  private CartaoRepository cartaoRepository;

  private final long TRANSACTION_TIME_INTERVAL = 3;

  // Validações existentes
  public void validarFrequenciaTransacoes(Usuario usuario) throws Exception {
    List<Transacao> transacoesRecentes = usuario.getCartoes().stream()
        .flatMap(cartao -> cartao.getTransacoes().stream())
        .filter(t -> t.getDataTransacao().isAfter(LocalDateTime.now().minusMinutes(2)))
        .collect(Collectors.toList());

    if (transacoesRecentes.size() >= 2) {
        throw new Exception("Alta frequência de transações detectada. Por favor, aguarde.");
    }
}

public void validarTransacaoDuplicada(Usuario usuario, Transacao novaTransacao) throws Exception {
    boolean duplicada = usuario.getCartoes().stream()
        .flatMap(cartao -> cartao.getTransacoes().stream())
        .anyMatch(t -> t.getDataTransacao().isAfter(LocalDateTime.now().minusMinutes(5)) &&
                       Double.compare(t.getValor(), novaTransacao.getValor()) == 0 &&
                       t.getEstabelecimento().equals(novaTransacao.getEstabelecimento()));

    if (duplicada) {
        throw new Exception("Transação duplicada detectada. Aguarde alguns minutos.");
    }
}

  // Criar uma transação
    public Transacao criarTransacao(Cartao cartao, double valor, String comerciante) throws Exception {
        if (cartao.getAtivo() == false) {
            throw new Exception("Cartão não está ativo");
        }

        // Cartão tem limite para compra
        if (cartao.getLimiteCredito() < valor) {
            throw new Exception("Cartão sem limite para efetuar a compra");
        }

        //Verificar regras
        this.verificarAntifraude(cartao, valor, comerciante);

        //Passou nas regras, criar um nova transação
        Transacao transacao = new Transacao();
        transacao.setEstabelecimento(comerciante);
        transacao.setDataTransacao(LocalDateTime.now());
        transacao.setValor(valor);

        //Salva na base de dados
        transacaoRepository.save(transacao);

        //Diminui o limite do cartao
        cartao.setLimiteCredito(cartao.getLimiteCredito() - valor);

        //Associa a transacao ao cartao 
        cartao.getTransacoes().add(transacao);

        //Atualiza a base de dados com a nova transação para o cartao e atualiza o limite
        cartaoRepository.save(cartao);

        return transacao;

    }
    // Buscar uma transação por ID
    public Transacao buscarTransacaoPorId(int transacaoId) {
        return transacaoRepository.findById(transacaoId).orElse(null);
    }

    // Listar todas as transações de um cartão
    public List<Transacao> listarTransacoesDoCartao(int cartaoId) throws Exception {
        Cartao cartao = cartaoRepository.findById(cartaoId)
            .orElseThrow(() -> new Exception("Cartão não encontrado com ID: " + cartaoId));
        
        return cartao.getTransacoes();
    }

    // Atualizar uma transação existente
    public Transacao atualizarTransacao(int transacaoId, Transacao transacaoAtualizada) throws Exception {
        Transacao transacaoExistente = transacaoRepository.findById(transacaoId)
            .orElseThrow(() -> new Exception("Transação não encontrada com ID: " + transacaoId));

        transacaoExistente.setValor(transacaoAtualizada.getValor());
        transacaoExistente.setEstabelecimento(transacaoAtualizada.getEstabelecimento());
        transacaoExistente.setDataTransacao(transacaoAtualizada.getDataTransacao());

        return transacaoRepository.save(transacaoExistente);
    }

    // Deletar uma transação
    public void deletarTransacao(int transacaoId) throws Exception {
        Transacao transacao = transacaoRepository.findById(transacaoId)
            .orElseThrow(() -> new Exception("Transação não encontrada com ID: " + transacaoId));

        transacaoRepository.delete(transacao);
    }

    private void verificarAntifraude(Cartao cartao, double valor, String comerciante) throws Exception {

        // Valida se o cartão tem transações nos ultimos 3 minutos
        LocalDateTime localDateTime = LocalDateTime.now().minus(TRANSACTION_TIME_INTERVAL, ChronoUnit.MINUTES);

        List<Transacao> ultimasTransacoes = cartao
                .getTransacoes()
                .stream()
                .filter(x -> x.getDataTransacao().isAfter(localDateTime))
                .toList();
    
        if (ultimasTransacoes.size() >= 3) {
            throw new Exception("Cartão utilizado muitas vezes em um período curto");
        }

    }
  
}
