package com.example.base.infrastructure.api;

import com.example.base.application.usuario.criar.CriarUsuarioOutput;
import com.example.base.application.usuario.criar.CriarUsuarioUseCase;
import com.example.base.application.usuario.desativar.DesativarUsuarioOutput;
import com.example.base.application.usuario.desativar.DesativarUsuarioUseCase;
import com.example.base.application.usuario.exception.NotFoundException;
import com.example.base.application.usuario.login.LoginUsuarioUseCase;
import com.example.base.application.usuario.recuperar.listar.ListarUsuarioUseCase;
import com.example.base.application.usuario.recuperar.obter.ObterUsuarioPorIDOutput;
import com.example.base.application.usuario.recuperar.obter.ObterUsuarioPorIDUseCase;
import com.example.base.domain.exception.DomainException;
import com.example.base.domain.usuario.Usuario;
import com.example.base.infrastructure.ControllerTest;
import com.example.base.infrastructure.security.JwtAuthenticationFilter;
import com.example.base.infrastructure.security.JwtService;
import com.example.base.infrastructure.usuario.models.CriarUsuarioAPIInput;
import com.example.base.infrastructure.usuario.persistence.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Instant;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ControllerTest(controllers = UsuarioAPI.class)
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
    @MockBean
    private ListarUsuarioUseCase listarUsuarioUseCase;
    @MockBean
    private ObterUsuarioPorIDUseCase obterUsuarioPorIDUseCase;

    private static final String REQUEST_MAPPING_USUARIOS = "/usuarios";
    private static final String MESSAGE_REPONSE_BODY = "$.message";
    private static final String CONTENT_TYPE = "Content-Type";



    @Test
    public void dadoParametrosValidos_quandoExecutarCriarUsuario_entaoRetornaUsuarioComIdEEmail() throws Exception{
        // setup:
        final var idEsperado = "dummy-id-popos323";
        final var emailEsperado = "gleidisney@email.com.br";
        final var nomeEsperado = "Gleidisney";
        final var senhaEsperada = "osi03w3";

        final var input =
                new CriarUsuarioAPIInput(nomeEsperado, senhaEsperada, emailEsperado);

        when(criarUsuarioUseCase.execute(any()))
                .thenReturn(CriarUsuarioOutput.from(idEsperado, emailEsperado));

        // execute:
        final var request = post(REQUEST_MAPPING_USUARIOS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(input));

         final var response = mvc.perform(request)
                 .andDo(MockMvcResultHandlers.print());

        // verify:
        response
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("Location",  "/usuarios/dummy-id-popos323"))
                .andExpect(header().string(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", Matchers.equalTo(idEsperado)));

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
        final var request = post(REQUEST_MAPPING_USUARIOS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(input));

        final var response = mvc.perform(request)
                .andDo(MockMvcResultHandlers.print());

        // verify:
        response
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(header().string("Location", Matchers.nullValue()))
                .andExpect(header().string(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath(MESSAGE_REPONSE_BODY, Matchers.equalTo(mensagemErroEsperada)));

        Mockito.verify(criarUsuarioUseCase, times(1)).execute(Mockito.argThat(cmd ->
                Objects.equals(nomeEsperado, cmd.getNome())
                && Objects.equals(senhaEsperada, cmd.getSenha())
                && Objects.equals(emailEsperado, cmd.getEmail())
        ));

    }

    @Test
    @WithMockUser(username="usuario-com-credenciais-validas")
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
        final var request = delete("/usuarios/312/desativar")
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
    @WithMockUser(username="usuario-com-credenciais-validas")
    public void dadoUmCommandInvalidoComIdInexistente_quandoExecutarDesativarUsuario_deveSerRetornadoStatusCode404() throws Exception{
        //setup
        final var id = "dummy-id-nao-existe";
        final var mensagemErroEsperada = "Usuario com ID dummy-id-nao-existe não foi encontrado.";



        doThrow(NotFoundException.with(Usuario.class, id))
                .when(desativarUsuarioUseCase).execute(any());

        // execute:
        final var request = delete("/usuarios/dummy-id-nao-existe/desativar")
                .contentType(MediaType.APPLICATION_JSON);

        final var response = mvc.perform(request)
                .andDo(MockMvcResultHandlers.print());

        // verify:
        response
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(jsonPath(MESSAGE_REPONSE_BODY, Matchers.equalTo(mensagemErroEsperada)));


        Mockito.verify(desativarUsuarioUseCase, times(1)).execute(Mockito.argThat(cmd ->
                Objects.equals(id, cmd.getId())
        ));

    }

    @Test
    public void dadoUmCommandSemCredencial_quandoExecutarDesativarUsuario_deveSerRetornadoStatusCode403() throws Exception{
        // setup:
        // execute:
        final var request = delete("/usuarios/dummy-id-nao-existe/desativar")
                .contentType(MediaType.APPLICATION_JSON);

        final var response = mvc.perform(request)
                .andDo(MockMvcResultHandlers.print());

        // verify:
        response
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());

    }

    @Test
    @WithMockUser(username="usuario-com-credenciais-validas")
    public void dadoUmCommandValido_quandoExecutarListarUsuario_deveSerRetornadoStatusCode200() throws Exception{
        // setup:
        final var outputPagination = UsuarioAPITestHelper.paginationListaUsuarioOutput();
        final var bodyEsperado = mapper.writeValueAsString(outputPagination);

        when(listarUsuarioUseCase.execute(any()))
                .thenReturn(outputPagination);


        // execute:
        final var request = get(REQUEST_MAPPING_USUARIOS)
                .contentType(MediaType.APPLICATION_JSON);

        final var response = mvc.perform(request)
                .andDo(MockMvcResultHandlers.print());

        // verify:
        response
                .andDo(MockMvcResultHandlers.print())
                .andExpect(header().string(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string(bodyEsperado));

        Mockito.verify(listarUsuarioUseCase, times(1)).execute(any());
    }

    @Test
    @WithMockUser(username="usuario-com-credenciais-validas")
    public void dadoUmCommandValido_quandoExecutarObterUsuarioPorId_deveSerRetornadoStatusCode200() throws Exception{
        // setup:
        final var id = "dummy-id-23s3w4";
        final var outputEsperado = new ObterUsuarioPorIDOutput(
                "dummy-nome", "email@email.com", Instant.now(), null);
        final var bodyEsperado = mapper.writeValueAsString(outputEsperado);

        when(obterUsuarioPorIDUseCase.execute(any()))
                .thenReturn(outputEsperado);

        // execute:
        final var request = get("/usuarios/" + id)
                .contentType(MediaType.APPLICATION_JSON);

        final var response = mvc.perform(request)
                .andDo(MockMvcResultHandlers.print());

        // verify:
        response
                .andDo(MockMvcResultHandlers.print())
                .andExpect(header().string(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string(bodyEsperado));

        Mockito.verify(obterUsuarioPorIDUseCase, times(1)).execute(any());
    }

    @Test
    @WithMockUser(username="usuario-com-credenciais-validas")
    public void dadoUmCommandValido_quandoExecutarObterUsuarioPorIdENaoTiverCorrespondeciaDeID_deveSerRetornadoStatusCode404() throws Exception{
        // setup:
        final var id = "dummy-id-nao-existe";
        final var mensagemErroEsperada = "Usuario com ID dummy-id-nao-existe não foi encontrado.";

        doThrow(NotFoundException.with(Usuario.class, id))
                .when(obterUsuarioPorIDUseCase).execute(any());

        // execute:
        final var request = get("/usuarios/" + id)
                .contentType(MediaType.APPLICATION_JSON);

        final var response = mvc.perform(request)
                .andDo(MockMvcResultHandlers.print());

        // verify:
        response
                .andDo(MockMvcResultHandlers.print())
                .andExpect(header().string(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(jsonPath(MESSAGE_REPONSE_BODY, Matchers.equalTo(mensagemErroEsperada)));;

        Mockito.verify(obterUsuarioPorIDUseCase, times(1)).execute(any());
    }


}
