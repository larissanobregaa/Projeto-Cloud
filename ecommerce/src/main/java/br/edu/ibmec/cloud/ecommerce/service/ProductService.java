package br.edu.ibmec.cloud.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ibmec.cloud.ecommerce.entity.Product;
import br.edu.ibmec.cloud.ecommerce.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public Product save(Product product) {
        return repository.save(product);
    }

    public List<Product> findAll() {
        return repository.findAll();
    }

    public List<Product> findByCategory(String category) {
        return repository.findByProductCategory(category);
    }

    public List<Product> findProductByName(String productName) {
        return repository.findByProductNameContainingIgnoreCase(productName);
    }

    public Optional<Product> findById(String id) {
        System.out.println("Buscando produto com ID: " + id);
        return repository.findById(id);
    }

    public void delete(String id) throws Exception {
        if (!repository.existsById(id)) {
            throw new Exception("Produto não encontrado.");
        }
        repository.deleteById(id);
    }
}