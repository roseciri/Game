package game.player;

import game.board.Hand;
import game.board.Line;
import game.board.card.Card;
import game.io.IOParty;
import game.io.IOPlayer;
import game.rule.CollectCardPhase;
import game.rule.SelectionLinePhase;

import java.util.Collection;
import java.util.List;

public class Player {
	Hand hand;
	final String name;
	final IOPlayer communicator;
	int points = 0;

	public Player(String name, IOParty communicator) {
		this.name = name;
		this.communicator = communicator.addPlayerCommunicator(this);
	}

	public void setHand(Hand hand) {
		this.hand = hand;
	}

	@Override
	public String toString() {
		return name;
	}


	public void selectCard(CollectCardPhase collectCardPhase) {
		communicator.selectCard(c -> collectCardPhase.playerPlayCard(this, new Card(c, c)));
	}

	public void addPoint(Collection<Card> addCard) {
		points += addCard.stream().map(Card::getCowsSum).reduce(Integer::sum).orElse(0);
	}

	public void selectLine(SelectionLinePhase selectionLinePhase, List<Line> lines) {
		communicator.selectLine(c -> selectionLinePhase.select(lines.get(0)));
	}


	public String getName() {
		return name;
	}

	public Hand getHand() {
		return hand;
	}
}
