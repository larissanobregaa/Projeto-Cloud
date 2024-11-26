package br.edu.ibmec.cloud.ecommerce.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.ibmec.cloud.ecommerce.entity.Product;
import br.edu.ibmec.cloud.ecommerce.service.ProductService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class ProductController {

    @Autowired
    private ProductService service;

    // Criar um novo produto
    @PostMapping
    public ResponseEntity<Product> create(@Valid @RequestBody Product product) {
        this.service.save(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    // Buscar produtos por categoria
    @GetMapping(value = "/category/{category}")
    public ResponseEntity<List<Product>> getByCategory(@PathVariable("category") String category) {
        List<Product> products = this.service.findByCategory(category);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // Buscar produtos por nome
    @GetMapping(params = "productName")
    public ResponseEntity<List<Product>> getByProductName(@RequestParam String productName) {
        List<Product> products = this.service.findProductByName(productName);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

}
