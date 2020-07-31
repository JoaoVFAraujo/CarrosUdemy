package com.example.carros.domain.usecase;

import com.example.carros.domain.dto.CarroDTO;
import com.example.carros.domain.model.Carro;
import com.example.carros.domain.repository.CarroRepository;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarroBusiness {

    @Autowired
    private CarroRepository carroRepository;

    public List<CarroDTO> findAll() {
        return this.carroRepository.findAll().stream().map(CarroDTO::create).collect(Collectors.toList());
    }

    public Optional<CarroDTO> findById(Integer id) {
        return this.carroRepository.findById(id).map(CarroDTO::create);
    }

    public List<CarroDTO> findByTipo(String tipo) {
        return this.carroRepository.findByTipo(tipo).stream().map(CarroDTO::create).collect(Collectors.toList());
    }

    public CarroDTO save(Carro carro) {
        Assert.isNull(carro.getId(), "Não foi possível inserir o registro");

        return CarroDTO.create(this.carroRepository.save(carro));
    }

    public CarroDTO update(Carro carro) {
        Assert.notNull(carro.getId(), "Não foi possível atualizar o registro");

        Optional<Carro> carroOptional = this.carroRepository.findById(carro.getId());

        if (carroOptional.isPresent()) {
            Carro carroMock = carroOptional.get();
            carroMock.setNome(carro.getNome());
            carroMock.setTipo(carro.getTipo());

            return CarroDTO.create(this.carroRepository.save(carroMock));
        } else {
            throw new RuntimeException("Não foi possível atualizar o registro");
        }
    }

    public void delete(Carro carro) {
        if (findById(carro.getId()).isPresent()) {
            this.carroRepository.delete(carro);
        }
    }

}
