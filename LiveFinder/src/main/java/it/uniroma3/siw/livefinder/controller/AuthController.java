package it.uniroma3.siw.livefinder.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.livefinder.controller.validator.CredentialsValidator;
import it.uniroma3.siw.livefinder.controller.validator.UserValidator;
import it.uniroma3.siw.livefinder.model.Credentials;
import it.uniroma3.siw.livefinder.model.Indirizzo;
import it.uniroma3.siw.livefinder.model.User;
import it.uniroma3.siw.livefinder.service.CredentialsService;

@Controller
public class AuthController {

	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private UserValidator userValidator;

	@Autowired
	private CredentialsValidator credentialsValidator;

	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
		
		return "registerForm";
	}
	
	@GetMapping("/adminRegister")
	public String showAdminRegisterForm(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
		
		return "adminRegisterForm";
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
	
	@GetMapping("/changePassword")
	public String showChangePasswordForm(Model model) {
		model.addAttribute("credentials", new Credentials());
		return "changePasswordForm";
	}
	
	@GetMapping("/changeUsername")
	public String changeUsername(Model model) {		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("oldUsername", userDetails.getUsername());
		model.addAttribute("newUsername", new String());
		
		return "changeUsernameForm";
	}

	@GetMapping("/default")
	public String defaultAfterLogin(Model model) {
		Credentials credentials = credentialsService.getCredentials();

		if(credentials != null){
			model.addAttribute("user", credentials.getUser());
			if(credentials.getRole().equals(Credentials.ADMIN_ROLE)){
				return "admin/home";
			}
		}
		
		return "index";
	}
	
	@GetMapping("/oauthDefault")
	public String defaultAfterOAuthLogin(Model model, OAuth2AuthenticationToken authentication) {		
		// ricava il client corrispondente al token
		OAuth2User oAuth2User = authentication.getPrincipal();
		Map<String,Object> attributes = oAuth2User.getAttributes();
		
		String[] nomeCompleto;
		String nome = ((String) attributes.get("name"));
		
		if(authentication.getAuthorizedClientRegistrationId().equals("google")) {
			String email = (String) attributes.get("email");
			Credentials userCredentials = credentialsService.getCredentials(email);
		    
			if(userCredentials != null) {
		    	model.addAttribute("user", userCredentials.getUser());
		    }
		    else {
		    	Credentials oauthCredentials = new Credentials();
			    User oauthUser = new User();

				if(nome!=null){
					nomeCompleto = nome.split(" ");
					oauthUser.setNome(nomeCompleto[0]);
					oauthUser.setCognome(nomeCompleto[1]);
				}

			    oauthCredentials.setUser(oauthUser);
			    oauthCredentials.setUsername(email);
			    credentialsService.saveCredentials(oauthCredentials, false);
			    model.addAttribute("user", oauthUser);
		    }
		}
		if(authentication.getAuthorizedClientRegistrationId().equals("github")) {
			String username= (String) attributes.get("login");
			Credentials userCredentials = credentialsService.getCredentials(username);
		    
			if(userCredentials != null) {
		    	model.addAttribute("user", userCredentials.getUser());
		    }
		    else {
		    	Credentials oauthCredentials = new Credentials();
			    User oauthUser = new User();

				if(nome!=null){
					nomeCompleto = nome.split(" ");
					oauthUser.setNome(nomeCompleto[0]);
					oauthUser.setCognome(nomeCompleto[1]);
				}

			    oauthCredentials.setUser(oauthUser);
			    oauthCredentials.setUsername(username);
			    credentialsService.saveCredentials(oauthCredentials, false);
			    model.addAttribute("user", oauthUser);
		    }
		}

		return "index";
	}
	
	@GetMapping("/profile")
	public String userProfile(Model model) {

		Credentials credentials = credentialsService.getCredentials();
		User user = credentials.getUser();
		model.addAttribute("credentials", credentials);
		model.addAttribute("user", user);
		model.addAttribute("canChange", credentialsService.isLoggedWithEmail());

		Indirizzo indirizzo = user.getIndirizzo()!=null ? user.getIndirizzo() : new Indirizzo();
		model.addAttribute("indirizzo", indirizzo);
		
		return "userProfile";
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
			credentialsService.saveCredentials(credentials, false);
			model.addAttribute("messageEN", "Registration successful!");
			model.addAttribute("messageIT", "Registrazione effettuata con successo!");
			return "operationSuccessful";
		}
		return "registerForm";
	}
	
	@PostMapping("/adminRegister")
	public String registerAdmin(@ModelAttribute("user") User user, BindingResult userBindingResult,
			@ModelAttribute("credentials") Credentials credentials, BindingResult credentialsBindingResult, 
			Model model) {

		// validate user and credentials fields
		this.userValidator.validate(user, userBindingResult);
		this.credentialsValidator.validate(credentials, credentialsBindingResult);

		// if neither of them had invalid contents, store the User and the Credentials into the DB
		if(!userBindingResult.hasErrors() && ! credentialsBindingResult.hasErrors()
				&& credentials.getMagicWord().equals(Credentials.MAGIC_WORD)) {
			// set the user and store the credentials;
			// this also stores the User, thanks to Cascade.ALL policy
			credentials.setUser(user);
			credentialsService.saveCredentials(credentials, true);
			model.addAttribute("messageEN", "Admin registration successful!");
			model.addAttribute("messageIT", "Registrazione Admin effettuata con successo!");
			return "operationSuccessful";
		}
		return "adminRegisterForm";
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
	
	@PostMapping("/changePassword")
	public String changePassword(@ModelAttribute("credentials") Credentials credentials, BindingResult credentialsBindingResult, 
			Model model) {
		// validate credentials fields
		this.credentialsValidator.validateReset(credentials, credentialsBindingResult);
		
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		// if it hasn't invalid contents, store the Credentials into the DB
		if(!credentialsBindingResult.hasErrors() && 
				credentialsService.encodePassword(credentials.getOldPassword()).equals(userDetails.getPassword())) {
			// get the user and store the credentials;
			// this also stores the User, thanks to Cascade.ALL policy
			Credentials oldCredentials = credentialsService.getCredentials(credentials.getUsername());
			credentials.setUser(oldCredentials.getUser());
			credentialsService.updatePassword(credentials, oldCredentials.getId());
			model.addAttribute("messageEN", "Password successfully changed!");
			model.addAttribute("messageIT", "Password cambiata correttamente!");
			return "operationSuccessful";
		}
		return "changePasswordForm";
	}
	
	@PostMapping("/changeUsername")
	public String changeUsername(@ModelAttribute("newUsername") String newUsername, BindingResult usernameBindingResult, Model model) {
		this.credentialsValidator.validateUsername(credentialsValidator, usernameBindingResult);
		
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(!usernameBindingResult.hasErrors()) {
			Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
			credentialsService.updateUsername(newUsername, credentials.getId());
			model.addAttribute("messageEN", "Username successfully changed!");
			model.addAttribute("messageIT", "Username cambiata correttamente!");
			return "operationSuccessful";
		}
		
		return "changeUsernameForm";
	}
}
