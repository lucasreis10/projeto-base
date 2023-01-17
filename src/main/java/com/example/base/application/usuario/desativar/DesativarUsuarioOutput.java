package com.example.base.application.usuario.desativar;

import com.example.base.domain.usuario.Usuario;

import java.time.Instant;

public class DesativarUsuarioOutput {

    private final String id;
    private final String email;
    private final Instant dataCriacao;
    private final Instant dataExclusao;


    private DesativarUsuarioOutput(String id, String email, Instant dataCriacao, Instant dataExclusao) {
        this.id = id;
        this.email = email;
        this.dataCriacao = dataCriacao;
        this.dataExclusao = dataExclusao;
    }

    public static DesativarUsuarioOutput from(Usuario usuario){
        return new DesativarUsuarioOutput(
          usuario.getId(),
          usuario.getEmail(),
          usuario.getDataCriacao(),
          usuario.getDataExclusao()
        );
    }

    public String getId() {
        return id;
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
