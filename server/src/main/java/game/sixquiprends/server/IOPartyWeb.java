package game.sixquiprends.server;

import exception.NotEnoughtCardException;
import game.io.AddPlayerOrPlayAction;
import game.io.GetPlayerAction;
import game.io.IOParty;
import game.io.IOPlayer;
import game.player.Player;

public class IOPartyWeb extends IOParty {

	private PartiesHolder partiesHolder;

	public IOPartyWeb(PartiesHolder partiesHolder) {
		super();
		this.partiesHolder = partiesHolder;
	}


	public void needPlayer(GetPlayerAction action) {
		partiesHolder.addAction(this, action);
	}

	public void addPlayerOrPlay(AddPlayerOrPlayAction action) throws NotEnoughtCardException {
		String choice = "add";
		if ("add".equals(choice)) {
			String name = "name";
			action.getAddPlayerAction().addPlayer(name);
		} else if ("play".equals(choice)) {
			action.getPlayAction().play();
		}
	}

	@Override
	public IOPlayer addPlayerCommunicator(Player player) {
		IOPlayer ioPlayer = new IOPlayerWeb(player, this);
		playerCommunicator.put(player.getName(), ioPlayer);
		return ioPlayer;
	}


	public IOPlayerWeb getIOPlayerWeb(String playerId) {
		return (IOPlayerWeb) playerCommunicator.get(playerId);
	}
}
