package model;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Libro extends ArticoloCartaceo {

	private String autore, genere;

	public Libro(String titolo, String codiceISBN, int numeroPagine, int annoPubblicazione, String autore,
			String genere) {
		super(titolo, codiceISBN, numeroPagine, annoPubblicazione);
		this.autore = autore;
		this.genere = genere;

	}

	public Libro(String[] array) {
		super(array);
		this.autore = array[4];
		this.genere = array[5];
	}

	@Override
	public String saveId() {

		return Arrays.asList(super.saveFormat(), getAutore(), getGenere()).stream().collect(Collectors.joining("@"));
	}

	public String getAutore() {
		return autore;
	}

	public void setAutore(String autore) {
		this.autore = autore;
	}

	public String getGenere() {
		return genere;
	}

	public void setGenere(String genere) {
		this.genere = genere;
	}

	@Override
	public String toString() {
		return "Libro [autore=" + autore + ", genere=" + genere + ", Titolo=" + getTitolo() + ", CodiceISBN="
				+ getCodiceISBN() + ", NumeroPagine=" + getNumeroPagine() + ", AnnoPubblicazione="
				+ getAnnoPubblicazione() + "]";
	}

}
