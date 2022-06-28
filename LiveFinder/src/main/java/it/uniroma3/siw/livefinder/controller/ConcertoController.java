package it.uniroma3.siw.livefinder.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.livefinder.controller.validator.ConcertoValidator;
import it.uniroma3.siw.livefinder.model.Biglietto;
import it.uniroma3.siw.livefinder.model.Concerto;
import it.uniroma3.siw.livefinder.service.BigliettoService;
import it.uniroma3.siw.livefinder.service.ConcertoService;
import it.uniroma3.siw.livefinder.service.LuogoService;
import it.uniroma3.siw.livefinder.service.TourService;

@Controller
public class ConcertoController {

	Logger logger = LoggerFactory.getLogger(ConcertoController.class);

	@Autowired
	private TourService tourService;

	@Autowired
	private LuogoService luogoService;
	
	@Autowired
	private ConcertoService concertoService;

	@Autowired
	private ConcertoValidator concertoValidator;

	@Autowired
	private BigliettoService bigliettoService;

	@GetMapping("/users/concerto/{id}")
	private String getConcerto(@PathVariable("id") Long id, Model model){
		model.addAttribute("concerto", concertoService.findById(id));
		return "concerto";
	}

	@RequestMapping(value = {"/admin/concertoForm", "/admin/concertoForm/{id}"})
	public String getConcertoForm(@PathVariable(name = "id", required = false)Long id, Model model){
		Concerto concerto;

		if(id!=null){
			concerto = concertoService.findById(id);
		}else{
			concerto = new Concerto();
		}

		//concerto.setData(new Date());
		model.addAttribute("concerto", concerto);
		model.addAttribute("listaTour", tourService.findAll());
		model.addAttribute("luoghi", luogoService.findAll());
		return "admin/concertoForm";
	}

	@PostMapping("/users/concerti/cerca")
	public String findByCitta(@RequestParam("citta") String cerca, Model model){
		logger.info("Sto cercando: "+cerca);
		model.addAttribute("concerti", concertoService.findByCitta(cerca.toUpperCase()));
		return "concerti";
	}

	
	@RequestMapping(path = {"/","/search"})
	public String home(Concerto concerto, Model model, String citta) {
		if(citta!=null) {
			List<Concerto> concertiPerCitta = concertoService.getByCitta(citta);
			model.addAttribute("concerti", concertiPerCitta);
		}else {
			List<Concerto> concerti = concertoService.getAllConcerti();
			model.addAttribute("concerti", concerti);
		}
		return "index";
	}

	@GetMapping("/users/concerti")
	public String getAll(Model model){
		model.addAttribute("concerti", concertoService.getAllConcerti());
		return "concerti";
	}

	@PostMapping("/admin/concerto")
	public String newConcerto(@Valid @ModelAttribute("concerto") Concerto concerto,
								BindingResult bindingResult, Model model,
								@RequestParam(name="numBiglietti",required = false) int numBiglietti){
		concertoValidator.validate(concerto, bindingResult);
		
		if(!bindingResult.hasErrors()){
			//concertoService.save(concerto);

			for(int i=1; i<=numBiglietti; i++){
				concerto.addBiglietto(new Biglietto());
			}

			model.addAttribute("concerto", concerto);
			return "bigliettiForm";
		}else{
			model.addAttribute("listaTour", tourService.findAll());
			model.addAttribute("luoghi", luogoService.findAll());
			return "admin/concertoForm";
		}

	}

	@PostMapping("/admin/addBiglietti")
	public String addBiglietti(@ModelAttribute("concerto") Concerto concerto, BindingResult bindingResult, Model model){
		logger.info("Numero biglietti: "+concerto.getBiglietti().size());
		/**
		 * Con questo metodo rimuovo i biglietti che hanno campi vuoti prima di salvarli.
		 * Do la possibilità all'admin di inserire un numero più grande di biglietti e in caso di cambiare idea semplicemente non
		 * riempiendo alcuni cambi biglietto 
		 */
		concerto.getBiglietti().removeIf(biglietto -> {
			if(biglietto.getTipologia().isEmpty() || biglietto.getTipologia() == ""){
				bigliettoService.delete(biglietto);
				return true;
			}else{
				return false;
			}
		});
		
		//bigliettoService.deleteIfConcertoNull();
		concertoService.save(concerto);

		model.addAttribute("concerto", concerto);
		return "concerto";
	}
}
