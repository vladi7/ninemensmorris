package sprint2.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

import sprint2.product.Board;
import sprint2.product.Dot;
import sprint2.product.GUI;
import sprint2.product.GameState;



public class TestWinConditions {
	private final Board board = new Board(4);

	@Test
	public void testOpponentBlocked() {
		board.makeMoveFirstPhase(6);
		board.makeMoveFirstPhase(7);
		board.makeMoveFirstPhase(8);
		board.makeMoveFirstPhase(11);
		board.makeMoveFirstPhase(15);
		board.makeMoveFirstPhase(16);
		board.makeMoveFirstPhase(17);
		board.makeMoveFirstPhase(12);
		assertEquals(board.getGameState(),GameState.BLACK_WON);
		System.out.println(board.getGameState());
		
		

		new GUI(board); 
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
