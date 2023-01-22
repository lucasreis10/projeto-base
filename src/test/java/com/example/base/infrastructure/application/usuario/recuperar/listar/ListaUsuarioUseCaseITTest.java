package com.example.base.infrastructure.application.usuario.recuperar.listar;


import com.example.base.application.usuario.recuperar.listar.ListarUsuarioUseCase;
import com.example.base.domain.pagination.SearchQuery;
import com.example.base.infrastructure.IntegrationTest;
import com.example.base.infrastructure.usuario.persistence.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.example.base.domain.usuario.Usuario.newUsuario;
import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.assertEquals;

@IntegrationTest
public class ListaUsuarioUseCaseITTest {

    @Autowired
    private ListarUsuarioUseCase listarUsuarioUseCase;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;



    @BeforeEach
    void mockUp() {
        final var usuarios = of(
                newUsuario("Monkey D. Luffy", "dsaeae2", "monkey.d.luffy@email.com"),
                newUsuario("Roronoa Zoro", "32asda", "zoro@email.com"),
                newUsuario("Nami", "asde423", "nami@email.com"),
                newUsuario("Sanji", "llzpla", "sanji@email.com"),
                newUsuario("Nico Robin", "zxeazmd", "nico@email.com"),
                newUsuario("Jimbei", "zndne", "jimbei@email.com")
        );

        usuarioRepository.saveAllAndFlush(usuarios);
    }

    @Test
    public void dadoUmQueryValida_quandoExecutarListarCategoriaComPesquiaSemCorrespondencia_entaoUmaListaCategoriaEhRetornada() {
        // setup:
        final var page = 0;
        final var perPage = 10;
        final var textoDePesquisa = "eosieo";
        final var sort = "nome";
        final var direction = "asc";
        final var total = 0;
        final var qtdItens = 0;

        final var query =
                new SearchQuery(page, perPage, textoDePesquisa, sort, direction);
        // execute
        final var outputPaginationPagination = listarUsuarioUseCase.execute(query);

        // verify
        assertEquals(qtdItens, outputPaginationPagination.getItems().size());
        assertEquals(page, outputPaginationPagination.getCurrentPage());
        assertEquals(perPage, outputPaginationPagination.getPerPage());
        assertEquals(total, outputPaginationPagination.getTotal());
    }

    @ParameterizedTest
    @CsvSource({
        "Monkey,0,10,1,1,Monkey D. Luffy",
        "Roro,0,10,1,1,Roronoa Zoro",
        "Nico,0,10,1,1,Nico Robin",
    })
    public void dadoUmaQueryValida_quandoExecutarListarCategoria_entaoListaUsuariosEhRetornada(
            final String textoDePesquisa,
            final int page,
            final int perPage,
            final int itemsCount,
            final long total,
            final String userName
    ) {
        // setup:
        final var sort = "nome";
        final var direction = "asc";
        final var query =
                new SearchQuery(page, perPage, textoDePesquisa, sort, direction);

        // execute:
        final var outputPagination = listarUsuarioUseCase.execute(query);
        
        // verify:
        Assertions.assertEquals(itemsCount, outputPagination.getItems().size());
        Assertions.assertEquals(page, outputPagination.getCurrentPage());
        Assertions.assertEquals(perPage, outputPagination.getPerPage());
        Assertions.assertEquals(total, outputPagination.getTotal());
        Assertions.assertEquals(userName, outputPagination.getItems().get(0).getNome());
    }


}
