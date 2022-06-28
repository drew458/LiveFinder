package it.uniroma3.siw.livefinder.controller.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.livefinder.model.Citta;

@Component
public class CittaValidator implements Validator{

    @Override
    public boolean supports(Class<?> clazz) {
        return Citta.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        
    }
    
}
