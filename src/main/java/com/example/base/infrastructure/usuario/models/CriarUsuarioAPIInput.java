package com.example.base.infrastructure.usuario.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CriarUsuarioAPIInput {

    @JsonProperty("nome")
    private String nome;
    @JsonProperty("senha")
    private String senha;
    @JsonProperty("email")
    private String email;


    public CriarUsuarioAPIInput() {
    }

    public CriarUsuarioAPIInput(String nome, String senha, String email) {
        this.nome = nome;
        this.senha = senha;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public String getSenha() {
        return senha;
    }

    public String getEmail() {
        return email;
    }
}
