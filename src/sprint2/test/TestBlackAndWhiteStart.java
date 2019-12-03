package sprint2.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

import sprint2.product.Board;
import sprint2.product.Dot;
import sprint2.product.GUI;
import sprint2.product.GameRegime;
import sprint2.product.GameState;

public class TestBlackAndWhiteStart {
	
	private final Board board = new Board(9);
	
	@Test
	public void testWhiteStart() {
		board.setCurrentGameRegime(GameRegime.P1vP2);
		board.setGameState(GameState.PLAYING1);
		board.setCurrentTurn(Dot.WHITE);
		board.makeMoveFirstPhase(0);
		
		assertThat(board.getDot(0, 0), is(Dot.WHITE));
	}
	
	@Test	
	public void testBlackStart() {
		board.setCurrentGameRegime(GameRegime.P1vP2);
		board.setGameState(GameState.PLAYING1);
		board.setCurrentTurn(Dot.BLACK);
		board.makeMoveFirstPhase(0);
		
		assertThat(board.getDot(0, 0), is(Dot.BLACK));
	}

}
