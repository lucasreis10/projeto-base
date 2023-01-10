package com.example.base.domain.usuario;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
public class Usuario {

    @Id
    private Long id;
    private String nome;
    private String email;
    private String senha;

    private Usuario(Long id, String nome, String senha, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public static Usuario newUsuario(String nome, String senha, String email) {
        return new Usuario(null, nome, senha, email);
    }


}
