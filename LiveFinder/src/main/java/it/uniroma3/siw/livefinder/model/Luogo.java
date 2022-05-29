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

@Entity
public class Luogo {
    
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

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public List<Concerto> getConcerti() {
		return concerti;
	}

	public void setConcerti(List<Concerto> concerti) {
		this.concerti = concerti;
	}

	public Contatto getContatto() {
		return contatto;
	}

	public void setContatto(Contatto contatto) {
		this.contatto = contatto;
	}

	public Citta getCitta() {
		return citta;
	}

	public void setCitta(Citta citta) {
		this.citta = citta;
	}

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
		if (contatto != null) {
			builder.append("contatto=");
			builder.append(contatto);
			builder.append(", ");
		}
		if (citta != null) {
			builder.append("citta=");
			builder.append(citta);
		}
		builder.append("]");
		return builder.toString();
	}
}
