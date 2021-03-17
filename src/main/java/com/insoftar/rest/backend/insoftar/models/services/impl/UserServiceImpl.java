package com.insoftar.rest.backend.insoftar.models.services.impl;

import com.insoftar.rest.backend.insoftar.config.constants.GeneralConstants;
import com.insoftar.rest.backend.insoftar.exceptions.BadRequestException;
import com.insoftar.rest.backend.insoftar.exceptions.InternalServerErrorException;
import com.insoftar.rest.backend.insoftar.exceptions.NotFountException;
import com.insoftar.rest.backend.insoftar.exceptions.RestException;
import com.insoftar.rest.backend.insoftar.models.dto.UserDTO;
import com.insoftar.rest.backend.insoftar.models.dto.converters.UserConverter;
import com.insoftar.rest.backend.insoftar.models.dto.mapper.UserMapper;
import com.insoftar.rest.backend.insoftar.models.entities.User;
import com.insoftar.rest.backend.insoftar.models.repositories.UserRepository;
import com.insoftar.rest.backend.insoftar.models.services.UserService;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public UserDTO save(UserDTO userDTO) throws RestException {
        User userCreated;
        User userNew = new User();
        boolean update = false;

        if (userDTO.getId() != null && this.userRepository.findById(userDTO.getId()).isPresent()) {
            update = true;
            userNew.setId(userDTO.getId());
        }

        User userSearch = this.userRepository.findByEmail(userDTO.getEmail());
        if (userSearch != null && !update) {
            throw new BadRequestException(GeneralConstants.EMAIL_ALREADT_EXIST_TEXT, GeneralConstants.EMAIL_ALREADT_EXIST_TEXT);
        }

        userSearch = this.userRepository.findByIdNumber(userDTO.getIdNumber());
        if (userSearch != null && !update) {
            throw new BadRequestException(GeneralConstants.ID_NUMBER_ALREADT_EXIST_TEXT, GeneralConstants.ID_NUMBER_ALREADT_EXIST_TEXT);
        }

        userNew.setFirstname(userDTO.getFirstname());
        userNew.setLastname(userDTO.getLastname());
        userNew.setEmail(userDTO.getEmail());
        userNew.setIdNumber(userDTO.getIdNumber());
        userNew.setPhone(userDTO.getPhone());

        try {
            userCreated = this.userRepository.save(userNew);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException(GeneralConstants.INTERNAL_SERVER_ERROR_TEXT, String.valueOf(e.getMostSpecificCause()));
        }

        return UserMapper.INSTANCE.toDto(userCreated);
    }

    @Override
    public UserDTO findById(Long id) throws RestException {
        User user = this.getUserEntity(id);

        if (user == null) {

            return null;
        }

        return UserMapper.INSTANCE.toDto(user);
    }

    @Override
    public void deleteById(Long id) throws RestException {
        if (Optional.ofNullable(this.getUserEntity(id)).isPresent()) {
            this.userRepository.deleteById(id);
        }
    }

    private User getUserEntity(Long id) throws RestException {

        return this.userRepository.findById(id).orElseThrow(() -> new NotFountException("UNF-404", "USER_NOT_FOUND"));
    }

}
