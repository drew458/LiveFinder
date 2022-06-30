package it.uniroma3.siw.livefinder.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.livefinder.model.Citta;
import it.uniroma3.siw.livefinder.service.CittaService;

@Component
public class CittaValidator implements Validator{

    @Autowired
    private CittaService cittaService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Citta.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(cittaService.alreadyExists((Citta) target)){
            errors.reject("citta.duplicato");
        }
    }
    
}
