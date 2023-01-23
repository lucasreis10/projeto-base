package com.example.base.application.usuario.login;

public class LoginUsuarioOutput {

    private String tokenJwt;

    public LoginUsuarioOutput(){}
    public LoginUsuarioOutput(String tokenJwt) {
        this.tokenJwt = tokenJwt;
    }

    public static LoginUsuarioOutput with(String tokenJwt) {
        return new LoginUsuarioOutput(tokenJwt);
    }

    public String getTokenJwt() {
        return tokenJwt;
    }
}
