package game.sixquiprends.server.model;

import java.util.UUID;

public class Party {
	String id;

	public Party(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		UUID uuid;

		public Builder setUuid(UUID uuid) {
			this.uuid = uuid;
			return this;
		}

		public Party build(){
			return new Party(uuid.toString());
		}
	}
}
