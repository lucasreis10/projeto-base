package com.example.base.application.usuario.login;

import com.example.base.application.UseCase;
import com.example.base.application.usuario.exception.UsuarioOuSenhaIncorretosException;
import com.example.base.domain.usuario.Usuario;
import com.example.base.infrastructure.security.JwtService;
import com.example.base.infrastructure.usuario.persistence.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


@Component
public class LoginUsuarioUseCase extends UseCase<LoginUsuarioCommand, LoginUsuarioOutput> {

    private final UsuarioRepository usuarioRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Autowired
    public LoginUsuarioUseCase(
            UsuarioRepository usuarioRepository,
            AuthenticationManager authenticationManager,
            JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public LoginUsuarioOutput execute(LoginUsuarioCommand input) {
        autenticarUsuario(input);

        final var usuario = obterUsuarioPor(input.getEmail());
        final var jwtToken = jwtService.generateToken(usuario.getEmail());

        return LoginUsuarioOutput.with(jwtToken);
    }

    private Usuario obterUsuarioPor(String email) {
        return usuarioRepository
                .findByEmail(email)
                .orElseThrow(UsuarioOuSenhaIncorretosException::new);
    }

    private void autenticarUsuario(LoginUsuarioCommand input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getSenha()
                )
        );
    }

}
