package game.rule;

import game.board.card.Card;
import game.player.Player;
import game.player.PlayerList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class CollectCardPhase  {

	private static final Logger logger = LoggerFactory.getLogger(CollectCardPhase.class);

	final PlayerList playerlist;
	Map<Player, Card> payload = new HashMap<>();

	private ReentrantLock pauseLock = new ReentrantLock();
	private Condition reactive = pauseLock.newCondition();

	public CollectCardPhase(Tour tour) {
		this.playerlist = tour.getPlayerList();
	}

	public void start() {
		Set<Player> players = playerlist.getPlayers();
		players.forEach(p -> Executors.newSingleThreadExecutor().submit(()-> p.selectCard(this)) );
	}

	public Map<Player, Card> getPayload() {
		pauseLock.lock();
		try {
			while (!payload.keySet().containsAll(playerlist.getPlayers())) {
				reactive.await();
			}
		} catch (InterruptedException e) {
			logger.error("Le trhead a été interrompu",e);
			Thread.currentThread().interrupt();
		} finally {
			pauseLock.unlock();
		}
		return payload;
	}

	public void playerPlayCard(Player p, Card c) {
		pauseLock.lock();
		payload.put(p, c);
		reactive.signal();
		pauseLock.unlock();
	}
}
