package game.rule;

import game.board.card.Card;
import game.player.Player;
import game.player.PlayerList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.Map;

public class Tour {

	private static final Logger logger = LoggerFactory.getLogger(Tour.class);

	final CollectCardPhase collectCards;
	final Party party;

	public Tour(Party party) {
		this.party = party;
		this.collectCards = new CollectCardPhase(this);
	}


	public void start() {
		collectCards.start();
		Map<Player, Card> payload = collectCards.getPayload();
		logger.atDebug().log(payload.toString());
		payload.entrySet().stream()
				.sorted(Comparator.comparingInt(e -> e.getValue().getValue()))
				.forEach(e -> e.getKey().addPoint(party.addCard(e.getKey(), e.getValue())));

	}

	public PlayerList getPlayerList() {
		return party.playerList;
	}
}
