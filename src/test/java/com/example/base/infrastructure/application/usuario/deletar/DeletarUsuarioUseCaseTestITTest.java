package com.example.base.infrastructure.application.usuario.deletar;


import com.example.base.application.usuario.deletar.DeletarUsuarioCommand;
import com.example.base.application.usuario.deletar.DeletarUsuarioUseCase;
import com.example.base.domain.usuario.Usuario;
import com.example.base.infrastructure.IntegrationTest;
import com.example.base.infrastructure.usuario.persistence.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import static com.example.base.domain.usuario.Usuario.newUsuario;
import static org.junit.jupiter.api.Assertions.assertEquals;

@IntegrationTest
public class DeletarUsuarioUseCaseTestITTest {

    @Autowired
    private DeletarUsuarioUseCase deletarUsuarioUseCase;
    @Autowired
    private UsuarioRepository usuarioRepository;


    @Test
    public void dadoUmCommandValido_quandoExecutarDeletarUsuario_deveEstarOk() {
        //setup
        //execute
        assertEquals(0, usuarioRepository.count());

        Usuario usuario = usuarioRepository.save(
                newUsuario("dummy-nome", "dummy-senha", "dummy-email@email.com"));

        assertEquals(1, usuarioRepository.count());

        deletarUsuarioUseCase.execute(DeletarUsuarioCommand.with(usuario.getId()));

        //verify
        assertEquals(0, usuarioRepository.count());
    }

}
