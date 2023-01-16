package com.example.base.application.usuario.exception;

public class UsuarioNotFound extends RuntimeException {

    private static final String MSG_ERRO_PADRAO = "Erro ao recuperar usuário com ID fornecido.";

    public UsuarioNotFound() {
        super(MSG_ERRO_PADRAO);
    }

    public UsuarioNotFound(String mensagemErro) {
        super(mensagemErro);
    }

}
