package com.example.base.application.usuario.desativar;

import com.example.base.application.UseCase;
import com.example.base.application.usuario.exception.NotFoundException;
import com.example.base.domain.usuario.Usuario;
import com.example.base.infrastructure.usuario.persistence.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DesativarUsuarioUseCase extends UseCase<DesativarUsuarioCommand, DesativarUsuarioOutput> {


    private final UsuarioRepository usuarioRepository;

    @Autowired
    public DesativarUsuarioUseCase(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    @Override
    public DesativarUsuarioOutput execute(DesativarUsuarioCommand command) {
        final var id = command.getId();
        final var usuario = obterUsuario(id);
        final var usuarioDesativado = usuario.desativar();

        usuarioRepository.save(usuarioDesativado);

        return DesativarUsuarioOutput.from(usuarioDesativado);
    }

    private Usuario obterUsuario(String id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> NotFoundException.with(Usuario.class, id));
    }

}
