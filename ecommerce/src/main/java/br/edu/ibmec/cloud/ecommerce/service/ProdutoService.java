package br.edu.ibmec.cloud.ecommerce.service;

import br.edu.ibmec.cloud.ecommerce.entity.Produto;
import br.edu.ibmec.cloud.ecommerce.repository.ProdutoRepository;
import com.azure.cosmos.models.PartitionKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> findByNomeProduto(String nomeProduto) {
        return this.produtoRepository.findByNomeProduto(nomeProduto);
    }

    public Optional<Produto> findByProdutoId(String produtoId) {
        return this.produtoRepository.findById(produtoId);
    }

    public List<Produto> buscarTodosProdutos() {
        return (List<Produto>) produtoRepository.findAll();
    }

    public void save(Produto produto) {
        produto.setProdutoId(UUID.randomUUID().toString());
        this.produtoRepository.save(produto);
    }

    public void delete(String produtoId) throws Exception {

        Optional<Produto> optProduto = this.produtoRepository.findById(produtoId);

        if (optProduto.isPresent() == false)
            throw new Exception("Não encontrei o produto a ser excluido");

        this.produtoRepository.deleteById(produtoId, new PartitionKey(optProduto.get().getCategoriaProduto()));
    }

    public List<Produto> findByCategoriaProduto(String categoriaProduto) {
        return this.produtoRepository.findByCategoriaProduto(categoriaProduto);
    }
}
