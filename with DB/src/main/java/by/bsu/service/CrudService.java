package by.bsu.service;

import by.bsu.dto.AbstractDto;

import java.util.List;

public interface CrudService<D extends AbstractDto> {
    void save(D dto);

    void delete(Long id);

    void update(D dto);

    D getById(Long id);

    List<D> findAll();
}
