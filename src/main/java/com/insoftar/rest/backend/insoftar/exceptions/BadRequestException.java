package com.insoftar.rest.backend.insoftar.exceptions;

import com.insoftar.rest.backend.insoftar.models.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;
import java.util.Collections;

/**
 * <h1>BadRequestException</h1>
 * Manejo de las excepciones por solicitudes REST erroneas
 *
 * @author Oscar Orlando Rodriguez Cristancho <oorodriguezc@gmail.com>
 * @version 1.0.0
 * @since 14-03-2021
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RestException {

    @Serial
    private static final long serialVersionUID = 1L;

    public BadRequestException(String code, String message) {
        super(code, HttpStatus.BAD_REQUEST.value(), message);
    }

    public BadRequestException(String code, String message, ErrorDTO data) {
        super(code, HttpStatus.BAD_REQUEST.value(), message, Collections.singletonList(data));
    }

}
