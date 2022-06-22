package it.uniroma3.siw.livefinder.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity 
public class Contatto {
    
    public Contatto(String facebookLink, String instragramLink, String youtubeLink, String twitterLink,
			String numTelefono) {
		super();
		this.facebookLink = facebookLink;
		this.instragramLink = instragramLink;
		this.youtubeLink = youtubeLink;
		this.twitterLink = twitterLink;
		this.numTelefono = numTelefono;
	}

	public Contatto() {
    	
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String facebookLink;

    private String instragramLink;

    private String youtubeLink;

    private String twitterLink;

    private String numTelefono;

	@Override
	public int hashCode() {
		return Objects.hash(facebookLink, instragramLink, numTelefono, twitterLink, youtubeLink);
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
		Contatto other = (Contatto) obj;
		return Objects.equals(facebookLink, other.facebookLink) && Objects.equals(instragramLink, other.instragramLink)
				&& Objects.equals(numTelefono, other.numTelefono) && Objects.equals(twitterLink, other.twitterLink)
				&& Objects.equals(youtubeLink, other.youtubeLink);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Contatto [");
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (facebookLink != null) {
			builder.append("facebookLink=");
			builder.append(facebookLink);
			builder.append(", ");
		}
		if (instragramLink != null) {
			builder.append("instragramLink=");
			builder.append(instragramLink);
			builder.append(", ");
		}
		if (youtubeLink != null) {
			builder.append("youtubeLink=");
			builder.append(youtubeLink);
			builder.append(", ");
		}
		if (twitterLink != null) {
			builder.append("twitterLink=");
			builder.append(twitterLink);
			builder.append(", ");
		}
		if (numTelefono != null) {
			builder.append("numTelefono=");
			builder.append(numTelefono);
		}
		builder.append("]");
		return builder.toString();
	}
    
}
