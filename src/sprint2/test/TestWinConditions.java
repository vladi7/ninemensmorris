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
		new GUI(board); 
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		new GUI(board); 
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
	
	@Test
	public void testWhiteOpponentLessThanThreePiecesBlackWins() {
		board.setCurrentGameRegime(GameRegime.P1vP2);
		board.setGameState(GameState.PLAYING1);
		board.setCurrentTurn(Dot.WHITE);
		
		board.makeMoveFirstPhase(0);
		board.makeMoveFirstPhase(9);
		board.makeMoveFirstPhase(1);
		board.makeMoveFirstPhase(21);
		board.makeMoveFirstPhase(14);
		board.makeMoveFirstPhase(15);
		board.makeMoveFirstPhase(23);
		board.makeMoveFirstPhase(22);
		new GUI(board); 
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		board.makeMoveSecondPhaseA(14);
		board.makeMoveSecondPhaseB(14, 2);
		new GUI(board); 
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		board.makeMoveThirdPhase(21);
		new GUI(board); 
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		board.makeMoveSecondPhaseA(22);
		board.makeMoveSecondPhaseB(22, 21);
		new GUI(board); 
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		board.makeMoveSecondPhaseA(2);
		board.makeMoveSecondPhaseB(2, 14);
		new GUI(board); 
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		board.makeMoveSecondPhaseA(21);
		board.makeMoveSecondPhaseB(21, 22);
		new GUI(board); 
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		board.makeMoveSecondPhaseA(14);
		board.makeMoveSecondPhaseB(14, 2);
		new GUI(board); 
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		board.makeMoveThirdPhase(22);
		new GUI(board); 
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(board.getGameState(),GameState.WHITE_WON);
		


	}
	@Test
	public void testBlackOpponentLessThanThreePiecesWhiteWins() {
		board.setCurrentGameRegime(GameRegime.P1vP2);
		board.setGameState(GameState.PLAYING1);
		board.setCurrentTurn(Dot.BLACK);
		
		board.makeMoveFirstPhase(0);
		board.makeMoveFirstPhase(9);
		board.makeMoveFirstPhase(1);
		board.makeMoveFirstPhase(21);
		board.makeMoveFirstPhase(14);
		board.makeMoveFirstPhase(15);
		board.makeMoveFirstPhase(23);
		board.makeMoveFirstPhase(22);
		new GUI(board); 
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		board.makeMoveSecondPhaseA(14);
		board.makeMoveSecondPhaseB(14, 2);
		new GUI(board); 
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		board.makeMoveThirdPhase(21);
		new GUI(board); 
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		board.makeMoveSecondPhaseA(22);
		board.makeMoveSecondPhaseB(22, 21);
		new GUI(board); 
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		board.makeMoveSecondPhaseA(2);
		board.makeMoveSecondPhaseB(2, 14);
		new GUI(board); 
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		board.makeMoveSecondPhaseA(21);
		board.makeMoveSecondPhaseB(21, 22);
		new GUI(board); 
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		board.makeMoveSecondPhaseA(14);
		board.makeMoveSecondPhaseB(14, 2);
		new GUI(board); 
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		board.makeMoveThirdPhase(22);
		new GUI(board); 
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(board.getGameState(),GameState.BLACK_WON);
		


	}
}
