package com.insoftar.rest.backend.insoftar.exceptions;

import com.insoftar.rest.backend.insoftar.models.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;
import java.util.Collections;

/**
 * <h1>NotFountException</h1>
 * Manejo de las excepciones por errores al no encontrar la información requerida
 *
 * @author Oscar Orlando Rodriguez Cristancho <oorodriguezc@gmail.com>
 * @version 1.0.0
 * @since 14-03-2021
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFountException extends RestException {

    @Serial
    private static final long serialVersionUID = 1L;

    public NotFountException(String code, String message) {
        super(code, HttpStatus.NOT_FOUND.value(), message);
    }

    public NotFountException(String code, String message, ErrorDTO data) {
        super(code, HttpStatus.NOT_FOUND.value(), message, Collections.singletonList(data));
    }

}
