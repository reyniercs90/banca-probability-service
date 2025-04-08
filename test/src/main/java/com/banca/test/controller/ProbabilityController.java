package com.banca.test.controller;

import com.banca.test.dto.ResultDto;
import com.banca.test.service.ProbabilityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/probabilities")
public class ProbabilityController {

    @Autowired
   private ProbabilityService probabilityService;

    @GetMapping("/calculateProbabilities")
    public List<ResultDto> getProbabilities(
            @RequestParam("winProbability") double winProbability,
            @RequestParam("numberOfRaces") int numberOfRaces){

        log.info("Receiving request to calculate probabilities with winProbability={} y numberOfRaces={}",
                winProbability, numberOfRaces);

        try {
            List<ResultDto> result = probabilityService.calculateProbabilities(winProbability, numberOfRaces);
            log.info("Correctly calculated probabilities.");
            return result;
        } catch (Exception e) {
            log.error("Error in calculating probabilities: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error in calculating probabilities");
        }

    }

}
