package it.uniroma3.siw.livefinder.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "users") // "User" è riservato in Postgres
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false)
	private String nome;
	
	@Column(nullable=false)
	private String cognome;

	public User() {	}

	public User(Long id, String nome, String cognome) {
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
	}
	
	@Override
	public boolean equals(Object obj) {
		User that = (User) obj;
		return this.id.equals(that.getId());
	}
	
	@Override
	public int hashCode() {
		return this.id.hashCode();
	}

}
