package com.example.base.e2e.usuario;


import com.example.base.application.usuario.login.LoginUsuarioOutput;
import com.example.base.application.usuario.recuperar.obter.ObterUsuarioPorIDOutput;
import com.example.base.infrastructure.E2ETest;
import com.example.base.infrastructure.usuario.models.CriarUsuarioAPIInput;
import com.example.base.infrastructure.usuario.models.LoginUsuarioAPIInput;
import com.example.base.infrastructure.usuario.persistence.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@E2ETest
@Testcontainers
public class UsuarioE2ETest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ObjectMapper mapper;

    @Container
    private static final MySQLContainer MY_SQL_CONTAINER =
            new MySQLContainer("mysql:latest")
                    .withPassword("123456")
                    .withUsername("root")
                    .withDatabaseName("projeto_base");

    @DynamicPropertySource
    public static void setDataSourceProperties(final DynamicPropertyRegistry registry) {
        final var mappedPort = MY_SQL_CONTAINER.getMappedPort(3306);
        System.out.printf("Container is running on port %s ", mappedPort);
        registry.add("mysql.port", () -> mappedPort);
    }

    @Test
    public void comoUmUsuarioDoSistemaDevoSerCapazDeCriarOMeuProprioUsuarioComValoresValidos() throws Exception {
        // setup:
        assertTrue(MY_SQL_CONTAINER.isRunning());
        assertEquals(0, usuarioRepository.count());

        final var nomeEsperado = "Benjamin Button";
        final var senha = "09sdw";
        final var emailEsperado = "button.benjamin@email.com";

        // execute:
        final var usuarioID = gerarUsuario(nomeEsperado, senha, emailEsperado);

        final var credencial = gerarCredencial(emailEsperado, senha);

        final var usuario = recuperarUsuarioPorId(usuarioID, credencial.getTokenJwt());

        // verify:
        assertEquals(1, usuarioRepository.count());
        assertEquals(usuario.getNome(), nomeEsperado);
        assertEquals(usuario.getEmail(), emailEsperado);
        assertNotNull(usuario.getDataCriacao());
        assertNull(usuario.getDataExclusao());

    }


    private String gerarUsuario(final String nome, final String senha, final String email) throws Exception {
        final var requestBody =  new CriarUsuarioAPIInput(nome, senha, email);

        final var request = post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestBody));

        return mvc.perform(request)
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse().getHeader("Location")
                .replace("/usuarios/", "");
    }

    private LoginUsuarioOutput gerarCredencial(final String email, final String senha) throws Exception {
        final var requestBody =  new LoginUsuarioAPIInput(email, senha);

        final var request = post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestBody));

        final var contentAsString = mvc.perform(request)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return mapper.readValue(contentAsString, LoginUsuarioOutput.class);
    }


    private ObterUsuarioPorIDOutput recuperarUsuarioPorId(String id, String tokenJwt) throws Exception {
        final var request = get("/usuarios/" + id)
                .header("Authorization", "Bearer " + tokenJwt);

        final var contentAsString = mvc.perform(request)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return mapper.readValue(contentAsString, ObterUsuarioPorIDOutput.class);

    }
}
