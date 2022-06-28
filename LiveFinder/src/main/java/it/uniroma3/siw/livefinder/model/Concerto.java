package it.uniroma3.siw.livefinder.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
public class Concerto implements Comparable<Concerto>{
	
	public Concerto(Date data) {
		super();
		this.data = data;
	}

	public Concerto() {
		
	}
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

	/*
	 * La data viene inserita nella form con un input type="datetime-local" che permette di visualizzare
	 * la data secondo il tipo di formato usato nel paese in cui si vede la pagina. Va aggiunta questa 
	 * annotazione per permettere la conversione della data e va azzeccata la formattazione nascosta 
	 * usata da datetime-local 
	 */
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date data;

	@NotNull
	@ManyToOne
	private Tour tour;

	@NotNull
	@ManyToOne
	private Luogo luogo;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "concerto_id")
	private List<Biglietto> biglietti;

	public void addBiglietto(Biglietto biglietto){
		if(this.getBiglietti()==null){
			this.setBiglietti(new ArrayList<>());
		}
		this.getBiglietti().add(biglietto);
	}

	@Override
	public int hashCode() {
		return Objects.hash(data);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Concerto other = (Concerto) obj;
		return Objects.equals(data, other.data);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
        sb.append("Concerto");
        sb.append("{id=").append(id);
        sb.append(", data=").append(data);
        return sb.toString();
	}

	@Override
	public int compareTo(Concerto that) {
		int compare = this.getData().compareTo(that.getData());
		if(compare == 0){
			compare = this.getTour().getNome().compareTo(that.getTour().getNome());
		}
		return compare;
	}
}
