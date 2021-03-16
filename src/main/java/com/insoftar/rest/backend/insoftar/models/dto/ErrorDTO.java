package com.insoftar.rest.backend.insoftar.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * <h1>ErrorDTO</h1>
 * Objeto para el manejo de los datos de retorno de los errores
 *
 * @author Oscar Orlando Rodriguez Cristancho <oorodriguezc@gmail.com>
 * @version 1.0.0
 * @since 14-03-2021
 */
@Getter
@Setter
public class ErrorDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String name;

    private String value;

    public ErrorDTO(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
