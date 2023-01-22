package com.example.base.application.usuario.recuperar.obter;

import com.example.base.application.usuario.exception.NotFoundException;
import com.example.base.domain.usuario.Usuario;
import com.example.base.infrastructure.usuario.persistence.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ObterUsuarioPorIDUseCaseTest {


    @InjectMocks
    private ObterUsuarioPorIDUseCase obterUsuarioPorIDUseCase;
    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    public void dadoUmCommand_quandoExecutarObterUsuarioPorID_entaoUmUsuarioEhRetornado() {
        // setup:
        final var nomeEsperado = "dummy-nome";
        final var emailEsperado = "dummy@email.com";
        final var usuario = Usuario.newUsuario(nomeEsperado, "dummy-senha", emailEsperado);

        when(usuarioRepository.findById(any()))
                .thenReturn(Optional.of(usuario));

        // execute:
        final var output = obterUsuarioPorIDUseCase.execute("dummy-id");

        // verify:
        assertEquals(nomeEsperado, output.getNome());
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

        when(usuarioRepository.findById(any()))
                .thenReturn(Optional.empty());

        // execute:
        final var exception = assertThrows(NotFoundException.class, () -> obterUsuarioPorIDUseCase.execute(idInexistente));

        // verify:
        verify(usuarioRepository, times(1)).findById(any());
        assertEquals(erroEsperado, exception.getMessage());
    }

}
