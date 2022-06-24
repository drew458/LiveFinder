package it.uniroma3.siw.livefinder.model;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
public class Biglietto {
	
	public Biglietto(@NotBlank String tipologia,
			@DecimalMin(value = "0.00", inclusive = true) @Digits(integer = 3, fraction = 2) BigDecimal prezzo) {
		super();
		this.tipologia = tipologia;
		this.prezzo = prezzo;
	}

	public Biglietto() {
		
	}
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String tipologia;

    /**
     * L'annotazione in questione non supporta i double.
     * inclusive = true è di default ma è indicato per leggibilità.
     */
    @DecimalMin(value = "0.00", inclusive = true)
    @Digits(integer=3, fraction = 2)
    private BigDecimal prezzo;

	private Integer numPosti;

	@Override
	public int hashCode() {
		return Objects.hash(prezzo, tipologia);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Biglietto other = (Biglietto) obj;
		return Objects.equals(prezzo, other.prezzo) && Objects.equals(tipologia, other.tipologia);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
        sb.append("Artista");
        sb.append("{id=").append(id);
        sb.append(", tipologia=").append(tipologia);
        sb.append(", prezzo=").append(prezzo);
        sb.append("}\n");
        return sb.toString();
	}
}
