package br.edu.ibmec.projeto_cloud.service;

import br.edu.ibmec.projeto_cloud.model.Cartao;
import br.edu.ibmec.projeto_cloud.model.Transacao;
import br.edu.ibmec.projeto_cloud.model.Usuario;
import br.edu.ibmec.projeto_cloud.repository.CartaoRepository;
import br.edu.ibmec.projeto_cloud.repository.TransacaoRepository;

import java.time.LocalDateTime;
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
    public Transacao criarTransacao(int cartaoId, Transacao transacao) throws Exception {
        Cartao cartao = cartaoRepository.findById(cartaoId)
            .orElseThrow(() -> new Exception("Cartão não encontrado com ID: " + cartaoId));

        if (!cartao.isAtivo()){
            throw new Exception("Transação não permitida: Cartão Inativo.");
        }
        
        Usuario usuario = cartao.getUsuario();  // Acessa o usuário dono do cartão
        validarFrequenciaTransacoes(usuario);   
        validarTransacaoDuplicada(usuario, transacao);

        // Atribui o cartão à transação e salva no banco
        transacao.setCartao(cartao);
        return transacaoRepository.save(transacao);
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
  
}
