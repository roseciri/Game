package game.rule;

import exception.NotEnoughtCardException;
import exception.TecnicalException;
import game.board.Hand;
import game.board.Table;
import game.board.card.Card;
import game.board.card.CardStack;
import game.io.AddPlayerOrPlayAction;
import game.io.GetPlayerAction;
import game.io.IOParty;
import game.io.PlayAction;
import game.player.Player;
import game.player.PlayerList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Set;

public class Party {

	private static final Logger logger = LoggerFactory.getLogger(Party.class);

	final PlayerList playerList = new PlayerList();
	final CardStack cardStack = new CardStack();
	private final Table table;

	private IOParty communicator;

	public Party(IOParty communicator) {
		this.communicator = communicator;
		cardStack.init();
		try {
			table = new Table(cardStack.getNewCard(4));
		} catch (NotEnoughtCardException e) {
			throw new TecnicalException("La pioche ne devrait pas être vide au lancement");
		}
	}

	public void launch() {
		try {
			communicator.needPlayer(this::addPlayer);
		} catch (NotEnoughtCardException e) {
			throw new TecnicalException("La pioche ne devrait pas être vide au lancement", e);
		}
	}

	private void addPlayer(String name) throws NotEnoughtCardException {
		addPlayer(new Player(name,communicator));
		if (playerList.getPlayersNumber() < 2) {
			communicator.needPlayer(this::addPlayer);
		} else {
			communicator.addPlayerOrPlay(new AddPlayerOrPlayAction() {
				/**
				 * Je pourraisfaire en sorte qu'une seule action soit utilisé
				 * @return action de jouer
				 */
				@Override
				public PlayAction getPlayAction() {
					return Party.this::play;
				}

				@Override
				public GetPlayerAction getAddPlayerAction() {
					return Party.this::addPlayer;
				}
			});
		}
	}

	private void addPlayer(Player p1) throws NotEnoughtCardException {
		playerList.addPlayer(p1);
		var hand = new Hand();
		for (var i = 1; i <= 10; i++) {
			hand.addCard(cardStack.getNewCard());
		}
		p1.setHand(hand);
	}

	public void play() {
		for (var i = 1; i <= 10; i++) {
			new Tour(this).start();
			//logger.atDebug().log(playerList.displayScore());
		}
	}

	@Override
	public String toString() {
		return "game.rule.Party{\n" +
				"playerList = " + playerList +
				"\ncardStack = " + cardStack +
				"\n}";
	}

	public Collection<Card> addCard(Player p, Card c) {
		Set<Card> cards = table.addCard(p, c);
		//logger.atDebug().log(table.toString());
		return cards;
	}

	public Table getTable() {
		return table;
	}
}
