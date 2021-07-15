package game.sixquiprends.server;


import exception.NotEnoughtCardException;
import game.board.Line;
import game.board.card.Card;
import game.io.*;
import game.rule.CollectCardPhase;
import game.rule.SelectionLinePhase;
import state.ActionImpossibleException;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Singleton
public class PartiesHolder {

	Map<UUID, IOPartyWeb> parties = new HashMap<>();
	Map<IOPartyWeb, Action> actions = new HashMap<>();
	Map<IOPlayerWeb, Action> playerActions = new HashMap<>();


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

	public void start(UUID uuid) {
		Action action = actions.get(parties.get(uuid));
		if (action instanceof AddPlayerOrPlayAction) {
			((AddPlayerOrPlayAction) action).getPlayAction().play();
		} else {
			throw new ActionImpossibleException();
		}
	}

	public void playerSelectCard(UUID uuid, String playerId, int card) {
		IOPlayerWeb ioPlayerWeb = parties.get(uuid).getIOPlayerWeb(playerId);
		Action action = playerActions.get(ioPlayerWeb);
		if (action instanceof GetCardAction) {
			((GetCardAction) action).selectCard(card);
		} else {
			throw new ActionImpossibleException();
		}
	}

	public void playerSelectLine(UUID uuid, String playerId, int line) {
		IOPlayerWeb ioPlayerWeb = parties.get(uuid).getIOPlayerWeb(playerId);
		Action action = playerActions.get(ioPlayerWeb);
		if (action instanceof GetLineAction) {
			((GetLineAction) action).selectLine(line);
		} else {
			throw new ActionImpossibleException();
		}
	}
}
