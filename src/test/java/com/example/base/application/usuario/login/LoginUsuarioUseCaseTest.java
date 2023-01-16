package com.example.base.application.usuario.login;

import com.example.base.application.usuario.exception.UsuarioOuSenhaIncorretosException;
import com.example.base.domain.usuario.Usuario;
import com.example.base.infrastructure.usuario.persistence.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.example.base.application.usuario.login.LoginUsuarioCommand.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginUsuarioUseCaseTest {

    @InjectMocks
    private LoginUsuarioUseCase loginUsuarioUseCase;

    @Mock
    private UsuarioRepository usuarioRepository;


    @Test
    public void dadoUmCommandValido_quandoExecutarLogin_deveEstarOk() {
        //setup
        final var email = "dummy@email.com";
        final var senha = "dummy";
        final var command= with(email, senha);

        when(usuarioRepository.findByEmailAndSenha(any(), any()))
                .thenReturn(Optional.of(Usuario.newUsuario("dummy", senha, email)));

        //execute
        Assertions.assertDoesNotThrow(() -> loginUsuarioUseCase.execute(command));

        //verify
        verify(usuarioRepository, times(1)).findByEmailAndSenha(any(), any());
    }

    @Test
    public void dadoUmCommandValido_quandoExecutarLogin_deveRetornaErroUsuarioOuSenhaIncorretos() {
        //setup
        final var mensagemErroEsperada = "Usuário ou senha estão incorretos.";
        final var command= with("dummy@email.com", "dummy");

        when(usuarioRepository.findByEmailAndSenha(any(), any()))
                .thenReturn(Optional.empty());

        //execute
        UsuarioOuSenhaIncorretosException exception =
                assertThrows(UsuarioOuSenhaIncorretosException.class, () -> loginUsuarioUseCase.execute(command));

        //verify
        verify(usuarioRepository, times(1)).findByEmailAndSenha(any(), any());
        assertEquals(exception.getMessage(), mensagemErroEsperada);
    }


}
