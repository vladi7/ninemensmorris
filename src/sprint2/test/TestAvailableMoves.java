package sprint2.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

import sprint2.product.Board;
import sprint2.product.Dot;
import sprint2.product.GUI;


public class TestAvailableMoves {
	private final Board board = new Board(4);

	@Test
	public void testMovesAreAvailable() {
		board.makeMoveFirstPhase(13);
		board.makeMoveFirstPhase(1);
		board.makeMoveFirstPhase(2);
		board.makeMoveFirstPhase(3);
		board.makeMoveFirstPhase(4);
		board.makeMoveFirstPhase(5);
		board.makeMoveFirstPhase(6);
		board.makeMoveFirstPhase(7);
		board.makeMoveSecondPhaseA(13);

			
		
		assertThat(board.getDot(5,3), is(Dot.GRAY));
		assertThat(board.getDot(4,3), is(Dot.HIGHLIGHTWHITE));
		assertThat(board.getDot(6,3), is(Dot.HIGHLIGHTWHITE));
		assertThat(board.getDot(5,1), is(Dot.BLACK));
		assertThat(board.getDot(5,5), is(Dot.HIGHLIGHTWHITE));
	}

}
