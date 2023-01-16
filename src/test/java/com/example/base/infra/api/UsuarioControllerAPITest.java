package com.example.base.infra.api;

import com.example.base.application.usuario.criar.CriarUsuarioUseCase;
import com.example.base.application.usuario.login.LoginUsuarioUseCase;
import com.example.base.infra.ControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@ControllerTest(controllers = UsuarioAPI.class)
public class UsuarioControllerAPITest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private CriarUsuarioUseCase criarUsuarioUseCase;
    @MockBean
    private LoginUsuarioUseCase loginUsuarioUseCase;

    @Test
    public void teste() {

    }


}
