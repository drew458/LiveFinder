package it.uniroma3.siw.livefinder.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.livefinder.model.Tour;
import it.uniroma3.siw.livefinder.service.TourService;

@Component
public class TourValidator implements Validator{

    @Autowired
    private TourService tourService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Tour.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(tourService.alreadyExists((Tour)target)){
            errors.reject("tour.duplicato");
        }
    }
    
}
