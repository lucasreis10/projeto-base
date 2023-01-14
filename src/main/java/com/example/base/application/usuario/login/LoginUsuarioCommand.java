package com.example.base.application.usuario.login;

import lombok.Getter;

public class LoginUsuarioCommand {

    private String email;
    private String senha;

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }
}
