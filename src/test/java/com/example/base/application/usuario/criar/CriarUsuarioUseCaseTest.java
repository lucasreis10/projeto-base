package com.example.base.application.usuario.criar;

import com.example.base.domain.exception.DomainException;
import com.example.base.infra.usuario.persistence.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.base.application.usuario.criar.CriarUsuarioCommand.with;
import static com.example.base.domain.usuario.Usuario.newUsuario;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CriarUsuarioUseCaseTest {

    @InjectMocks
    private CriarUsuarioUseCase criarUsuarioUseCase;
    @Mock
    private UsuarioRepository usuarioRepository;


    @Test
    public void dadoUmComandoValido_quandoExecutarCriarUsuario_deveRetornarUmUsuarioComId() {
        // setup
        final var nomeEsperado = "dummy";
        final var senhaEsperada = "dummy";
        final var emailEsperado = "rr.richards@email.com";
        final var command = with(nomeEsperado, senhaEsperada, emailEsperado);
        final var outputEsperado = newUsuario(nomeEsperado, senhaEsperada, emailEsperado);

        // execute
        when(usuarioRepository.save(Mockito.any())).thenReturn(outputEsperado);

        final var output = criarUsuarioUseCase.execute(command);

        // verify
        assertNotNull(output);
        assertNotNull(output.getId());
        assertEquals(output.getEmail(), emailEsperado);
        verify(usuarioRepository, times(1)).save(Mockito.any());
    }

    @Test
    public void dadoUmComandoComNomeInvalido_quandoExecutarCriarUsuario_deveRetornarUmaExecao() {
        // setup
        final var exceptionEsperada = "'nome' deve ter 3 a 255 caracteres.";
        final var nomeInvalido = "aa";
        final var senhaEsperada = "dummy";
        final var emailEsperado = "rr.richards@email.com";
        final var command = with(nomeInvalido, senhaEsperada, emailEsperado);

        // execute
        final var exception = assertThrows(DomainException.class, () -> criarUsuarioUseCase.execute(command));

        // verify
        assertNotNull(exception.getMessage(), exceptionEsperada);

    }



}
