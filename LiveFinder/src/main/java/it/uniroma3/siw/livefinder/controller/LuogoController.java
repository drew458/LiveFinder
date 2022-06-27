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

import it.uniroma3.siw.livefinder.controller.validator.LuogoValidator;
import it.uniroma3.siw.livefinder.model.Luogo;
import it.uniroma3.siw.livefinder.service.LuogoService;

@Controller
public class LuogoController {

	@Autowired
	private LuogoService luogoService;
	
	@Autowired
	private LuogoValidator luogoValidator;
	
	@PostMapping("/admin/luogo")
	public String addLuogo(@Valid @ModelAttribute(value="luogo") Luogo luogo, 
			BindingResult bindingResult, Model model) {
		this.luogoValidator.validate(luogo, bindingResult);
		
		if (!bindingResult.hasErrors()) {
			
			this.luogoService.save(luogo);
			model.addAttribute("luogo", luogo);
			
			return "luogo"; 
		}
		else {
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
		return "luogo";
	}
	
	@GetMapping("/users/luoghi")
	public String getLuoghi(Model model) {
		List<Luogo> luoghi = luogoService.findAll();
		model.addAttribute("luoghi", luoghi);
		return "luoghi";
	}
	
	@GetMapping("/admin/luogoForm")
	public String luogoForm(Model model) {
		model.addAttribute("luogo", new Luogo());
		return "admin/luogoForm";
	}
}
