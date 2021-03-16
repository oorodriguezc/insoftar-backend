package com.insoftar.rest.backend.insoftar.exceptions;

import com.insoftar.rest.backend.insoftar.models.dto.ErrorDTO;
import lombok.Getter;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>RestException</h1>
 * Manejo de las excepciones del servicio REST
 *
 * @author Oscar Orlando Rodriguez Cristancho <oorodriguezc@gmail.com>
 * @version 1.0.0
 * @since 14-03-2021
 */
@Getter
public class RestException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String code;

    private final int responseCode;

    private final List<ErrorDTO> errorList = new ArrayList<>();

    public RestException(String code, int responseCode, String message) {
        super(message);
        this.code = code;
        this.responseCode = responseCode;
    }

    public RestException(String code, int responseCode, String message, List<ErrorDTO> errorList) {
        super(message);
        this.code = code;
        this.responseCode = responseCode;
        this.errorList.addAll(errorList);
    }
}
