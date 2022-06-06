package it.uniroma3.siw.livefinder.model;

import java.time.Year;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

@Entity
public class Artista {
	
	public Artista(String nome, String genere, Year annoFormazione, boolean isGroup) {
		super();
		this.nome = nome;
		this.genere = genere;
		this.annoFormazione = annoFormazione;
		this.isGroup = isGroup;
	}

	public Artista() {
		
	}
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

	@NotBlank
    private String nome;

	@NotBlank
    private String genere;

    private Year annoFormazione;

    private boolean isGroup;

    @OneToMany(mappedBy = "artista")
    private List<Tour> listaTour;

    @OneToOne
    private Contatto contatto;

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

	public String getGenere() {
		return genere;
	}

	public void setGenere(String genere) {
		this.genere = genere;
	}

	public Year getAnnoFormazione() {
		return annoFormazione;
	}

	public void setAnnoFormazione(Year annoFormazione) {
		this.annoFormazione = annoFormazione;
	}

	public boolean getIsGroup() {
		return isGroup;
	}

	public void setIsGroup(boolean isGroup) {
		this.isGroup = isGroup;
	}

	public List<Tour> getListaTour() {
		return listaTour;
	}

	public void setListaTour(List<Tour> listaTour) {
		this.listaTour = listaTour;
	}

	public Contatto getContatto() {
		return contatto;
	}

	public void setContatto(Contatto contatto) {
		this.contatto = contatto;
	}

	@Override
	public int hashCode() {
		return Objects.hash(annoFormazione, genere, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Artista other = (Artista) obj;
		return Objects.equals(annoFormazione, other.annoFormazione) && Objects.equals(genere, other.genere)
				&& Objects.equals(nome, other.nome);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
        sb.append("Artista");
        sb.append("{id=").append(id);
        sb.append(", nome=").append(nome);
        sb.append(", genere=").append(genere);
        sb.append(", annoFormazione=").append(annoFormazione);
        sb.append(", isGroup=").append(isGroup);
        sb.append(", listaTour=").append(listaTour);
        sb.append(", contatto=").append(contatto);
        sb.append("}\n");
        return sb.toString();
	}
}
