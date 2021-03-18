package com.insoftar.rest.backend.insoftar.models.services.impl;

import com.insoftar.rest.backend.insoftar.config.constants.GeneralConstants;
import com.insoftar.rest.backend.insoftar.models.dto.UserDTO;
import com.insoftar.rest.backend.insoftar.models.dto.converters.UserConverter;
import com.insoftar.rest.backend.insoftar.models.dto.mapper.UserMapper;
import com.insoftar.rest.backend.insoftar.models.entities.User;
import com.insoftar.rest.backend.insoftar.models.repositories.UserRepository;
import com.insoftar.rest.backend.insoftar.models.services.UserService;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

/**
 * <h1>UserServiceImpl</h1>
 * Servicio para el manejo de la lógica de negocio de los usuarios
 *
 * @author Oscar Orlando Rodriguez Cristancho <oorodriguezc@gmail.com>
 * @version 1.0
 * @since 14-03-2021
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userConverter = new UserConverter();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDTO> findAll(Pageable pageable) {
        Page<User> users = this.userRepository.findAll(pageable);

        return this.userConverter.toDTOPage(pageable, users);
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        User userCreated;
        User userNew = new User();
        boolean update = false;

        if (userDTO.getId() != null && this.userRepository.findById(userDTO.getId()).isPresent()) {
            update = true;
            userNew.setId(userDTO.getId());
        }

        User userSearch = this.userRepository.findByEmail(userDTO.getEmail());
        if (userSearch != null && !update) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, GeneralConstants.EMAIL_ALREADY_EXIST_TEXT);
        }

        userSearch = this.userRepository.findByIdNumber(userDTO.getIdNumber());
        if (userSearch != null && !update) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, GeneralConstants.ID_NUMBER_ALREADT_EXIST_TEXT);
        }

        userNew.setFirstname(userDTO.getFirstname());
        userNew.setLastname(userDTO.getLastname());
        userNew.setEmail(userDTO.getEmail());
        userNew.setIdNumber(userDTO.getIdNumber());
        userNew.setPhone(userDTO.getPhone());

        try {
            userCreated = this.userRepository.save(userNew);
        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, String.valueOf(e.getMostSpecificCause()), e);
        }

        return UserMapper.INSTANCE.toDto(userCreated);
    }

    @Override
    public UserDTO findById(Long id) {
        User user = this.getUserEntity(id);

        if (user == null) {

            return null;
        }

        return UserMapper.INSTANCE.toDto(user);
    }

    @Override
    public void deleteById(Long id) {
        if (Optional.ofNullable(this.getUserEntity(id)).isPresent()) {
            this.userRepository.deleteById(id);
        }
    }

    private User getUserEntity(Long id) {

        return this.userRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, GeneralConstants.USER_NOT_FOUND_TEXT)
        );
    }

}
