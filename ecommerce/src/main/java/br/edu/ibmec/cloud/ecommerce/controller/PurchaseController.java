package br.edu.ibmec.cloud.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Purchase> getPurchaseById(@RequestParam String accountId) {
        Purchase purchase = this.service.findById(accountId);
        return new ResponseEntity<>(purchase, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") String id) throws Exception {
        this.service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }  
}
