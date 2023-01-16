package com.example.base.application.usuario.deletar;

import com.example.base.application.UnitUseCase;
import com.example.base.infrastructure.usuario.persistence.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeletarUsuarioUseCase extends UnitUseCase<DeletarUsuarioCommand> {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public DeletarUsuarioUseCase(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void execute(DeletarUsuarioCommand command) {
        final var id = command.getId();

        if(usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
        }

    }

}
