package com.example.base.infrastructure.api;


import com.example.base.infrastructure.usuario.models.LoginUsuarioAPIInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/auth")
public interface AutenticacaoAPI {


    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Realizar login por e-mail e senha.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "200", description = "Login realizado com sucesso."),
            @ApiResponse(responseCode =  "404", description = "Usuário ou senha estão incorretos."),
            @ApiResponse(responseCode =  "500", description = "Um erro interno foi lançado.")
    })
    public ResponseEntity<?> autenteicar(@RequestBody LoginUsuarioAPIInput input);

}
