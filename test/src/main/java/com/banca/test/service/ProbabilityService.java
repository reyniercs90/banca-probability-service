package com.banca.test.service;

import com.banca.test.dto.ResultDto;
import com.banca.test.entities.ResultEntity;
import com.banca.test.mapper.ResultMapper;
import com.banca.test.repository.ResultRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProbabilityService {

    private static final String WIN_RACE = "W";
    private static final String LOSE_RACE = "L";

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private ResultMapper resultMapper;

    public List<ResultDto> calculateProbabilities(double winProbability, int numberOfRaces) {
        log.info("Starting probability calculation with winProbability={} and numberOfRaces={}", winProbability, numberOfRaces);

        try {
            List<ResultEntity> resultEntities = new ArrayList<>();

            log.debug("Generating all possible sequences and their probabilities...");
            generateProbabilities("", 1.0, winProbability, numberOfRaces, resultEntities);
            log.debug("Sequence generation completed. Total sequences: {}", resultEntities.size());

            log.info("Saving calculated results to the database...");
            resultRepository.saveAll(resultEntities);
            log.info("Results successfully saved.");

            log.debug("Converting entities to DTOs...");
            List<ResultDto> results = resultEntities.stream()
                    .map(resultMapper::toDto)
                    .collect(Collectors.toList());
            log.debug("DTO conversion completed.");

            log.info("Probability calculation completed successfully.");
            return results;
        } catch (Exception e) {
            log.error("An error occurred during probability calculation: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    private void generateProbabilities(String sequence, double probabilityAccount,
                                       double winProbability, int numberOfRace, List<ResultEntity> results) {
        if (sequence.length() == numberOfRace) {
            results.add(new ResultEntity(null, sequence, probabilityAccount));
            return;
        }

        generateProbabilities(sequence + WIN_RACE,
                probabilityAccount * winProbability, winProbability, numberOfRace, results);

        generateProbabilities(sequence + LOSE_RACE,
                probabilityAccount * (1 - winProbability), winProbability, numberOfRace, results);
    }
}
