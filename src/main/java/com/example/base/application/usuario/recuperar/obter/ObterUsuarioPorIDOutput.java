package com.example.base.application.usuario.recuperar.obter;

import com.example.base.domain.usuario.Usuario;

import java.time.Instant;

public class ObterUsuarioPorIDOutput {

    private final String nome;
    private final String email;
    private final Instant dataCriacao;
    private final Instant dataExclusao;

    private ObterUsuarioPorIDOutput(String nome, String email, Instant dataCriacao, Instant dataExclusao) {
        this.nome = nome;
        this.email = email;
        this.dataCriacao = dataCriacao;
        this.dataExclusao = dataExclusao;
    }

    public static ObterUsuarioPorIDOutput from(Usuario usuario) {
        return new ObterUsuarioPorIDOutput(
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

    public Instant getDataExclusao() {
        return dataExclusao;
    }
}
