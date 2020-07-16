package com.example.carros.domain.repository;

import com.example.carros.domain.model.Carro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarroRepository extends JpaRepository<Carro, Integer> {
    List<Carro> findByTipo(String tipo);
}
