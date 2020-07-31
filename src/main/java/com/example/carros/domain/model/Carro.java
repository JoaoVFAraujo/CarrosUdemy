package com.example.carros.domain.model;

import lombok.Data;

import javax.persistence.*;

@Entity()
@Data
public class Carro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String tipo;

}
