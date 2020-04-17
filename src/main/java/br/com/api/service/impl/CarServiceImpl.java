package br.com.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.model.CarTO;
import br.com.api.repository.CarRepository;
import br.com.api.service.CarService;

@Service("carService")
public class CarServiceImpl implements CarService {

	@Autowired
	private CarRepository carRepository;
	
	@Override
	public void saveCar(CarTO car) {
		carRepository.saveAndFlush(car);
	}

	@Override
	public List<CarTO> listAll() {
		return carRepository.findAll();
	}

	@Override
	public CarTO findCarById(Long id) {
		return carRepository.findCarById(id);
	}
	
	@Override
	public List<CarTO> findCarByModel(String model) {
		return carRepository.findByModelIgnoreCaseContaining(model);
	}
	
	@Override
	public void updateCar(CarTO car) {
		carRepository.saveAndFlush(car);
	}

	@Override
	public void deleteCar(CarTO car) {
		carRepository.delete(car);
	}
	
}
