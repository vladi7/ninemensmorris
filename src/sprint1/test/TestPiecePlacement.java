package sprint1.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

import sprint1.product.Board;
import sprint1.product.Dot;

public class TestPiecePlacement {
	private final Board board = new Board();;

	@Test
	public void testPiecePlacementPhase1() {
		board.makeMoveFirstPhase(0, 0);
		board.makeMoveFirstPhase(0, 3);		
		assertThat(board.getDot(0, 0), is(Dot.WHITE));
		assertThat(board.getDot(0, 3), is(Dot.BLACK));

	}

}
