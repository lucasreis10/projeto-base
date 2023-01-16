package com.example.base.application.usuario.desativar;

public class DesativarUsuarioCommand {

    private String id;

    private DesativarUsuarioCommand(String id) {
        this.id = id;
    }

    public static DesativarUsuarioCommand with(String id) {
        return new DesativarUsuarioCommand(id);
    }

    public String getId() {
        return id;
    }
}
