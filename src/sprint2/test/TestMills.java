package sprint2.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import sprint2.product.Board;
import sprint2.product.GUI;

public class TestMills {
	private Board board;

	@Before
	public void setUp() throws Exception {
		board = new Board(5);
	}

	@Test
	public void testMillInTHeFirstPhase() {
		board.makeMoveFirstPhase(0);
		board.makeMoveFirstPhase(3);
		board.makeMoveFirstPhase(2);

		new GUI(board); 
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
