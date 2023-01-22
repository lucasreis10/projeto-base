package com.example.base.infrastructure.application.usuario.login;


import com.example.base.application.usuario.exception.UsuarioOuSenhaIncorretosException;
import com.example.base.application.usuario.login.LoginUsuarioOutput;
import com.example.base.application.usuario.login.LoginUsuarioUseCase;
import com.example.base.domain.usuario.Usuario;
import com.example.base.infrastructure.IntegrationTest;
import com.example.base.infrastructure.usuario.persistence.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;


import static com.example.base.application.usuario.login.LoginUsuarioCommand.with;
import static com.example.base.domain.usuario.Usuario.newUsuario;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@IntegrationTest
public class LoginUsuarioUseCaseITTest {

    @Autowired
    private LoginUsuarioUseCase loginUsuarioUseCase;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void dadoUmCommandValido_quandoExecutarLogin_deveRetornarJwtToken() {
        //setup
        final var nome = "dummy-nome";
        final var email = "dummy@email.com";
        final var senha = "dummy-senha";
        final var command= with(email, senha);
        final var usuario = newUsuario(nome, passwordEncoder.encode(senha), email);

        //execute
        assertEquals(0, usuarioRepository.count());

        usuarioRepository.save(usuario);

        assertEquals(1, usuarioRepository.count());

        LoginUsuarioOutput output = loginUsuarioUseCase.execute(command);

        //verify
        assertNotNull(output);
        assertNotNull(output.getTokenJwt());

    }

    @Test
    public void dadoUmCommandValido_quandoExecutarLogin_deveRetornaErroUsuarioOuSenhaIncorretos() {
        //setup
        final var mensagemEsperada = "Bad credentials";
        final var nome = "dummy-nome";
        final var email = "dummy@email.com";
        final var senha = "dummy-senha";
        final var command = with(email, "12345");
        final var usuario = newUsuario(nome, passwordEncoder.encode(senha), email);

        //execute
        assertEquals(0, usuarioRepository.count());

        usuarioRepository.save(usuario);

        final var exeception = assertThrows(BadCredentialsException.class,
                () -> loginUsuarioUseCase.execute(command));

        //verify
        assertEquals(1, usuarioRepository.count());
        assertEquals(exeception.getMessage(), mensagemEsperada);

    }
}
