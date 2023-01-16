package com.example.base.application.usuario.exception;


public class NotFoundException extends RuntimeException {


    private NotFoundException(String mensagem) {
        super(mensagem);
    }

    public static NotFoundException with(
            final Class<?> clazz,
            final String id
    ) {
        final var erro = String.format("%s com ID %s não foi encontrado.",
                clazz.getSimpleName(),
                id
        );

        return new NotFoundException(erro);
    }

}
