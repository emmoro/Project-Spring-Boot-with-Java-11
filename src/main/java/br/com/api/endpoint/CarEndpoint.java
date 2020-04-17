package br.com.api.endpoint;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.error.CustomErrorType;
import br.com.api.model.CarTO;
import br.com.api.service.CarService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("cars")
public class CarEndpoint {
	
	private CarService carService;

	@Autowired
	public CarEndpoint(CarService carService) {
		this.carService = carService;
	}
	
	@PostMapping(path ="saveCar")
	@Transactional
	@ApiOperation(value ="Saves car object information.", response = CarTO.class)
	public ResponseEntity<?> saveCar(@Valid @RequestBody CarTO car) {	
		try {
			carService.saveCar(car);
			return new ResponseEntity<>(car, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(path = "listAll")
	@ApiOperation(value ="Search all available cars.", response = CarTO[].class)
	public ResponseEntity<?> listAll() {
		try {
			List<CarTO> listCars = carService.listAll();
			return new ResponseEntity<>(listCars, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new CustomErrorType("Car not found"), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(path = "findCarById/{id}")
	@ApiOperation(value ="Search car through ID.", response = CarTO.class)
	public ResponseEntity<?> findCarById(@PathVariable("id") Long id) {
		try {
			CarTO car = carService.findCarById(id);
			if (car != null) {
				return new ResponseEntity<CarTO>(car, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new CustomErrorType("Car not found"), HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(path = "findCarByModel/{model}")
	@ApiOperation(value ="Search car through Model.", response = CarTO[].class)
	public ResponseEntity<?> findCarByModel(@PathVariable("model") String model) {
		try {
			List<CarTO> listCars = carService.findCarByModel(model);
			if (listCars != null && !listCars.isEmpty()) {
				return new ResponseEntity<>(listCars, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new CustomErrorType("Car not found"), HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(path = "updateCar")
	@Transactional
	@ApiOperation(value ="Update data the car.", response = CarTO.class)
	public ResponseEntity<?> updateCar(@Valid @RequestBody CarTO car) {
		try {
			carService.saveCar(car);
			return new ResponseEntity<>(car, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new CustomErrorType("Error to save the Car!"), HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping(path ="deleteCar/{id}")
	@Transactional
	@ApiOperation(value ="Delete the information the car.", response = Void.class)
	public ResponseEntity<?> deleteCar(@PathVariable("id") Long id) {
		try {
			CarTO oldCar = carService.findCarById(id);
			if (oldCar != null) {
				carService.deleteCar(oldCar);
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new CustomErrorType("Car not found"), HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(new CustomErrorType("Error to delete the Car!"), HttpStatus.NOT_FOUND);
		}
	}

}
