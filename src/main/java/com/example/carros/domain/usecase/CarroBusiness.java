package com.example.carros.domain.usecase;

import com.example.carros.domain.model.Carro;
import com.example.carros.domain.repository.CarroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarroBusiness {

    @Autowired
    private CarroRepository carroRepository;

    public List<Carro> findAll() {
        return this.carroRepository.findAll();
    }

    public Optional<Carro> findById(Integer id) {
        return this.carroRepository.findById(id);
    }

    public List<Carro> findByTipo(String tipo) {
        return this.carroRepository.findByTipo(tipo);
    }

}
