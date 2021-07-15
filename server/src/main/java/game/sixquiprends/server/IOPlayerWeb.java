package game.sixquiprends.server;

import game.io.GetCardAction;
import game.io.GetLineAction;
import game.io.IOPlayer;
import game.player.Player;

import javax.inject.Inject;

public class IOPlayerWeb extends IOPlayer {

	public IOPlayerWeb(Player player, IOPartyWeb ioPartyWeb) {
		super(player, ioPartyWeb);
	}

	@Override
	public void selectCard(GetCardAction action) {
		String card = "1";
		action.selectCard(Integer.parseInt(card));
	}

	@Override
	public void selectLine(GetLineAction action) {
		String line = "1";
		action.selectLine(Integer.parseInt(line));
	}

	public Player getPlayer() {
		return player;
	}
}
