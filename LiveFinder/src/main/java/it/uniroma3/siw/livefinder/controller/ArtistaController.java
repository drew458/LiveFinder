package it.uniroma3.siw.livefinder.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.livefinder.controller.validator.ArtistaValidator;
import it.uniroma3.siw.livefinder.model.Artista;
import it.uniroma3.siw.livefinder.service.ArtistaService;
import it.uniroma3.siw.livefinder.service.TourService;

@Controller
public class ArtistaController {
	
	@Autowired
	private ArtistaService artistaService;
	
	@Autowired
	private ArtistaValidator artistaValidator;
	
	@Autowired
	private TourService tourService;
	
	/*
	 * convenzione: get per le operazioni di lettura, post per gli aggiornamenti
	 * il path è associato alle classi del dominio
	*/
	
	// Metodo post per inserire un artista

	@PostMapping("/artista")
	public String addArtista(@Valid @ModelAttribute(value="artista") Artista artista, 
			BindingResult bindingResult, Model model) {

		/* Se non ci sono errori inserisce la ricorrenza di artista 
		 * tramite la save del service 
		 * */
		
		/* Si invoca anche il metodo validate del Validator, oltre 
		 * alle validazioni automatiche dell'annotazione @valid
		 */
		this.artistaValidator.validate(artista, bindingResult);
		
		if (!bindingResult.hasErrors()) {
			
			this.artistaService.save(artista); // salvo un oggetto artista
			model.addAttribute("artista", artista);
			
			// Ogni metodo ritorna la stringa col nome della vista successiva
			// se NON ci sono errori si va alla form di visualizzazione dati inseriti
			return "artista"; 
		}
		else {
			//model.addAttribute("artista", artista);
			// se ci sono errori si rimanda alla form di inserimento
			return "admin/artistaForm"; 
		}
	}
	
	
	// Metodi per delete
	
		@GetMapping("/confermaDeleteArtista/{id}")
		public String confermaDeleteArtista(@PathVariable("id") Long id, Model model) {
			this.artistaService.deleteById(id);
			model.addAttribute("artisti", this.artistaService.findAll());
			return "artisti";
		}
		
		@GetMapping("/deleteArtista/{id}")
		public String deleteArtista(@PathVariable("id") Long id, Model model) {
			model.addAttribute("artista", this.artistaService.findById(id));
			return "deleteArtista";
		}

	// Metodi get

	// richiede un singolo artista tramite id
	@GetMapping("/artista/{id}")
	public String getArtista(@PathVariable("id")Long id, Model model) {
		// id è una variabile associata al path
		Artista artista = artistaService.findById(id);
		model.addAttribute("artista", artista);
		// ritorna la form con i dati dell'entità richiesta
		return "artista";
	}
	
	// richiede tutti gli artisti, non c'è id
	@GetMapping("/artisti")
	public String getArtisti(Model model) {
		List<Artista> artisti = artistaService.findAll();
		model.addAttribute("artisti", artisti);
		return "artisti";
	}
	
	@GetMapping("/artistaForm")
	public String artistaForm(Model model) {
		model.addAttribute("artista", new Artista());
		return "admin/artistaForm";
	}
	
	//richiede tutti i tour dell'artista passato nel path
	@GetMapping("/artista/{id}/tour")
	public String getTour(@Valid @PathVariable("id") Long id, Model model) {
		Artista artista = artistaService.findById(id);
		model.addAttribute("tour", tourService.findAllByArtista(artista));
		return "tour";
	}
}