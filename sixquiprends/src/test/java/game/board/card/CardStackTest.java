package game.board.card;


import exception.NotEnoughtCardException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

class CardStackTest {

	@Test
	void testInit_AllDifferentCardInStack() throws NotEnoughtCardException {
		var cardStack = new CardStack();
		cardStack.init();
		Set<Card> result = new HashSet<>();
		for (var i = 1; i <= CardStack.CARDS_NUMBER; i++) {
			Card c = cardStack.getNewCard();
			Assertions.assertFalse(result.contains(c));
			result.add(c);
		}
		Assertions.assertEquals( CardStack.CARDS_NUMBER,result.size());
	}

	@Test
	void testInit_AllDifferentCardInStackOnce() throws NotEnoughtCardException {
		var cardStack = new CardStack();
		cardStack.init();
		Set<Card> result = new HashSet<>(cardStack.getNewCard(104));
		Assertions.assertEquals(CardStack.CARDS_NUMBER, result.size());
	}

	@Test()
	void testInit_NotMore() {
		var cardStack = new CardStack();
		cardStack.init();
		Set<Card> result = new HashSet<>();
		Assertions.assertThrows(NotEnoughtCardException.class, () -> cardStack.getNewCard(1056));
	}

}