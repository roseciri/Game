package game.sixquiprends.server;

import exception.NotEnoughtCardException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.UUID;

//  http://localhost:8087/Game/swagger-ui/#/
//  http://localhost:8080/Game/swagger-ui/#/
@Slf4j
@RestController
@RequestMapping(path = "/SixQuiPrends/")
@Api(value = "gameaction", description = "Operations for playing game")
public class StarterController {

	private static final Logger logger = LoggerFactory.getLogger(StarterController.class);

	private final PartiesHolder partiesHolder = new PartiesHolder();


	@ApiOperation(value = "Six qui prends game creation", response = String.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully created party"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	})
	@PostMapping(path = "/party")
	public String createParty() {
		return partiesHolder.getNewParty().toString();
	}


	@PostMapping(path = "/party/{partyId}/start")
	public void startGame(@PathVariable String partyId) {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Lancement de la party %s", partyId));
		}
		partiesHolder.start(UUID.fromString(partyId));

	}

	@PostMapping(path = "party/{partyId}/player")
	public void addPlayer(@PathVariable String partyId, @RequestBody String name) {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("[%s] Creation du joueur ", name));
		}
		try {
			partiesHolder.addPlayer(UUID.fromString(partyId), name);
		} catch (NotEnoughtCardException e) {
			e.printStackTrace();//comment je change les status http en cas d'erreur
		}
	}

	@PostMapping(path = "party/{partyId}/player/{playerId}/card/{card}/play")
	public void playerSelectCard(@PathVariable String partyId, @PathVariable String playerId, @PathVariable int card) {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("[%s][%s]Creation du joueur %s", partyId, playerId, card));
		}
		partiesHolder.playerSelectCard(UUID.fromString(partyId), playerId, card);
	}

	@PostMapping(path = "party/{partyId}/player/{playerId}/line/{line}/select")
	public void playerSelectLine(@PathVariable String partyId, @PathVariable String playerId, @PathVariable int line) {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("[%s][%s]Le joueur selectonne la ligne %s", partyId, playerId, line));
		}
		partiesHolder.playerSelectLine(UUID.fromString(partyId), playerId, line);
	}
}
