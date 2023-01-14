package com.example.base.domain.usuario;

import java.util.UUID;

public class UsuarioID {

    private final String value;


    private UsuarioID(String value) {
        this.value = value;
    }

    public static UsuarioID unique() {
        return new UsuarioID(UUID.randomUUID().toString());
    }

    public String getValue() {
        return value;
    }
}
