package it.uniroma3.siw.livefinder.service;

import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.livefinder.model.Credentials;
import it.uniroma3.siw.livefinder.repository.CredentialsRepository;

@Service
public class CredentialsService {

	@Autowired
    protected PasswordEncoder passwordEncoder;

	@Autowired
	protected CredentialsRepository credentialsRepository;

	public Credentials updateCredentials(Credentials credentials){
		return credentialsRepository.save(credentials); //crud repository usa save anche per l'update
	}

	public Credentials findByUsername(String username){
		return credentialsRepository.findByUsername(username).orElse(null);
	}

	public boolean isLoggedWithEmail(){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return principal.getClass().equals(User.class);
	}

	/**
	 * Questo metodo ritorna un oggetto credentials associato all'utente loggato al momento
	 */
	public Credentials getCredentials(){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = null;
        
		//se loggato con Google
	    if(principal.getClass().equals(DefaultOidcUser.class)){ 
	        DefaultOidcUser user = (DefaultOidcUser) principal;
	        credentials = this.findByUsername(user.getAttribute("email"));
	    }
			
			//se loggato con GitHub
	    else if(principal.getClass().equals(DefaultOAuth2User.class)){ 
	        DefaultOAuth2User user = (DefaultOAuth2User) principal;
	        credentials = this.findByUsername(user.getAttribute("login"));
	    }
	        
	    //se loggato con email e pwd
	    else if(principal.getClass().equals(User.class)){
	        User user = (User) principal;
	        credentials = this.findByUsername(user.getUsername());
	    }
	
		return credentials;
	}
	
	@Transactional
	public Credentials getCredentials(Long id) {
		Optional<Credentials> result = this.credentialsRepository.findById(id);
		return result.orElse(null);
	}

	@Transactional
	public Credentials getCredentials(String username) {
		Optional<Credentials> result = this.credentialsRepository.findByUsername(username);
		return result.orElse(null);
	}
		
    @Transactional
    public Credentials saveCredentials(Credentials credentials, boolean isAdmin) {
        if(isAdmin) {
        	credentials.setRole(Credentials.ADMIN_ROLE);
        }
        else {
        	credentials.setRole(Credentials.DEFAULT_ROLE);
        }
        
        if(credentials.getUser().getCognome()==null) {
        	credentials.getUser().setCognome(".");
        }

        try {
        	credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
        }
        catch(Exception e) {
        	credentials.setPassword(this.passwordEncoder.encode(RandomStringUtils.randomAlphanumeric(10)));
        }
        
        return this.credentialsRepository.save(credentials);
    }
    
    @Transactional
    public Credentials updateUsername(String username, Long id) {
    	Credentials toUpdateCredentials = credentialsRepository.findById(id).get();
    	toUpdateCredentials.setUsername(username);
    	credentialsRepository.save(toUpdateCredentials);
    	return toUpdateCredentials;
    }
    
    @Transactional
    public Credentials updatePassword(Credentials credentials, Long id) {
		Credentials toUpdateCredentials = credentialsRepository.findById(id).get();
    	toUpdateCredentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
    	credentialsRepository.save(toUpdateCredentials);
    	return toUpdateCredentials;
    }
    
    public String encodePassword(String password) {
    	return this.passwordEncoder.encode(password);
    }
    
    @Transactional
    public String getRoleAuthenticated() {
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = this.getCredentials(userDetails.getUsername());
    	return credentials.getRole();
    }
}
