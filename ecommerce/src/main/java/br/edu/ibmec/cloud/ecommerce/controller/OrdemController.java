package br.edu.ibmec.cloud.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ibmec.cloud.ecommerce.entity.Ordem;
import br.edu.ibmec.cloud.ecommerce.service.OrdemService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/ordens")
public class OrdemController {
    @Autowired
    private OrdemService ordemService; 

    @PostMapping
    public ResponseEntity<Ordem> create(@Valid @RequestBody Ordem ordem) {
        this.ordemService.save(ordem);
        return new ResponseEntity<>(ordem, HttpStatus.CREATED);
    }

    @GetMapping(value = "/ordemId/{id}")
    public ResponseEntity<Ordem> getByProdutoId(@PathVariable("produtoId") String id) {
        Ordem ordem = this.ordemService.findByOrdemId(id);

        return new ResponseEntity<>(ordem, HttpStatus.OK);
    }
}