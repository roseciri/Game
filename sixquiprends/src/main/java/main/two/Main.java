package main.two;

import exception.NotEnoughtCardException;
import main.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Main {

	private static final Logger logger = LoggerFactory.getLogger(Main.class);


	public static void main(String[] args) {
		logger.atDebug().log("C'est parti mon quiqui");
		Map<UUID, IOPartyEtat> parties = new HashMap<>();
		var end = false;
		while (!end) {
			String instruction = IOUtils.read();
			if (instruction.equals("create")) {
				create(parties);
			} else if (instruction.startsWith("addp")) {
				addPalyer(parties, instruction);
			} else if (instruction.startsWith("play")) {
				play(parties, instruction);
			} else if (instruction.equals("end")) {
				end = finish();
			}
		}
	}

	private static boolean finish() {
		return true;
	}
	@SuppressWarnings("squid:S106")
	private static void create(Map<UUID, IOPartyEtat> parties) {
		var identifiantParty = UUID.randomUUID();
		var ioParty = new IOPartyEtat();
		ioParty.createParty();
		parties.put(identifiantParty, ioParty);
		System.out.println("Vous venez de créer la partie " + identifiantParty);
	}
	@SuppressWarnings("squid:S106")
	private static void addPalyer(Map<UUID, IOPartyEtat> parties, String instruction) {
		var words = instruction.split(" ");
		if (words.length == 3) {
			try {
				parties.get(UUID.fromString(words[1])).addPlayer(words[2]);
			} catch (NotEnoughtCardException e) {
				System.out.println("Il n'est plus possible d'ajouter de joueur (pas assez de cartes, il faudrait faire plus de cartes...)");
			}
		} else {
			System.out.println("L'instruction addp a 2 arguments le num de la partie et le nom du joueur");
		}
	}
	@SuppressWarnings("squid:S106")
	private static void play(Map<UUID, IOPartyEtat> parties, String instruction) {
		var words = instruction.split(" ");
		if (words.length == 2) {
			parties.get(UUID.fromString(words[1])).addPlay();
		} else {
			System.out.println("L'instruction addp a 1 argument le num de la partie");
		}
	}
}
