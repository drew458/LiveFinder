package it.uniroma3.siw.livefinder.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Citta {
	
	public Citta(String nome, String provincia, String regione) {
		super();
		this.nome = nome;
		this.provincia = provincia;
		this.regione = regione;
	}

	public Citta() {
		
	}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;

    private String provincia;

    private String regione;

    @OneToMany(mappedBy = "citta")
    private List<Concerto> concerti;

    @OneToMany(mappedBy = "citta")
    private List<Luogo> luoghi;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getRegione() {
		return regione;
	}

	public void setRegione(String regione) {
		this.regione = regione;
	}

	public List<Concerto> getConcerti() {
		return concerti;
	}

	public void setConcerti(List<Concerto> concerti) {
		this.concerti = concerti;
	}

	public List<Luogo> getLuoghi() {
		return luoghi;
	}

	public void setLuoghi(List<Luogo> luoghi) {
		this.luoghi = luoghi;
	}

	@Override
	public int hashCode() {
		return Objects.hash(nome, provincia, regione);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Citta other = (Citta) obj;
		return Objects.equals(nome, other.nome) && Objects.equals(provincia, other.provincia)
				&& Objects.equals(regione, other.regione);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
        sb.append("Artista");
        sb.append("{id=").append(id);
        sb.append(", nome=").append(nome);
        sb.append(", provincia=").append(provincia);
        sb.append(", regione=").append(regione);
        sb.append(", concerti=").append(concerti);
        sb.append(", luoghi=").append(luoghi);
        sb.append("}\n");
        return sb.toString();
	}
    
}
