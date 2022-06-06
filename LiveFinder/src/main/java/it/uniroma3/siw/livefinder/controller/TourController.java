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

import it.uniroma3.siw.livefinder.controller.validator.TourValidator;
import it.uniroma3.siw.livefinder.model.Tour;
import it.uniroma3.siw.livefinder.service.ArtistaService;
import it.uniroma3.siw.livefinder.service.TourService;

@Controller
public class TourController {

    @Autowired
    private TourService tourService;

    @Autowired
    private TourValidator tourValidator;

    @Autowired
    private ArtistaService artistaService;

    @GetMapping("/tour/{id}")
    public String getTourById(@PathVariable("id") Long id, Model model){
        model.addAttribute("tour", tourService.findById(id));
        return "tour";
    }

    @GetMapping("/listaTour")
    public String getAllTours(Model model){
        List<Tour> listaTour = tourService.findAll();
        model.addAttribute("listaTour", listaTour);
        return "listaTour";
    }
    
    @GetMapping("/tourForm")
    public String getTourForm(Model model){
        model.addAttribute("tour", new Tour());
        model.addAttribute("listaArtisti", artistaService.findAll());
        return "tourForm";
    }

    @PostMapping("/tour")
    public String newTour(@Valid @ModelAttribute("tour") Tour tour, BindingResult bindingResult, Model model){
        tourValidator.validate(tour, bindingResult);

        if(!bindingResult.hasErrors()){
            tourService.save(tour);
            model.addAttribute("tour", tour);
            return "tour";
        }else{
            return "tourForm";
        }
    }
}
