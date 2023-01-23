package com.example.base.application.usuario.recuperar.obter;

import com.example.base.domain.usuario.Usuario;

import java.time.Instant;

public class ObterUsuarioPorIDOutput {

    private String nome;
    private String email;
    private Instant dataCriacao;
    private Instant dataExclusao;

    public ObterUsuarioPorIDOutput() {

    }
    public ObterUsuarioPorIDOutput(String nome, String email, Instant dataCriacao, Instant dataExclusao) {
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
