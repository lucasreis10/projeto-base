package com.example.base.infra.api;


import com.example.base.application.usuario.criar.CriarUsuarioCommand;
import com.example.base.application.usuario.login.LoginUsuarioCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "usuarios")
@Tag(name = "Usuários")
public interface UsuarioAPI {


    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Criar um novo usuário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "201", description = "Criado com sucesso."),
            @ApiResponse(responseCode =  "422", description = "Um erro de validação foi lançado."),
            @ApiResponse(responseCode =  "500", description = "Um erro interno foi lançado.")
    })
    public ResponseEntity<?> criarUsuario(final CriarUsuarioCommand input);

    @PostMapping(
            value = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Realizar login por e-mail e senha.")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "200", description = "Login realizado com sucesso."),
            @ApiResponse(responseCode =  "404", description = "Usuário ou senha estão incorretos."),
            @ApiResponse(responseCode =  "500", description = "Um erro interno foi lançado.")
    })
    public ResponseEntity<?> login(final LoginUsuarioCommand input);

}
