package com.example.base.infrastructure.application.usuario.criar;


import com.example.base.application.usuario.criar.CriarUsuarioUseCase;
import com.example.base.infrastructure.IntegrationTest;
import com.example.base.infrastructure.usuario.persistence.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.example.base.application.usuario.criar.CriarUsuarioCommand.with;
import static org.junit.jupiter.api.Assertions.*;

@IntegrationTest
public class CriarUsuarioUseCaseITTest {

    @Autowired
    private CriarUsuarioUseCase criarUsuarioUseCase;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void dadoUmComandoValido_quandoExecutarCriarUsuario_deveRetornarUmUsuarioComId() {
        // setup
        final var nomeEsperado = "Robert Richards";
        final var senhaEsperada = "09sdw";
        final var emailEsperado = "rr.richards@email.com";
        final var command = with(nomeEsperado, senhaEsperada, emailEsperado);

        assertEquals(0, usuarioRepository.count());

        // execute
        final var output = criarUsuarioUseCase.execute(command);
        final var usuario = usuarioRepository.findById(output.getId()).get();

        // verify
        assertEquals(1, usuarioRepository.count());
        assertNotNull(usuario.getId());
        assertEquals(usuario.getNome(), nomeEsperado);
        assertEquals(usuario.getEmail(), emailEsperado);
        assertEquals(usuario.getSenha(), senhaEsperada);
        assertEquals(usuario.getId(), output.getId());
        assertEquals(usuario.getEmail(), output.getEmail());
        assertNotNull(usuario.getDataCriacao());
        assertNull(usuario.getDataExclusao());
    }



}
