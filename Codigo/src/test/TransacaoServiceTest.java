package br.edu.ibmec.projeto_cloud;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.edu.ibmec.projeto_cloud.model.Cartao;
import br.edu.ibmec.projeto_cloud.model.Transacao;
import br.edu.ibmec.projeto_cloud.model.Usuario;
import br.edu.ibmec.projeto_cloud.repository.TransacaoRepository;
import br.edu.ibmec.projeto_cloud.service.CartaoService;
import br.edu.ibmec.projeto_cloud.service.TransacaoService;

public class TransacaoServiceTest {

    @InjectMocks
    private TransacaoService transacaoService;

    @Mock
    private TransacaoRepository transacaoRepository;

    @Mock
    private CartaoService cartaoService;

    private Usuario usuario;
    private Cartao cartao;
    private Transacao transacao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        usuario = new Usuario();
        usuario.setId(1);

        cartao = new Cartao();
        cartao.setId(1);
        cartao.setTransacoes(Arrays.asList());

        transacao = new Transacao();
        transacao.setId(1);
        transacao.setValor(100.0);
        transacao.setEstabelecimento("Loja X");
    }

    // Testa a criação de uma transação
    @Test
    public void testCriarTransacao() throws Exception {
        when(cartaoService.buscarCartaoPorId(cartao.getId())).thenReturn(cartao);
        when(transacaoRepository.save(any(Transacao.class))).thenReturn(transacao);

        Transacao resultado = transacaoService.criarTransacao(cartao.getId(), transacao);

        assertNotNull(resultado);
        assertEquals(100.0, resultado.getValor());
        assertEquals("Loja X", resultado.getEstabelecimento());
    }

    // Testa a atualização de uma transação
    @Test
    public void testAtualizarTransacao() throws Exception {
        when(transacaoRepository.findById(1)).thenReturn(Optional.of(transacao));
        Transacao transacaoAtualizada = new Transacao();
        transacaoAtualizada.setValor(200.0);
        transacaoAtualizada.setEstabelecimento("Loja Y");

        when(transacaoRepository.save(any(Transacao.class))).thenReturn(transacaoAtualizada);

        Transacao resultado = transacaoService.atualizarTransacao(1, transacaoAtualizada);

        assertNotNull(resultado);
        assertEquals(200.0, resultado.getValor());
        assertEquals("Loja Y", resultado.getEstabelecimento());
    }

    // Testa a exclusão de uma transação
    @Test
    public void testDeletarTransacao() throws Exception {
        when(transacaoRepository.findById(1)).thenReturn(Optional.of(transacao));

        transacaoService.deletarTransacao(1);

        verify(transacaoRepository, times(1)).delete(transacao);
    }

    // Testa a listagem de transações de um cartão
    @Test
    public void testListarTransacoesDoCartao() {
        cartao.setTransacoes(Arrays.asList(transacao));
        when(cartaoService.listarCartoesDoUsuario(usuario.getId())).thenReturn(Arrays.asList(cartao));

        List<Transacao> transacoes = transacaoService.listarTransacoesDoCartao(cartao.getId());

        assertNotNull(transacoes);
        assertEquals(1, transacoes.size());
        assertEquals(transacao, transacoes.get(0));
    }

    // Testa a validação de transação duplicada
    @Test
    public void testValidarTransacaoDuplicada() throws Exception {
        cartao.setTransacoes(Arrays.asList(transacao));
        usuario.setCartoes(Arrays.asList(cartao));

        Transacao novaTransacao = new Transacao();
        novaTransacao.setValor(100.0);
        novaTransacao.setEstabelecimento("Loja X");

        Exception exception = assertThrows(Exception.class, () -> {
            transacaoService.validarTransacaoDuplicada(usuario, novaTransacao);
        });

        assertEquals("Transação duplicada detectada", exception.getMessage());
    }
}
