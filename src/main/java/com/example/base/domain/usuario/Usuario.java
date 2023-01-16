package com.example.base.domain.usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.Instant;

@Entity
public class Usuario {

    @Id
    private UsuarioID id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String senha;

    @Column(name = "data_criacao", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant dataCriacao;

    @Column(name = "data_exclusao", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant dataExclusao;

    private Usuario(UsuarioID id, String nome, String senha, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.dataCriacao = Instant.now();
        validar();
    }

    private void validar() {
        new UsuarioValidate(this).validate();
    }

    public static Usuario newUsuario(String nome, String senha, String email) {
        UsuarioID usuarioID = UsuarioID.unique();
        return new Usuario(usuarioID, nome, senha, email);
    }

    public Usuario deletar() {
        if(this.dataExclusao == null)  {
            this.dataExclusao = Instant.now();
        }

        return this;
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

    public Instant getDataCriacao() {
        return dataCriacao;
    }

    public Instant getDataExclusao() {
        return dataExclusao;
    }

}
