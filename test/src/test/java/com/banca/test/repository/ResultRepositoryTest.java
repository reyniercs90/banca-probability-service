package com.banca.test.repository;

import com.banca.test.entities.ResultEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ResultRepositoryTest {

    @Autowired
    private ResultRepository resultRepository;

    @BeforeEach
    void setUp() {
        // clean DB before each test
        resultRepository.deleteAll();
    }

    @Test
    @Transactional
    public void testSaveAndFindResultEntity() {
        // Create new entity
        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setSequence("WLW");
        resultEntity.setProbability(0.75);

        // Save in DB
        ResultEntity savedEntity = resultRepository.save(resultEntity);

        // Verify that the ID has been generated automatically
        assertNotNull(savedEntity.getId(), "The ID should not be null");

        // Retrieve the entity from the database
        ResultEntity foundEntity = resultRepository.findById(savedEntity.getId()).orElse(null);

        // Verify that the entity was found and the values are correct
        assertNotNull(foundEntity, "The entity should be found");
        assertEquals("WLW", foundEntity.getSequence(), "The sequence should be WLW");
        assertEquals(0.75, foundEntity.getProbability(), "The probability should be 0.75");
    }
}
