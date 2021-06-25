package main.one;

import game.board.Hand;
import game.io.IOParty;
import main.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintStream;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	static Lock verrou = new ReentrantLock();

	public static void main(String[] args) {
		logger.atDebug().log("start");
		IOParty ioParty = new IOPartyEvent();
		ioParty.createParty();
	}

	@SuppressWarnings("squid:S106")
	public static String quiJoue() {
		new PrintStream(System.out).println("Quel joueur joue ?");
		return IOUtils.read();
	}

	@SuppressWarnings("squid:S106")
	public static String ajouterUnJoueurOuJouer() {
		new PrintStream(System.out).println("Jouer(play) ou Ajouter un joueur (add) ?");
		return IOUtils.read();
	}

	@SuppressWarnings("squid:S106")
	public static String quelCarte(String name, Hand hand) {
		verrou.lock();
		new PrintStream(System.out).printf("[%s] Quelle Carte sélectionnée ? (%s)%n", name,  hand.toString());
		String read = IOUtils.read();
		verrou.unlock();
		return read;

	}

	@SuppressWarnings("squid:S106")
	public static String quelLigne(String name) {
		verrou.lock();
		new PrintStream(System.out).printf("[%s] Quelle Ligne sélectionnée  ?%n", name);
		String read = IOUtils.read();
		verrou.unlock();
		return read;
	}

	@SuppressWarnings("squid:S106")
	public static void afficheTable(String displayTable) {
		new PrintStream(System.out).print(displayTable);
	}
}
