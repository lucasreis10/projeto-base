package com.example.base.application.usuario.login;

public class LoginUsuarioCommand {

    private String email;
    private String senha;


    private LoginUsuarioCommand(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public static LoginUsuarioCommand with(String email, String senha) {
        return new LoginUsuarioCommand(email, senha);
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }
}
