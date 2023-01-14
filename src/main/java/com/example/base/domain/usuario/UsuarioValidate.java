package com.example.base.domain.usuario;

import com.example.base.domain.exception.DomainException;
import com.example.base.domain.validation.Validator;

public class UsuarioValidate implements Validator {

    private static final int TAMANHO_MINIMO = 3;
    private static final int TAMANHO_MAXIMO = 255;
    private Usuario usuario;

    public UsuarioValidate(Usuario usuario) {
        this.usuario = usuario;
    }


    @Override
    public void validate() {
        checarRegraNome();
    }

    private void checarRegraNome() {
        final var nome = usuario.getNome();

        if(nome == null) {
            throw new DomainException("'nome' não pode ser nulo.");
        }

        if(nome.isBlank()) {
            throw new DomainException("'nome' não pode ser vazio.");
        }

        if(nome.length() < TAMANHO_MINIMO || nome.length() >= TAMANHO_MAXIMO) {
            throw new DomainException("'nome' deve ter 3 a 255 caracteres.");
        }

    }
}
