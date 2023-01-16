package com.example.base.infrastructure.usuario.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginUsuarioAPIInput {

    @JsonProperty("email")
    private String email;
    @JsonProperty("senha")
    private String senha;


    public LoginUsuarioAPIInput() {
    }

    public LoginUsuarioAPIInput(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }
}
