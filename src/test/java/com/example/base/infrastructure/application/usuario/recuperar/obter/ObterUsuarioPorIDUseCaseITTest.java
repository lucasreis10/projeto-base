package com.example.base.infrastructure.application.usuario.recuperar.obter;

import com.example.base.application.usuario.exception.NotFoundException;
import com.example.base.application.usuario.recuperar.obter.ObterUsuarioPorIDUseCase;
import com.example.base.domain.usuario.Usuario;
import com.example.base.infrastructure.IntegrationTest;
import com.example.base.infrastructure.usuario.persistence.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@IntegrationTest
public class ObterUsuarioPorIDUseCaseITTest {

    @Autowired
    private ObterUsuarioPorIDUseCase obterUsuarioPorIDUseCase;
    @Autowired
    private UsuarioRepository usuarioRepository;


    @Test
    public void dadoUmCommand_quandoExecutarObterUsuarioPorID_entaoUmUsuarioEhRetornado() {
        // setup:
        final var nomeEsperado = "dummy-nome";
        final var emailEsperado = "dummy@email.com";
        final var usuario = Usuario.newUsuario(nomeEsperado, "dummy-senha", emailEsperado);

        assertEquals(0, usuarioRepository.count());

        final var idUsuario = usuarioRepository.save(usuario).getId();

        // execute:
        final var output = obterUsuarioPorIDUseCase.execute(idUsuario);

        // verify:
        assertEquals(1, usuarioRepository.count());
        assertEquals(nomeEsperado, output.getNome());
        assertEquals(emailEsperado, output.getEmail());
        assertNotNull(output.getDataCriacao());
        assertNull(output.getDataExclusao());
    }

    @Test
    public void dadoUmCommandValido_quandoExecutarObterUsuarioPorIDComIDNaoCorrespondente_entaoUmaExceptionEhRetornada() {
        // setup:
        final var idInexistente = "id-inexistente";
        final var erroEsperado = "Usuario com ID id-inexistente não foi encontrado.";
        final var usuario = Usuario.newUsuario("dummy-nome", "dummy-senha", "dummy@email.com");

        assertEquals(0, usuarioRepository.count());

        usuarioRepository.save(usuario);

        // execute:
        final var exception = assertThrows(NotFoundException.class, () -> obterUsuarioPorIDUseCase.execute(idInexistente));

        // verify:
        assertEquals(1, usuarioRepository.count());
        assertEquals(erroEsperado, exception.getMessage());
    }



}
