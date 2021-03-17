package com.insoftar.rest.backend.insoftar.models.dto.mapper;

import com.insoftar.rest.backend.insoftar.models.dto.UserDTO;
import com.insoftar.rest.backend.insoftar.models.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * <h1>UserMapper</h1>
 * Implementación para el convertir entidades user a DTO y viseversa
 *
 * @author Oscar Orlando Rodriguez Cristancho <oorodriguezc@gmail.com>
 * @version 1.0.0
 * @since 17-03-2021
 */
@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDto(User user);

    User toEntity(UserDTO userDTO);

    List<UserDTO> toDto(List<User> users);

    List<User> toEntity(List<UserDTO> userDTOS);
}
