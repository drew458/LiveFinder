package it.uniroma3.siw.livefinder.model;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Concerto implements Comparable{
	
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
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date data;

	@ManyToOne
	private Tour tour;

	@ManyToOne
	private Citta citta;

	@ManyToOne
	private Luogo luogo;

	@OneToMany
	@JoinColumn(name = "biglietto_id")
	private List<Biglietto> biglietti;

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Tour getTour() {
		return tour;
	}

	public void setTour(Tour tour) {
		this.tour = tour;
	}

	public Citta getCitta() {
		return citta;
	}

	public void setCitta(Citta citta) {
		this.citta = citta;
	}

	public Luogo getLuogo() {
		return luogo;
	}

	public void setLuogo(Luogo luogo) {
		this.luogo = luogo;
	}

	public List<Biglietto> getBiglietti() {
		return biglietti;
	}

	public void setBiglietti(List<Biglietto> biglietti) {
		this.biglietti = biglietti;
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
        sb.append("Artista");
        sb.append("{id=").append(id);
        sb.append(", data=").append(data);
        sb.append(", tour=").append(tour);
        sb.append(", citta=").append(citta);
        sb.append(", luogo=").append(luogo);
        for(Biglietto biglietto : biglietti)
        	sb.append(", biglietto=").append(biglietto);
        sb.append("}\n");
        return sb.toString();
	}

	@Override
	public int compareTo(Object o) {
		Concerto that = (Concerto)o;
		int compare = this.getData().compareTo(that.getData());
		if(compare == 0){
			compare = this.getTour().getNome().compareTo(that.getTour().getNome());
		}
		return compare;
	}
}
