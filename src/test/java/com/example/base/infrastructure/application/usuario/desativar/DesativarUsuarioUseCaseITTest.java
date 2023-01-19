package com.example.base.infrastructure.application.usuario.desativar;

import com.example.base.application.usuario.desativar.DesativarUsuarioCommand;
import com.example.base.application.usuario.desativar.DesativarUsuarioUseCase;
import com.example.base.application.usuario.exception.NotFoundException;
import com.example.base.domain.usuario.Usuario;
import com.example.base.infrastructure.IntegrationTest;
import com.example.base.infrastructure.usuario.persistence.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@IntegrationTest
public class DesativarUsuarioUseCaseITTest {

    @Autowired
    private DesativarUsuarioUseCase desativarUsuarioUseCase;
    @Autowired
    private UsuarioRepository usuarioRepository;


    @Test
    public void dadoUmCommandValido_quandoExecutarDesativarUsuario_deveRetornarUsuarioDesativado() {
        //setup
        final var nomeEsperado = "dummy-nome";
        final var emailEsperado = "dummy@email.com";
        final var senhaEsperada = "dummy-senha";
        final var usuario = Usuario.newUsuario(nomeEsperado, emailEsperado, senhaEsperada);

        //execute
        assertEquals(0, usuarioRepository.count());

        final var novoUsuario = usuarioRepository.save(usuario);

        assertNull(novoUsuario.getDataExclusao());

        final var output = desativarUsuarioUseCase
                .execute(DesativarUsuarioCommand.with(novoUsuario.getId()));

        //verify
        assertEquals(1, usuarioRepository.count());
        assertNotNull(output.getId());
        assertNotNull(output.getDataExclusao());
        assertEquals(novoUsuario.getDataCriacao(), output.getDataCriacao());
        assertTrue(output.getDataCriacao().isBefore(output.getDataExclusao()));
    }

    @Test
    public void dadoUmCommandInvalidoComIdInexistente_quandoExecutarDesativarUsuario_deveRetornarUmaException() {
        //setup
        final var id = "dummy-id-nao-existe";
        final var command = DesativarUsuarioCommand.with(id);
        final var mensagemErroEsperada = "Usuario com ID dummy-id-nao-existe não foi encontrado.";

        //execute
        final var output = assertThrows(NotFoundException.class,
                () -> desativarUsuarioUseCase.execute(command));

        //verify
        assertEquals(output.getMessage(), mensagemErroEsperada);
    }

}
