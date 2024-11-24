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

    // Salvar ou atualizar um produto
    public Product save(Product product) {
        return repository.save(product);
    }

    // Buscar todos os produtos
    public List<Product> findAll() {
        return repository.findAll();
    }

    // Buscar produto por categoria
    public List<Product> findByCategory(String category) {
        return repository.findByProductCategory(category);
    }

    // Buscar produto por nome
    public List<Product> findProductByName(String productName) {
        return repository.findByProductNameContainingIgnoreCase(productName);
    }

    // Buscar produto pelo ID
    public Optional<Product> findById(String id) {
        return repository.findById(id);
    }

    // Deletar produto pelo ID
    public void delete(String id) throws Exception {
        if (!repository.existsById(id)) {
            throw new Exception("Produto não encontrado.");
        }
        repository.deleteById(id);
    }
}