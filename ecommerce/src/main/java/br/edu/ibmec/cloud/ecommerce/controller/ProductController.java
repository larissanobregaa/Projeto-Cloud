package br.edu.ibmec.cloud.ecommerce.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.ibmec.cloud.ecommerce.entity.Product;
import br.edu.ibmec.cloud.ecommerce.service.ProductService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    // POST: Criar um novo produto
    @PostMapping
    public ResponseEntity<Product> create(@Valid @RequestBody Product product) {
        this.service.save(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    // GET: Buscar todos os produtos
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = this.service.findAll(); // Método findAll no ProductService
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // GET: Buscar produtos por categoria
    @GetMapping(value = "/category/{category}")
    public ResponseEntity<List<Product>> getByCategory(@PathVariable("category") String category) {
        List<Product> products = this.service.findByCategory(category);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // GET: Buscar produtos por nome
    @GetMapping(params = "productName")
    public ResponseEntity<List<Product>> getByProductName(@RequestParam String productName) {
        List<Product> products = this.service.findProductByName(productName);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // DELETE: Deletar um produto pelo ID
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) throws Exception {
        this.service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // PUT: Atualizar um produto pelo ID
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") String id, @Valid @RequestBody Product productDetails) {
        Optional<Product> optionalProduct = this.service.findById(id); // Método findById no ProductService

        // Verifica se o produto existe
        return optionalProduct.map(product -> {
            product.setProductName(productDetails.getProductName());
            product.setProductCategory(productDetails.getProductCategory());
            product.setPrice(productDetails.getPrice());
            product.setUrlFoto(productDetails.getUrlFoto());
            product.setProductDescription(productDetails.getProductDescription());
            product.setStock(productDetails.getStock());

            Product updatedProduct = this.service.save(product); // Atualiza e salva o produto
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // Retorna 404 se o produto não existir
    }
}
