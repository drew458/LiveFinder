package it.uniroma3.siw.livefinder.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.livefinder.controller.validator.LuogoValidator;
import it.uniroma3.siw.livefinder.model.Indirizzo;
import it.uniroma3.siw.livefinder.model.Luogo;
import it.uniroma3.siw.livefinder.service.CittaService;
import it.uniroma3.siw.livefinder.service.LuogoService;

@Controller
public class LuogoController {

	@Autowired
	private LuogoService luogoService;
	
	@Autowired
	private LuogoValidator luogoValidator;

	@Autowired
	private CittaService cittaService;
	
	@PostMapping("/admin/luogo")
	public String addLuogo(@Valid @ModelAttribute(value="luogo") Luogo luogo, BindingResult bindingLuogoResult,
							@Valid @ModelAttribute("indirizzo") Indirizzo indirizzo, 
							BindingResult bindingIndirizzoResult, Model model) {
		this.luogoValidator.validate(luogo, bindingLuogoResult);
		
		if (!bindingIndirizzoResult.hasErrors() && !bindingLuogoResult.hasErrors()) {
			luogo.setIndirizzo(indirizzo);
			indirizzo.setCitta(luogo.getCitta().getNome());
			this.luogoService.save(luogo);

			model.addAttribute("luogo", luogo);
			return "luogo"; 
		}
		else {
			model.addAttribute("listaCitta", cittaService.findAll());
			return "admin/luogoForm"; 
		}
	}
	
	@GetMapping("/admin/confermaDeleteLuogo/{id}")
	public String confermaDeleteLuogo(@PathVariable("id") Long id, Model model) {
		model.addAttribute("luogo", this.luogoService.findById(id));
		return "admin/confermaDeleteLuogo";
	}
	
	@GetMapping("/admin/deleteLuogo/{id}")
	public String deleteLuogo(@PathVariable("id") Long id, Model model) {
		this.luogoService.deleteById(id);
		model.addAttribute("luoghi", luogoService.findAll());
		return "luoghi";
	}
	
	@GetMapping("/users/luogo/{id}")
	public String getLuogo(@PathVariable("id")Long id, Model model) {
		Luogo luogo = luogoService.findById(id);
		model.addAttribute("luogo", luogo);
		model.addAttribute("concerti", luogo.getConcerti().parallelStream().sorted().collect(Collectors.toList()));
		return "luogo";
	}
	
	@GetMapping("/users/luoghi")
	public String getLuoghi(Model model) {
		List<Luogo> luoghi = luogoService.findAll();
		model.addAttribute("luoghi", luoghi);
		return "luoghi";
	}
	
	@GetMapping({"/admin/luogoForm", "/admin/luogoForm/{id}"})
	public String luogoForm(@PathVariable(name="id", required=false) Long id, Model model) {
		Luogo luogo = id!=null ? luogoService.findById(id) : new Luogo();
		Indirizzo indirizzo = luogo.getIndirizzo()!=null ? luogo.getIndirizzo() : new Indirizzo();
		model.addAttribute("luogo", luogo);
		model.addAttribute("indirizzo", indirizzo);
		model.addAttribute("listaCitta", cittaService.findAll());
		return "admin/luogoForm";
	}
}
