package it.uniroma3.siw.livefinder.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class Luogo implements Comparable<Luogo>{
    
    public Luogo(String nome, String indirizzo, String cap) {
		super();
		this.nome = nome;
		this.indirizzo = indirizzo;
		this.cap = cap;
	}

	public Luogo() {
    	
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;

    private String indirizzo;

    private String cap;

    //private String coordinata;

    @OneToMany(mappedBy = "luogo")
    private List<Concerto> concerti;

    @OneToOne
    private Contatto contatto;

    @ManyToOne
    private Citta citta;
	@Override
	public int hashCode() {
		return Objects.hash(cap, indirizzo, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Luogo other = (Luogo) obj;
		return Objects.equals(cap, other.cap) && Objects.equals(indirizzo, other.indirizzo)
				&& Objects.equals(nome, other.nome);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Luogo [");
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (nome != null) {
			builder.append("nome=");
			builder.append(nome);
			builder.append(", ");
		}
		if (indirizzo != null) {
			builder.append("indirizzo=");
			builder.append(indirizzo);
			builder.append(", ");
		}
		if (cap != null) {
			builder.append("cap=");
			builder.append(cap);
			builder.append(", ");
		}
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int compareTo(Luogo o) {
		return this.getNome().compareTo(o.getNome());
	}
}
