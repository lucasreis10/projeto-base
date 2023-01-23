package com.example.base.infrastructure.api.controllers;

import com.example.base.application.usuario.criar.CriarUsuarioOutput;
import com.example.base.application.usuario.criar.CriarUsuarioUseCase;
import com.example.base.application.usuario.desativar.DesativarUsuarioCommand;
import com.example.base.application.usuario.desativar.DesativarUsuarioOutput;
import com.example.base.application.usuario.desativar.DesativarUsuarioUseCase;
import com.example.base.application.usuario.recuperar.listar.ListaUsuarioOutput;
import com.example.base.application.usuario.recuperar.listar.ListarUsuarioUseCase;
import com.example.base.application.usuario.recuperar.obter.ObterUsuarioPorIDOutput;
import com.example.base.application.usuario.recuperar.obter.ObterUsuarioPorIDUseCase;
import com.example.base.domain.pagination.Pagination;
import com.example.base.domain.pagination.SearchQuery;
import com.example.base.infrastructure.api.UsuarioAPI;
import com.example.base.infrastructure.usuario.models.CriarUsuarioAPIInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static com.example.base.application.usuario.criar.CriarUsuarioCommand.with;

@RestController
public class UsuarioController implements UsuarioAPI {

    private final CriarUsuarioUseCase criarUsuarioUseCase;
    private final DesativarUsuarioUseCase desativarUsuarioUseCase;
    private final ListarUsuarioUseCase listarUsuarioUseCase;
    private final ObterUsuarioPorIDUseCase obterUsuarioPorIDUseCase;

    @Autowired
    public UsuarioController(
            CriarUsuarioUseCase criarUsuarioUseCase,
            DesativarUsuarioUseCase desativarUsuarioUseCase,
            ListarUsuarioUseCase listarUsuarioUseCase,
            ObterUsuarioPorIDUseCase obterUsuarioPorIDUseCase) {
        this.criarUsuarioUseCase = criarUsuarioUseCase;
        this.desativarUsuarioUseCase = desativarUsuarioUseCase;
        this.listarUsuarioUseCase = listarUsuarioUseCase;
        this.obterUsuarioPorIDUseCase = obterUsuarioPorIDUseCase;
    }

    @Override
    public ResponseEntity<?> criarUsuario(final CriarUsuarioAPIInput input) {
        final var command =
                with(input.getNome(), input.getSenha(), input.getEmail());


        CriarUsuarioOutput output = criarUsuarioUseCase.execute(command);

        return ResponseEntity
                .created(URI.create("/usuarios/" + output.getId()))
                .body(output);
    }

    @Override
    public ResponseEntity<?> desativar(String input) {
        DesativarUsuarioCommand command = DesativarUsuarioCommand.with(input);

        DesativarUsuarioOutput output = desativarUsuarioUseCase.execute(command);

        return ResponseEntity.ok(output);
    }

    @Override
    public Pagination<ListaUsuarioOutput> listarUsuarios(
            String search,
            int page,
            int perPage,
            String sort,
            String direction
    ) {
        final var query = new SearchQuery(page, perPage, search, sort, direction);

        return listarUsuarioUseCase.execute(query);
    }

    @Override
    public ResponseEntity<?> obterUsuario(String input) {
        final var output = obterUsuarioPorIDUseCase.execute(input);

        return ResponseEntity.ok(output);
    }

}
