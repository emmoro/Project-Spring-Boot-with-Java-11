package br.com.api.endpoint;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.api.model.CarTO;
import br.com.api.repository.CarRepository;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CarEndpointTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@MockBean
	private CarRepository carRepository;
	
	@Autowired
	private MockMvc mockMvc;
	
	private CarTO car;
	
	@BeforeEach
	public void setup() {
		car = new CarTO(1L, "GOL", "Volkswagen", 2010L, LocalDate.now());
		BDDMockito.when(carRepository.findCarById(car.getId())).thenReturn(car);
	}
	
	@Test
	public void listCarsShouldReturnStatusCode200() {
		List<CarTO> listCars = Arrays.asList(
				new CarTO(1L, "GOL", "Volkswagen", 2010L, LocalDate.now()),
				new CarTO(2L, "GOL", "Volkswagen", 2010L, LocalDate.now()));
		
		BDDMockito.when(carRepository.findAll()).thenReturn(listCars);
		ResponseEntity<String> response = restTemplate.getForEntity("/cars/listAll", String.class);
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	public void getCarShouldReturnStatusCode200() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.get("/cars/findCarById/{id}", car.getId()))
				.andExpect(MockMvcResultMatchers.status()
				.isOk());
	}
	
	@Test
	public void getCarShouldReturnStatusCode404() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.get("/cars/findCarById/{id}", 2))
				.andExpect(MockMvcResultMatchers.status()
				.isNotFound());
	}
	
	@Test
	public void deleteCarShouldReturnStatusCode200() throws Exception {
		BDDMockito.doNothing().when(carRepository).delete(car);
		ResponseEntity<String> response = restTemplate.exchange("/cars/deleteCar/{id}", HttpMethod.DELETE, null, String.class, car.getId());
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	public void deleteCarShouldReturnStatusCode404() throws Exception {
		CarTO car = new CarTO(1L, "GOL", "Volkswagen", 2010L, LocalDate.now());
		BDDMockito.when(carRepository.getOne(car.getId())).thenReturn(car);
		
		BDDMockito.doNothing().when(carRepository).delete(car);
		mockMvc.perform(MockMvcRequestBuilders
				.delete("/cars/deleteCar/{id}", 2))
				.andExpect(MockMvcResultMatchers.status()
				.isNotFound());
	}
	
	@Test
	public void createNewCarShouldReturnStatus200() throws Exception {
		CarTO newCar = new CarTO(2L, "Onix", "Chevrolet", 2018L, LocalDate.now());
		BDDMockito.when(carRepository.save(car)).thenReturn(newCar);
		
		ResponseEntity<String> response = restTemplate.postForEntity("/cars/saveCar", newCar, String.class);
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	public void createNewCarShouldReturnStatus400() throws Exception {
		CarTO newCar = new CarTO(2L, null, "Chevrolet", 2018L, LocalDate.now());
		BDDMockito.when(carRepository.save(car)).thenReturn(newCar);
		
		ResponseEntity<String> response = restTemplate.postForEntity("/cars/saveCar", newCar, String.class);
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(400);
	}
	
	@Test
	public void updateCarShouldReturnStatus200() throws Exception {
		car.setModel("Onix");
		BDDMockito.when(carRepository.save(car)).thenReturn(car);
		ResponseEntity<String> response = restTemplate.postForEntity("/cars/updateCar", car, String.class);
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	public void updateCarShouldReturnStatus400() throws Exception {
		car.setModel(null);
		BDDMockito.when(carRepository.save(car)).thenReturn(car);
		ResponseEntity<String> response = restTemplate.postForEntity("/cars/updateCar", car, String.class);
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(400);
	}
	
	@Test
	public void findCarsShouldReturnStatus200() throws Exception {
		List<CarTO> listCars = Arrays.asList(
				new CarTO(1L, "GOL", "Volkswagen", 2010L, LocalDate.now()),
				new CarTO(2L, "GOL", "Volkswagen", 2010L, LocalDate.now()));
		
		BDDMockito.when(carRepository.findByModelIgnoreCaseContaining("gol")).thenReturn(listCars);
		ResponseEntity<String> response = restTemplate.getForEntity("/cars/findCarByModel/{model}", String.class, "gol");
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	public void findCarsShouldReturnStatus404() throws Exception {
		List<CarTO> listCars = Arrays.asList(
				new CarTO(1L, "GOL", "Volkswagen", 2010L, LocalDate.now()),
				new CarTO(2L, "GOL", "Volkswagen", 2010L, LocalDate.now()));
		
		BDDMockito.when(carRepository.findByModelIgnoreCaseContaining("gol")).thenReturn(listCars);
		ResponseEntity<String> response = restTemplate.getForEntity("/cars/findCarByModel/{model}", String.class, "onix");
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(404);
	}

}
