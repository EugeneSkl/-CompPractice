package by.bsu.dao;

import by.bsu.entity.StatusEntity;

import java.util.List;

public interface StatusDao extends CrudDao<StatusEntity> {
    List<StatusEntity> findByName(String name);

    List<StatusEntity> findStatusOfOrder(Long orderId);
}
