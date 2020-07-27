package com.example.carros.controller;

import com.example.carros.domain.model.Carro;
import com.example.carros.domain.usecase.CarroBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/carros")
public class CarrosController {

    @Autowired
    private CarroBusiness carroBusiness;

    @GetMapping()
    public ResponseEntity<List<Carro>> listAll() {
        return ResponseEntity.ok(this.carroBusiness.findAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Carro> findById(@PathVariable Integer id) {
        Optional<Carro> carroOptional = this.carroBusiness.findById(id);

        return carroOptional
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/tipo/{tipo}")
    public ResponseEntity<List<Carro>> findByTipo(@PathVariable() String tipo) {
        List<Carro> carroList = this.carroBusiness.findByTipo(tipo);

        return carroList.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(carroList);
    }

    @PostMapping()
    public Carro save(@RequestBody Carro carro) {
        return this.carroBusiness.save(carro);
    }

    @PutMapping()
    public Carro update(@RequestBody Carro carro) {
        return this.carroBusiness.update(carro);
    }

    @DeleteMapping()
    public void delete(@RequestBody Carro carro) {
        this.carroBusiness.delete(carro);
    }

}
