package com.example.base.domain.usuario;

import org.junit.jupiter.api.Test;

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
        assertNull(usuario.getId());
        assertEquals(usuario.getNome(), nomeEsperado);
        assertEquals(usuario.getEmail(), emailEsperado);
        assertEquals(usuario.getSenha(), senhaEsperada);
    }

}
