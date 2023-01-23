package com.example.base.domain.usuario;

import com.example.base.domain.DomainID;

import java.util.UUID;

public class UsuarioID extends DomainID {

    private UsuarioID(String value) {
        super(value);
    }

    public static UsuarioID unique() {
        return new UsuarioID(UUID.randomUUID().toString());
    }

    public String getValue() {
        return this.id;
    }
}
