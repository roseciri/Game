package game.sixquiprends.server.controller;

import exception.NotEnoughtCardException;
import game.io.GetPlayerAction;
import game.io.PlayAction;
import game.sixquiprends.server.controller.error.RestMessageError;
import game.sixquiprends.server.controller.holder.PartiesHolder;
import game.sixquiprends.server.model.Party;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import state.ActionImpossibleException;

import static org.springframework.http.ResponseEntity.status;

import java.util.Optional;
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
	})
	@PostMapping(path = "/party", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Party> createParty() {
		UUID newPartyId = partiesHolder.getNewParty();
		return status(HttpStatus.CREATED).body(Party.builder().setUuid(newPartyId).build());
	}

	//TODO cette méthode doit retourner l'état de la table
	@PostMapping(path = "/party/{partyId}/start")
	public void startGame(@PathVariable String partyId) {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Lancement de la party %s", partyId));
		}
		partiesHolder.get(UUID.fromString(partyId)).getPlayAction().ifPresent(PlayAction::play);

	}

	//TODO cette méthode doit retourner le joueur, avec sa main
	@PostMapping(path = "party/{partyId}/player")
	public void addPlayer(@PathVariable String partyId, @RequestBody String name) {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("[%s] Creation du joueur ", name));
		}
		try {
			Optional<GetPlayerAction> actionOptional = partiesHolder.get(UUID.fromString(partyId)).getGetPlayerAction();
			GetPlayerAction action = actionOptional.orElseThrow(ActionImpossibleException::new);
			action.addPlayer(name);
		} catch (NotEnoughtCardException e) {
			throw new ActionImpossibleException(e);
		}
	}

	//TODO cette méthode doit reourner la main du joueur ???
	@PostMapping(path = "party/{partyId}/player/{playerId}/card/{card}/play")
	public void playerSelectCard(@PathVariable String partyId, @PathVariable String playerId, @PathVariable int card) {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("[%s][%s]Creation du joueur %s", partyId, playerId, card));
		}
		partiesHolder.get(UUID.fromString(partyId)).getSelectCardActionForPlayer(playerId).ifPresent(a -> a.selectCard(card));
	}

	//TODO cette méthode doit reourner les cartes que le joueurs a récupéré pour le score ?
	@PostMapping(path = "party/{partyId}/player/{playerId}/line/{line}/select")
	public void playerSelectLine(@PathVariable String partyId, @PathVariable String playerId, @PathVariable int line) {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("[%s][%s]Le joueur selectionne la ligne %s", partyId, playerId, line));
		}
		partiesHolder.get(UUID.fromString(partyId)).getSelectLineActionForPlayer(playerId).ifPresent(a -> a.selectLine(line));
	}

	@ExceptionHandler(ActionImpossibleException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public RestMessageError actionImpossible(ActionImpossibleException e) {
		return new RestMessageError("403", "cette action n'est pas disponible", e.getMessage());
	}
}
