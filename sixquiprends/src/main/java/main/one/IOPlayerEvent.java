package main.one;

import game.io.GetCardAction;
import game.io.GetLineAction;
import game.io.IOPlayer;
import game.player.Player;

public class IOPlayerEvent extends IOPlayer {

	public IOPlayerEvent(Player player, IOPartyEvent ioPartyEvent) {
		super(player, ioPartyEvent);
	}

	@Override
	public void selectCard(GetCardAction action) {
		Main.afficheTable(ioParty.displayTable());
		String card = Main.quelCarte(player.getName(), player.getHand());
		action.selectCard(Integer.parseInt(card));
	}

	@Override
	public void selectLine(GetLineAction action) {
		Main.afficheTable(ioParty.displayTable());
		String line = Main.quelLigne(player.getName());
		action.selectLine(Integer.parseInt(line));
	}
}
