package com.insoftar.rest.backend.insoftar.models.dto.converters;

import com.insoftar.rest.backend.insoftar.models.dto.UserDTO;
import com.insoftar.rest.backend.insoftar.models.dto.mapper.UserMapper;
import com.insoftar.rest.backend.insoftar.models.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * <h1>UserConverter</h1>
 * Implementación para el convertir entidades Usuarios a DTO y viseversa
 *
 * @author Oscar Orlando Rodriguez Cristancho <oorodriguezc@gmail.com>
 * @version 1.0.0
 * @since 17-03-2021
 */
public class UserConverter implements AbstractConverter<User, UserDTO> {
    @Override
    public Page<UserDTO> toDTOPage(Pageable pageRequest, Page<User> users) {

        List<UserDTO> userDTOS = UserMapper.INSTANCE.toDto(users.getContent());

        return new PageImpl<>(userDTOS, pageRequest, users.getTotalElements());
    }
}
