package com.example.base.application.usuario.login;

import com.example.base.application.usuario.exception.UsuarioOuSenhaIncorretosException;
import com.example.base.infra.usuario.persistence.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class LoginUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;

    public LoginUsuarioUseCase(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void execute(LoginUsuarioCommand input) {
        usuarioRepository.findByEmailAndSenha(input.getEmail(), input.getSenha())
                .orElseThrow(UsuarioOuSenhaIncorretosException::new);
    }

}
