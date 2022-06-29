package it.uniroma3.siw.livefinder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.livefinder.service.ConcertoService;
import it.uniroma3.siw.livefinder.service.TourService;

@Controller
public class MainController {
	
	@Autowired
	private ConcertoService concertoService;
	
	@Autowired
	private TourService tourService;

	@GetMapping(value = {"/", "index"})
	public String index(Model model) {
		
		model.addAttribute("concerti", this.concertoService.findAll());
		model.addAttribute("listaTour", this.tourService.findAll());
		
		return "index";
	}
}
