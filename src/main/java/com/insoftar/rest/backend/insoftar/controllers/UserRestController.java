package com.insoftar.rest.backend.insoftar.controllers;

import com.insoftar.rest.backend.insoftar.config.constants.GeneralConstants;
import com.insoftar.rest.backend.insoftar.exceptions.RestException;
import com.insoftar.rest.backend.insoftar.models.dto.UserDTO;
import com.insoftar.rest.backend.insoftar.models.services.UserService;
import com.insoftar.rest.backend.insoftar.responses.RestResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

/**
 * <h1>UserRestController</h1>
 * Controlador REST para con los manejadores API de usuarios
 *
 * @author Oscar Orlando Rodriguez Cristancho <oorodriguezc@gmail.com>
 * @version 1.0.0
 * @since 14-03-2021
 */
@CrossOrigin(origins = {"http://localhost:4200", "*"})
@RestController
@RequestMapping("/api/v1/users")
public class UserRestController {

    private final UserService userService;

    /**
     * Controlador de la clase
     *
     * @param userService Servicio de usuarios
     */
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Método para obtener la lista de usuarios por paginación
     *
     * @param page Número de la página a consultar
     * @param size Tamaño de la página a consultar
     * @return Retorna un paginable con el resultado de la consulta
     * @throws RestException Controla con la excepción para la respuesta REST
     */
    @GetMapping(value = "/pages", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse<Page<UserDTO>> index(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) throws RestException {
        Pageable pageRequest = PageRequest.of(page, size, Sort.by("id").ascending());

        Page<UserDTO> data = this.userService.findAll(pageRequest);

        return new RestResponse<>(GeneralConstants.SUCCESS_TEXT, String.valueOf(HttpStatus.OK), GeneralConstants.OK_TEXT, data);
    }

    /**
     * Método para buscar un usuario según su ID
     *
     * @param id Parámetro con el ID del usuario a consultar
     * @return Retorno del ResponseEntity con los datos de respuesta y el estado
     */
    @GetMapping("/{id}")
    public RestResponse<UserDTO> show(@PathVariable Long id) throws RestException {

        return new RestResponse<>(GeneralConstants.SUCCESS_TEXT, String.valueOf(HttpStatus.OK), GeneralConstants.OK_TEXT, this.userService.findById(id));
    }

    /**
     * Método de creación de un usuario según los parámetros
     * <p>
     * //     * @param userRest Usuario a crear
     *
     * @param result Objeto con el resultado de la validación
     * @return Retorno del ResponseEntity con los datos de respuesta y el estado
     */
    @PostMapping("")
    public RestResponse<UserDTO> create(@Valid @RequestBody UserDTO userDTO, BindingResult result) throws RestException {

        this.getUserResultBindingError(result);

        return new RestResponse<>(GeneralConstants.SUCCESS_TEXT, String.valueOf(HttpStatus.CREATED), GeneralConstants.OK_TEXT, this.userService.save(userDTO));
    }

    /**
     * Método para la actualización de un usuario según los parámetros
     *
     * @param userDTO Usuario a modificar
     * @param result  Objeto con el resultado de la validación
     * @param id      Parámetro con el ID del usuario a eliminar
     * @return Retorno del ResponseEntity con los datos de respuesta y el estado
     */
    @PutMapping("/{id}")
    public RestResponse<UserDTO> update(@Valid @RequestBody UserDTO userDTO, @PathVariable Long id, BindingResult result) throws RestException {

        UserDTO userDTOActual = this.userService.findById(id);
        if (userDTOActual == null) {
            return new RestResponse<>(GeneralConstants.USER_NOT_FOUND_TEXT, String.valueOf(HttpStatus.NOT_FOUND), GeneralConstants.USER_NOT_FOUND_TEXT);
        }

        this.getUserResultBindingError(result);

        userDTO.setId(id);

        return new RestResponse<>(GeneralConstants.SUCCESS_TEXT, String.valueOf(HttpStatus.OK), GeneralConstants.OK_TEXT, this.userService.save(userDTO));
    }

    /**
     * Método para eliminar un usuario según su ID
     *
     * @param id Parámetro con el ID del usuario a eliminar
     * @return Retorno del ResponseEntity con los datos de respuesta y el estado
     */
    @DeleteMapping("/{id}")
    public RestResponse<Object> delete(@PathVariable Long id) throws RestException {

        this.userService.deleteById(id);

        return new RestResponse<>(GeneralConstants.SUCCESS_TEXT, String.valueOf(HttpStatus.OK), GeneralConstants.OK_TEXT);
    }

    private void getUserResultBindingError(BindingResult result) throws RestException {
        if (result.hasErrors()) {
            for (FieldError results : result.getFieldErrors()) {
                throw new RestException(results.getCode(), HttpStatus.BAD_REQUEST.value(), Objects.requireNonNull(results.getDefaultMessage()).toUpperCase());
            }
        }
    }

}
