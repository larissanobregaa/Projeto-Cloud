package br.edu.ibmec.cloud.ecommerce.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.ibmec.cloud.ecommerce.entity.Produto;
import br.edu.ibmec.cloud.ecommerce.service.ProdutoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @PostMapping
    public ResponseEntity<Produto> create(@Valid @RequestBody Produto produto) {
        this.service.save(produto);
        return new ResponseEntity<>(produto, HttpStatus.CREATED);
    }

    @GetMapping(value = "/categoria/{categoria}")
    public ResponseEntity<List<Produto>> getByCategoriaProduto(@PathVariable("categoria") String categoria) {
        List<Produto> produtos = this.service.findByCategoriaProduto(categoria);

        return new ResponseEntity<>(produtos, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Produto>> getByNomeProduto(@RequestParam String nomeProduto) {
        List<Produto> produtos = this.service.findByNomeProduto(nomeProduto);
        return new ResponseEntity<>(produtos, HttpStatus.OK);
    }

    @GetMapping("/todosProdutos")
    public ResponseEntity<List<Produto>> getAllProdutos() {
        List<Produto> produtos = this.service.buscarTodosProdutos(); 
        return new ResponseEntity<>(produtos, HttpStatus.OK);
    }
}
