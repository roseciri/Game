package game.board.card;

import exception.NotEnoughtCardException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CardStack {

	private static final Logger logger = LoggerFactory.getLogger(CardStack.class);
	public static final int CARDS_NUMBER = 104;

	private final List<Card> cardList = new ArrayList<>(CARDS_NUMBER);
	private Iterator<Card> iterator;
	

	public void init() {
		//Création du tas de carte dans le désordre
		for (var i = 1; i <= CARDS_NUMBER; i++) {
			cardList.add(getRandom()% (cardList.size() + 1), createCards(i));
		}
		iterator = cardList.iterator();
		//logger.atDebug().log("init ok");
	}


	private int getRandom() {
		return new SecureRandom().nextInt(CARDS_NUMBER);
	}

	private Card createCards(int i) {
		return new Card(i, getNbCowsForValue(i));
	}

	private int getNbCowsForValue(int i) {
		return i;
	}

	public Card getNewCard() throws NotEnoughtCardException {
		if(iterator.hasNext()){
			return iterator.next();
		} else {
			throw new NotEnoughtCardException("Plus de cartes dans la pioche");
		}
	}

	@Override
	public String toString() {
		return "CardStack{" +
				"cardsList=" + cardList +
				'}';
	}

	public List<Card> getNewCard(int nb) throws NotEnoughtCardException {
		List<Card> result = new ArrayList<>(nb);
		for (var i = 0; i < nb; i++) {
			result.add(getNewCard());
		}
		return result;
	}
}
