package com.example.base.domain.usuario;

import com.example.base.domain.exception.DomainException;
import org.junit.jupiter.api.Test;

import static com.example.base.application.usuario.criar.CriarUsuarioCommand.with;
import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {

    @Test
    public void dadoParametrosValidos_quandoExecutarNewUsuario_entaoUsuarioEhInstaciado() {
        // setup:
        final var nomeEsperado = "Gleidisnay";
        final var emailEsperado = "gleidisney@email.com.br";
        final var senhaEsperada = "oid9a82osj";

        // execute:
        final var usuario = Usuario.newUsuario(nomeEsperado, senhaEsperada, emailEsperado);

        // verify:
        assertNotNull(usuario);
        assertNotNull(usuario.getId());
        assertEquals(usuario.getNome(), nomeEsperado);
        assertEquals(usuario.getEmail(), emailEsperado);
        assertEquals(usuario.getSenha(), senhaEsperada);
        assertNotNull(usuario.getDataCriacao());
        assertNull(usuario.getDataExclusao());
    }

    @Test
    public void dadoUmComandoComNomeInvalido_quandoExecutarNewUsuario_deveRetornarUmaExecao() {
        // setup:
        final var exceptionEsperada = "'nome' deve ter 3 a 255 caracteres.";
        final var nomeEsperado = "Ab";
        final var emailEsperado = "dummy";
        final var senhaEsperada = "dummy";

        // execute:
        final var exception = assertThrows(DomainException.class, () -> Usuario.newUsuario(nomeEsperado, senhaEsperada, emailEsperado));

        // verify:
        assertEquals(exception.getMessage(), exceptionEsperada);
    }

    @Test
    public void dadoUmComandoComNomeNull_quandoExecutarNewUsuario_deveRetornarUmaExecao() {
        // setup:
        final var exceptionEsperada = "'nome' não pode ser nulo.";
        final String nomeEsperado = null;
        final var emailEsperado = "dummy";
        final var senhaEsperada = "dummy";

        // execute:
        final var exception = assertThrows(DomainException.class, () -> Usuario.newUsuario(nomeEsperado, senhaEsperada, emailEsperado));

        // verify:
        assertEquals(exception.getMessage(), exceptionEsperada);
    }

    @Test
    public void dadoUmComandoComNomeVazio_quandoExecutarNewUsuario_deveRetornarUmaExecao() {
        // setup:
        final var exceptionEsperada = "'nome' não pode ser vazio.";
        final var nomeEsperado = "";
        final var emailEsperado = "dummy";
        final var senhaEsperada = "dummy";

        // execute:
        final var exception = assertThrows(DomainException.class, () -> Usuario.newUsuario(nomeEsperado, senhaEsperada, emailEsperado));

        // verify:
        assertEquals(exception.getMessage(), exceptionEsperada);
    }

    @Test
    public void dadoParametrosValidos_quandoExecutarDeletar_entaoUsuarioComDataExclusaoDiferenteDeNullRetorna() {
        // setup:
        final var nomeEsperado = "Gleidisnay";
        final var emailEsperado = "gleidisney@email.com.br";
        final var senhaEsperada = "oid9a82osj";

        // execute:
        final var usuario = Usuario.newUsuario(nomeEsperado, senhaEsperada, emailEsperado).deletar();

        // verify:
        assertNotNull(usuario);
        assertNotNull(usuario.getId());
        assertEquals(usuario.getNome(), nomeEsperado);
        assertEquals(usuario.getEmail(), emailEsperado);
        assertEquals(usuario.getSenha(), senhaEsperada);
        assertNotNull(usuario.getDataCriacao());
        assertNotNull(usuario.getDataExclusao());
    }



}
