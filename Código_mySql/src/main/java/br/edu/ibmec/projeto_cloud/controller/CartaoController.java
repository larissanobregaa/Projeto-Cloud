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

import br.edu.ibmec.projeto_cloud.model.Cartao;
import br.edu.ibmec.projeto_cloud.service.CartaoService;

@RestController
@RequestMapping("/cartao")
public class CartaoController {

    @Autowired
    private CartaoService cartaoService;

    // Criar um novo cartão associado a um usuário
    @PostMapping("/usuarios/{usuarioId}/criar")
    public ResponseEntity<Cartao> criarCartao(@PathVariable int usuarioId, @RequestBody Cartao cartao) throws Exception {
        Cartao novoCartao = cartaoService.criarCartao(usuarioId, cartao);
        return new ResponseEntity<>(novoCartao, HttpStatus.CREATED);
    }

    @PostMapping("/usuarios/{usuarioId}/associar")
    public ResponseEntity<Cartao> associarCartao(@PathVariable int usuarioId, @RequestBody Cartao cartao) {
        Cartao cartaoAssociado = cartaoService.associarCartao(usuarioId, cartao);
        return new ResponseEntity<>(cartaoAssociado, HttpStatus.CREATED);
    }

    // Buscar um cartão por ID
    @GetMapping("/cartoes/{cartaoId}")
    public ResponseEntity<Cartao> buscarCartaoPorId(@PathVariable int cartaoId) {
        Cartao cartao = cartaoService.buscarCartaoPorId(cartaoId);
        if (cartao == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cartao, HttpStatus.OK);
    }

    // Atualizar os dados de um cartão
    @PutMapping("/cartoes/{cartaoId}")
    public ResponseEntity<Cartao> atualizarCartao(@PathVariable int cartaoId, @RequestBody Cartao cartaoAtualizado) {
        try {
            Cartao cartao = cartaoService.atualizarCartao(cartaoId, cartaoAtualizado);
            return new ResponseEntity<>(cartao, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Deletar um cartão por ID
    @DeleteMapping("/cartoes/{cartaoId}")
    public ResponseEntity<Void> deletarCartao(@PathVariable int cartaoId) {
        try {
            cartaoService.deletarCartao(cartaoId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Listar todos os cartões de um usuário
    @GetMapping("/usuarios/{usuarioId}/cartoes")
    public ResponseEntity<?> listarCartoesDoUsuario(@PathVariable int usuarioId) {
        try {
            List<Cartao> cartoes = cartaoService.listarCartoesDoUsuario(usuarioId);
            return new ResponseEntity<>(cartoes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao listar cartões: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Ativar cartão (se permitido)
    @PutMapping("/cartoes/{cartaoId}/ativar")
    public ResponseEntity<String> ativarCartao(@PathVariable int cartaoId) {
        try {
            cartaoService.ativarCartao(cartaoId);
            return new ResponseEntity<>("Cartão ativado com sucesso.", HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao ativar cartão: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Desativar cartão
    @PutMapping("/cartoes/{cartaoId}/desativar")
    public ResponseEntity<String> desativarCartao(@PathVariable int cartaoId) {
        try {
            cartaoService.desativarCartao(cartaoId);
            return new ResponseEntity<>("Cartão desativado com sucesso.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao desativar cartão: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

