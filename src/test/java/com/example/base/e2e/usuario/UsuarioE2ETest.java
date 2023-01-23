package com.example.base.e2e.usuario;


import com.example.base.application.usuario.criar.CriarUsuarioUseCase;
import com.example.base.infrastructure.E2ETest;
import com.example.base.infrastructure.security.JwtAuthenticationFilter;
import com.example.base.infrastructure.security.JwtService;
import com.example.base.infrastructure.usuario.persistence.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;



@E2ETest
@Testcontainers
public class UsuarioE2ETest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private JwtAuthenticationFilter authFilter;
    @Autowired
    private JwtService jwtService;
    @MockBean
    private UsuarioRepository usuarioRepository;
    @MockBean
    private CriarUsuarioUseCase criarUsuarioUseCase;

    @Container
    private static final MySQLContainer MY_SQL_CONTAINER =
            new MySQLContainer("mysql:latest")
                    .withPassword("123456")
                    .withUsername("root")
                    .withDatabaseName("projeto_base");

    @DynamicPropertySource
    public static void setDataSourceProperties(final DynamicPropertyRegistry registry) {
        final var mappedPort = MY_SQL_CONTAINER.getMappedPort(3306);
        System.out.printf("Container is running on port %s ", mappedPort);
        registry.add("mysql.port", () -> mappedPort);
    }

    @Test
    public void teste() {
        Assertions.assertTrue(MY_SQL_CONTAINER.isRunning());
    }

}
