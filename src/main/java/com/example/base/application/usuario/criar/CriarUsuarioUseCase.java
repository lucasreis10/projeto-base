package com.example.base.application.usuario.criar;

import com.example.base.domain.usuario.Usuario;
import com.example.base.infra.usuario.persistence.UsuarioRepository;
import org.springframework.stereotype.Service;

import static com.example.base.domain.usuario.Usuario.*;

@Service
public class CriarUsuarioUseCase {

    private final UsuarioRepository repository;

    public CriarUsuarioUseCase(UsuarioRepository repository) {
        this.repository = repository;
    }

    public CriarUsuarioOutput execute(CriarUsuarioCommand command) {
        var usuario = newUsuario(command.getNome(), command.getSenha(), null);

        Usuario novoUsuario = repository.save(usuario);

        return CriarUsuarioOutput.from(novoUsuario);
    }


}
