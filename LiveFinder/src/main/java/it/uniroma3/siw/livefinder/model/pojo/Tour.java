package it.uniroma3.siw.livefinder.model.pojo;

import java.time.Year;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

@Entity
public class Tour {
	
	public Tour(@NotBlank String nome, Year anno) {
		super();
		this.nome = nome;
		this.anno = anno;
	}

	public Tour() {
		
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	private String nome;
	
	private Year anno;

	@ManyToOne
	private Artista artista;

	@OneToMany(mappedBy = "tour")
	private List<Concerto> concerti;

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

	public Year getAnno() {
		return anno;
	}

	public void setAnno(Year anno) {
		this.anno = anno;
	}

	public Artista getArtista() {
		return artista;
	}

	public void setArtista(Artista artista) {
		this.artista = artista;
	}

	public List<Concerto> getConcerti() {
		return concerti;
	}

	public void setConcerti(List<Concerto> concerti) {
		this.concerti = concerti;
	}

	@Override
	public int hashCode() {
		return Objects.hash(anno, nome);
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
		Tour other = (Tour) obj;
		return Objects.equals(anno, other.anno) && Objects.equals(nome, other.nome);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Tour [id=");
		builder.append(id);
		builder.append(", nome=");
		builder.append(nome);
		builder.append(", anno=");
		builder.append(anno);
		builder.append(", artista=");
		builder.append(artista);
		builder.append(", concerti=");
		builder.append(concerti);
		builder.append("]");
		return builder.toString();
	}

}
