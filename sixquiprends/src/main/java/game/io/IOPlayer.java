package game.io;


import game.player.Player;

public abstract class IOPlayer {

	protected final Player player;
	protected final IOParty ioParty;

	protected IOPlayer(Player player, IOParty ioParty) {
		this.player = player;
		this.ioParty = ioParty;
	}

	public abstract void selectCard(GetCardAction action);

	public abstract void selectLine(GetLineAction action);
}
