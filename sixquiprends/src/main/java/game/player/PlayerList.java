package game.player;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class PlayerList {

	private static final Logger logger = LoggerFactory.getLogger(PlayerList.class);

	Set<Player> players = new HashSet<>();

	public void addPlayer(Player p) {
		players.add(p);
	}

	@Override
	public String toString() {
		return "game.player.PlayerList{" +
				"players=" + players +
				'}';
	}

	public void playOneCard() {
		//logger.atDebug().log("playOneCard");
	}

	public Set<Player> getPlayers() {
		return players;
	}

	public int getPlayersNumber() {
		return players.size();
	}

	public String displayScore() {
		return players.stream().map(p -> p.name +" : "+p.points).collect(Collectors.joining("\n"));
	}
}
