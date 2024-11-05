package br.edu.ibmec.projeto_cloud;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.edu.ibmec.projeto_cloud.model.Endereco;
import br.edu.ibmec.projeto_cloud.repository.EnderecoRepository;
import br.edu.ibmec.projeto_cloud.service.EnderecoService;

public class EnderecoServiceTest {

    @InjectMocks
    private EnderecoService enderecoService;

    @Mock
    private EnderecoRepository enderecoRepository;

    private Endereco endereco;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        endereco = new Endereco();
        endereco.setId(1);
        endereco.setRua("Rua A");
        endereco.setNumero("123");
        endereco.setBairro("Bairro B");
        endereco.setCidade("Cidade C");
        endereco.setEstado("Estado D");
        endereco.setCep("12345-678");
    }

    // Testa a criação de um endereço
    @Test
    public void testCriarEndereco() {
        when(enderecoRepository.save(any(Endereco.class))).thenReturn(endereco);

        Endereco resultado = enderecoService.criarEndereco(endereco);

        assertNotNull(resultado);
        assertEquals("Rua A", resultado.getRua());
    }

    // Testa a criação de um endereço com campos nulos
    @Test
    public void testCriarEnderecoComCamposNulos() {
        Endereco enderecoInvalido = new Endereco();
        enderecoInvalido.setRua(null); // Simula um endereço com campo rua nulo

        Exception exception = assertThrows(Exception.class, () -> {
            enderecoService.criarEndereco(enderecoInvalido);
        });

        assertEquals("Rua não pode ser nula", exception.getMessage()); // Ajuste a mensagem conforme sua implementação
    }

    // Testa a busca de um endereço por ID
    @Test
    public void testGetEnderecoById() {
        when(enderecoRepository.findById(1)).thenReturn(Optional.of(endereco));

        Endereco resultado = enderecoService.getEnderecoById(1);

        assertNotNull(resultado);
        assertEquals("Rua A", resultado.getRua());
    }

    // Testa a busca de um endereço com ID inválido
    @Test
    public void testGetEnderecoByIdInvalido() {
        when(enderecoRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            enderecoService.getEnderecoById(1);
        });

        assertEquals("Endereço não encontrado", exception.getMessage()); // Ajuste a mensagem conforme sua implementação
    }

    // Testa a atualização de um endereço
    @Test
    public void testAtualizarEndereco() throws Exception {
        when(enderecoRepository.findById(1)).thenReturn(Optional.of(endereco));
        Endereco enderecoAtualizado = new Endereco();
        enderecoAtualizado.setRua("Rua B");

        when(enderecoRepository.save(any(Endereco.class))).thenReturn(enderecoAtualizado);

        Endereco resultado = enderecoService.atualizarEndereco(1, enderecoAtualizado);

        assertNotNull(resultado);
        assertEquals("Rua B", resultado.getRua());
    }

    // Testa a atualização de um endereço com ID inválido
    @Test
    public void testAtualizarEnderecoInvalido() {
        when(enderecoRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            enderecoService.atualizarEndereco(1, endereco);
        });

        assertEquals("Endereço não encontrado", exception.getMessage()); // Ajuste a mensagem conforme sua implementação
    }

    // Testa a exclusão de um endereço
    @Test
    public void testDeletarEndereco() throws Exception {
        when(enderecoRepository.findById(1)).thenReturn(Optional.of(endereco));

        enderecoService.deletarEndereco(1);

        verify(enderecoRepository, times(1)).delete(endereco);
    }

    // Testa a exclusão de um endereço que não existe
    @Test
    public void testDeletarEnderecoInvalido() {
        when(enderecoRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            enderecoService.deletarEndereco(1);
        });

        assertEquals("Endereço não encontrado", exception.getMessage()); // Ajuste a mensagem conforme sua implementação
    }
}
