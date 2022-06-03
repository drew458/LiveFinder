package it.uniroma3.siw.livefinder.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.livefinder.model.Artista;
import it.uniroma3.siw.livefinder.model.Tour;
import it.uniroma3.siw.livefinder.repository.TourRepository;

@Service
public class TourService {

	@Autowired
	private TourRepository tourRepository;
	
	public List<Tour> findAllByArtista(Artista artista) {
		List<Tour> tour = new ArrayList<Tour>();
		for(Tour t : tourRepository.findAllByArtista(artista)) {
			tour.add(t);
		}
		return tour;
	}

}
