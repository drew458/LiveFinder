package it.uniroma3.siw.livefinder.controller.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.livefinder.model.Indirizzo;
import it.uniroma3.siw.livefinder.model.User;

/**
 * Validator for User
 */
@Component
public class UserValidator implements Validator{

	final Integer MAX_NAME_LENGTH = 100;
    final Integer MIN_NAME_LENGTH = 2;
    
    final Integer MAX_VIA_LENGTH = 100;
    final Integer MIN_VIA_LENGTH = 2;
    
    final Integer MAX_CAP_LENGTH = 5;
    final Integer MIN_CAP_LENGTH = 5;
    
    final Integer MAX_NUMCIVICO_LENGTH = 5;
    final Integer MIN_NUMCIVICO_LENGTH = 1;
    
    final Integer MAX_CITTA_LENGTH = 100;
    final Integer MIN_CITTA_LENGTH = 1;

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;
        String nome = user.getNome().trim();
        String cognome = user.getCognome().trim();

        if (nome.isEmpty())
            errors.rejectValue("nome", "required");
        else if (nome.length() < MIN_NAME_LENGTH || nome.length() > MAX_NAME_LENGTH)
            errors.rejectValue("nome", "size");

        if (cognome.isEmpty())
            errors.rejectValue("cognome", "required");
        else if (cognome.length() < MIN_NAME_LENGTH || cognome.length() > MAX_NAME_LENGTH)
            errors.rejectValue("cognome", "size");		
	}
	
	public void validateIndirizzo(Object target, Errors errors) {
		Indirizzo indirizzo = (Indirizzo) target;
		String via = indirizzo.getVia();
		String cap = indirizzo.getCap();
		String numCivico = indirizzo.getNumCivico();
		String citta = indirizzo.getCitta();
		
		if(via.isEmpty())
			errors.rejectValue("via", "required");
		else if(via.length() < MIN_VIA_LENGTH || via.length() > MAX_VIA_LENGTH)
			errors.rejectValue("via", "size");
		
		if(cap.isEmpty())
			errors.rejectValue("cap", "required");
		else if(cap.length() < MIN_CAP_LENGTH || cap.length() > MAX_CAP_LENGTH)
			errors.rejectValue("cap", "size");
		
		if(numCivico.isEmpty())
			errors.rejectValue("numCivico", "required");
		else if(numCivico.length() < MIN_NUMCIVICO_LENGTH || numCivico.length() > MAX_NUMCIVICO_LENGTH)
			errors.rejectValue("numCivico", "size");
		
		if(citta.isEmpty())
			errors.rejectValue("citta", "required");
		else if(citta.length() < MIN_CITTA_LENGTH || citta.length() > MAX_CITTA_LENGTH)
			errors.rejectValue("citta", "size");
		
	}
}
