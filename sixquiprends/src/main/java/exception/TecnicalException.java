package exception;

public class TecnicalException extends RuntimeException {
	public TecnicalException(String s) {
		super(s);
	}

	public TecnicalException(String s, NotEnoughtCardException e) {
		super(s, e);
	}
}
