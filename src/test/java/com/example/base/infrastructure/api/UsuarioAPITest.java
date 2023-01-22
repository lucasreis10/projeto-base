package com.example.base.infrastructure.api;

import com.example.base.application.usuario.criar.CriarUsuarioOutput;
import com.example.base.application.usuario.criar.CriarUsuarioUseCase;
import com.example.base.application.usuario.desativar.DesativarUsuarioOutput;
import com.example.base.application.usuario.desativar.DesativarUsuarioUseCase;
import com.example.base.application.usuario.exception.NotFoundException;
import com.example.base.application.usuario.exception.UsuarioOuSenhaIncorretosException;
import com.example.base.application.usuario.login.LoginUsuarioOutput;
import com.example.base.application.usuario.login.LoginUsuarioUseCase;
import com.example.base.domain.exception.DomainException;
import com.example.base.domain.usuario.Usuario;
import com.example.base.infrastructure.ControllerTest;
import com.example.base.infrastructure.security.JwtAuthenticationFilter;
import com.example.base.infrastructure.security.JwtService;
import com.example.base.infrastructure.usuario.models.CriarUsuarioAPIInput;
import com.example.base.infrastructure.usuario.models.LoginUsuarioAPIInput;
import com.example.base.infrastructure.usuario.persistence.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ControllerTest(controllers = UsuarioAPI.class)
//@AutoConfigureMockMvc(addFilters = false)
public class UsuarioAPITest {

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
    private CriarUsuarioUseCase criarUsuarioUseCase;
    @MockBean
    private LoginUsuarioUseCase loginUsuarioUseCase;
    @MockBean
    private DesativarUsuarioUseCase desativarUsuarioUseCase;


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
    public void dadoUmCommandValido_quandoExecutarDesativarUsuario_deveSerRetornadoStatusCode200() throws Exception{
        //setup
        final var id = "312";
        final var nomeEsperado = "dummy-nome";
        final var emailEsperado = "dummy@email.com";
        final var senhaEsperada = "dummy-senha";
        final var usuario = Usuario.newUsuario(nomeEsperado, senhaEsperada, emailEsperado).desativar();
        final var outputEsperado = DesativarUsuarioOutput.from(usuario);

        when(desativarUsuarioUseCase.execute(any()))
                .thenReturn(outputEsperado);

        // execute:
        final var request = delete("/usuarios/"+id+"/desativar")
                .contentType(MediaType.APPLICATION_JSON);

        final var response = mvc.perform(request)
                .andDo(MockMvcResultHandlers.print());

        // verify:
        response
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(desativarUsuarioUseCase, times(1)).execute(Mockito.argThat(cmd ->
                Objects.equals(id, cmd.getId())
        ));

    }

    @Test
    public void dadoUmCommandInvalidoComIdInexistente_quandoExecutarDesativarUsuario_deveSerRetornadoStatusCode404() throws Exception{
        //setup
        final var id = "dummy-id-nao-existe";
        final var mensagemErroEsperada = "Usuario com ID dummy-id-nao-existe não foi encontrado.";


        doThrow(NotFoundException.with(Usuario.class, id))
                .when(desativarUsuarioUseCase).execute(any());

        // execute:
        final var request = delete("/usuarios/"+id+"/desativar")
                .contentType(MediaType.APPLICATION_JSON);

        final var response = mvc.perform(request)
                .andDo(MockMvcResultHandlers.print());

        // verify:
        response
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(jsonPath("$.message", Matchers.equalTo(mensagemErroEsperada)));


        Mockito.verify(desativarUsuarioUseCase, times(1)).execute(Mockito.argThat(cmd ->
                Objects.equals(id, cmd.getId())
        ));

    }

}
