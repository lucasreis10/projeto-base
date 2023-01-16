package com.example.base.infra.api.controllers;

import com.example.base.application.usuario.criar.CriarUsuarioCommand;
import com.example.base.application.usuario.criar.CriarUsuarioOutput;
import com.example.base.application.usuario.criar.CriarUsuarioUseCase;
import com.example.base.application.usuario.login.LoginUsuarioCommand;
import com.example.base.application.usuario.login.LoginUsuarioUseCase;
import com.example.base.infra.api.UsuarioAPI;
import com.example.base.infra.usuario.models.CriarUsuarioAPIInput;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static com.example.base.application.usuario.criar.CriarUsuarioCommand.with;

@RestController
public class UsuarioController implements UsuarioAPI {

    private final CriarUsuarioUseCase criarUsuarioUseCase;
    private final LoginUsuarioUseCase loginUsuarioUseCase;

    public UsuarioController(
            CriarUsuarioUseCase criarUsuarioUseCase,
            LoginUsuarioUseCase loginUsuarioUseCase
    ) {
        this.criarUsuarioUseCase = criarUsuarioUseCase;
        this.loginUsuarioUseCase = loginUsuarioUseCase;
    }

    @Override
    public ResponseEntity<?> criarUsuario(final CriarUsuarioAPIInput input) {
        final var command =
                with(input.getNome(), input.getSenha(), input.getEmail());


        CriarUsuarioOutput output = criarUsuarioUseCase.execute(command);

        return ResponseEntity
                .created(URI.create("/usuarios/" + output.getId()))
                .body(output);
    }

    @Override
    public ResponseEntity<?> login(final LoginUsuarioCommand input) {

        loginUsuarioUseCase.execute(input);

        return ResponseEntity.ok().build();
    }

}
