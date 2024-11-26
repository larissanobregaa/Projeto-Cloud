package br.edu.ibmec.cloud.ecommerce.controller;

import br.edu.ibmec.cloud.ecommerce.entity.Ordem;
import br.edu.ibmec.cloud.ecommerce.entity.Produto;
import br.edu.ibmec.cloud.ecommerce.errorHandler.CheckoutException;
import br.edu.ibmec.cloud.ecommerce.request.CheckoutRequest;
import br.edu.ibmec.cloud.ecommerce.request.CheckoutResponse;
import br.edu.ibmec.cloud.ecommerce.service.CheckoutService;
import br.edu.ibmec.cloud.ecommerce.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    @Autowired
    private CheckoutService service;

    @Autowired
    private ProdutoService productService;

    @PostMapping
    public ResponseEntity<CheckoutResponse> checkout(@RequestBody CheckoutRequest request) throws Exception{
        
        Optional<Produto> optProduct = this.productService.findByProdutoId(request.getProdutoId());

        if (optProduct.isPresent() == false)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Produto produto = optProduct.get();
        CheckoutResponse response = new CheckoutResponse();
        
        try {
            Ordem ordem = this.service.checkout(request.getUsuarioId(), produto, request.getCartaoId(), request.getDataTransacao());
            response.setDataTransacao(ordem.getDataTransacao());
            response.setProdutoId(ordem.getProdutoId());
            response.setStatus(ordem.getStatus());
            response.setOrdemId(ordem.getOrdemId());
            response.setErro(null);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (CheckoutException e) {
            response.setDataTransacao(request.getDataTransacao());
            response.setProdutoId(request.getProdutoId());
            response.setStatus("REPROVADO");
            response.setErro(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
