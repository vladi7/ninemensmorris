package sprint1.product;

public class Board {
	private static final int TOTALROWS = 7;
	private static final int TOTALCOLUMNS = 7;
	private Dot currentTurn;
	private boolean allPiecesOnBoard;
	private int numBlackPieces = 9;
	private int numWhitePieces = 9;

	public enum Dot {
		EMPTY, WHITE, BLACK, NOTUSED
	}

	public enum GameState {
		PLAYING, DRAW, WHITE_WON, BLACK_WON
	}

	private GameState currentGameState;

	private Dot[][] grid;
	private Dot[][] gridNotUsed;

	public Board() {
		grid = new Dot[TOTALROWS][TOTALCOLUMNS];
		gridNotUsed = new Dot[TOTALROWS][TOTALCOLUMNS];
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
					gridNotUsed[row][col] = Dot.NOTUSED;
				} else {
					grid[row][col] = Dot.EMPTY;
				}

			}
		}
		currentGameState = GameState.PLAYING;
		currentTurn = Dot.WHITE;
	}

	public void makeMove(int rowSelected, int colSelected) {
		if (rowSelected >= 0 && rowSelected < TOTALROWS && colSelected >= 0 && colSelected < TOTALCOLUMNS
				&& grid[rowSelected][colSelected] == Dot.EMPTY && grid[rowSelected][colSelected] != Dot.NOTUSED) {
			grid[rowSelected][colSelected] = currentTurn; // Place token
			if (currentTurn == Dot.WHITE && !allPiecesOnBoard) {
				numWhitePieces -= 1;
			}
			if (currentTurn == Dot.BLACK && !allPiecesOnBoard) {
				numBlackPieces -= 1;
			}
			currentTurn = (currentTurn == Dot.WHITE) ? Dot.BLACK : Dot.WHITE; // change turn
		}
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
					return false; // an white cell found, not win
				}
				if (grid[row][col] == Dot.WHITE) {
					return false; // an black cell found, not win
				}
			}
		}
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
		} else if (grid[rowSelected][colSelected] == Dot.NOTUSED) {
			return gridNotUsed[rowSelected][colSelected];
		} else {
			return null;
		}
	}
}
