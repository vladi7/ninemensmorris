package sprint2.test;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

import sprint2.product.Board;
import sprint2.product.Dot;
import sprint2.product.GUI;
import sprint2.product.GameRegime;
import sprint2.product.GameState;

public class TestPiecePlacement {
	private final Board board = new Board(4);

	@Test
	public void testPiecePlacementPhase1() {
		board.setCurrentGameRegime(GameRegime.P1vP2);
		board.setGameState(GameState.PLAYING1);
		board.setCurrentTurn(Dot.WHITE);
		board.makeMoveFirstPhase(0);
		board.makeMoveFirstPhase(1);
		
		assertThat(board.getDot(0, 0), is(Dot.WHITE));
		assertThat(board.getDot(3, 0), is(Dot.BLACK));
		for (int row = 0; row != board.getTotalRows(); ++row) {
			for (int col = 0; col != board.getTotalColumns(); ++col) {
				if(row==0&&col==0||row==0&&col==3) {
					continue;				
				}
				else {
					assertThat(board.getDot(col, row), anyOf(is(Dot.EMPTY), is(Dot.NOTUSED)));
				}
			}
		}


	}
	@Test
	public void testPieceMovingPhase2() {
		board.setCurrentGameRegime(GameRegime.P1vP2);
		board.setGameState(GameState.PLAYING1);
		board.setCurrentTurn(Dot.WHITE);
		board.makeMoveFirstPhase(0);
		board.makeMoveFirstPhase(1);
		board.makeMoveFirstPhase(2);
		board.makeMoveFirstPhase(3);
		board.makeMoveFirstPhase(4);
		board.makeMoveFirstPhase(5);
		board.makeMoveFirstPhase(6);
		board.makeMoveFirstPhase(7);
		board.makeMoveSecondPhaseA(0);
		board.makeMoveSecondPhaseB(0, 9);

		assertThat(board.getDot(0, 0), is(Dot.EMPTY));
		assertThat(board.getDot(0, 3), is(Dot.WHITE));
		
	}
}
