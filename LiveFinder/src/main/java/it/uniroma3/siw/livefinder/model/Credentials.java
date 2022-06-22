package it.uniroma3.siw.livefinder.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity
public class Credentials {

	public static final String DEFAULT_ROLE = "DEFAULT";
	public static final String ADMIN_ROLE = "ADMIN";
	public static final String MAGIC_WORD = "SIW";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = false)
	private String password;
	
	@Transient
	private String confirmPassword;
	
	@Transient
	private String oldPassword;
	
	@Transient
	private String magicWord;
	
	@Column(nullable = false)
	private String role;
	
	@OneToOne(cascade = CascadeType.ALL)
	private User user;

	public Credentials() {	}

	public Credentials(Long id, String username, String password, String role, User user) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
		this.user = user;
	}

	@Override
	public int hashCode() {
		return this.id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		Credentials that = (Credentials) obj;
		return this.id.equals(that.getId());
	}
}
