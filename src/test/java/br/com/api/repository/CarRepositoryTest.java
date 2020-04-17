package br.com.api.repository;

import java.time.LocalDate;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.api.model.CarTO;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CarRepositoryTest {
	
	@Autowired
	private CarRepository carRepository;
	
	@Test
	public void createShouldCar() {
		CarTO car = new CarTO(null, "GOL", "Volkswagen", 2010L, LocalDate.now());
		this.carRepository.saveAndFlush(car);
		Assertions.assertThat(car.getId()).isNotNull();
		Assertions.assertThat(car.getModel()).isEqualTo("GOL");
	}
	
	@Test
	public void deleteShouldRemoveCar() {
		CarTO car = new CarTO(null, "GOL", "Volkswagen", 2010L, LocalDate.now());
		this.carRepository.saveAndFlush(car);
		this.carRepository.delete(car);
		Assertions.assertThat(carRepository.findById(car.getId())).isNotNull();
	}
	
	@Test
	public void updateShouldChangeAndPersistCar() {
		CarTO car = new CarTO(null, "GOL", "Volkswagen", 2010L, LocalDate.now());
		this.carRepository.saveAndFlush(car);
		car.setModel("Onix");
		car.setBrand("Chevrolet");
		this.carRepository.saveAndFlush(car);
		Assertions.assertThat(car.getModel()).isEqualTo("Onix");
	}
	
	@Test
	public void findByModelIgnoreCaseContaining() {
		CarTO car = new CarTO(null, "GOL", "Volkswagen", 2010L, LocalDate.now());
		CarTO car2 = new CarTO(null, "GOL", "Volkswagen", 2019L, LocalDate.now());
		this.carRepository.saveAndFlush(car);
		this.carRepository.saveAndFlush(car2);
		List<CarTO> listCars = carRepository.findByModelIgnoreCaseContaining("gol");
		Assertions.assertThat(listCars.get(0).getModel()).isEqualTo("Gol");
	}
	
}
