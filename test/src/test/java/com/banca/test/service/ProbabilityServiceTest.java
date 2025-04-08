package com.banca.test.service;

import com.banca.test.dto.ResultDto;
import com.banca.test.entities.ResultEntity;
import com.banca.test.mapper.ResultMapper;
import com.banca.test.repository.ResultRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProbabilityServiceTest {

    @InjectMocks
    private ProbabilityService probabilityService;

    @Mock
    private ResultRepository resultRepository;

    @Mock
    private ResultMapper resultMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGenerateCorrectNumberOfSequences() {
        double winProbability = 0.5;
        int numberOfRaces = 3; // Se esperan 2^3 = 8 combinaciones

        when(resultMapper.toDto((ResultEntity) any())).thenAnswer(invocation -> {
            ResultEntity entity = invocation.getArgument(0);
            return new ResultDto(entity.getSequence(), entity.getProbability());
        });

        List<ResultDto> results = probabilityService.calculateProbabilities(winProbability, numberOfRaces);

        assertEquals(8, results.size());
    }

    @Test
    void shouldSaveResultsToRepository() {
        double winProbability = 0.7;
        int numberOfRaces = 2;

        when(resultMapper.toDto((ResultEntity) any())).thenAnswer(invocation -> {
            ResultEntity entity = invocation.getArgument(0);
            return new ResultDto(entity.getSequence(), entity.getProbability());
        });

        probabilityService.calculateProbabilities(winProbability, numberOfRaces);

        ArgumentMatcher<List<ResultEntity>> matcher = list -> list.size() == 4;
        verify(resultRepository, times(1)).saveAll(argThat(matcher));
    }

    @Test
    void shouldConvertEntitiesToDtosCorrectly() {
        double winProbability = 1.0;
        int numberOfRaces = 1;

        when(resultMapper.toDto((ResultEntity) any())).thenAnswer(invocation -> {
            ResultEntity entity = invocation.getArgument(0);
            return new ResultDto("TestSequence", 0.999);
        });

        List<ResultDto> results = probabilityService.calculateProbabilities(winProbability, numberOfRaces);

        assertEquals(2, results.size()); // 1 carrera => 2 posibilidades: W y L
        for (ResultDto dto : results) {
            assertEquals("TestSequence", dto.getSequence());
            assertEquals(0.999, dto.getProbability());
        }
    }
}
