package game.sixquiprends.server;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
//  http://localhost:8087/Game/swagger-ui/#/
@Slf4j
@RestController
@RequestMapping(path = "/SixQuiPrends/")
public class StarterController {

	private static final Logger logger = LoggerFactory.getLogger(StarterController.class);

	@Inject
	private PartiesHolder partiesHolder;

	@PostMapping(path = "/party")
	public String createParty() {
		return partiesHolder.getNewParty().toString();
	}

	@PostMapping(path = "/party/{partyId}/start")
	public void startGame(@PathVariable String partyId) {
		logger.debug("Lancement de la party " + partyId);
	}

	@PostMapping(path = "party/{partyId}/player")
	public void addPlayer(@PathVariable String partyId, @RequestBody String name) {
		logger.debug("["+partyId+"] Creation du joueur " + name);
	}

	@PostMapping(path = "party/{partyId}/player/{playerId}/card/{card}/play")
	public void playerSelectCard(@PathVariable String partyId, @PathVariable String playerId, @PathVariable int card) {
		logger.debug("["+partyId+"]["+playerId+"]Creation du joueur " + card);
	}

	@PostMapping(path = "party/{partyId}/player/{playerId}/line/{line}/select")
	public void playerSelectLine(@PathVariable String partyId, @PathVariable String playerId, @PathVariable int line) {
		logger.debug("["+partyId+"]["+playerId+"]Le joueur selectonne la ligne " + line);
	}
}
