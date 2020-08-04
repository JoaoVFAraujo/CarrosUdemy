package com.example.carros;

import com.example.carros.domain.dto.CarroDTO;
import com.example.carros.domain.model.Carro;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SpringBootTest(classes = CarrosApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CarrosAPITests {

	@Autowired
	private TestRestTemplate restTemplate;

	private ResponseEntity<CarroDTO> findByIdCarro(String url) {
		return this.restTemplate.getForEntity(url, CarroDTO.class);
	}

	private ResponseEntity<List<CarroDTO>> findAllCarros(String url) {
		return this.restTemplate.exchange(
				url,
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<CarroDTO>>() {
				}
		);
	}

	@Test
	void testSave() {
		Carro carro = Carro.init().withNome("Ferrari").withTipo("esportivos");

		ResponseEntity<CarroDTO> response = this.restTemplate.postForEntity("/carros", carro, null);

		// Verificar criação
		Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());

		// Buscar objeto
		String location = response.getHeaders().getLocation().toString();
		CarroDTO carroLocation = this.findByIdCarro(location).getBody();

		Assert.assertNotNull("Não existe o carro", carroLocation);
		Assert.assertEquals("Atributo Nome diferente", "Ferrari", carroLocation.getNome());
		Assert.assertEquals("Atributo Tipo diferente", "esportivos", carroLocation.getTipo());

		// Deletar objeto
		this.restTemplate.delete(location);

		// Verificar delete
//		Assert.assertFalse("Encontrou objeto deletado", this.carroBusiness.findById(id).isPresent());
	}

	@Test
	void testFindAll() {
		List<CarroDTO> carros = this.findAllCarros("/carros").getBody();

		Assert.assertEquals("Tamanho diferente", 30, carros.size());
	}

	@Test
	void testFindById() {
		ResponseEntity<CarroDTO> response = this.findByIdCarro("/carros/11");
		ResponseEntity<CarroDTO> responseErro = this.findByIdCarro("/carros/100");

		CarroDTO carro = response.getBody();

		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assert.assertEquals(HttpStatus.NOT_FOUND, responseErro.getStatusCode());
		Assert.assertEquals("Atributo Nome diferente","Ferrari FF", carro.getNome());
	}

	@Test
	void testFindByTipo() {

		Assert.assertEquals("Tamanho de classicos diferente",
				10,this.findAllCarros("/carros/tipo/classicos").getBody().size());
		Assert.assertEquals("Tamanho de esportivos diferente",
				10,this.findAllCarros("/carros/tipo/esportivos").getBody().size());
		Assert.assertEquals("Tamanho de luxo diferente",
				10,this.findAllCarros("/carros/tipo/luxo").getBody().size());
		Assert.assertEquals("HttpStatus diferente",
				HttpStatus.NO_CONTENT, this.findAllCarros("/carros/tipo/xxx").getStatusCode());

	}

}
