package com.example.base.application.usuario.recuperar.listar;

import com.example.base.application.UseCase;
import com.example.base.domain.pagination.Pagination;
import com.example.base.domain.pagination.SearchQuery;
import com.example.base.infrastructure.usuario.persistence.UsuarioMySQLGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ListarUsuarioUseCase extends
        UseCase<SearchQuery, Pagination<ListaUsuarioOutput>> {

    private final UsuarioMySQLGateway usuarioMySQLGateway;


    @Autowired
    public ListarUsuarioUseCase(UsuarioMySQLGateway usuarioMySQLGateway) {
        this.usuarioMySQLGateway = usuarioMySQLGateway;
    }

    @Override
    public Pagination<ListaUsuarioOutput> execute(SearchQuery searchQuery) {
        return usuarioMySQLGateway.obterTodos(searchQuery)
                .map(ListaUsuarioOutput::from);
    }


}
