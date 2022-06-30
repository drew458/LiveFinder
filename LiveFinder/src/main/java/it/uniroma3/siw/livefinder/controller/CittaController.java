package it.uniroma3.siw.livefinder.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.livefinder.controller.validator.CittaValidator;
import it.uniroma3.siw.livefinder.model.Citta;
import it.uniroma3.siw.livefinder.service.CittaService;
import it.uniroma3.siw.livefinder.service.ConcertoService;

@Controller
public class CittaController {

    @Autowired
    private CittaService cittaService;

    @Autowired
    private CittaValidator cittaValidator;

    @Autowired
    private ConcertoService concertoService;

    @GetMapping("/users/citta/{id}")
    public String findById(@PathVariable("id") Long id, Model model){
        Citta citta = cittaService.findById(id);
        model.addAttribute("concerti", concertoService.findByCitta(citta));
        model.addAttribute("luoghi", citta.getLuoghi());
        model.addAttribute("titoloConcerti", citta.getNome());
        return "concerti";
    }

    @GetMapping("/users/listaCitta")
    public String findAll(Model model){
        model.addAttribute("listaCitta", cittaService.findAll());
        return "listaCitta";
    }

    @GetMapping(value = {"/admin/cittaForm", "/admin/cittaForm/{id}"})
    public String getCittaForm(@PathVariable(name = "id", required = false) Long id, Model model){
        Citta citta = id!=null ? cittaService.findById(id) : new Citta();
        
        model.addAttribute("citta", citta);
        return "admin/cittaForm";
    }

    @PostMapping("/admin/citta")
    public String newCitta(@Valid @ModelAttribute("citta") Citta citta, 
                            BindingResult bindingResult, Model model){
        cittaValidator.validate(citta, bindingResult);

        if(!bindingResult.hasErrors()){
            cittaService.save(citta);
            model.addAttribute("listaCitta", cittaService.findAll());
            return "listaCitta";
        }else{
            return "admin/cittaForm";
        }
    }
    
}
