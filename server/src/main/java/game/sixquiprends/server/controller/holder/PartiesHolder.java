package game.sixquiprends.server.controller.holder;


import game.sixquiprends.server.IOPartyWeb;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Singleton
public class PartiesHolder {

	Map<UUID, IOPartyWeb> parties = new HashMap<>();

	public UUID getNewParty() {
		UUID key = UUID.randomUUID();
		IOPartyWeb ioParty = new IOPartyWeb();
		parties.put(key, ioParty);
		ioParty.createParty();
		return key;

	}


	public IOPartyWeb get(UUID uuid) {
		return parties.get(uuid);
	}
}

