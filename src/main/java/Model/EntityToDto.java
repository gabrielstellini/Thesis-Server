package Model;

import org.springframework.beans.BeanUtils;

public abstract class EntityToDto <D, E>{
    public D toDto(E entity, D dto) {
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }
}
