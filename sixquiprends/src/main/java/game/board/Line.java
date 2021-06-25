package game.board;

import game.board.card.Card;

import java.util.*;
import java.util.stream.Collectors;

public class Line {

	List<Card> cards = new ArrayList<>();
	int value;

	public Line(Card c) {
		addCardToEnd(c);
	}

	private void addCardToEnd(Card c) {
		cards.add(c);
		value = c.getValue();
	}

	public Optional<Set<Card>> addCard(Card c) {
		if (!isFinished()) {
			addCardToEnd(c);
			return Optional.empty();
		} else {
			return Optional.of(getCards(c));
		}
	}

	public boolean isFinished() {
		return cards.size() >= 5;
	}

	public Set<Card> getCards(Card c) {
		Set<Card> result = new HashSet<>(cards);
		cards = new ArrayList<>();
		addCardToEnd(c);
		return result;
	}

	@Override
	public String toString() {
		String values = cards.stream().map(c -> String.valueOf(c.getValue())).collect(Collectors.joining("|"));
		return "|" + values + "|";
	}
}
