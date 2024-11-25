package br.edu.ibmec.cloud.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ibmec.cloud.ecommerce.entity.Purchase;
import br.edu.ibmec.cloud.ecommerce.service.PurchaseService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/compras")
public class PurchaseController {

    @Autowired
    private PurchaseService service;

    @PostMapping
    public ResponseEntity<Purchase> create(@Valid @RequestBody Purchase purchase) {
        this.service.save(purchase);
        return new ResponseEntity<>(purchase, HttpStatus.CREATED);
    }

     @GetMapping
    public ResponseEntity<Purchase> getPurchaseByUsuarioId(@RequestParam String usuarioId) {
        Purchase purchase = this.service.findByUsuarioId(usuarioId);
        return new ResponseEntity<>(purchase, HttpStatus.OK);
    }
}
