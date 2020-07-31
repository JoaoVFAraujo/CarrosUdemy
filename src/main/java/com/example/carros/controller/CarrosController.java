package com.example.carros.controller;

import com.example.carros.domain.dto.CarroDTO;
import com.example.carros.domain.model.Carro;
import com.example.carros.domain.usecase.CarroBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/carros")
public class CarrosController {

    @Autowired
    private CarroBusiness carroBusiness;

    @GetMapping()
    public ResponseEntity<List<CarroDTO>> listAll() {
        return ResponseEntity.ok(this.carroBusiness.findAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CarroDTO> findById(@PathVariable Integer id) {
        Optional<CarroDTO> carroOptional = this.carroBusiness.findById(id);

        return carroOptional
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/tipo/{tipo}")
    public ResponseEntity<List<CarroDTO>> findByTipo(@PathVariable() String tipo) {
        List<CarroDTO> carroList = this.carroBusiness.findByTipo(tipo);

        return carroList.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(carroList);
    }

    @PostMapping()
    public ResponseEntity<CarroDTO> save(@RequestBody Carro carro) {

        try {
            CarroDTO c = this.carroBusiness.save(carro);

            URI location = this.getUri(c.getId());
            return ResponseEntity.created(location).build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping()
    public ResponseEntity<CarroDTO> update(@RequestBody Carro carro) {

        try {
            return ResponseEntity.ok(this.carroBusiness.update(carro));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }


    }

    @DeleteMapping()
    public void delete(@RequestBody Carro carro) {
        this.carroBusiness.delete(carro);
    }

    private URI getUri(Integer id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
    }

}
