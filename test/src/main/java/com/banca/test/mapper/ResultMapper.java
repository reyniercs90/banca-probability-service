package com.banca.test.mapper;

import com.banca.test.dto.ResultDto;
import com.banca.test.entities.ResultEntity;
import org.springframework.stereotype.Component;

@Component
public class ResultMapper {

    // Convert from Entity to DTO
    public ResultDto toDto(ResultEntity entity){
        return  new ResultDto(entity.getSequence(), entity.getProbability());
    }

    // Convert from DTO to Entity
    public ResultEntity toDto(ResultDto dto){
        return  new ResultEntity(null,dto.getSequence(), dto.getProbability());
    }
}
