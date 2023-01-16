package com.example.base.infra.api;

import com.example.base.application.usuario.criar.CriarUsuarioOutput;
import com.example.base.application.usuario.criar.CriarUsuarioUseCase;
import com.example.base.application.usuario.login.LoginUsuarioUseCase;
import com.example.base.domain.exception.DomainException;
import com.example.base.infra.ControllerTest;
import com.example.base.infra.usuario.models.CriarUsuarioAPIInput;
import com.example.base.infra.usuario.models.LoginUsuarioAPIInput;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ControllerTest(controllers = UsuarioAPI.class)
public class UsuarioControllerAPITest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private CriarUsuarioUseCase criarUsuarioUseCase;
    @MockBean
    private LoginUsuarioUseCase loginUsuarioUseCase;

    @Test
    public void dadoParametrosValidos_quandoExecutarCriarUsuario_entaoRetornaUsuarioComIdEEmail() throws Exception{
        // setup:
        final var idEsperado = "dummy-id";
        final var emailEsperado = "gleidisney@email.com.br";
        final var nomeEsperado = "Gleidisney";
        final var senhaEsperada = "osi03w3";

        final var input =
                new CriarUsuarioAPIInput(nomeEsperado, senhaEsperada, emailEsperado);

        when(criarUsuarioUseCase.execute(any()))
                .thenReturn(CriarUsuarioOutput.from(idEsperado, emailEsperado));

        // execute:
        final var request = post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(input));

         final var response = mvc.perform(request)
                 .andDo(MockMvcResultHandlers.print());

        // verify:
        response
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("Location",  "/usuarios/dummy-id"))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", Matchers.equalTo("dummy-id")));

        Mockito.verify(criarUsuarioUseCase, times(1)).execute(Mockito.argThat(cmd ->
                Objects.equals(nomeEsperado, cmd.getNome())
                && Objects.equals(senhaEsperada, cmd.getSenha())
                && Objects.equals(emailEsperado, cmd.getEmail())
        ));

    }

    @Test
    public void dadoUmNomeNullInvalido_quandoExecutarCriarUsuario_deveSerRetornadoNotificationErroStatus422() throws Exception{
         final var emailEsperado = "gleidisney@email.com.br";
        final String nomeEsperado = null;
        final var senhaEsperada = "osi03w3";
        final var mensagemErroEsperada = "'nome' não pode ser nulo.";

        final var input =
                new CriarUsuarioAPIInput(nomeEsperado, senhaEsperada, emailEsperado);

        when(criarUsuarioUseCase.execute(any()))
                .thenThrow(new DomainException(mensagemErroEsperada));

        // execute:
        final var request = post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(input));

        final var response = mvc.perform(request)
                .andDo(MockMvcResultHandlers.print());

        // verify:
        response
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(header().string("Location", Matchers.nullValue()))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.message", Matchers.equalTo(mensagemErroEsperada)));

        Mockito.verify(criarUsuarioUseCase, times(1)).execute(Mockito.argThat(cmd ->
                Objects.equals(nomeEsperado, cmd.getNome())
                && Objects.equals(senhaEsperada, cmd.getSenha())
                && Objects.equals(emailEsperado, cmd.getEmail())
        ));

    }

    @Test
    public void dadoUmCommandValido_quandoExecutarLogin_deveSerRetornadoStatusCode200() throws Exception{
        final var emailEsperado = "gleidisney@email.com.br";
        final var senhaEsperada = "osi03w3";

        final var input =
                new LoginUsuarioAPIInput(emailEsperado, senhaEsperada);

        doNothing()
                .when(loginUsuarioUseCase).execute(Mockito.any());

        // execute:
        final var request = post("/usuarios/login")
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


}
