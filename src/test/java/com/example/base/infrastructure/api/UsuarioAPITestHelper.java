package com.example.base.infrastructure.api;

import com.example.base.application.usuario.recuperar.listar.ListaUsuarioOutput;
import com.example.base.domain.pagination.Pagination;

import java.time.Instant;
import java.util.List;

public class UsuarioAPITestHelper {

    private UsuarioAPITestHelper() {}

    public static Pagination<ListaUsuarioOutput> paginationListaUsuarioOutput() {
        final var listaUsuarioOutputs = listaUsuarioOutputs();

        return new Pagination<ListaUsuarioOutput>(1, 10, 2, listaUsuarioOutputs);
    }

    private static List<ListaUsuarioOutput> listaUsuarioOutputs() {
        final var now = Instant.now();

        return List.of(
                new ListaUsuarioOutput("dummy-a", "dummy-a@email.com", now, null),
                new ListaUsuarioOutput("dummy-b", "dummy-b@email.com", now, null)
        );
    }

}
