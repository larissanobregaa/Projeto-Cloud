package br.edu.ibmec.cloud.ecommerce.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.ibmec.cloud.ecommerce.entity.Extrato;
import br.edu.ibmec.cloud.ecommerce.service.ExtratoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/extratos")
public class ExtratoController {

    @Autowired
    private ExtratoService service;

    @PostMapping
    public ResponseEntity<Extrato> create(@Valid @RequestBody Extrato extrato) {
        this.service.save(extrato);
        return new ResponseEntity<>(extrato, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Extrato> getById(@PathVariable("id") String id) {
        Optional<Extrato> extrato = this.service.findById(id);

        if (extrato.isPresent()) {
            return new ResponseEntity<>(extrato.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Extrato>> getByClienteId(@PathVariable("clienteId") String clienteId) {
        List<Extrato> extratos = this.service.findByClienteId(clienteId);
        return new ResponseEntity<>(extratos, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) throws Exception {
        this.service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}