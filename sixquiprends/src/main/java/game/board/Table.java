package game.board;

import game.board.card.Card;
import game.player.Player;
import game.rule.SelectionLinePhase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

public class Table {

	private static final Logger logger = LoggerFactory.getLogger(Table.class);

	List<Line> lines = new ArrayList<>(4);

	public Table(List<Card> cards) {
		cards.forEach(c -> lines.add(new Line(c)));
	}

	public Set<Card> addCard(Player p, Card c) {
		Optional<Line> line = selectLineToAdd(c);
		var selectionLinePhase = new SelectionLinePhase();
		if (line.isEmpty()) {
			Executors.newSingleThreadExecutor().submit(() -> p.selectLine(selectionLinePhase, lines));
			while (selectionLinePhase.getLine() == null) {
				try {
					//noinspection BusyWait
					sleep(10);
				} catch (InterruptedException e) {
					logger.error("Le thread a été interrompu", e);
					Thread.currentThread().interrupt();
				}
			}
			p.getHand().remove(c);
			return selectionLinePhase.getLine().getCards(c);
		}
		p.getHand().remove(c);
		return line.stream()
				.map(l -> l.addCard(c).orElse(Collections.emptySet()))
				.flatMap(Collection::stream)
				.collect(Collectors.toSet());
	}

	private Optional<Line> selectLineToAdd(Card c) {
		return lines.stream().filter(l -> l.value < c.getValue()).max(Comparator.comparingInt(l -> l.value));
	}

	@Override
	public String toString() {
		return "\n--------------------------\n" +
				lines.stream()
						.map(Line::toString)
						.collect(Collectors.joining("\n"))
				+ "\n--------------------------\n";
	}
}
