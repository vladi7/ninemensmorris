package sprint2.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

import sprint2.product.Board;
import sprint2.product.Dot;
import sprint2.product.GUI;
import sprint2.product.GameRegime;
import sprint2.product.GameState;



public class TestWinConditions {
	private final Board board = new Board(4);

	@Test
	public void testWhiteOpponentBlockedBlackWins() {
		board.setCurrentGameRegime(GameRegime.P1vP2);
		board.setGameState(GameState.PLAYING1);
		board.setCurrentTurn(Dot.WHITE);
		board.makeMoveFirstPhase(6);
		board.makeMoveFirstPhase(7);
		board.makeMoveFirstPhase(8);
		board.makeMoveFirstPhase(11);
		board.makeMoveFirstPhase(15);
		board.makeMoveFirstPhase(16);
		board.makeMoveFirstPhase(17);
		board.makeMoveFirstPhase(12);
		assertEquals(board.getGameState(),GameState.BLACK_WON);

	}
	@Test
	public void testBlackOpponentBlockedWhiteWins() {
		board.setCurrentGameRegime(GameRegime.P1vP2);
		board.setGameState(GameState.PLAYING1);
		board.setCurrentTurn(Dot.WHITE);
		board.makeMoveFirstPhase(7);
		board.makeMoveFirstPhase(6);
		board.makeMoveFirstPhase(11);
		board.makeMoveFirstPhase(8);
		board.makeMoveFirstPhase(16);
		board.makeMoveFirstPhase(15);
		board.makeMoveFirstPhase(13);
		board.makeMoveFirstPhase(17);
		
		
		board.makeMoveSecondPhaseA(13);
		board.makeMoveSecondPhaseB(13, 12);
		new GUI(board); 
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(board.getGameState(),GameState.WHITE_WON);
	}
}
