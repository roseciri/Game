package game.sixquiprends.server.model;

import game.board.BoardGameData;
import game.board.card.Card;

public class BordGame {

	final Card[][] cards;

	public BordGame(Card[][] cards) {
		this.cards = cards;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		BoardGameData data;
		public Builder setBoardGameData(BoardGameData boardGameData) {
			this.data = boardGameData;
			return this;
		}

		public BordGame build() {
			Card[][] cards = data.getLines().stream().map(lineData -> lineData.getCards().stream().toArray(Card[]::new)).toArray(Card[][]::new);
			BordGame bordGame = new BordGame(cards);
			return bordGame;
		}
	}

	public Card[][] getCards() {
		return cards;
	}
}
