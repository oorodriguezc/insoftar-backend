package com.insoftar.rest.backend.insoftar.exceptions;

import com.insoftar.rest.backend.insoftar.models.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;
import java.util.Collections;

/**
 * <h1>InternalServerErrorException</h1>
 * Manejo de las excepciones por errores internos
 *
 * @author Oscar Orlando Rodriguez Cristancho <oorodriguezc@gmail.com>
 * @version 1.0.0
 * @since 14-03-2021
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerErrorException extends RestException {

    @Serial
    private static final long serialVersionUID = 1L;

    public InternalServerErrorException(String code, String message) {
        super(code, HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
    }

    public InternalServerErrorException(String code, String message, ErrorDTO data) {
        super(code, HttpStatus.INTERNAL_SERVER_ERROR.value(), message, Collections.singletonList(data));
    }

}
