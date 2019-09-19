package sprint1.product;

public class Board {
	private static final int TOTALROWS = 7;
	private static final int TOTALCOLUMNS = 7;
	private Dot currentTurn;
	private int numBlackPieces = 2;
	private int numWhitePieces = 2;
	private int[][][] adj = {{{0,1},{0,2},{0,3}},{{}}};
	public enum Dot {
		EMPTY, WHITE, BLACK, NOTUSED, GRAY
	}

	public enum GameState {
		PLAYING1, PLAYING2a, PLAYING2b1, PLAYING2b2, PlAYING3a, PLAYING3b, DRAW, WHITE_WON, BLACK_WON
	}

	private GameState currentGameState;

	private Dot[][] grid;

	public Board() {
		grid = new Dot[TOTALROWS][TOTALCOLUMNS];
		initBoard();
	}

	public void initBoard() {
		for (int row = 0; row < TOTALROWS; ++row) {
			for (int col = 0; col < TOTALCOLUMNS; ++col) {
				if ((row == 0 && col == 1) || (row == 0 && col == 2) || (row == 0 && col == 4) || (row == 0 && col == 5)
						|| (row == 1 && col == 0) || (row == 1 && col == 0) || (row == 1 && col == 2)
						|| (row == 1 && col == 4) || (row == 1 && col == 6) || (row == 2 && col == 0)
						|| (row == 2 && col == 1) || (row == 2 && col == 5) || (row == 2 && col == 6)
						|| (row == 3 && col == 3) || (row == 4 && col == 0) || (row == 4 && col == 1)
						|| (row == 4 && col == 5) || (row == 4 && col == 6) || (row == 5 && col == 0)
						|| (row == 5 && col == 2) || (row == 5 && col == 4) || (row == 5 && col == 6)
						|| (row == 6 && col == 1) || (row == 6 && col == 2) || (row == 6 && col == 4)
						|| (row == 6 && col == 5)) {
					grid[row][col] = Dot.NOTUSED;
				} else {
					grid[row][col] = Dot.EMPTY;
				}

			}
		}
		currentGameState = GameState.PLAYING1;
		currentTurn = Dot.WHITE;
	}

	public void makeMoveFirstPhase(int rowSelected, int colSelected) {
		if (rowSelected >= 0 && rowSelected < TOTALROWS && colSelected >= 0 && colSelected < TOTALCOLUMNS
				&& grid[rowSelected][colSelected] == Dot.EMPTY && grid[rowSelected][colSelected] != Dot.NOTUSED) {
			grid[rowSelected][colSelected] = currentTurn;
			if (currentTurn == Dot.WHITE) {
				numWhitePieces -= 1;
			} else if (currentTurn == Dot.BLACK) {
				numBlackPieces -= 1;
			}

			if (numWhitePieces == 0 && numBlackPieces == 0) {
				currentGameState = GameState.PLAYING2a;

			}
			currentTurn = (currentTurn == Dot.WHITE) ? Dot.BLACK : Dot.WHITE;
		}
	}

	public void makeMoveSecondPhaseA(int rowSelected, int colSelected) {
		if (rowSelected >= 0 && rowSelected < TOTALROWS && colSelected >= 0 && colSelected < TOTALCOLUMNS
				&& grid[rowSelected][colSelected] == currentTurn && grid[rowSelected][colSelected] != Dot.NOTUSED) {
			grid[rowSelected][colSelected] = Dot.GRAY;
			currentGameState = GameState.PLAYING2b1;

		}
	}

	public void makeMoveSecondPhaseB1(int rowFrom, int colFrom, int rowSelected, int colSelected) {
		if (grid[rowSelected][colSelected] == currentTurn) {
			grid[rowSelected][colSelected] = Dot.GRAY;
			grid[rowFrom][colFrom] = currentTurn;
			currentGameState = GameState.PLAYING2b2;
			return;

		}

		if (rowSelected >= 0 && rowSelected < TOTALROWS && colSelected >= 0 && colSelected < TOTALCOLUMNS
				&& grid[rowSelected][colSelected] == Dot.EMPTY && grid[rowSelected][colSelected] != Dot.NOTUSED) {
			grid[rowSelected][colSelected] = currentTurn;
			grid[rowFrom][colFrom] = Dot.EMPTY;
			currentGameState = GameState.PLAYING2a;

			currentTurn = (currentTurn == Dot.WHITE) ? Dot.BLACK : Dot.WHITE;

		}
	}

	public void makeMoveSecondPhaseB2(int colFrom, int rowFrom, int rowSelected, int colSelected) {
		if (grid[rowSelected][colSelected] == currentTurn) {
			grid[rowSelected][colSelected] = Dot.GRAY;
			grid[rowFrom][colFrom] = currentTurn;
			currentGameState = GameState.PLAYING2b1;
			return;
		}
		if(grid[rowSelected][colSelected]!=Dot.EMPTY)
		grid[rowSelected][colSelected] = currentTurn;
		grid[rowFrom][colFrom] = Dot.EMPTY;
		currentGameState = GameState.PLAYING2a;
		currentTurn = (currentTurn == Dot.WHITE) ? Dot.BLACK : Dot.WHITE;

	}

	private void updateGameState(Dot turn, int rowSelected, int colSelected) {
		if (hasWon(turn, rowSelected, colSelected)) { // check for win
			currentGameState = (turn == Dot.WHITE) ? GameState.WHITE_WON : GameState.BLACK_WON;
		} else if (isDraw()) {
			currentGameState = GameState.DRAW;
		}
	}

	public boolean isDraw() {
		for (int row = 0; row < TOTALROWS; ++row) {
			for (int col = 0; col < TOTALCOLUMNS; ++col) {
				if (grid[row][col] == Dot.EMPTY) {
					return false; // an empty cell found, not draw
				}
			}
		}
		return true;
	}

	public boolean hasWon(Dot turn, int rowSelected, int colSelected) {
		for (int row = 0; row < TOTALROWS; ++row) {
			for (int col = 0; col < TOTALCOLUMNS; ++col) {
				if (grid[row][col] == Dot.WHITE) {
					return false; // a white cell found, not win
				}
				if (grid[row][col] == Dot.WHITE) {
					return false; // a black cell found, not win
				}
			}
		}
		return true;
	}

	public boolean checkValidMove(int col, int row) {		
		return true;
	}

	public GameState getGameState() {
		return currentGameState;
	}

	public int getTotalRows() {
		return TOTALROWS;
	}

	public int getTotalColumns() {
		return TOTALCOLUMNS;
	}

	public Dot getDot(int rowSelected, int colSelected) {
		if (rowSelected >= 0 && rowSelected < TOTALROWS && colSelected >= 0 && colSelected < TOTALCOLUMNS) {
			return grid[rowSelected][colSelected];
		} else {
			return null;
		}
	}

	public int getNumBlackPiecesFirstPhase() {
		return numBlackPieces;
	}

	public int getNumWhitePiecesFirstPhase() {
		return numWhitePieces;
	}

	public Dot getCurrentTurn() {
		return currentTurn;
	}
}
