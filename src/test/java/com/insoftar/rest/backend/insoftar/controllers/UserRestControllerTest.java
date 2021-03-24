package com.insoftar.rest.backend.insoftar.controllers;

import com.insoftar.rest.backend.insoftar.config.constants.GeneralConstants;
import com.insoftar.rest.backend.insoftar.models.dto.UserDTO;
import com.insoftar.rest.backend.insoftar.models.services.impl.UserServiceImpl;
import com.insoftar.rest.backend.insoftar.responses.RestResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class UserRestControllerTest {

    private static final String RESPONSE_OK_CODE = "200 OK";
    private static final String RESPONSE_CREATED_CODE = "201 CREATED";

    private static final int PAGE_REQUEST = 5;
    private static final int SIZE_REQUEST = 5;

    private static final UserDTO USER_DTO = new UserDTO();
    private static final Long USER_ID = 12L;
    private static final String USER_FIRSTNAME = "John";
    private static final String USER_LASTNAME = "Doe";
    private static final String USER_ID_NUMBER = "123456789";
    private static final String USER_PHONE = "3115673452";
    private static final String USER_EMAIL = "john_doe@mail.com";

    @Mock
    UserServiceImpl userService;

    @Mock
    Page<UserDTO> userDTOPage;

    @Mock
    BindingResult bindingResult;

    @InjectMocks
    UserRestController userRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        USER_DTO.setId(USER_ID);
        USER_DTO.setFirstname(USER_FIRSTNAME);
        USER_DTO.setLastname(USER_LASTNAME);
        USER_DTO.setIdNumber(USER_ID_NUMBER);
        USER_DTO.setPhone(USER_PHONE);
        USER_DTO.setEmail(USER_EMAIL);
    }

    @Test
    void index() {
        Pageable pageRequest = PageRequest.of(PAGE_REQUEST, SIZE_REQUEST, Sort.by("id").ascending());
        Mockito.when(this.userService.findAll(pageRequest)).thenReturn(userDTOPage);

        RestResponse<Page<UserDTO>> response = this.userRestController.index(PAGE_REQUEST, SIZE_REQUEST);
        assertEquals(RESPONSE_OK_CODE, response.getStatus());
        assertEquals(GeneralConstants.USERS_FOUND_TEXT, response.getMessage());
        verify(this.userService, times(1)).findAll(pageRequest);
    }

    @Test
    void show() {
        Mockito.when(this.userService.findById(USER_ID)).thenReturn(USER_DTO);

        RestResponse<UserDTO> response = this.userRestController.show(USER_ID);
        assertEquals(RESPONSE_OK_CODE, response.getStatus());
        assertEquals(GeneralConstants.USER_FOUND_TEXT, response.getMessage());
        verify(this.userService, times(1)).findById(USER_ID);
    }

    @Test
    void create() {
        Mockito.when(this.userService.save(USER_DTO)).thenReturn(USER_DTO);

        RestResponse<UserDTO> response = this.userRestController.create(USER_DTO, this.bindingResult);
        assertEquals(RESPONSE_CREATED_CODE, response.getStatus());
        assertEquals(GeneralConstants.USER_CREATED_TEXT, response.getMessage());
        verify(this.userService, times(1)).save(USER_DTO);
    }

    @Test
    void update() {
        Mockito.when(this.userService.findById(USER_ID)).thenReturn(USER_DTO);
        Mockito.when(this.userService.save(USER_DTO)).thenReturn(USER_DTO);

        RestResponse<UserDTO> response = this.userRestController.update(USER_DTO, USER_ID, this.bindingResult);
        assertEquals(RESPONSE_OK_CODE, response.getStatus());
        assertEquals(GeneralConstants.USER_UPDATED_TEXT, response.getMessage());
        verify(this.userService, times(1)).findById(USER_ID);
        verify(this.userService, times(1)).save(USER_DTO);
    }

    @Test
    void delete() {
        RestResponse<Object> response = this.userRestController.delete(USER_ID);
        assertEquals(RESPONSE_OK_CODE, response.getStatus());
        assertEquals(GeneralConstants.USER_DELETED_TEXT, response.getMessage());
    }
}