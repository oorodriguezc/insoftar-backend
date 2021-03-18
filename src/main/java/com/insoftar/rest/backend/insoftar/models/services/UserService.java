package com.insoftar.rest.backend.insoftar.models.services;

import com.insoftar.rest.backend.insoftar.models.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * <h1>UserService</h1>
 * Interfaz para el manejo de la lógica de negocio de los usuarios
 *
 * @author Oscar Orlando Rodriguez Cristancho <oorodriguezc@gmail.com>
 * @version 1.0
 * @since 14-03-2021
 */
public interface UserService {
    Page<UserDTO> findAll(Pageable pageable);

    UserDTO save(UserDTO userDTO);

    UserDTO findById(Long id);

    void deleteById(Long id);
}
