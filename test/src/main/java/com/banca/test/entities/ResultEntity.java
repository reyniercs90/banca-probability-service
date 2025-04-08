package com.banca.test.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;


    private  String sequence;
    private double probability;


}
