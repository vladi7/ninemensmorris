package sprint2.test;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import sprint2.product.Board;
import sprint2.product.Dot;
import sprint2.product.GUI;

public class TestMills {
	private Board board;

	@Before
	public void setUp() throws Exception {
		board = new Board(4);
	}

	@Test
	public void testMillInTheFirstPhase() {
		board.makeMoveFirstPhase(0);
		board.makeMoveFirstPhase(3);
		board.makeMoveFirstPhase(1);
		board.makeMoveFirstPhase(4);
		int[] coord = board.indexOf(2);
		int colTo = coord[1];
		int rowTo = coord[0];
		board.makeMoveFirstPhase(2);
		assertTrue(board.checkMill(colTo, rowTo));

	}
	@Test
	public void testMillInTheSecondPhase() {
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
		int[] coord = board.indexOf(2);
		int colTo = coord[1];
		int rowTo = coord[0];
		assertTrue(board.checkMill(colTo, rowTo));

	
		
	}
	@Test
	public void testMillInTheThirdPhase() {
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
		
		int[] coord = board.indexOf(3);
		int colTo = coord[1];
		int rowTo = coord[0];
		assertEquals(board.getDot(colTo, rowTo), Dot.EMPTY);

	
		
	}
}
