package Model;

import org.springframework.beans.BeanUtils;

public abstract class EntityToDto <D, E>{
    public D toDto(E entity, D dto) {
        if(entity != null & dto != null) {
            BeanUtils.copyProperties(entity, dto);
            return dto;
        }
        else {
            return null;
        }
    }
}
