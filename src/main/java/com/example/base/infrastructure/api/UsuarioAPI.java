package com.example.base.infrastructure.api;


import com.example.base.application.usuario.login.LoginUsuarioCommand;
import com.example.base.infrastructure.usuario.models.CriarUsuarioAPIInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> criarUsuario(@RequestBody final CriarUsuarioAPIInput input);


    @DeleteMapping(
            value = "{id}/desativar",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Desativar usuário por id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "200", description = "Usuário desativado com sucesso."),
            @ApiResponse(responseCode =  "404", description = "Usuário não existe."),
            @ApiResponse(responseCode =  "500", description = "Um erro interno foi lançado.")
    })
    public ResponseEntity<?> desativar(@PathVariable("id") final String input);

}
