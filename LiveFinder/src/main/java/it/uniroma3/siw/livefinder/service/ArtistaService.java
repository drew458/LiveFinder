package it.uniroma3.siw.livefinder.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.livefinder.model.Artista;
import it.uniroma3.siw.livefinder.repository.ArtistaRepository;

@Service
public class ArtistaService {
	
	@Autowired
	private ArtistaRepository artistaRepository;
	
	@Transactional
	public void save(Artista artista) {
		artistaRepository.save(artista);
	}
	
	@Transactional
	public void delete(Artista artista) {
		artistaRepository.delete(artista);
	}
	
	@Transactional
	public void deleteById(Long id) {
		artistaRepository.deleteById(id);
	}
	
	public Artista findById(Long id) {
		return artistaRepository.findById(id).get();
	}
	
	public List<Artista> findAll() {
		List<Artista> artisti = new ArrayList<Artista>();
		for (Artista a : artistaRepository.findAll()) {
			artisti.add(a);
		}
		return artisti;
	}

	public boolean alreadyExists(Artista artista) {
		if(artista.getId()==null){
			return artistaRepository.existsByNomeAndGenereAndAnnoFormazione(artista.getNome(), artista.getGenere(), artista.getAnnoFormazione());
		}else if(artistaRepository.existsByNomeAndGenereAndAnnoFormazione(artista.getNome(), artista.getGenere(), artista.getAnnoFormazione())){
			Artista esistente = artistaRepository.findByNomeAndGenereAndAnnoFormazione(artista.getNome(), artista.getGenere(), artista.getAnnoFormazione());
			return !(artista.getId().equals(esistente.getId()));
		}else{
			return false;
		}
	}

}
