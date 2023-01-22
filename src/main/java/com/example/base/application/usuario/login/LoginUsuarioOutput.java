package com.example.base.application.usuario.login;

public class LoginUsuarioOutput {

    private final String tokenJwt;

    private LoginUsuarioOutput(String tokenJwt) {
        this.tokenJwt = tokenJwt;
    }

    public static LoginUsuarioOutput with(String tokenJwt) {
        return new LoginUsuarioOutput(tokenJwt);
    }

    public String getTokenJwt() {
        return tokenJwt;
    }
}
