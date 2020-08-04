package com.example.carros;

import com.example.carros.domain.dto.CarroDTO;
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
		Optional<CarroDTO> op = this.carroBusiness.findById(id);
		Assert.assertTrue("Não encontrou o carro", op.isPresent());

		carroResult = op.get();
		Assert.assertEquals("Atributo Nome diferente","Ferrari", carroResult.getNome());
		Assert.assertEquals("Atributo Tipo diferente","esportivos", carroResult.getTipo());

		// Deletar objeto
		this.carroBusiness.delete(id);

		// Verificar delete
		Assert.assertFalse("Encontrou objeto deletado", this.carroBusiness.findById(id).isPresent());
	}

	@Test
	void testFindAll() {
		List<CarroDTO> carros = this.carroBusiness.findAll();

		Assert.assertEquals("Tamanho diferente", 30, carros.size());
	}

	@Test
	void testFindById() {
		Optional<CarroDTO> op = this.carroBusiness.findById(11);
		CarroDTO carro = op.get();

		Assert.assertTrue("Carro não encontrado", op.isPresent());
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
