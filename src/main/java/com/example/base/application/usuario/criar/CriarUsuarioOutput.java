package com.example.base.application.usuario.criar;

import com.example.base.domain.usuario.Usuario;
import lombok.Data;

@Data
public class CriarUsuarioOutput {

    Long id;

    private CriarUsuarioOutput(Long id) {
        this.id = id;
    }

    public static CriarUsuarioOutput from(Usuario usuario) {
        return new CriarUsuarioOutput(usuario.getId());
    }
}
