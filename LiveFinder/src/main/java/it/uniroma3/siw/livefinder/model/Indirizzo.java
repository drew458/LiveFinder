package it.uniroma3.siw.livefinder.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Entity
@Data
public class Indirizzo {

    public Indirizzo(){
        
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotBlank
    private String via;

    @NotBlank
    private String cap;

    @NotBlank
    private String numCivico; //è una stringa perché potrebbe contenere delle lettere

    @NotBlank
    private String citta; //è una stringa perché non mi interessa salvare le città degli user
}
