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

    @GetMapping("/users/tour/{id}")
    public String getTourById(@PathVariable("id") Long id, Model model){
        Tour tour = tourService.findById(id);
        model.addAttribute("tour", tour);
        model.addAttribute("concerti", tour.getConcerti().parallelStream().sorted().collect(Collectors.toList()));
        return "tour";
    }

    @GetMapping("/users/listaTour")
    public String getAllTours(Model model){
        List<Tour> listaTour = tourService.findAll();
        model.addAttribute("listaTour", listaTour);
        return "listaTour";
    }
    
    @GetMapping({"/admin/tourForm", "/admin/tourForm/{id}"})
    public String getTourForm(@PathVariable(name="id", required=false) Long id, Model model){
        Tour tour = id!=null ? tourService.findById(id) : new Tour();
        model.addAttribute("tour", tour);
        model.addAttribute("listaArtisti", artistaService.findAll());
        return "admin/tourForm";
    }

    @GetMapping("/admin/confermaDeleteTour/{id}")
  public String confermaDeleteTour(@PathVariable("id") Long id, Model model) {
    model.addAttribute("tour", tourService.findById(id));
    return "admin/confermaDeleteTour";
  }

  @GetMapping("/admin/deleteTour/{id}")
  public String deleteTour(@PathVariable("id") Long id, Model model) {
    tourService.deleteById(id);
    model.addAttribute("listaTour", tourService.findAll());
    return "listaTour";
  }

    @PostMapping("/admin/tour")
    public String newTour(@Valid @ModelAttribute("tour") Tour tour, BindingResult bindingResult, Model model){
        tourValidator.validate(tour, bindingResult);

        if(!bindingResult.hasErrors()){
            tourService.save(tour);
            model.addAttribute("tour", tour);
            return "tour";
        }else{
            model.addAttribute("listaArtisti", artistaService.findAll());
            return "admin/tourForm";
        }
    }
}
