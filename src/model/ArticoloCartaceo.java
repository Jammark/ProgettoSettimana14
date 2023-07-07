package model;

import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class ArticoloCartaceo {

	private String titolo, codiceISBN;
	private int numeroPagine;
	private int annoPubblicazione;

	public ArticoloCartaceo(String titolo, String codiceISBN, int numeroPagine, int annoPubblicazione) {
		this.titolo = titolo;
		this.codiceISBN = codiceISBN;
		this.numeroPagine = numeroPagine;
		this.annoPubblicazione = annoPubblicazione;
	}

	public ArticoloCartaceo(String[] array) {
		this(array[0], array[1], Integer.parseInt(array[2]), Integer.parseInt(array[3]));
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getCodiceISBN() {
		return codiceISBN;
	}

	public void setCodiceISBN(String codiceISBN) {
		this.codiceISBN = codiceISBN;
	}

	public int getNumeroPagine() {
		return numeroPagine;
	}

	public void setNumeroPagine(int numeroPagine) {
		this.numeroPagine = numeroPagine;
	}

	public int getAnnoPubblicazione() {
		return annoPubblicazione;
	}

	public void setAnnoPubblicazione(int annoPubblicazione) {
		this.annoPubblicazione = annoPubblicazione;
	}

	protected String saveFormat() {
		return Arrays
				.asList(this.getClass().getName(), getTitolo(), getCodiceISBN(), getNumeroPagine(),
						getAnnoPubblicazione())
				.stream()
				.map(String::valueOf).collect(Collectors.joining("@"));
	}

	public abstract String saveId();

	public static ArticoloCartaceo fromString(String val) {
		String[] array = val.split("@");
		String className = array[0];
		array = Arrays.copyOfRange(array, 1, array.length);
		if (className.equals(Rivista.class.getName())) {
			return new Rivista(array);
		} else if (className.equals(Libro.class.getName())) {
			return new Libro(array);
		}else {
			return null;
		}
	}
}
