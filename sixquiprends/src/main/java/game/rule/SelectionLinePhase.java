package game.rule;

import exception.TecnicalException;
import game.board.Line;
import game.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class SelectionLinePhase {

	private static final Logger logger = LoggerFactory.getLogger(SelectionLinePhase.class);

	private Line line;
	List<Line> lines;
	FutureTask<Line> task;
	ExecutorService executorService;

	public SelectionLinePhase(Player p, List<Line> lines) {
		this.lines = lines;
		task = new FutureTask<>(() -> {
			p.selectLine(this, lines);
			return line;
		});
		executorService = Executors.newSingleThreadExecutor();
	}


	public void select(Line line) {
		this.line = line;
	}

	public Line getLine() {
		try {
			Line result = task.get();
			executorService.shutdown();
			return result;
		} catch (InterruptedException | ExecutionException e) {
			logger.atWarn().setCause(e).log("Interrupted!");
			Thread.currentThread().interrupt();
		}
		throw new TecnicalException("Problème au niveau du therad de selection de la line");
	}

	public void start() {
		executorService.submit(task);
	}
}
