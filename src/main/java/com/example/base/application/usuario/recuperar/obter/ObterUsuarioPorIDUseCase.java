package com.example.base.application.usuario.recuperar.obter;

import com.example.base.application.UseCase;
import com.example.base.application.usuario.exception.NotFoundException;
import com.example.base.domain.usuario.Usuario;
import com.example.base.infrastructure.usuario.persistence.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ObterUsuarioPorIDUseCase extends UseCase<String, ObterUsuarioPorIDOutput> {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public ObterUsuarioPorIDUseCase(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public ObterUsuarioPorIDOutput execute(String input) {
        return usuarioRepository
                .findById(input)
                .map(ObterUsuarioPorIDOutput::from)
                .orElseThrow(() -> NotFoundException.with(Usuario.class, input));
    }
}
