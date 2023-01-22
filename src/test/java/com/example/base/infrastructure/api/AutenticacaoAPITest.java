package com.example.base.infrastructure.api;


import com.example.base.application.usuario.exception.UsuarioOuSenhaIncorretosException;
import com.example.base.application.usuario.login.LoginUsuarioOutput;
import com.example.base.application.usuario.login.LoginUsuarioUseCase;
import com.example.base.infrastructure.ControllerTest;
import com.example.base.infrastructure.security.JwtAuthenticationFilter;
import com.example.base.infrastructure.security.JwtService;
import com.example.base.infrastructure.usuario.models.LoginUsuarioAPIInput;
import com.example.base.infrastructure.usuario.persistence.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Objects;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ControllerTest(controllers = UsuarioAPI.class)
public class AutenticacaoAPITest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private JwtAuthenticationFilter authFilter;
    @Autowired
    private JwtService jwtService;
    @MockBean
    private UsuarioRepository usuarioRepository;
    @MockBean
    private LoginUsuarioUseCase loginUsuarioUseCase;

    @Test
    public void dadoUmCommandValido_quandoExecutarLogin_deveSerRetornadoStatusCode200() throws Exception{
        final var emailEsperado = "gleidisney@email.com.br";
        final var senhaEsperada = "osi03w3";
        final var tokenEsperado = "token-jwt";
        final var outputEsperado = LoginUsuarioOutput.with(tokenEsperado);

        final var input =
                new LoginUsuarioAPIInput(emailEsperado, senhaEsperada);

        when(loginUsuarioUseCase.execute(any()))
                .thenReturn(outputEsperado);

        // execute:
        final var request = post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(input));

        final var response = mvc.perform(request)
                .andDo(MockMvcResultHandlers.print());

        // verify:
        response
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(loginUsuarioUseCase, times(1)).execute(Mockito.argThat(cmd ->
                Objects.equals(senhaEsperada, cmd.getSenha())
                        && Objects.equals(emailEsperado, cmd.getEmail())
        ));

    }

    @Test
    public void dadoUmCommandInvalido_quandoExecutarLogin_deveSerRetornadoStatusCode404() throws Exception{
        final var emailEsperado = "naoexiste@email.com";
        final var senhaEsperada = "123456";
        final var mensagemErroEsperada = "Usuário ou senha estão incorretos.";

        final var input =
                new LoginUsuarioAPIInput(emailEsperado, senhaEsperada);

        when(usuarioRepository.findByEmail(any()))
                .thenReturn(Optional.empty());

        doThrow(new UsuarioOuSenhaIncorretosException())
                .when(loginUsuarioUseCase).execute(any());

        // execute:
        final var request = post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(input));

        final var response = mvc.perform(request)
                .andDo(MockMvcResultHandlers.print());

        // verify:
        response
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(jsonPath("$.message", Matchers.equalTo(mensagemErroEsperada)));

        Mockito.verify(loginUsuarioUseCase, times(1)).execute(Mockito.argThat(cmd ->
                Objects.equals(senhaEsperada, cmd.getSenha())
                        && Objects.equals(emailEsperado, cmd.getEmail())
        ));

    }

}
