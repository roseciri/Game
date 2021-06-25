package game.board;

import game.board.card.Card;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Hand {
	Set<Card> cardSet = new HashSet<>();

	public void addCard(Card newCard) {
		cardSet.add(newCard);
	}

	/**
	 * Je rajoute un commentaire pour tester git
	 * @return laliste des cerate de la main
	 */
	@Override
	public String toString() {
		return "game.board.Hand{" +
				"cardSet=" + cardSet.stream().sorted(Comparator.comparingInt(Card::getValue)).collect(Collectors.toList()) +
				'}';
	}

	public void remove(Card c) {
		cardSet.remove(c);
	}
}
