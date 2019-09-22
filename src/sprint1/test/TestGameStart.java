package sprint1.test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

import sprint1.product.Board;
import sprint1.product.Dot;
import sprint1.product.GameState;

public class TestGameStart {
	private final Board board = new Board();
	
	@Test
	public void testStart() {
		assertThat(board.getGameState(), is(GameState.PLAYING1));
	}
	
	public void testWhiteStarts() {
		assertThat(board.getCurrentTurn(), is(Dot.WHITE));
	}
}
