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
        // Limpiar la base de datos antes de cada test (opcional)
        resultRepository.deleteAll();
    }

    @Test
    @Transactional
    public void testSaveAndFindResultEntity() {
        // Crear una nueva entidad
        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setSequence("WLW");
        resultEntity.setProbability(0.75);

        // Guardar en la base de datos
        ResultEntity savedEntity = resultRepository.save(resultEntity);

        // Verificar que el ID se haya generado automáticamente
        assertNotNull(savedEntity.getId(), "The ID should not be null");

        // Recuperar la entidad de la base de datos
        ResultEntity foundEntity = resultRepository.findById(savedEntity.getId()).orElse(null);

        // Verificar que la entidad fue encontrada y los valores sean correctos
        assertNotNull(foundEntity, "The entity should be found");
        assertEquals("WLW", foundEntity.getSequence(), "The sequence should be WLW");
        assertEquals(0.75, foundEntity.getProbability(), "The probability should be 0.75");
    }
}
