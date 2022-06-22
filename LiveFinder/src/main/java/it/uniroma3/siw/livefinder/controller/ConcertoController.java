package it.uniroma3.siw.livefinder.controller;

import java.util.List;

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

import it.uniroma3.siw.livefinder.model.Concerto;
import it.uniroma3.siw.livefinder.service.CittaService;
import it.uniroma3.siw.livefinder.service.ConcertoService;
import it.uniroma3.siw.livefinder.service.LuogoService;
import it.uniroma3.siw.livefinder.service.TourService;

@Controller
public class ConcertoController {

	Logger logger = LoggerFactory.getLogger(ConcertoController.class);

	@Autowired
	private TourService tourService;

	@Autowired
	private CittaService cittaService;

	@Autowired
	private LuogoService luogoService;
	
	@Autowired
	private ConcertoService concertoService;

	@GetMapping("/admin/nuovoConcerto")
	public String getConcertoForm(Model model){
		Concerto concerto = new Concerto();
		//concerto.setData(new Date());
		model.addAttribute("concerto", concerto);
		model.addAttribute("listaTour", tourService.findAll());
		model.addAttribute("luoghi", luogoService.findAll());
		return "concertoForm";
	}

	@PostMapping("/concerti/cerca")
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

	@RequestMapping(value = {"/concerti","/concerti/{citta}"})
	public String getAll(@PathVariable(required = false) String citta, Model model){
		if(citta != null){
			model.addAttribute("concerti", concertoService.findByCitta(citta));
		}else{
			model.addAttribute("concerti", concertoService.getAllConcerti());
		}
		return "concerti";
	}

	@PostMapping("/admin/concerto")
	public String newConcerto(@ModelAttribute("concerto") Concerto concerto, BindingResult bindingResult, Model model){
		logger.info("Ecco il concerto: " + concerto.getData());
		concerto.setCitta(concerto.getLuogo().getCitta());
		concertoService.save(concerto);

		model.addAttribute("concerto", concerto);
		return "concerto";
	}
}
