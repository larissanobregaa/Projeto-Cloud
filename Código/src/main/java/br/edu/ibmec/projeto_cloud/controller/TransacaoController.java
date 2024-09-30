package br.edu.ibmec.projeto_cloud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.edu.ibmec.projeto_cloud.model.Transacao;
import br.edu.ibmec.projeto_cloud.service.TransacaoService;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {
  @Autowired
  private TransacaoService transacaoService;

  @PostMapping("/cartoes/{cartaoId}/criar")
public ResponseEntity<Transacao> criarTransacao(@PathVariable int cartaoId, @RequestBody Transacao transacao) throws Exception {
   
        Transacao novaTransacao = transacaoService.criarTransacao(cartaoId, transacao);
        
        return new ResponseEntity<>(novaTransacao, HttpStatus.CREATED);
   
  }

  // Buscar uma transação por ID
    @GetMapping("/{transacaoId}")
    public ResponseEntity<Transacao> buscarTransacaoPorId(@PathVariable int transacaoId) {
        Transacao transacao = transacaoService.buscarTransacaoPorId(transacaoId);
        if (transacao == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(transacao, HttpStatus.OK);
    }

    // Listar todas as transações de um cartão
    @GetMapping("/cartoes/{cartaoId}")
    public ResponseEntity<List<Transacao>> listarTransacoesDoCartao(@PathVariable int cartaoId) {
        try {
            List<Transacao> transacoes = transacaoService.listarTransacoesDoCartao(cartaoId);
            return new ResponseEntity<>(transacoes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Atualizar uma transação existente
    @PutMapping("/{transacaoId}")
    public ResponseEntity<Transacao> atualizarTransacao(@PathVariable int transacaoId, @RequestBody Transacao transacaoAtualizada) {
        try {
            Transacao transacao = transacaoService.atualizarTransacao(transacaoId, transacaoAtualizada);
            return new ResponseEntity<>(transacao, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Deletar uma transação por ID
    @DeleteMapping("/{transacaoId}")
    public ResponseEntity<Void> deletarTransacao(@PathVariable int transacaoId) {
        try {
            transacaoService.deletarTransacao(transacaoId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
