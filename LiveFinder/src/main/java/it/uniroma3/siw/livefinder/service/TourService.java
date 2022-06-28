package it.uniroma3.siw.livefinder.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.livefinder.model.Artista;
import it.uniroma3.siw.livefinder.model.Tour;
import it.uniroma3.siw.livefinder.repository.TourRepository;

@Service
public class TourService {

	@Autowired
	private TourRepository tourRepository;

	/**
	 * 
	 * @param id
	 * @return Tour or null if tour doesn't exists
	 */
	public Tour findById(Long id){
		return tourRepository.findById(id).orElse(null);
	}

	public Tour findByNome(String nome){
		return tourRepository.findByNome(nome);
	}

	/**
	 * per aggiornare un tour esistente con crudRepository si può sempre usare il metodo save
	 * @param tour
	 */
	public void update(Tour tour){
		this.save(tour);
	}

	public void save(Tour tour){
		tourRepository.save(tour);
	}
	
	public List<Tour> findAllByArtista(Artista artista) {
		return StreamSupport.stream(tourRepository.findAllByArtista(artista).spliterator(),true)
		.sorted()
		.collect(Collectors.toList());
	}

	/**
	 * Questa logica (farina del mio sacco quindi liberi di migliorarla) permette di riconoscere eventuali 
	 * duplicati ma anche di modificare un tour esistente senza incappare nell'errore di duplicazione
	 * @param tour
	 * @return
	 */
    public boolean alreadyExists(Tour tour) {
        if(tour.getId()==null){
			return tourRepository.existsByNome(tour.getNome());
		}else if(tourRepository.existsByNome(tour.getNome())){
			Tour tourEsistente = tourRepository.findByNome(tour.getNome());
			return !(tour.getId().equals(tourEsistente.getId()));
		}else{
			return false;
		}
    }

	public List<Tour> findAll(){
		return StreamSupport.stream(tourRepository.findAll().spliterator(),true)
		.sorted()
		.collect(Collectors.toList());
	}

	public void deleteTourById(Long id){
		tourRepository.deleteById(id);
	}

	public void deleteAll(){
		tourRepository.deleteAll();
	}

}
