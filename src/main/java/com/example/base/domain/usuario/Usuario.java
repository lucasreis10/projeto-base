package com.example.base.domain.usuario;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Usuario {

    @Id
    private final UsuarioID id;
    private final String nome;
    private final String email;
    private final String senha;

    private Usuario(UsuarioID id, String nome, String senha, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        validar();
    }

    private void validar() {
        new UsuarioValidate(this).validate();
    }

    public static Usuario newUsuario(String nome, String senha, String email) {
        UsuarioID usuarioID = UsuarioID.unique();
        return new Usuario(usuarioID, nome, senha, email);
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public UsuarioID getId() {
        return id;
    }
}
