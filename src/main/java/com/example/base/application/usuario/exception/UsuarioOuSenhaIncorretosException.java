package com.example.base.application.usuario.exception;

public class UsuarioOuSenhaIncorretosException extends RuntimeException{

    private static final String USUARIO_OU_SENHA_INCORRETOS_MSG = "Usuário ou senha estão incorretos";

    public UsuarioOuSenhaIncorretosException() {
        super(USUARIO_OU_SENHA_INCORRETOS_MSG);

    }
}
