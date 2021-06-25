package game.io;

public interface AddPlayerOrPlayAction extends Action {
	PlayAction getPlayAction();
	GetPlayerAction getAddPlayerAction();
}
