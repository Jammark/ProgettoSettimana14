package program;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.javafaker.Faker;

import model.Archivio;
import model.ArticoloCartaceo;
import model.ArticoloNonDisponibileException;
import model.Libro;
import model.Periodicità;
import model.Rivista;

public class Main {

	private static final Logger log = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {

		Faker faker = new Faker(new Random(24));
		List<Libro> libri = Arrays.asList(
				new Libro("Musica latina", codiceISBN(faker), 100, 1980, faker.name().name(),
						"musica"),
				new Libro("Musica rock", codiceISBN(faker), 150, 1990, faker.name().name(),
						"musica"),
				new Libro("Torte e dolci", codiceISBN(faker), 300, 2005, faker.name().name(), "cucina"),
				new Libro("Pane e biscotti", codiceISBN(faker), 250, 2008, faker.name().name(), "cucina"));

		log.info("Serie di libri:");
		libri.forEach(l -> log.info("Lista libri: " + l));

		List<Rivista> riviste = Arrays.asList(
				new Rivista("Settimana enigmistica", codiceISBN(faker), 70, 1995,
						Periodicità.SETTIMANALE),
				new Rivista("Focus", codiceISBN(faker), 200, 2000, Periodicità.MENSILE),
				new Rivista("Monete e banconote", codiceISBN(faker), 60, 1997,
						Periodicità.SEMESTRALE));
		log.info("Serie di riviste:");
		riviste.forEach(r -> log.info("Lista riviste: " + r));

		Archivio archivio = new Archivio();
		libri.forEach(archivio::aggiungi);
		riviste.forEach(archivio::aggiungi);

		Scanner sc = new Scanner(System.in);
		log.info("Inserire codice ISBN dell'elemento da rimuovere");
		String codice = sc.nextLine();
		try {
			archivio.rimuovi(codice);

			log.info("Inserire codice ISBN dell'elemento da cercare");
			codice = sc.nextLine();
			ArticoloCartaceo articolo = archivio.ricercaByCodice(codice);
			log.info("Ricerca secondo codice isbn: " + articolo);
			archivio.ricercaByPubblicazione(cercaPerAnno(sc))
					.forEach(a -> log.info("ricerca per anno pubblicazione: " + a));
			log.info("Inserire autore per la ricerca");
			String autore = sc.nextLine();
			archivio.ricercaByAutore(autore).forEach(a -> log.info("Ricerca per autore: " + a));

		} catch (ArticoloNonDisponibileException e) {
			log.error(e.getMessage());
		} finally {
			sc.close();
		}

		try {
			Archivio.saveOnDisk(archivio.convertToString());

			Archivio fromFile = new Archivio(Archivio.readFromDisk());
			log.info("Lettura archivio.");
			fromFile.valori().forEach(a -> log.info("Archivio: " + a));

		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	public static int cercaPerAnno(Scanner sc) {
		try {
			log.info("Inserire anno pubblicazione articolo da cercare");
			int input = Integer.parseInt(sc.nextLine());
			return input;
		} catch (NumberFormatException e) {
			log.error("Valore inserito non corretto.");
			log.error(e.getMessage());
			return cercaPerAnno(sc);
		}
	}

	public static String codiceISBN(Faker f) {
		return f.regexify("[0-9A-Z]{3}([A-Z]){2}([0-9A-Z]){2}");
	}

}
