package it.uniroma3.siw.livefinder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import it.uniroma3.siw.livefinder.model.Concerto;
import it.uniroma3.siw.livefinder.service.ConcertoService;

@Controller
public class ConcertoController {
	
	@Autowired
	private ConcertoService concertoService;
	
	@RequestMapping(path = {"/","/search"})
	public String home(Concerto concerto, Model model, String citta) {
		if(citta!=null) {
			List<Concerto> concertiPerCitta = concertoService.getByCitta(citta);
			model.addAttribute("concertiPerCitta", concertiPerCitta);
		}else {
			List<Concerto> concerti = concertoService.getAllConcerti();
			model.addAttribute("concerti", concerti);}
		return "index";
		}
	}
