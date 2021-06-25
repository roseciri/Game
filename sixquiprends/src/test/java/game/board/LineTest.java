package game.board;

import game.board.card.Card;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

class LineTest {

	@Test
	void test_line_get() {
		Card c1 = new Card(1, 0);
		Card c2 = new Card(2, 0);
		Card c3 = new Card(3, 0);
		var l = new Line(c1);
		l.addCard(c2);
		Set<Card> cards = l.getCards(c3);
		Assertions.assertEquals(2, cards.size());
		Assertions.assertTrue(cards.contains(c1));
		Assertions.assertTrue(cards.contains(c2));
	}

	@Test
	void test_line_add() {
		Card c1 = new Card(1, 0);
		Card c2 = new Card(2, 0);
		Card c3 = new Card(3, 0);
		Card c4 = new Card(4, 0);
		Card c5 = new Card(5, 0);
		Card c6 = new Card(6, 0);
		var l = new Line(c1);
		Optional<Set<Card>> cards1 = l.addCard(c2);
		Assertions.assertTrue(cards1.isEmpty());
		Optional<Set<Card>> cards2 = l.addCard(c3);
		Assertions.assertTrue(cards2.isEmpty());
		Optional<Set<Card>> cards3 = l.addCard(c4);
		Assertions.assertTrue(cards3.isEmpty());
		Optional<Set<Card>> cards4 = l.addCard(c5);
		Assertions.assertTrue(cards4.isEmpty());
		Optional<Set<Card>> cards5 = l.addCard(c6);
		Assertions.assertFalse(cards5.isEmpty());
		Assertions.assertEquals(5, cards5.get().size());
	}
}