package com.example.base.application.usuario.recuperar.listar;

import com.example.base.domain.pagination.Pagination;
import com.example.base.domain.pagination.SearchQuery;
import com.example.base.domain.usuario.Usuario;
import com.example.base.infrastructure.usuario.persistence.UsuarioMySQLGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.example.base.application.usuario.recuperar.listar.ListaUsuarioUseCaseTestHelper.listaUsuarios;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListarUsuarioUseCaseTest {


    @InjectMocks
    private ListarUsuarioUseCase listarUsuarioUseCase;
    @Mock
    private UsuarioMySQLGateway usuarioMySQLGateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(usuarioMySQLGateway);
    }


    @Test
    public void dadoUmaQueryValida_quandoExecutarListarUsuario_entaoUmaListaDeUsuarioEhRetornada() {
        // setup:
        final var usuarios = listaUsuarios();
        final var page = 0;
        final var perPage = 10;
        final var terms = "";
        final var sort = "dataCriacao";
        final var direction = "asc";
        final var qtdItens = 2;
        final var query = new SearchQuery(page, perPage, terms, sort, direction);
        final var pagination = new Pagination<>(page, perPage, usuarios.size(), usuarios);
        final var resultadoEsperado = pagination.map(ListaUsuarioOutput::from);

        when(usuarioMySQLGateway.obterTodos(eq(query)))
                .thenReturn(pagination);

        // execute:
        final var output = listarUsuarioUseCase.execute(query);

        // verify:
        assertEquals(qtdItens, resultadoEsperado.getItems().size());
        assertEquals(resultadoEsperado, output);
        assertEquals(page, resultadoEsperado.getCurrentPage());
        assertEquals(perPage, resultadoEsperado.getPerPage());
        assertEquals(usuarios.size(), resultadoEsperado.getTotal());
    }


}
