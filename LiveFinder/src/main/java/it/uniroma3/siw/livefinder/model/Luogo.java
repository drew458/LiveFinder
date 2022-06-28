package it.uniroma3.siw.livefinder.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
public class Luogo implements Comparable<Luogo>{
    
    public Luogo(String nome) {
		super();
		this.nome = nome;
	}

	public Luogo() {
    	
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

	@NotBlank
    private String nome;

    //private String coordinata;

	@OneToOne(cascade = CascadeType.ALL) //unidirezionale
	private Indirizzo indirizzo;

    @OneToMany(mappedBy = "luogo")
    private List<Concerto> concerti;

	@NotNull
    @ManyToOne
    private Citta citta;

	@Override
	public int hashCode() {
		return Objects.hash(nome);
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
		return Objects.equals(nome, other.nome);
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
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int compareTo(Luogo o) {
		return this.getNome().compareTo(o.getNome());
	}
}
