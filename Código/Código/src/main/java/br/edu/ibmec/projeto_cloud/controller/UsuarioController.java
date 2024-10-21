package br.edu.ibmec.projeto_cloud.controller;

import br.edu.ibmec.projeto_cloud.exception.UsuarioException;
import br.edu.ibmec.projeto_cloud.model.Cartao;
import br.edu.ibmec.projeto_cloud.model.Endereco; // Importando a nova classe Endereco
import br.edu.ibmec.projeto_cloud.model.Usuario;
import br.edu.ibmec.projeto_cloud.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping
    public ResponseEntity<List<Usuario>> getUsuario() {
        return new ResponseEntity<>(service.getAllItems(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable("id") int id) {
        try {
            Usuario response = service.getItem(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UsuarioException e) {
            return ResponseEntity.notFound().build(); // Retorna 404 se o usuário não for encontrado
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build(); // Retorna 500 em caso de erro interno
        }
    }

    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario novoUsuario = service.criarUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable int id, @RequestBody Usuario usuarioAtualizado) {
        try {
            Usuario usuario = service.atualizarUsuario(id, usuarioAtualizado);
            return ResponseEntity.ok(usuario);
        } catch (UsuarioException e) {
            return ResponseEntity.notFound().build(); // Retorna 404 se o usuário não for encontrado
        } catch (Exception e) {
            return ResponseEntity.badRequest().build(); // Retorna 400 em caso de erro de validação
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable int id) {
        try {
            service.deletarUsuario(id);
            return ResponseEntity.noContent().build();
        } catch (UsuarioException e) {
            return ResponseEntity.notFound().build(); // Retorna 404 se o usuário não for encontrado
        } catch (Exception e) {
            return ResponseEntity.badRequest().build(); // Retorna 400 em caso de erro
        }
    }

    @PostMapping("/{id}/cartoes")
    public ResponseEntity<Void> associarCartao(@PathVariable int id, @RequestBody Cartao cartao, @RequestParam double valorTransacao) {
        try {
            service.associarCartao(cartao, id, valorTransacao);
            return ResponseEntity.ok().build();
        } catch (UsuarioException e) {
            return ResponseEntity.badRequest().build(); // Retorna 400 em caso de erro na associação do cartão
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build(); // Retorna 500 em caso de erro interno
        }
    }

    @GetMapping("/{id}/cartoes")
    public ResponseEntity<List<Cartao>> verCartoesAssociados(@PathVariable int id) {
        try {
            List<Cartao> cartoes = service.verCartoesAssociados(id);
            return ResponseEntity.ok(cartoes);
        } catch (UsuarioException e) {
            return ResponseEntity.notFound().build(); // Retorna 404 se o usuário não for encontrado
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build(); // Retorna 500 em caso de erro interno
        }
    }

    // Método para adicionar um endereço a um usuário
    @PostMapping("/{id}/enderecos")
    public ResponseEntity<Void> associarEndereco(@PathVariable int id, @RequestBody Endereco endereco) {
        try {
            service.associarEndereco(endereco, id);
            return ResponseEntity.ok().build();
        } catch (UsuarioException e) {
            return ResponseEntity.badRequest().build(); // Retorna 400 em caso de erro na associação do endereço
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build(); // Retorna 500 em caso de erro interno
        }
    }

    // Método para listar endereços associados a um usuário
    @GetMapping("/{id}/enderecos")
    public ResponseEntity<List<Endereco>> verEnderecosAssociados(@PathVariable int id) {
        try {
            List<Endereco> enderecos = service.verEnderecosAssociados(id);
            return ResponseEntity.ok(enderecos);
        } catch (UsuarioException e) {
            return ResponseEntity.notFound().build(); // Retorna 404 se o usuário não for encontrado
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build(); // Retorna 500 em caso de erro interno
        }
    }
}
