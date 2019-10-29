package sprint2.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import sprint2.product.Board;
import sprint2.product.Dot;
import sprint2.product.GUI;

public class TestFlying {
	private final Board board = new Board(4);

	@Test
	public void testFlying() {
		board.makeMoveFirstPhase(0);
		board.makeMoveFirstPhase(3);
		board.makeMoveFirstPhase(1);
		board.makeMoveFirstPhase(4);
		
		board.makeMoveFirstPhase(14);
		board.makeMoveFirstPhase(8);
		board.makeMoveFirstPhase(8);
		board.makeMoveFirstPhase(19);
		board.makeMoveFirstPhase(20);
		board.makeMoveSecondPhaseA(14);
		board.makeMoveSecondPhaseB(14, 2);
		board.makeMoveThirdPhase(3);
		board.makeMoveSecondPhaseA(4);
		board.makeMoveSecondPhaseB(4, 23);
		assertThat(board.getDot(3, 1), is(Dot.EMPTY));
		assertThat(board.getDot(6, 6), is(Dot.BLACK));
	}

}
