package com.example.base.application.usuario.criar;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CriarUsuarioCommand {

    @NotNull
    @Size(min = 3, max = 255)
    private String nome;

    @NotNull
    @Size(min = 5, max = 90)
    private String senha;


}
