package com.insoftar.rest.backend.insoftar.models.dto.converters;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * <h1>CountryConverter</h1>
 * Interfaz para la implementación de los convertidores de entidades a DTO
 *
 * @author Oscar Orlando Rodriguez Cristancho <oorodriguezc@gmail.com>
 * @version 1.0.0
 * @since 17-03-2021
 */
public interface AbstractConverter<E, D> {
    Page<D> toDTOPage(Pageable pageRequest, Page<E> page);
}
