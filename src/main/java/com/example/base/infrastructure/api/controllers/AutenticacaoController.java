package com.example.base.infrastructure.api.controllers;

import com.example.base.application.usuario.login.LoginUsuarioUseCase;
import com.example.base.infrastructure.api.AutenticacaoAPI;
import com.example.base.infrastructure.usuario.models.LoginUsuarioAPIInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static com.example.base.application.usuario.login.LoginUsuarioCommand.*;

@RestController
public class AutenticacaoController implements AutenticacaoAPI {

    private final LoginUsuarioUseCase loginUsuarioUseCase;

    @Autowired
    public AutenticacaoController(LoginUsuarioUseCase loginUsuarioUseCase) {
        this.loginUsuarioUseCase = loginUsuarioUseCase;
    }

    @Override
    public ResponseEntity<?> autenteicar(LoginUsuarioAPIInput input) {
        final var command = with(input.getEmail(), input.getSenha());

        loginUsuarioUseCase.execute(command);

        return ResponseEntity.ok().build();

    }
}
