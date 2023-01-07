package com.github.moaresoliveira.bibliotecaadminapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CepInvalidoException extends RuntimeException {

    public CepInvalidoException(String cep) {
        super("Cep inválido: " + cep);
    }

}
