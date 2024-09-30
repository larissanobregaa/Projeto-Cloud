package br.edu.ibmec.projeto_cloud.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ibmec.projeto_cloud.dto.CartaoTransacaoDTO;
import br.edu.ibmec.projeto_cloud.model.Cartao;
import br.edu.ibmec.projeto_cloud.model.Transacao;
import br.edu.ibmec.projeto_cloud.model.Usuario;
import br.edu.ibmec.projeto_cloud.service.UsuarioService;
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

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    
    @Autowired
    private UsuarioService service; // Padronize o nome como 'service'

    @GetMapping
    public ResponseEntity<List<Usuario>> getUsuario() {
    List<Usuario> usuarios = service.getAllItems();
    if (usuarios.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(usuarios, HttpStatus.OK);
        }

    @GetMapping("{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable("id") int id){
        Usuario response = service.getItem(id);

        if (response == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> criarUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario novoUsuario = service.criarUsuario(usuario); // Use 'service' ao invés de 'usuarioService'
            return ResponseEntity.ok(novoUsuario);
        } catch (Exception e) {
            // Retorna uma mensagem de erro com a exceção capturada
            return ResponseEntity.badRequest().body("Erro ao criar usuário: " + e.getMessage());
        }
    }
    

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable int id, @RequestBody Usuario usuarioAtualizado) {
        try {
            Usuario usuario = service.atualizarUsuario(id, usuarioAtualizado); // Use 'service'
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable int id) {
        try {
            service.deletarUsuario(id); // Use 'service'
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/cartoes")
public ResponseEntity<Void> associarCartao(@PathVariable int id, @RequestBody CartaoTransacaoDTO cartaoTransacaoDTO) {
    try {
        Cartao cartao = cartaoTransacaoDTO.getCartao();
        Transacao transacao = cartaoTransacaoDTO.getTransacao();
        service.associarCartao(cartao, id, transacao); // Chame o serviço com os objetos deserializados
        return ResponseEntity.ok().build();
    } catch (Exception e) {
        return ResponseEntity.badRequest().build();
    }
}


    @GetMapping("/{id}/cartoes")
    public ResponseEntity<List<Cartao>> verCartoesAssociados(@PathVariable int id) {
        try {
            List<Cartao> cartoes = service.verCartoesAssociados(id); // Use 'service'
            return ResponseEntity.ok(cartoes);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
