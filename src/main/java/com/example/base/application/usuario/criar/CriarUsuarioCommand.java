package com.example.base.application.usuario.criar;



public class CriarUsuarioCommand {

    private final String nome;
    private final String senha;
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
