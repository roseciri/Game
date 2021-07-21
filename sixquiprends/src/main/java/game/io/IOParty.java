package game.io;

import exception.NotEnoughtCardException;
import game.player.Player;
import game.rule.Party;

import java.util.HashMap;
import java.util.Map;

public abstract class IOParty<T extends IOPlayer> {

	protected Map<String, T> playerCommunicator = new HashMap<>();
	protected Party party;

	public void createParty() {
		party = new Party(this);
		party.launch();
	}

	public abstract void needPlayer(GetPlayerAction action) throws NotEnoughtCardException;

	public abstract void addPlayerOrPlay(AddPlayerOrPlayAction action) throws NotEnoughtCardException;

	public abstract T addPlayerCommunicator(Player name);

	public String displayTable() {
		return party.getTable().toString();
	}
}
