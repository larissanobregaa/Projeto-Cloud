package br.edu.ibmec.projeto_cloud;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.edu.ibmec.projeto_cloud.model.Usuario;
import br.edu.ibmec.projeto_cloud.repository.UsuarioRepository;
import br.edu.ibmec.projeto_cloud.service.UsuarioService;
@SpringBootTest
public class UsuarioServiceTest {
    @Autowired
    private UsuarioService usuarioService;

    @MockBean
    private UsuarioRepository usuarioRepository;

    // Teste para criação de usuário
    @Test
    public void testCriarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNome("João");

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario resultado = usuarioService.criarUsuario(usuario);

        assertNotNull(resultado);
        assertEquals("João", resultado.getNome());
    }

    // Teste para buscar usuário por ID
    @Test
    public void testBuscarUsuarioPorId() {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNome("Maria");

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.getItem(1);

        assertNotNull(resultado);
        assertEquals("Maria", resultado.getNome());
    }

    // Teste para atualizar usuário
    @Test
    public void testAtualizarUsuario() throws Exception {
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId(1);
        usuarioExistente.setNome("João");

        Usuario usuarioAtualizado = new Usuario();
        usuarioAtualizado.setNome("Carlos");

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuarioExistente));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioAtualizado);

        Usuario resultado = usuarioService.atualizarUsuario(1, usuarioAtualizado);

        assertNotNull(resultado);
        assertEquals("Carlos", resultado.getNome());
    }

    // Teste para deletar usuário
    @Test
    public void testDeletarUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1);

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        usuarioService.deletarUsuario(1);

        verify(usuarioRepository, times(1)).delete(usuario);
    }
}

