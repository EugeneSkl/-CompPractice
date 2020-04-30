package by.bsu.service.impl;

import by.bsu.dao.StatusDao;
import by.bsu.dto.StatusDto;
import by.bsu.entity.StatusEntity;
import by.bsu.service.StatusService;
import by.bsu.util.DtoEntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatusServiceImpl implements StatusService {
    private StatusDao statusDao;

    @Autowired
    public StatusServiceImpl(StatusDao statusDao) {
        this.statusDao = statusDao;
    }

    @Override
    public List<StatusDto> findByName(String name) {
        List<StatusEntity> entities = statusDao.findByName(name);
        return convertEachEntityToDto(entities);
    }

    @Override
    public List<StatusDto> findStatusOfOrder(Long orderId) {
        List<StatusEntity> entities = statusDao.findStatusOfOrder(orderId);
        return convertEachEntityToDto(entities);
    }

    @Override
    public void save(StatusDto dto) {
        StatusEntity statusEntity = DtoEntityConverter.entityFromDto(dto);
        statusDao.add(statusEntity);
    }

    @Override
    public void delete(Long id) {
        statusDao.remove(id);
    }

    @Override
    public void update(StatusDto dto) {
        StatusEntity statusEntity = DtoEntityConverter.entityFromDto(dto);
        statusDao.update(statusEntity);
    }

    @Override
    public StatusDto getById(Long id) {
        StatusEntity entity = statusDao.getById(id);
        return DtoEntityConverter.dtoFromEntity(entity);
    }

    @Override
    public List<StatusDto> findAll() {
        List<StatusEntity> entities = statusDao.findAll();
        return convertEachEntityToDto(entities);
    }

    private List<StatusDto> convertEachEntityToDto(List<StatusEntity> entities) {
        return entities.stream()
                .map(DtoEntityConverter::dtoFromEntity)
                .collect(Collectors.toList());
    }
}
