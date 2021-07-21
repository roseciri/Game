package game.sixquiprends.server;

import game.io.Action;
import game.io.GetCardAction;
import game.io.GetLineAction;
import game.io.IOPlayer;
import game.player.Player;

import java.util.Optional;
import java.util.function.Function;

public class IOPlayerWeb extends IOPlayer {

	Optional<Action> actionOptional;

	public IOPlayerWeb(Player player, IOPartyWeb ioPartyWeb) {
		super(player, ioPartyWeb);
	}

	public Optional<GetCardAction> getSelectCardAction() {
		return getAction(a -> {
			if (a instanceof GetCardAction) {
				return Optional.of((GetCardAction) a);
			}
			return Optional.empty();
		});
	}

	public Optional<GetLineAction> getSelectLineAction() {
		return getAction(a -> {
			if (a instanceof GetLineAction) {
				return Optional.of((GetLineAction) a);
			}
			return Optional.empty();
		});
	}

	@Override
	public void selectCard(GetCardAction a) {
		actionOptional = Optional.of(a);
	}

	@Override
	public void selectLine(GetLineAction a) {
		actionOptional = Optional.of(a);
	}

	private <T> Optional<T> getAction(Function<Action, Optional<T>> actionProvider) {
		if (actionOptional.isPresent()) {
			return actionProvider.apply(actionOptional.get());
		}
		return Optional.empty();
	}
}
