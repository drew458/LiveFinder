package it.uniroma3.siw.livefinder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.livefinder.repository.IndirizzoRepository;

@Service
public class IndirizzoService {
	
	@Autowired
	private IndirizzoRepository indirizzoRepository;
	
	public void deleteById(Long id) {
		this.indirizzoRepository.deleteById(id);
	}

}
