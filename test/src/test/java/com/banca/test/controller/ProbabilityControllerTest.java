package com.banca.test.controller;

import com.banca.test.dto.ResultDto;
import com.banca.test.service.ProbabilityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class ProbabilityControllerTest {

    @Mock
    private ProbabilityService probabilityService;

    @InjectMocks
    private ProbabilityController probabilityController;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void shouldReturnListOfResultDtosWhenServiceSucceeds() {
        // Given
        double winProbability = 0.7;
        int numberOfRaces = 2;

        List<ResultDto> mockResult = Arrays.asList(
                new ResultDto("WW", 0.49),
                new ResultDto("WL", 0.21),
                new ResultDto("LW", 0.21),
                new ResultDto("LL", 0.09)
        );

        when(probabilityService.calculateProbabilities(winProbability, numberOfRaces)).thenReturn(mockResult);

        // When
        List<ResultDto> result = probabilityController.getProbabilities(winProbability, numberOfRaces);

        // Then
        assertEquals(4, result.size());
        assertEquals("WW", result.get(0).getSequence());
        verify(probabilityService, times(1)).calculateProbabilities(winProbability, numberOfRaces);
    }

    @Test
    void shouldThrowExceptionWhenServiceFails() {
        // Given
        double winProbability = 0.7;
        int numberOfRaces = 2;

        when(probabilityService.calculateProbabilities(winProbability, numberOfRaces))
                .thenThrow(new RuntimeException("Unexpected error"));

        // Then
        assertThrows(ResponseStatusException.class, () ->
                probabilityController.getProbabilities(winProbability, numberOfRaces));
    }
}
