package br.com.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.api.model.CarTO;

@Repository
public interface CarRepository extends JpaRepository<CarTO, Long> {

	public List<CarTO> findByModelIgnoreCaseContaining(String model);
	
	@Query("SELECT car FROM CarTO car WHERE car.id = :id ")
	public CarTO findCarById(@Param("id") Long id);
	
}
