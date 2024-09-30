package br.edu.ibmec.projeto_cloud;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.edu.ibmec.projeto_cloud.model.Cartao;
import br.edu.ibmec.projeto_cloud.model.Transacao;
import br.edu.ibmec.projeto_cloud.model.Usuario;
import br.edu.ibmec.projeto_cloud.repository.CartaoRepository;
import br.edu.ibmec.projeto_cloud.repository.UsuarioRepository;
import br.edu.ibmec.projeto_cloud.service.CartaoService;
import br.edu.ibmec.projeto_cloud.service.TransacaoService;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@SpringBootTest
public class CartaoServiceTest {
    @Autowired
    private CartaoService cartaoService;

    @MockBean
    private CartaoRepository cartaoRepository;

    @MockBean
    private UsuarioRepository usuarioRepository;
// Testa a criação de um cartão
    @Test
    public void testCriarCartao() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        
        Cartao cartao = new Cartao();
        cartao.setNumeroCartao("1234-5678-9101-1121");

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));
        when(cartaoRepository.save(any(Cartao.class))).thenReturn(cartao);

        Cartao resultado = cartaoService.criarCartao(1, cartao);

        assertNotNull(resultado);
        assertEquals("1234-5678-9101-1121", resultado.getNumeroCartao());
    }


// Testa a atualização de um cartão
@Test
public void testAtualizarCartao() throws Exception {
    Cartao cartaoExistente = new Cartao();
    cartaoExistente.setId(1);
    cartaoExistente.setNumeroCartao("1234-5678-9101-1121");

    Cartao cartaoAtualizado = new Cartao();
    cartaoAtualizado.setNumeroCartao("4321-8765-1019-2111");

    when(cartaoRepository.findById(1)).thenReturn(Optional.of(cartaoExistente));
    when(cartaoRepository.save(any(Cartao.class))).thenReturn(cartaoAtualizado);

    Cartao resultado = cartaoService.atualizarCartao(1, cartaoAtualizado);

    assertNotNull(resultado);
    assertEquals("4321-8765-1019-2111", resultado.getNumeroCartao());
}

// Testa a exclusão de um cartão
@Test
public void testDeletarCartao() throws Exception {
    Cartao cartao = new Cartao();
    cartao.setId(1);

    when(cartaoRepository.findById(1)).thenReturn(Optional.of(cartao));

    cartaoService.deletarCartao(1);

    verify(cartaoRepository, times(1)).delete(cartao);
}

// Testa a validação de transação duplicada
@Test
public void testValidarTransacaoDuplicada() throws Exception {
    Usuario usuario = new Usuario();
    Transacao transacao = new Transacao();
    transacao.setValor(100.00);
    transacao.setEstabelecimento("Loja X");

    Transacao transacaoDuplicada = new Transacao();
    transacaoDuplicada.setValor(100.00);
    transacaoDuplicada.setEstabelecimento("Loja X");

    Cartao cartao = new Cartao();
    cartao.setTransacoes(Arrays.asList(transacao));
    usuario.setCartoes(Arrays.asList(cartao));

    Transacao novaTransacao = new Transacao();
    novaTransacao.setValor(100.00);
    novaTransacao.setEstabelecimento("Loja X");

    TransacaoService service = new TransacaoService();
    assertThrows(Exception.class, () -> {
        service.validarTransacaoDuplicada(usuario, novaTransacao);
    });
}
}