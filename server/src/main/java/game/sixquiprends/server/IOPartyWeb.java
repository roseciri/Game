package game.sixquiprends.server;

import exception.NotEnoughtCardException;
import game.io.*;
import game.player.Player;
import game.rule.SelectionLinePhase;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class IOPartyWeb extends IOParty<IOPlayerWeb> {

	Optional<Action> actionOptional;


	public IOPartyWeb() {
		super();
		actionOptional = Optional.empty();
	}

	/******
	 ***** Set actions From Party waiting player action
	 *********/


	public void needPlayer(GetPlayerAction a) {
		actionOptional = Optional.of(a);
	}

	public void addPlayerOrPlay(AddPlayerOrPlayAction a) throws NotEnoughtCardException {
		actionOptional = Optional.of(a);
	}

	@Override
	public IOPlayerWeb addPlayerCommunicator(Player player) {
		IOPlayerWeb ioPlayerWeb = new IOPlayerWeb(player, this);
		playerCommunicator.put(player.getName(), ioPlayerWeb);
		return ioPlayerWeb;
	}


	public Optional<GetPlayerAction> getGetPlayerAction() {
		return getAction(a -> {
			if (a instanceof GetPlayerAction) {
				return Optional.of((GetPlayerAction) a);
			} else if (a instanceof AddPlayerOrPlayAction) {
				return Optional.of(((AddPlayerOrPlayAction) a).getAddPlayerAction());
			}
			return Optional.empty();
		});
	}

	public Optional<PlayAction> getPlayAction() {
		return getAction(a -> {
			if (a instanceof AddPlayerOrPlayAction) {
				return Optional.of(((AddPlayerOrPlayAction) a).getPlayAction());
			}
			return Optional.empty();
		});
	}

	private <T> Optional<T> getAction(Function<Action, Optional<T>> actionProvider){
		if (actionOptional.isPresent()) {
			return actionProvider.apply(actionOptional.get());
		}
		return Optional.empty();
	}

	public Optional<GetCardAction> getSelectCardActionForPlayer(String playerId) {
		return playerCommunicator.get(playerId).getSelectCardAction();
	}


	public Optional<GetLineAction> getSelectLineActionForPlayer(String playerId) {
		return playerCommunicator.get(playerId).getSelectLineAction();
	}
}
