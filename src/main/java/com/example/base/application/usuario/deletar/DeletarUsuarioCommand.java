package com.example.base.application.usuario.deletar;

public class DeletarUsuarioCommand {

    private final Long id;

    private DeletarUsuarioCommand(Long id) {
        this.id = id;
    }

    public static DeletarUsuarioCommand with(Long id) {
        return new DeletarUsuarioCommand(id);
    }

    public Long getId() {
        return id;
    }
}
