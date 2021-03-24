package com.insoftar.rest.backend.insoftar.models.services.impl;

import com.insoftar.rest.backend.insoftar.config.constants.GeneralConstants;
import com.insoftar.rest.backend.insoftar.models.dto.UserDTO;
import com.insoftar.rest.backend.insoftar.models.entities.User;
import com.insoftar.rest.backend.insoftar.models.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserServiceImplTest {

    private static final int PAGE_REQUEST = 5;
    private static final int SIZE_REQUEST = 5;

    private static final User USER = new User();
    private static final UserDTO USER_DTO = new UserDTO();

    private static final Long USER_ID = 12L;
    private static final String USER_FIRSTNAME = "John";
    private static final String USER_LASTNAME = "Doe";
    private static final String USER_ID_NUMBER = "123456789";
    private static final String USER_PHONE = "3115673452";
    private static final String USER_EMAIL = "john_doe@mail.com";

    private static final String INTERNAL_SERVER_ERROR_MESSAGE = "INTERNAL_SERVER_ERROR_MESSAGE";

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        USER.setId(USER_ID);
        USER.setFirstname(USER_FIRSTNAME);
        USER.setLastname(USER_LASTNAME);
        USER.setIdNumber(USER_ID_NUMBER);
        USER.setPhone(USER_PHONE);
        USER.setEmail(USER_EMAIL);

        USER_DTO.setId(USER_ID);
        USER_DTO.setFirstname(USER_FIRSTNAME);
        USER_DTO.setLastname(USER_LASTNAME);
        USER_DTO.setIdNumber(USER_ID_NUMBER);
        USER_DTO.setPhone(USER_PHONE);
        USER_DTO.setEmail(USER_EMAIL);
    }

    @AfterEach
    void tearDown() {
        Mockito.verifyNoMoreInteractions(this.userRepository);
    }

    @Test
    void findAll() {
        List<User> userList = new ArrayList<>();
        userList.add(USER);
        Page<User> userPage = new PageImpl(userList);

        Pageable pageRequest = PageRequest.of(PAGE_REQUEST, SIZE_REQUEST, Sort.by("id").ascending());
        Mockito.when(this.userRepository.findAll(pageRequest)).thenReturn(userPage);

        Page<UserDTO> response = this.userService.findAll(pageRequest);

        assertEquals(1, response.getContent().size());

        Mockito.verify(this.userRepository, Mockito.times(1)).findAll(pageRequest);
    }

    @Test
    void save() {
        Mockito.when(this.userRepository.findById(USER.getId())).thenReturn(Optional.of(USER));
        Mockito.when(this.userRepository.findByEmail(USER.getEmail())).thenReturn(null);
        Mockito.when(this.userRepository.findByIdNumber(USER.getIdNumber())).thenReturn(null);
        Mockito.when(this.userRepository.save(Mockito.any(User.class))).thenReturn(USER);

        final UserDTO user = this.userService.save(USER_DTO);

        assertEquals(USER_ID, user.getId());
        Mockito.verify(this.userRepository, Mockito.times(1)).findById(USER.getId());
        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmail(USER.getEmail());
        Mockito.verify(this.userRepository, Mockito.times(1)).findByIdNumber(USER.getIdNumber());
        Mockito.verify(this.userRepository, Mockito.times(1)).save(Mockito.any(User.class));
    }

    @Test
    void saveEmailAlreadyExist() {
        Mockito.when(this.userRepository.findById(10L)).thenReturn(Optional.of(USER));
        Mockito.when(this.userRepository.findByEmail(USER.getEmail())).thenReturn(USER);
        Mockito.when(this.userRepository.findByIdNumber(USER.getIdNumber())).thenReturn(USER);
        Mockito.when(this.userRepository.save(Mockito.any(User.class))).thenReturn(USER);

        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () -> this.userService.save(USER_DTO));

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        Assertions.assertEquals(GeneralConstants.EMAIL_ALREADY_EXIST_TEXT, exception.getReason());

        Mockito.verify(this.userRepository, Mockito.times(1)).findById(USER.getId());
        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmail(USER.getEmail());
    }

    @Test
    void saveIdNumberAlreadyExist() {
        Mockito.when(this.userRepository.findById(10L)).thenReturn(Optional.of(USER));
        Mockito.when(this.userRepository.findByEmail(USER.getEmail())).thenReturn(null);
        Mockito.when(this.userRepository.findByIdNumber(USER.getIdNumber())).thenReturn(USER);

        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () -> this.userService.save(USER_DTO));

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        Assertions.assertEquals(GeneralConstants.ID_NUMBER_ALREADY_EXIST_TEXT, exception.getReason());

        Mockito.verify(this.userRepository, Mockito.times(1)).findById(USER.getId());
        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmail(USER.getEmail());
        Mockito.verify(this.userRepository, Mockito.times(1)).findByIdNumber(USER.getIdNumber());
    }

    @Test
    void saveTryCatch() {
        Mockito.when(this.userRepository.findById(USER.getId())).thenReturn(Optional.of(USER));
        Mockito.when(this.userRepository.findByEmail(USER.getEmail())).thenReturn(null);
        Mockito.when(this.userRepository.findByIdNumber(USER.getIdNumber())).thenReturn(null);
        Mockito.when(this.userRepository.save(Mockito.any(User.class))).thenThrow(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MESSAGE));

        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () -> this.userService.save(USER_DTO));
        Assertions.assertEquals(INTERNAL_SERVER_ERROR_MESSAGE, exception.getReason());
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());

        Mockito.verify(this.userRepository, Mockito.times(1)).findById(USER.getId());
        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmail(USER.getEmail());
        Mockito.verify(this.userRepository, Mockito.times(1)).findByIdNumber(USER.getIdNumber());
        Mockito.verify(this.userRepository, Mockito.times(1)).save(Mockito.any(User.class));
    }

    @Test
    void findById() {
        Mockito.when(this.userRepository.findById(USER.getId())).thenReturn(Optional.of(USER));

        UserDTO response = this.userService.findById(USER_ID);

        Assertions.assertEquals(USER_ID, response.getId());
        Mockito.verify(this.userRepository, Mockito.times(1)).findById(USER_ID);
    }

    @Test
    void findByIdNotFound() {
        Mockito.when(this.userRepository.findById(USER.getId())).thenReturn(Optional.empty());

        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () -> this.userService.findById(USER.getId()));

        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        Assertions.assertEquals(GeneralConstants.USER_NOT_FOUND_TEXT, exception.getReason());

        Mockito.verify(this.userRepository, Mockito.times(1)).findById(USER_ID);
    }

    @Test
    void deleteById() {
        Mockito.when(this.userRepository.findById(USER.getId())).thenReturn(Optional.of(USER));

        this.userService.deleteById(USER_ID);

        Mockito.verify(this.userRepository, Mockito.times(1)).findById(USER_ID);
        Mockito.verify(this.userRepository, Mockito.times(1)).deleteById(USER_ID);
    }

    @Test
    void deleteByIdNotFound() {
        Mockito.when(this.userRepository.findById(USER.getId())).thenReturn(Optional.empty());

        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () -> this.userService.findById(USER.getId()));

        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        Assertions.assertEquals(GeneralConstants.USER_NOT_FOUND_TEXT, exception.getReason());

        Mockito.verify(this.userRepository, Mockito.times(1)).findById(USER_ID);
    }
}