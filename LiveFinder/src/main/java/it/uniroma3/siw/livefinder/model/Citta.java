package it.uniroma3.siw.livefinder.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
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
    private List<Luogo> luoghi;

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
        sb.append("}\n");
        return sb.toString();
	}
    
}
