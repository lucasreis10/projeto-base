package com.example.base.application.usuario.criar;

import com.example.base.application.UseCase;
import com.example.base.domain.usuario.Usuario;
import com.example.base.infrastructure.usuario.persistence.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.base.domain.usuario.Usuario.*;

@Service
public class CriarUsuarioUseCase extends UseCase<CriarUsuarioCommand, CriarUsuarioOutput> {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CriarUsuarioUseCase(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public CriarUsuarioOutput execute(CriarUsuarioCommand command) {
        var senhaEncode = passwordEncoder.encode(command.getSenha());
        var usuario = newUsuario(command.getNome(), senhaEncode, command.getEmail());

        Usuario novoUsuario = repository.save(usuario);

        return CriarUsuarioOutput.from(novoUsuario);
    }


}
