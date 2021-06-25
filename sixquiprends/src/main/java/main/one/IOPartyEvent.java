package main.one;

import exception.NotEnoughtCardException;
import game.io.AddPlayerOrPlayAction;
import game.io.GetPlayerAction;
import game.io.IOParty;
import game.io.IOPlayer;
import game.player.Player;

public class IOPartyEvent extends IOParty {

	public void needPlayer(GetPlayerAction action) throws NotEnoughtCardException {
		String name = Main.quiJoue();
		action.addPlayer(name);
	}

	public void addPlayerOrPlay(AddPlayerOrPlayAction action) throws NotEnoughtCardException {
		String choice = Main.ajouterUnJoueurOuJouer();
		if("add".equals(choice)) {
			String name = Main.quiJoue();
			action.getAddPlayerAction().addPlayer(name);
		} else if("play".equals(choice)) {
			action.getPlayAction().play();
		}
	}

	@Override
	public IOPlayer addPlayerCommunicator(Player player) {
		IOPlayer ioPlayer = new IOPlayerEvent(player, this);
		playerCommunicator.put(player.getName(), ioPlayer);
		return ioPlayer;
	}

}
