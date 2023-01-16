package com.example.base.application.usuario.deletar;


import com.example.base.infra.usuario.persistence.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import static com.example.base.domain.usuario.Usuario.newUsuario;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeletarUsuarioUseCaseTest {

    @InjectMocks
    private DeletarUsuarioUseCase deletarUsuarioUseCase;
    @Mock
    private UsuarioRepository usuarioRepository;


    @Test
    public void dadoUmCommandValido_quandoExecutarDeletarUsuario_deveEstarOk() {
        //setup
        final var id = "123";
        final var command = DeletarUsuarioCommand.with(id);

        when(usuarioRepository.existsById(id))
                .thenReturn(Boolean.TRUE);
        doNothing().when(usuarioRepository)
                .deleteById(id);

        //execute
        Assertions.assertDoesNotThrow(() -> deletarUsuarioUseCase.execute(command));

        //verify
        Mockito.verify(usuarioRepository, times(1)).existsById(any());
        Mockito.verify(usuarioRepository, times(1)).deleteById(any());
    }

}
