package game.sixquiprends.server.controller.error;

public class RestMessageError {
	private final String code;
	private final String message;
	private final String description;

	public RestMessageError(String code, String message, String description) {
		this.code = code;
		this.message = message;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public String getDescription() {
		return description;
	}
}
