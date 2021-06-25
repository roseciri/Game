package game.board.card;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CardTest {

	@Test
	void test_init() {
		var c = new Card(1, 2);
		Assertions.assertEquals(1, c.getValue());
		Assertions.assertEquals(2, c.getCowsSum());
	}

	@Test
	void test_equals() {
		var c1 = new Card(1, 2);
		var c2 = new Card(1, 2);
		var c3 = new Card(1, 3);
		var c4 = new Card(2, 2);
		assertEquals(c1, c2);
		assertEquals(c2, c3);
		assertNotEquals(c1, c4);
	}

}