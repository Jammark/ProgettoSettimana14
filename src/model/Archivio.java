package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;

public class Archivio {

	private static final File file = new File("./assets/catalogo.txt");

	private Map<String, ArticoloCartaceo> catalogo;

	public Archivio() {
		this.catalogo = new HashMap<>();
	}

	public Archivio(String s) {
		this();
		getFromString(s).forEach(this::aggiungi);
	}

	public void aggiungi(ArticoloCartaceo ac) {
		this.catalogo.put(ac.getCodiceISBN(), ac);
	};

	public ArticoloCartaceo rimuovi(String codice) throws ArticoloNonDisponibileException {
		if (!this.catalogo.containsKey(codice)) {
			throw new ArticoloNonDisponibileException("Codice ISBN non trovato: " + codice);
		}
		return this.catalogo.remove(codice);
	}

	public ArticoloCartaceo ricercaByCodice(String codice) throws ArticoloNonDisponibileException {
		if (!this.catalogo.containsKey(codice)) {
			throw new ArticoloNonDisponibileException("Codice ISBN non trovato: " + codice);
		}
		return this.catalogo.get(codice);
	}

	public List<ArticoloCartaceo> ricercaByPubblicazione(int anno) throws ArticoloNonDisponibileException {

		Predicate<ArticoloCartaceo> p = ac -> ac.getAnnoPubblicazione() == anno;
		List<ArticoloCartaceo> risultati = this.catalogo.values().stream().filter(p).toList();
		if (risultati.isEmpty()) {
			throw new ArticoloNonDisponibileException("Anno di pubblicazione " + anno + " non ha corrispondenze.");
		} else {
			return risultati;
		}

	}

	public List<? extends ArticoloCartaceo> ricercaByAutore(String autore) throws ArticoloNonDisponibileException {

		Predicate<Libro> p = ac -> ac.getAutore().equals(autore);
		List<Libro> risultati = this.catalogo.values().stream().filter(ac -> ac instanceof Libro).map(ac -> (Libro) ac)
				.filter(p).toList();
		if (risultati.isEmpty()) {
			throw new ArticoloNonDisponibileException("Autore " + autore + " non ha corrispondenze.");
		} else {
			return risultati;
		}

	}

	public Collection<ArticoloCartaceo> valori() {
		return this.catalogo.values();
	}

	public String convertToString() {
		return this.catalogo.values().stream().map(ArticoloCartaceo::saveId).collect(Collectors.joining("#"));
	}

	private List<ArticoloCartaceo> getFromString(String val) {
		String[] array = val.split("#");
		return Stream.of(array).map(ArticoloCartaceo::fromString).toList();
	}

	public static void saveOnDisk(String testo) throws IOException {
		FileUtils.writeStringToFile(file, testo, "UTF-8");

	}

	public static String readFromDisk() throws IOException {
		if (file.exists()) {
			String content = FileUtils.readFileToString(file, "UTF-8");
			return content;

		} else {
			throw new FileNotFoundException("File non trovato!");

		}
	}
}
