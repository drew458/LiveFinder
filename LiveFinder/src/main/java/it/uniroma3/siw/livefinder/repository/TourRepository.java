package it.uniroma3.siw.livefinder.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.livefinder.model.Tour;

public interface TourRepository extends CrudRepository<Tour, Long>{
    
}
