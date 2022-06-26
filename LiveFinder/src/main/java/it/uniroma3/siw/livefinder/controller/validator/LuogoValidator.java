package it.uniroma3.siw.livefinder.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.livefinder.model.Artista;
import it.uniroma3.siw.livefinder.model.Luogo;
import it.uniroma3.siw.livefinder.service.LuogoService;

public class LuogoValidator implements Validator{

	
	@Autowired
	private LuogoService luogoService;
	
	@Override
	public boolean supports(Class<?> aClass) {
		return Artista.class.equals(aClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if(this.luogoService.alreadyExists((Luogo) target)) {
			errors.reject("artista.duplicato");
		}
		
	}
}
