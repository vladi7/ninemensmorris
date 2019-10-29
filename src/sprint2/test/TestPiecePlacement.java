package sprint2.test;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

import sprint2.product.Board;
import sprint2.product.Dot;

public class TestPiecePlacement {
	private final Board board = new Board();;

	@Test
	public void testPiecePlacementPhase1() {
		board.makeMoveFirstPhase(0, 0);
		board.makeMoveFirstPhase(0, 3);		
		assertThat(board.getDot(0, 0), is(Dot.WHITE));
		assertThat(board.getDot(0, 3), is(Dot.BLACK));
		for (int row = 0; row != board.getTotalRows(); ++row) {
			for (int col = 0; col != board.getTotalColumns(); ++col) {
				if(row!=0&&col!=0||row!=0&&col!=3) {
				assertThat(board.getDot(row, col), anyOf(is(Dot.EMPTY), is(Dot.NOTUSED)));
				}
			}
		}

	}

}
