package it.uniroma3.siw.livefinder.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.livefinder.model.Concerto;
import it.uniroma3.siw.livefinder.service.ConcertoService;

@Component
public class ConcertoValidator implements Validator{

    @Autowired 
    private ConcertoService concertoService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Concerto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(concertoService.alreadyExists((Concerto) target)){
            errors.reject("concerto.duplicato");
        }
    }
    
}
