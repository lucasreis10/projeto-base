package com.example.base.application.usuario.desativar;


import com.example.base.infra.usuario.persistence.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.example.base.domain.usuario.Usuario.newUsuario;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DesativarUsuarioUseCaseTest {

    @InjectMocks
    private DesativarUsuarioUseCase desativarUsuarioUseCase;
    @Mock
    private UsuarioRepository usuarioRepository;


    @Test
    public void dadoUmCommandValido_quandoExecutarDesativarUsuario_deveRetornarUsuarioDesativado() {
        //setup
        final var id = "123";
        final var command = DesativarUsuarioCommand.with(id);
        final var usuarioEsperado = newUsuario("dummy", "dummy", "dummy");

        when(usuarioRepository.findById(id))
                .thenReturn(Optional.of(usuarioEsperado));

        //execute
        final var output = desativarUsuarioUseCase.execute(command);

        //verify
        verify(usuarioRepository, times(1)).findById(any());
        verify(usuarioRepository, times(1)).save(any());
        assertNotNull(output.getId());
        assertNotNull(output.getDataExclusao());
        assertTrue(output.getDataCriacao().isBefore(output.getDataExclusao()));
    }

}
