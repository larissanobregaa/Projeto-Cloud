package br.edu.ibmec.projeto_cloud.controller;

import br.edu.ibmec.projeto_cloud.exception.UsuarioException;
import br.edu.ibmec.projeto_cloud.model.Endereco;
import br.edu.ibmec.projeto_cloud.service.EnderecoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    // Busca todos os endereços
    @GetMapping
    public ResponseEntity<List<Endereco>> getAllEnderecos() {
        List<Endereco> enderecos = enderecoService.getAllEnderecos();
        return new ResponseEntity<>(enderecos, HttpStatus.OK);
    }

    // Busca um endereço por ID
    @GetMapping("/{id}")
    public ResponseEntity<Endereco> getEnderecoById(@PathVariable("id") int id) {
        try {
            Endereco endereco = enderecoService.getEnderecoById(id);
            return new ResponseEntity<>(endereco, HttpStatus.OK);
        } catch (UsuarioException e) {
            return ResponseEntity.notFound().build(); // Retorna 404 se o endereço não for encontrado
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build(); // Retorna 500 em caso de erro interno
        }
    }

    // Cria um novo endereço
    @PostMapping
    public ResponseEntity<Endereco> criarEndereco(@RequestBody Endereco endereco) {
        try {
            Endereco novoEndereco = enderecoService.criarEndereco(endereco);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoEndereco);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build(); // Retorna 400 em caso de erro
        }
    }

    // Atualiza um endereço existente
    @PutMapping("/{id}")
    public ResponseEntity<Endereco> atualizarEndereco(@PathVariable int id, @RequestBody Endereco enderecoAtualizado) {
        try {
            Endereco endereco = enderecoService.atualizarEndereco(id, enderecoAtualizado);
            return ResponseEntity.ok(endereco);
        } catch (UsuarioException e) {
            return ResponseEntity.notFound().build(); // Retorna 404 se o endereço não for encontrado
        } catch (Exception e) {
            return ResponseEntity.badRequest().build(); // Retorna 400 em caso de erro
        }
    }

    // Deleta um endereço por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEndereco(@PathVariable int id) {
        try {
            enderecoService.deletarEndereco(id);
            return ResponseEntity.noContent().build();
        } catch (UsuarioException e) {
            return ResponseEntity.notFound().build(); // Retorna 404 se o endereço não for encontrado
        } catch (Exception e) {
            return ResponseEntity.badRequest().build(); // Retorna 400 em caso de erro
        }
    }
}
