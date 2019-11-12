package sprint2.product;

import java.util.Random;

import sprint2.product.*;

public class AI {
	private Board board;
	Random rand = new Random();

	public void updateBoard(Board board) {
		this.board = board;
	}

	public void makeMove() {
		if (board.getGameState() == GameState.PLAYING1) {
			while (true) {
				int move = rand.nextInt(24);
				boolean status = board.makeMoveFirstPhase(move);
				if (status) {
					break;
				}
			}
		}
		if (board.getGameState() == GameState.PLAYING2a) {
			while (true) {
				int move = rand.nextInt(24);
				boolean status = board.makeMoveSecondPhaseA(move);
				if (status) {
					break;
				}
			}
		}
		if (board.getGameState() == GameState.PLAYING2b1) {
			while (true) {
				int move = rand.nextInt(24);
				boolean status = board.makeMoveSecondPhaseA(move);
				if (status) {
					break;
				}
			}
		}
		if (board.getGameState() == GameState.PLAYING3a) {
			while (true) {
				int move = rand.nextInt(24);
				boolean status = board.makeMoveThirdPhase(move);
				if (status) {
					break;
				}
			}
		}

	}

	public void millIsClose() {

	}

}
