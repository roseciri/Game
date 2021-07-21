package state;

import exception.NotEnoughtCardException;

public class ActionImpossibleException extends RuntimeException {

	public ActionImpossibleException() {
		super();
	}

	public ActionImpossibleException(NotEnoughtCardException e) {
		super("Il n'y a plus accès de cartes dans la pioche", e);
	}

}
