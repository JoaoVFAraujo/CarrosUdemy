package com.example.carros;

import com.example.carros.domain.dto.CarroDTO;
import com.example.carros.domain.exception.ObjectNotFoundException;
import com.example.carros.domain.model.Carro;
import com.example.carros.domain.usecase.CarroBusiness;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class CarrosServiceTests {

	@Autowired
	private CarroBusiness carroBusiness;

	@Test
	void testSave() {
		Carro carroTest = Carro.init().withNome("Ferrari").withTipo("esportivos");

		CarroDTO carroResult = this.carroBusiness.save(carroTest);
		Assert.assertNotNull("Não existe o carro", carroResult);
		Integer id = carroResult.getId();
		Assert.assertNotNull("Não encontrou o Id", id);

		// Buscar objeto
		carroResult = this.carroBusiness.findById(id);
		Assert.assertNotNull("Não existe o carro", carroResult);

		Assert.assertEquals("Atributo Nome diferente","Ferrari", carroResult.getNome());
		Assert.assertEquals("Atributo Tipo diferente","esportivos", carroResult.getTipo());

		// Deletar objeto
		this.carroBusiness.delete(id);

		// Verificar delete
		try {
			Assert.assertNull("Encontrou objeto deletado", this.carroBusiness.findById(id));
		} catch (ObjectNotFoundException ex) {
			// OK
		}
	}

	@Test
	void testUpdate() {
		CarroDTO carroResult = this.carroBusiness.findById(6);
		Assert.assertNotNull("Não encontrou o carro", carroResult);
		Assert.assertEquals("Atributo Nome diferente", "Cadillac Eldorado", carroResult.getNome());
		Assert.assertEquals("Atributo Tipo diferente", "classicos", carroResult.getTipo());

		carroResult.setNome("Cadillac Edit");

		carroResult = this.carroBusiness.update(Carro.init()
				.withId(carroResult.getId())
				.withNome(carroResult.getNome())
				.withTipo(carroResult.getTipo()));

		Assert.assertNotNull("Não existe o carro", carroResult);
		Integer id = carroResult.getId();
		Assert.assertNotNull("Não encontrou o Id", id);

		carroResult = this.carroBusiness.findById(id);
		Assert.assertNotNull("Não encontrou o carro", carroResult);
		Assert.assertEquals("Atributo Nome diferente", "Cadillac Edit", carroResult.getNome());
	}

	@Test
	void testFindAll() {
		List<CarroDTO> carros = this.carroBusiness.findAll();

		Assert.assertEquals("Tamanho diferente", 30, carros.size());
	}

	@Test
	void testFindById() {
		CarroDTO carro = this.carroBusiness.findById(11);

		Assert.assertNotNull("Carro não encontrado", carro);
		Assert.assertEquals("Atributo Nome diferente","Ferrari FF", carro.getNome());
	}

	@Test
	void testFindByTipo() {

		Assert.assertEquals("Tamanho de classicos diferente",
				10,this.carroBusiness.findByTipo("classicos").size());
		Assert.assertEquals("Tamanho de esportivos diferente",
				10,this.carroBusiness.findByTipo("esportivos").size());
		Assert.assertEquals("Tamanho de luxo diferente",
				10,this.carroBusiness.findByTipo("luxo").size());
		Assert.assertEquals("Encontrou tipo não existente",
				0, this.carroBusiness.findByTipo("xxx").size());

	}

}
