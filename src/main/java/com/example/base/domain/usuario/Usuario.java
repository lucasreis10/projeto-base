package com.example.base.domain.usuario;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
public class Usuario {

    @Id
    private final Long id;
    private final String nome;
    private final String email;
    private final String senha;

    private Usuario(Long id, String nome, String senha, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public static Usuario newUsuario(String nome, String senha, String email) {
        return new Usuario(null, nome, senha, email);
    }

    public Long getId() {
        return id;
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
}
