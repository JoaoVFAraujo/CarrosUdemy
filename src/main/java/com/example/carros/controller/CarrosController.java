package com.example.carros.controller;

import com.example.carros.domain.model.Carro;
import com.example.carros.domain.usecase.CarroBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class CarrosController {

    @Autowired
    private CarroBusiness carroBusiness;

    @GetMapping(path = "/carros")
    public List<Carro> listAll() {
        return  this.carroBusiness.findAll();
    }

    @GetMapping(path = "/carros/{id}")
    public Optional<Carro> findById(@PathVariable Integer id) {
        return this.carroBusiness.findById(id);
    }

    @GetMapping(path = "/carros/tipo/{tipo}")
    public List<Carro> findByTipo(@PathVariable() String tipo) {
        return this.carroBusiness.findByTipo(tipo);
    }

}
