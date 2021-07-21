package game.board;

import java.util.List;

public interface BoardGameData {
	List<? extends LineData> getLines();
}
