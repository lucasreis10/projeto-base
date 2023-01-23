package com.example.base.application.usuario.recuperar.listar;

import com.example.base.domain.usuario.Usuario;

import java.util.List;

public class ListaUsuarioUseCaseTestHelper {

    private ListaUsuarioUseCaseTestHelper(){}

    public static List<Usuario> listaUsuarios() {
        return List.of(
                Usuario.newUsuario("dummy A", "dummy senha A", "dummy-a@email.com"),
                Usuario.newUsuario("dummy B", "dummy senha B", "dummy-b@email.com")
        );
    }

}
