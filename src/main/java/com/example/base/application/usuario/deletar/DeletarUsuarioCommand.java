package com.example.base.application.usuario.deletar;

public class DeletarUsuarioCommand {

    private final String id;

    private DeletarUsuarioCommand(String id) {
        this.id = id;
    }

    public static DeletarUsuarioCommand with(String id) {
        return new DeletarUsuarioCommand(id);
    }

    public String getId() {
        return id;
    }
}
