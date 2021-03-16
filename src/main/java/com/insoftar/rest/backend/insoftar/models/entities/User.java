package com.insoftar.rest.backend.insoftar.models.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * <h1>User</h1>
 * Entidad para los usuarios
 *
 * @author Oscar Orlando Rodriguez Cristancho <oorodriguezc@gmail.com>
 * @version 1.0.0
 * @since 14-03-2021
 */
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El nombre es requerido")
    @Size(min = 4, max = 100, message = "El nombre debe estar entre 4 y 100 caracteres")
    @Column(nullable = false)
    private String firstname;

    @NotEmpty(message = "Los apellidos son requerido")
    @Size(min = 4, max = 100, message = "El apellido debe estar entre 4 y 100 caracteres")
    @Column(nullable = false)
    private String lastname;

    @Column(length = 20, unique = true)
    @Pattern(regexp = "^\\d*$", message = "El número de identificación debe ser numérico")
    private String idNumber;

    @Pattern(regexp = "^\\d*$", message = "El teléfono debe ser numérico")
    @Column(length = 10)
    private String phone;

    @Email(message = "El formato no es el de un correo electrónico")
    @Column(unique = true)
    private String email;

}
