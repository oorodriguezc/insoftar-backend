package com.insoftar.rest.backend.insoftar.models.repositories;

import com.insoftar.rest.backend.insoftar.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <h1>UserRepository</h1>
 * Repositorio DAO para los usuarios
 *
 * @author Oscar Orlando Rodriguez Cristancho <oorodriguezc@gmail.com>
 * @version 1.0.0
 * @since 14-03-2021
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByIdNumber(String idNumber);
}