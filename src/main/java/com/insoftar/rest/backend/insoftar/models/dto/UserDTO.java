package com.insoftar.rest.backend.insoftar.models.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

/**
 * <h1>UserDTO</h1>
 * Usuario DTO para la operación con la lógica de negocio
 *
 * @author Oscar Orlando Rodriguez Cristancho <oorodriguezc@gmail.com>
 * @version 1.0.0
 * @since 14-03-2021
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("firstname")
    @NotEmpty(message = "El nombre es requerido")
    @Size(min = 4, max = 100, message = "El nombre debe estar entre 4 y 100 caracteres")
    private String firstname;

    @JsonProperty("lastname")
    @NotEmpty(message = "Los apellidos son requerido")
    @Size(min = 4, max = 100, message = "El apellido debe estar entre 4 y 100 caracteres")
    private String lastname;

    @JsonProperty("idNumber")
    @Pattern(regexp = "^\\d*$", message = "El número de identificación debe ser numérico")
    private String idNumber;

    @JsonProperty("phone")
    @Pattern(regexp = "^\\d*$", message = "El teléfono debe ser numérico")
    private String phone;

    @JsonProperty("email")
    @Email(message = "El formato no es el de un correo electrónico")
    private String email;
}
