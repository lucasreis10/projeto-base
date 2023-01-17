package com.example.base.infrastructure.application.usuario.login;


import com.example.base.application.usuario.exception.UsuarioOuSenhaIncorretosException;
import com.example.base.application.usuario.login.LoginUsuarioUseCase;
import com.example.base.domain.usuario.Usuario;
import com.example.base.infrastructure.IntegrationTest;
import com.example.base.infrastructure.usuario.persistence.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import static com.example.base.application.usuario.login.LoginUsuarioCommand.with;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@IntegrationTest
public class LoginUsuarioUseCaseITTest {

    @Autowired
    private LoginUsuarioUseCase loginUsuarioUseCase;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void dadoUmCommandValido_quandoExecutarLogin_deveEstarOk() {
        //setup
        final var nome = "dummy-nome";
        final var email = "dummy@email.com";
        final var senha = "dummy-senha";
        final var command= with(email, senha);
        final var usuario = Usuario.newUsuario(nome, senha, email);

        //execute
        assertEquals(0, usuarioRepository.count());

        usuarioRepository.save(usuario);
        loginUsuarioUseCase.execute(command);

        //verify
        assertEquals(1, usuarioRepository.count());

    }

    @Test
    public void dadoUmCommandValido_quandoExecutarLogin_deveRetornaErroUsuarioOuSenhaIncorretos() {
        //setup
        final var mensagemEsperada = "Usuário ou senha estão incorretos.";
        final var nome = "dummy-nome";
        final var email = "dummy@email.com";
        final var senha = "dummy-senha";
        final var command= with("naoexiste@meail.com", "`12345");
        final var usuario = Usuario.newUsuario(nome, senha, email);

        //execute
        assertEquals(0, usuarioRepository.count());

        usuarioRepository.save(usuario);
        final var exeception = assertThrows(UsuarioOuSenhaIncorretosException.class,
                () -> loginUsuarioUseCase.execute(command));

        //verify
        assertEquals(1, usuarioRepository.count());
        assertEquals(exeception.getMessage(), mensagemEsperada);

    }
}
