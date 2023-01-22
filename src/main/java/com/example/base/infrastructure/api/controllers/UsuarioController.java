package com.example.base.infrastructure.api.controllers;

import com.example.base.application.usuario.criar.CriarUsuarioOutput;
import com.example.base.application.usuario.criar.CriarUsuarioUseCase;
import com.example.base.application.usuario.desativar.DesativarUsuarioCommand;
import com.example.base.application.usuario.desativar.DesativarUsuarioOutput;
import com.example.base.application.usuario.desativar.DesativarUsuarioUseCase;
import com.example.base.application.usuario.login.LoginUsuarioCommand;
import com.example.base.application.usuario.login.LoginUsuarioUseCase;
import com.example.base.infrastructure.api.UsuarioAPI;
import com.example.base.infrastructure.usuario.models.CriarUsuarioAPIInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static com.example.base.application.usuario.criar.CriarUsuarioCommand.with;

@RestController
public class UsuarioController implements UsuarioAPI {

    private final CriarUsuarioUseCase criarUsuarioUseCase;
    private final LoginUsuarioUseCase loginUsuarioUseCase;
    private final DesativarUsuarioUseCase desativarUsuarioUseCase;

    @Autowired
    public UsuarioController(
            CriarUsuarioUseCase criarUsuarioUseCase,
            LoginUsuarioUseCase loginUsuarioUseCase,
            DesativarUsuarioUseCase desativarUsuarioUseCase) {
        this.criarUsuarioUseCase = criarUsuarioUseCase;
        this.loginUsuarioUseCase = loginUsuarioUseCase;
        this.desativarUsuarioUseCase = desativarUsuarioUseCase;
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
    public ResponseEntity<?> desativar(String input) {
        DesativarUsuarioCommand command = DesativarUsuarioCommand.with(input);

        DesativarUsuarioOutput output = desativarUsuarioUseCase.execute(command);

        return ResponseEntity.ok(output);
    }

}
