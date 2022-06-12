package it.uniroma3.siw.livefinder.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import it.uniroma3.siw.livefinder.controller.validator.CredentialsValidator;
import it.uniroma3.siw.livefinder.controller.validator.UserValidator;
import it.uniroma3.siw.livefinder.model.Credentials;
import it.uniroma3.siw.livefinder.model.User;
import it.uniroma3.siw.livefinder.service.CredentialsService;

@Controller
public class AuthController {

	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private OAuth2AuthorizedClientService authorizedClientService;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private CredentialsValidator credentialsValidator;

	@GetMapping("/register")
	public String showRegisterForm (Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
		return "registerForm";
	}

	@GetMapping("/login") 
	public String showLoginForm(Model model) {
		return "loginForm";
	}

	@GetMapping("/logout") 
	public String logout(Model model) {
		return "index";
	}
	
	@GetMapping("/resetPassword")
	public String showResetPasswordForm(Model model) {
		model.addAttribute("credentials", new Credentials());
		return "resetPasswordForm";
	}

	@GetMapping("/default")
	public String defaultAfterLogin(Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		model.addAttribute("user", credentials.getUser());
		if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
			return "admin/home";
		}
		return "index";
	}
	
	@GetMapping("/oauthDefault")
	public String defaultAfterOAuthLogin(Model model, OAuth2AuthenticationToken authentication) {		
		// ricava il client corrispondente al token
		OAuth2AuthorizedClient client = authorizedClientService
				.loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(), authentication.getName());

		// manda una richiesta al server che contiene le informazioni sull'utente e le salva in una Map
		String userInfoEndpointUri = client.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri();

		if (!userInfoEndpointUri.isEmpty()) {
		    RestTemplate restTemplate = new RestTemplate();
		    HttpHeaders headers = new HttpHeaders();
		    headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken().getTokenValue());
		    HttpEntity<String> entity = new HttpEntity<>("", headers);
		    ResponseEntity<Map> response = restTemplate.exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);
		    Map userAttributes = response.getBody();
		    Credentials userCredentials = credentialsService.getCredentials((String) userAttributes.get("email"));
		    if(userCredentials != null) {
		    	model.addAttribute("user", userCredentials.getUser());
		    }
		    else {
		    	Credentials oauthCredentials = new Credentials();
			    User oauthUser = new User();
			    oauthUser.setNome((String) userAttributes.get("name"));
			    oauthCredentials.setUser(oauthUser);
			    oauthCredentials.setUsername((String) userAttributes.get("email"));
			    credentialsService.saveCredentials(oauthCredentials);
			    model.addAttribute("user", oauthUser);
		    }			    
		}			
		return "index";
	}

	@PostMapping("/register")
	public String registerUser(@ModelAttribute("user") User user, BindingResult userBindingResult,
			@ModelAttribute("credentials") Credentials credentials, BindingResult credentialsBindingResult,
			Model model) {

		// valida User e Credentials
		this.userValidator.validate(user, userBindingResult);
		this.credentialsValidator.validate(credentials, credentialsBindingResult);

		// se sia User che Credentials sono validi, salvali nel DB
		if(!userBindingResult.hasErrors() && ! credentialsBindingResult.hasErrors()) {
			// imposta User e salva Credentials
			// viene salvato anche User grazie alla policy Cascade.ALL
			credentials.setUser(user);
			credentialsService.saveCredentials(credentials);
			model.addAttribute("messageEN", "Registration successful!");
			model.addAttribute("messageIT", "Registrazione effettuata con successo!");
			return "operationSuccessful";
		}
		return "registerForm";
	}
	
	@PostMapping("/resetPassword")
	public String resetPassword(@ModelAttribute("credentials") Credentials credentials, BindingResult credentialsBindingResult,
			Model model) {
		// validate credentials fields
		this.credentialsValidator.validateReset(credentials, credentialsBindingResult);

		// if it hasn't invalid contents, store the Credentials into the DB
		if(!credentialsBindingResult.hasErrors()) {
			// get the user and store the credentials;
			// this also stores the User, thanks to Cascade.ALL policy
			try {
				Credentials oldCredentials = credentialsService.getCredentials(credentials.getUsername());
				credentials.setUser(oldCredentials.getUser());
				credentialsService.updatePassword(credentials, oldCredentials.getId());
				model.addAttribute("messageEN", "Password reset successful!");
				model.addAttribute("messageIT", "Reset della password effettuato correttamente!");
				return "operationSuccessful";
			}
			// user not found
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		return "resetPasswordForm";
	}
}
