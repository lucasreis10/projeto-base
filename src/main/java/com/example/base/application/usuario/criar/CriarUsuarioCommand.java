package com.example.base.application.usuario.criar;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class CriarUsuarioCommand {

    @NotNull
    @Size(min = 3, max = 255)
    private final String nome;

    @NotNull
    @Size(min = 5, max = 90)
    private final String senha;

    @NotNull
    @Size(min = 15, max = 90)
    private final String email;

    private CriarUsuarioCommand(String nome, String senha, String email) {
        this.nome = nome;
        this.senha = senha;
        this.email = email;
    }

    public static CriarUsuarioCommand with(String nome, String senha, String email) {
        return new CriarUsuarioCommand(nome, senha, email);
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
