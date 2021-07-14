package game.sixquiprends.server;


import exception.NotEnoughtCardException;
import game.io.Action;
import game.io.AddPlayerOrPlayAction;
import game.io.GetPlayerAction;
import state.ActionImpossibleException;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Singleton
public class PartiesHolder {

	Map<UUID, IOPartyWeb> parties = new HashMap<>();
	Map<IOPartyWeb, Action> actions = new HashMap<>();


	public UUID getNewParty() {
		UUID key = UUID.randomUUID();
		IOPartyWeb ioParty = new IOPartyWeb(this);
		parties.put(key, ioParty);
		ioParty.createParty();
		return key;

	}

	public void addAction(IOPartyWeb ioPartyWeb, Action action) {
		actions.get(action);
	}

	public void addPlayer(UUID uuid, String playerName) throws NotEnoughtCardException {
		Action action = actions.get(parties.get(uuid));
		if (action instanceof GetPlayerAction) {
			((GetPlayerAction) action).addPlayer(playerName);
		} else if (action instanceof AddPlayerOrPlayAction) {
			((AddPlayerOrPlayAction) action).getAddPlayerAction().addPlayer(playerName);
		} else {
			throw new ActionImpossibleException();
		}
	}
}
