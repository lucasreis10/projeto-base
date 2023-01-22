package com.example.base.infrastructure.usuario.persistence;


import com.example.base.domain.pagination.Pagination;
import com.example.base.domain.pagination.SearchQuery;
import com.example.base.domain.usuario.Usuario;
import com.example.base.infrastructure.util.SpecificationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.example.base.infrastructure.util.SpecificationUtils.like;
import static org.springframework.data.domain.Sort.Direction.fromString;

@Component
public class UsuarioMySQLGateway {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioMySQLGateway(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    public Pagination<Usuario> obterTodos(final SearchQuery query) {

        final var page = PageRequest.of(
                query.getPage(),
                query.getPerPage(),
                Sort.by(fromString(query.getDirection()), query.getSort())
        );

        final var specification = Optional.ofNullable(query.getTerms())
                .filter(str ->  !str.isBlank())
                .map(str -> SpecificationUtils
                        .<Usuario>like("nome", str)
                        .or(like("email", str))
                )
                .orElse(null);

        final var pageResult =
                usuarioRepository.findAll(Specification.where(specification), page);

        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(usuario ->
                        Usuario.with(
                                usuario.getId(),
                                usuario.getNome(),
                                usuario.getEmail(),
                                usuario.getDataCriacao(),
                                usuario.getDataExclusao()
                        )).toList()
        );
    }


}
