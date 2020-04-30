package by.bsu.util;

import by.bsu.dto.DishDto;
import by.bsu.dto.OrderDto;
import by.bsu.dto.StatusDto;
import by.bsu.dto.UserDto;
import by.bsu.entity.DishEntity;
import by.bsu.entity.OrderEntity;
import by.bsu.entity.StatusEntity;
import by.bsu.entity.UserEntity;

public class DtoEntityConverter {
    public static DishDto dtoFromEntity(DishEntity entity) {
        DishDto dto = new DishDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        return dto;
    }

    public static DishEntity entityFromDto(DishDto dto) {
        DishEntity entity = new DishEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        return entity;
    }

    public static OrderDto dtoFromEntity(OrderEntity entity) {
        OrderDto dto = new OrderDto();
        dto.setId(entity.getId());
        dto.setDate(entity.getDate());
        dto.setDescription(entity.getDescription());
        dto.setStatusId(entity.getStatusId());
        dto.setUserId(entity.getUserId());
        return dto;
    }

    public static OrderEntity entityFromDto(OrderDto dto) {
        OrderEntity entity = new OrderEntity();
        entity.setId(dto.getId());
        entity.setDate(dto.getDate());
        entity.setDescription(dto.getDescription());
        entity.setStatusId(dto.getStatusId());
        entity.setUserId(dto.getUserId());
        return entity;
    }

    public static StatusDto dtoFromEntity(StatusEntity entity) {
        StatusDto dto = new StatusDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }

    public static StatusEntity entityFromDto(StatusDto dto) {
        StatusEntity entity = new StatusEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        return entity;
    }

    public static UserDto dtoFromEntity(UserEntity entity) {
        UserDto dto = new UserDto();
        dto.setId(entity.getId());
        dto.setLogin(entity.getLogin());
        dto.setPassword(entity.getPassword());
        return dto;
    }

    public static UserEntity entityFromDto(UserDto dto) {
        UserEntity entity = new UserEntity();
        entity.setId(dto.getId());
        entity.setLogin(dto.getLogin());
        entity.setPassword(dto.getPassword());
        return entity;
    }
}
