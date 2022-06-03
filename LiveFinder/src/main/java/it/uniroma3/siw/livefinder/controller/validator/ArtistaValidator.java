package it.uniroma3.siw.livefinder.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.livefinder.model.Artista;
import it.uniroma3.siw.livefinder.service.ArtistaService;

@Component
public class ArtistaValidator implements Validator {

	@Autowired
	private ArtistaService artistaService;
	
	@Override
	public boolean supports(Class<?> aClass) {
		return Artista.class.equals(aClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if(this.artistaService.alreadyExists((Artista) target)) {
			errors.reject("artista.duplicato");
		}
		
	}

}
