package com.example.base.application.usuario.login;

import com.example.base.application.UnitUseCase;
import com.example.base.application.usuario.exception.UsuarioOuSenhaIncorretosException;
import com.example.base.infrastructure.usuario.persistence.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class LoginUsuarioUseCase extends UnitUseCase<LoginUsuarioCommand> {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public LoginUsuarioUseCase(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void execute(LoginUsuarioCommand input) {
        usuarioRepository
                .findByEmailAndSenha(input.getEmail(), input.getSenha())
                .orElseThrow(UsuarioOuSenhaIncorretosException::new);
    }

}
