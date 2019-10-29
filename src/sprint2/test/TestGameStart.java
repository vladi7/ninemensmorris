package sprint2.test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

import sprint2.product.Board;
import sprint2.product.Dot;
import sprint2.product.GameState;

public class TestGameStart {
	private final Board board = new Board();
	
	@Test
	public void testStart() {
		assertThat(board.getGameState(), is(GameState.START));
	}
	
	public void testWhiteStarts() {
		assertThat(board.getCurrentTurn(), is(Dot.WHITE));
	}
}
