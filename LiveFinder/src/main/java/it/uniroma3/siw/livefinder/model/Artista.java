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
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
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

    @NotNull
    private Year annoFormazione;

    private boolean isGroup;

    @OneToMany(mappedBy = "artista")
    private List<Tour> listaTour;

    @OneToOne
    private Contatto contatto;

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
