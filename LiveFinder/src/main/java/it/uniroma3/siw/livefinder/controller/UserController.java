package it.uniroma3.siw.livefinder.controller;

import java.util.Collections;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.livefinder.model.Biglietto;
import it.uniroma3.siw.livefinder.model.Credentials;
import it.uniroma3.siw.livefinder.model.Indirizzo;
import it.uniroma3.siw.livefinder.model.User;
import it.uniroma3.siw.livefinder.service.BigliettoService;
import it.uniroma3.siw.livefinder.service.CredentialsService;

@Controller
public class UserController {

    @Autowired
    private BigliettoService bigliettoService;

    @Autowired
    private CredentialsService credentialsService;

    @PostMapping("/logged/checkout")
    public String prenota(@RequestParam(name="numBiglietti") Integer numBiglietti, 
                            @RequestParam("idBiglietto") Long idBiglietto, 
                            Model model){
        User user = credentialsService.getCredentials().getUser();

        model.addAttribute("user", user);
        model.addAttribute("numBiglietti", numBiglietti);
        model.addAttribute("biglietto", bigliettoService.findById(idBiglietto));
        
        model.addAttribute("indirizzo", new Indirizzo());
        return "checkout";
    }

    @PostMapping("/logged/compra")
    public String compra(@RequestParam("numBiglietti") Integer numBiglietti, @RequestParam("idBiglietto") Long idBiglietto, 
                        @RequestParam(name="salvaIndirizzo", required=false) boolean salva,
                        @Valid @ModelAttribute("indirizzo") Indirizzo indirizzo, BindingResult bindingResult, Model model){
        Credentials credentials = credentialsService.getCredentials();
        User user = credentials.getUser();
        
        if(user.getIndirizzo()!=null || !bindingResult.hasErrors()){
    
            Biglietto biglietto = bigliettoService.findById(idBiglietto);
            user.addBiglietti(Collections.nCopies(numBiglietti, biglietto)); //in questo modo suppongo che lo stesso utente possa comprare biglietti di tipologia diversa
    
            if(salva && indirizzo!=null){
                user.setIndirizzo(indirizzo);
            }
            credentialsService.updateCredentials(credentials);
            return "confermaAcquisto";
        }else{
            model.addAttribute("user", user);
            model.addAttribute("numBiglietti", numBiglietti);
            model.addAttribute("biglietto", bigliettoService.findById(idBiglietto));
            return "checkout";
        }
    }
}
