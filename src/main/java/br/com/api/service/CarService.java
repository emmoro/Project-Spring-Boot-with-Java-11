package br.com.api.service;

import java.util.List;

import br.com.api.model.CarTO;

public interface CarService {
	
	/**
	 * Save car
	 * @param CarTO car
	 */
	public abstract void saveCar(CarTO car);
	
	/**
	 * Search all car in the system
	 * @param Pageable pageable
	 * @return List<CarTO>
	 */
	public abstract List<CarTO> listAll();
	
	/**
	 * Find car by Id
	 * @param Long id
	 * @return CarTO
	 */
	public abstract CarTO findCarById(Long id);
	
	/**
	 * Find car by Model
	 * @param String model
	 * @return List<CarTO>
	 */
	public abstract List<CarTO> findCarByModel(String model);
	
	/**
	 * Update the car
	 * @param CarTO car
	 */
	public abstract void updateCar(CarTO car);
	
	/**
	 * Delete car
	 * @param CarTO car
	 */
	public abstract void deleteCar(CarTO car);

}
