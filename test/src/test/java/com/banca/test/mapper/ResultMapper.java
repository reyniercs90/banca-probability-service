package com.banca.test.mapper;

import com.banca.test.dto.ResultDto;
import com.banca.test.entities.ResultEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResultMapperTest {

    private ResultMapper resultMapper;

    @BeforeEach
    void setUp() {
        resultMapper = new ResultMapper();
    }

    @Test
    void testToDto() {
        ResultEntity entity = new ResultEntity(1L, "WL", 0.49);
        ResultDto dto = resultMapper.toDto(entity);

        assertEquals("WL", dto.getSequence());
        assertEquals(0.49, dto.getProbability());
    }

    @Test
    void testToEntity() {
        ResultDto dto = new ResultDto("LW", 0.51);
        ResultEntity entity = resultMapper.toDto(dto);

        assertNull(entity.getId()); // el ID lo dejo null al crear desde DTO
        assertEquals("LW", entity.getSequence());
        assertEquals(0.51, entity.getProbability());
    }
}

