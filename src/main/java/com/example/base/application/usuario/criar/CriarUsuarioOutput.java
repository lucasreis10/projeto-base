package com.example.base.application.usuario.criar;

import com.example.base.domain.usuario.Usuario;
import com.example.base.domain.usuario.UsuarioID;

public class CriarUsuarioOutput {

    private final String id;
    private final String email;

    private CriarUsuarioOutput(String id, String email) {
        this.id = id;
        this.email = email;
    }

    public static CriarUsuarioOutput from(Usuario usuario) {
        return new CriarUsuarioOutput(usuario.getId().getValue(), usuario.getEmail());
    }

    public static CriarUsuarioOutput with(UsuarioID id, String email) {
        return new CriarUsuarioOutput(id.getValue(), email);
    }

    public static CriarUsuarioOutput from(String id, String email) {
        return new CriarUsuarioOutput(id, email);
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
