package com.example.base.infra.api.controllers;

import com.example.base.application.usuario.criar.CriarUsuarioCommand;
import com.example.base.application.usuario.criar.CriarUsuarioOutput;
import com.example.base.application.usuario.criar.CriarUsuarioUseCase;
import com.example.base.application.usuario.login.LoginUsuarioCommand;
import com.example.base.application.usuario.login.LoginUsuarioUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping(value = "usuarios")
@Tag(name = "Usuários")
public class UsuarioController {

    private final CriarUsuarioUseCase criarUsuarioUseCase;
    private final LoginUsuarioUseCase loginUsuarioUseCase;

    public UsuarioController(CriarUsuarioUseCase criarUsuarioUseCase, LoginUsuarioUseCase loginUsuarioUseCase) {
        this.criarUsuarioUseCase = criarUsuarioUseCase;
        this.loginUsuarioUseCase = loginUsuarioUseCase;
    }


    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> criarUsuario(final CriarUsuarioCommand input) {

        CriarUsuarioOutput output = criarUsuarioUseCase.execute(input);

        return ResponseEntity
                .created(URI.create("/usuarios/" + output.getId()))
                .body(output);
    }

    @PostMapping(
            value = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> login(final LoginUsuarioCommand input) {

        loginUsuarioUseCase.execute(input);

        return ResponseEntity.ok().build();
    }

}
