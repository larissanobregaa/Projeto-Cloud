package br.edu.ibmec.projeto_cloud.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ibmec.projeto_cloud.exception.UsuarioException;
import br.edu.ibmec.projeto_cloud.model.Cartao;
import br.edu.ibmec.projeto_cloud.model.Transacao;
import br.edu.ibmec.projeto_cloud.model.Usuario;
import br.edu.ibmec.projeto_cloud.request.TransacaoRequest;
import br.edu.ibmec.projeto_cloud.request.TransacaoResponse;
import br.edu.ibmec.projeto_cloud.service.TransacaoService;
import br.edu.ibmec.projeto_cloud.service.UsuarioService;

@RestController
@RequestMapping("/autorizar")
public class TransacaoController {
    @Autowired
    private TransacaoService transacaoService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping()
    public ResponseEntity<TransacaoResponse> transacionar(@RequestBody TransacaoRequest request) throws Exception {

        // Buscando o usuario
        Usuario user = this.usuarioService.buscarUsuario(request.getIdUsuario());

        if (user == null)
            throw new UsuarioException("Usuario não encontrado, verifique o identificador");

        Optional<Cartao> optCartao = user.getCartoes()
                .stream()
                .filter(x -> x.numeroCartao.equals(request.getNumeroCartao()))
                .findFirst();
        // Não achei o cartao de credito do usuário
        if (optCartao.isPresent() == false)
            throw new UsuarioException(
                    "Não encontrei o cartão associado a esse usuário com o numero " + request.getNumeroCartao());

        // Cartao do usuário
        Cartao cartao = optCartao.get();
        TransacaoResponse response = new TransacaoResponse();

        try {
            Transacao transacao = this.transacaoService.criarTransacao(cartao, request.getValor(),
                    request.getEstabelecimento());
            // Objeto de resposta do usuario
            response.setDataTransacao(transacao.getDataTransacao());
            response.setStatus("APROVADO");
            response.setValor(transacao.getValor());
            response.setCodigoAutorizacao(UUID.randomUUID());

        } catch (Exception e) {
            response.setStatus("REPROVADO:" + e.getMessage());
            response.setDataTransacao(LocalDateTime.now());
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<List<Transacao>> extratoCartao(
        @PathVariable("id") int id, 
        @RequestParam ("numeroCartao") String numeroCartao) throws Exception {

        Usuario user = this.usuarioService.buscarUsuario(id);

        if (user == null)
            throw new UsuarioException("Usuario não encontrado, verifique o identificador");

        Optional<Cartao> optCartao = user.getCartoes()
                .stream()
                .filter(x -> x.numeroCartao.equals(numeroCartao))
                .findFirst();

        // Não achei o cartao de credito do usuário
        if (optCartao.isPresent() == false)
            throw new UsuarioException(
                    "Não encontrei o cartão associado a esse usuário com o numero " + numeroCartao);

        return new ResponseEntity<>(optCartao.get().getTransacoes(), HttpStatus.OK);                    
    }

}
