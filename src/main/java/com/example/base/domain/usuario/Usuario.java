package com.example.base.domain.usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;

@Entity
public class Usuario implements UserDetails {
    @Id
    private String id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String senha;

    @Column(name = "data_criacao", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant dataCriacao;

    @Column(name = "data_exclusao", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant dataExclusao;


    public Usuario() {}
    private Usuario(String id, String nome, String senha, String email) {
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

        return new Usuario(
                usuarioID.getValue(),
                nome,
                senha,
                email
        );
    }

    public Usuario desativar() {
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

    public String getId() {
        return id;
    }

    public Instant getDataCriacao() {
        return dataCriacao;
    }

    public Instant getDataExclusao() {
        return dataExclusao;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
