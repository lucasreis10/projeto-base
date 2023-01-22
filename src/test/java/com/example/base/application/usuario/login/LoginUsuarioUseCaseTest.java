package com.example.base.application.usuario.login;

import com.example.base.application.usuario.exception.UsuarioOuSenhaIncorretosException;
import com.example.base.domain.usuario.Usuario;
import com.example.base.infrastructure.security.JwtService;
import com.example.base.infrastructure.usuario.persistence.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

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
    @Mock
    private AuthenticationManager  authenticationManager;
    @Mock
    private JwtService jwtService;



    @Test
    public void dadoUmCommandValido_quandoExecutarLogin_deveRetornarJwtToken() {
        //setup
        final var email = "dummy@email.com";
        final var senha = "dummy";
        final var tokenEsperado = "jwt-token";
        final var authenticationToken = new UsernamePasswordAuthenticationToken("", "");
        final var command= with(email, senha);

        when(usuarioRepository.findByEmail(any()))
                .thenReturn(Optional.of(Usuario.newUsuario("dummy", senha, email)));

        when(authenticationManager.authenticate(any()))
                .thenReturn(authenticationToken);

        when(jwtService.generateToken(any()))
                .thenReturn(tokenEsperado);

        //execute
        LoginUsuarioOutput output = loginUsuarioUseCase.execute(command);

        //verify
        verify(usuarioRepository, times(1)).findByEmail(any());
        verify(authenticationManager, times(1)).authenticate(any());
        assertEquals(tokenEsperado, output.getTokenJwt());
    }

    @Test
    public void dadoUmCommandValido_quandoExecutarLogin_deveRetornaErroUsuarioOuSenhaIncorretos() {
        //setup
        final var mensagemErroEsperada = "Usuário ou senha estão incorretos.";
        final var command= with("dummy@email.com", "dummy");

        when(usuarioRepository.findByEmail(any()))
                .thenReturn(Optional.empty());

        //execute
        UsuarioOuSenhaIncorretosException exception =
                assertThrows(UsuarioOuSenhaIncorretosException.class, () -> loginUsuarioUseCase.execute(command));

        //verify
        verify(usuarioRepository, times(1)).findByEmail(any());
        assertEquals(exception.getMessage(), mensagemErroEsperada);
    }


}
