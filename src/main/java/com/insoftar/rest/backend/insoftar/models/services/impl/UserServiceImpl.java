package com.insoftar.rest.backend.insoftar.models.services.impl;

import com.insoftar.rest.backend.insoftar.config.constants.GeneralConstants;
import com.insoftar.rest.backend.insoftar.exceptions.BadRequestException;
import com.insoftar.rest.backend.insoftar.exceptions.InternalServerErrorException;
import com.insoftar.rest.backend.insoftar.exceptions.NotFountException;
import com.insoftar.rest.backend.insoftar.exceptions.RestException;
import com.insoftar.rest.backend.insoftar.models.dto.UserDTO;
import com.insoftar.rest.backend.insoftar.models.entities.User;
import com.insoftar.rest.backend.insoftar.models.repositories.UserRepository;
import com.insoftar.rest.backend.insoftar.models.services.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public static final ModelMapper modelMapper = new ModelMapper();
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDTO> findAll(Pageable pageable) {

        Page<User> users = this.userRepository.findAll(pageable);
        List<UserDTO> userDTOList = users
                .stream()
                .map(service -> modelMapper.map(service, UserDTO.class))
                .collect(Collectors.toList());

        return new PageImpl<>(userDTOList);
    }

    @Override
    public UserDTO save(UserDTO userDTO) throws RestException {
        User userCreated;
        User userNew = new User();
        boolean update = false;

        if (userDTO.getId() != null) {

            if (this.userRepository.findById(userDTO.getId()).isPresent()) {
                update = true;
                userNew.setId(userDTO.getId());
            }
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
            this.log.error(GeneralConstants.INTERNAL_SERVER_ERROR_TEXT, e);
            throw new InternalServerErrorException(GeneralConstants.INTERNAL_SERVER_ERROR_TEXT, String.valueOf(e.getMostSpecificCause().getMessage()));
        }

        return modelMapper.map(userCreated, UserDTO.class);
    }

    @Override
    public UserDTO findById(Long id) throws RestException {
        return modelMapper.map(this.getUserEntity(id), UserDTO.class);
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
