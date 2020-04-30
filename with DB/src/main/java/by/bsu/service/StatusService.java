package by.bsu.service;

import by.bsu.dto.StatusDto;

import java.util.List;

public interface StatusService extends CrudService<StatusDto> {
    List<StatusDto> findByName(String name);

    List<StatusDto> findStatusOfOrder(Long orderId);
}
