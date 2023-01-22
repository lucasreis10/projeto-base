package com.example.base.application.usuario.recuperar.listar;

import com.example.base.domain.usuario.Usuario;

import java.time.Instant;
import java.util.Objects;

public class ListaUsuarioOutput {

    private final String nome;
    private final String email;
    private final Instant dataCriacao;
    private final Instant dataExlusao;

    public ListaUsuarioOutput(String nome, String email, Instant dataCriacao, Instant dataExlusao) {
        this.nome = nome;
        this.email = email;
        this.dataCriacao = dataCriacao;
        this.dataExlusao = dataExlusao;
    }


    public static ListaUsuarioOutput from(Usuario usuario) {
        return new ListaUsuarioOutput(
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getDataCriacao(),
                usuario.getDataExclusao()
        );
    }


    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public Instant getDataCriacao() {
        return dataCriacao;
    }

    public Instant getDataExlusao() {
        return dataExlusao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListaUsuarioOutput that = (ListaUsuarioOutput) o;
        return Objects.equals(getNome(), that.getNome()) && Objects.equals(getEmail(), that.getEmail()) && Objects.equals(getDataCriacao(), that.getDataCriacao()) && Objects.equals(getDataExlusao(), that.getDataExlusao());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNome(), getEmail(), getDataCriacao(), getDataExlusao());
    }

}