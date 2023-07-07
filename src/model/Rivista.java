package model;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Rivista extends ArticoloCartaceo {

	private Periodicità periodicità;

	public Rivista(String titolo, String codiceISBN, int numeroPagine, int annoPubblicazione,
			Periodicità periodicità) {
		super(titolo, codiceISBN, numeroPagine, annoPubblicazione);
		this.periodicità = periodicità;
	}

	public Rivista(String[] arr) {
		super(arr);
		this.periodicità = Periodicità.valueOf(arr[4]);
	}

	@Override
	public String saveId() {

		return Arrays.asList(saveFormat(), getPeriodicità()).stream().map(String::valueOf)
				.collect(Collectors.joining("@"));
	}

	public Periodicità getPeriodicità() {
		return periodicità;
	}

	public void setPeriodicità(Periodicità periodicità) {
		this.periodicità = periodicità;
	}

	@Override
	public String toString() {
		return "Rivista [periodicità=" + periodicità + ", Titolo=" + getTitolo() + ", CodiceISBN=" + getCodiceISBN()
				+ ", NumeroPagine=" + getNumeroPagine() + ", AnnoPubblicazione="
				+ getAnnoPubblicazione() + "]";
	}


}
