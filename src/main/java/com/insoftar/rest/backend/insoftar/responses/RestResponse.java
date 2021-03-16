package com.insoftar.rest.backend.insoftar.responses;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * <h1>RestResponse</h1>
 * Clase para el manejo de las respuestas REST
 *
 * @author Oscar Orlando Rodriguez Cristancho <oorodriguezc@gmail.com>
 * @version 1.0
 * @since 14-03-2021
 */
@Getter
@Setter
public class RestResponse<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String status;
    private final String code;
    private final String message;
    private T data;

    public RestResponse(String status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public RestResponse(String status, String code, String message, T data) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
