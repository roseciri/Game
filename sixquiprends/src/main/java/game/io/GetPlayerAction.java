package game.io;

import exception.NotEnoughtCardException;

public interface GetPlayerAction extends Action {
	void addPlayer(String name) throws NotEnoughtCardException;
}
