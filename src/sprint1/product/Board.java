package sprint1.product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Board {
	private static final int TOTALROWS = 7;
	private static final int TOTALCOLUMNS = 7;
	private Dot currentTurn;
	private int numBlackPieces = 4;
	private int numWhitePieces = 4;
	private static List<List<Integer>> currentMillsArray;
	private int[][] positionOfCells = { { 0, 0 }, { 0, 3 }, { 0, 6 }, { 1, 1 }, { 1, 3 }, { 1, 5 }, { 2, 2 }, { 2, 3 },
			{ 2, 4 }, { 3, 0 }, { 3, 1 }, { 3, 2 }, { 3, 4 }, { 3, 5 }, { 3, 6 }, { 4, 2 }, { 4, 3 }, { 4, 4 },
			{ 5, 1 }, { 5, 3 }, { 5, 5 }, { 6, 0 }, { 6, 3 }, { 6, 6 } };
	private final int[][] neighborsArray = { { 1, 9 }, { 0, 2, 4 }, { 1, 14 }, { 4, 10 }, { 1, 3, 5, 7 }, { 4, 13 },
			{ 7, 11 }, { 4, 6, 8 }, { 7, 12 }, { 0, 10, 21 }, { 3, 9, 11, 18 }, { 6, 10, 15 }, { 8, 13, 17 },
			{ 5, 12, 14, 20 }, { 2, 13, 23 }, { 11, 16 }, { 15, 17, 19 }, { 12, 16 }, { 10, 19 }, { 16, 18, 20, 22 },
			{ 13, 19 }, { 9, 22 }, { 19, 21, 23 }, { 14, 22 }, };
	private final int[][] millsArray = { { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 }, { 9, 10, 11 }, { 12, 13, 14 },
			{ 15, 16, 17 }, { 18, 19, 20 }, { 21, 22, 23 }, { 0, 9, 21 }, { 3, 10, 18 }, { 6, 11, 15 }, { 1, 4, 7 },
			{ 16, 19, 22 }, { 8, 12, 17 }, { 5, 13, 20 }, { 2, 14, 23 }, };

	public enum Dot {
		EMPTY, WHITE, BLACK, NOTUSED, GRAY, BLACKMILL, WHITEMILL
	}

	public enum GameState {
		PLAYING1, PLAYING2a, PLAYING2b1, PLAYING2b2, PLAYING3a, PLAYING3b, DRAW, WHITE_WON, BLACK_WON
	}

	private GameState currentGameState;

	private Dot[][] grid;
	static {
		Integer[] test = { 0, 1, 2 };
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(test));
		// currentMillsArray.add(list);
	}

	public Board() {
		grid = new Dot[TOTALROWS][TOTALCOLUMNS];
		currentMillsArray = new ArrayList<List<Integer>>();
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
			if (checkMill(rowSelected, colSelected)) {
				currentGameState = GameState.PLAYING3a;
				return;
			}
			currentTurn = (currentTurn == Dot.WHITE) ? Dot.BLACK : Dot.WHITE;
		}

	}

	private int indexOf(int row, int col) {
		int i = 0;
		for (int[] position : positionOfCells) {
			if (position[0] == row && position[1] == col) {
				return i;
			}
			i++;
		}
		return 100000;
	}

	public void makeMoveSecondPhaseA(int rowSelected, int colSelected) {
		if (rowSelected >= 0 && rowSelected < TOTALROWS && colSelected >= 0 && colSelected < TOTALCOLUMNS
				&& (grid[rowSelected][colSelected] == currentTurn
						|| (currentTurn == Dot.WHITE && getDot(rowSelected, colSelected) == Dot.WHITEMILL)
						|| (currentTurn == Dot.BLACK && getDot(rowSelected, colSelected) == Dot.BLACKMILL))
				&& grid[rowSelected][colSelected] != Dot.NOTUSED) {
			grid[rowSelected][colSelected] = Dot.GRAY;
			currentGameState = GameState.PLAYING2b1;
		}
	}

	public void makeMoveSecondPhaseB1(int rowFrom, int colFrom, int rowSelected, int colSelected) {
		if (grid[rowSelected][colSelected] == currentTurn
				|| (currentTurn == Dot.WHITE && getDot(rowSelected, colSelected) == Dot.WHITEMILL)
				|| (currentTurn == Dot.BLACK && getDot(rowSelected, colSelected) == Dot.BLACKMILL)) {
			grid[rowSelected][colSelected] = Dot.GRAY;
			grid[rowFrom][colFrom] = currentTurn;
			currentGameState = GameState.PLAYING2b2;
			return;

		}

		if (rowSelected >= 0 && rowSelected < TOTALROWS && colSelected >= 0 && colSelected < TOTALCOLUMNS
				&& grid[rowSelected][colSelected] == Dot.EMPTY && grid[rowSelected][colSelected] != Dot.NOTUSED
				&& checkValidMoveNoFlying(rowFrom, colFrom, rowSelected, colSelected)) {
			grid[rowSelected][colSelected] = currentTurn;
			grid[rowFrom][colFrom] = Dot.EMPTY;
			currentGameState = GameState.PLAYING2a;
			if (checkMill(rowSelected, colSelected)) {
				currentGameState = GameState.PLAYING3b;
				return;
			}
			currentTurn = (currentTurn == Dot.WHITE) ? Dot.BLACK : Dot.WHITE;

		}
	}

	public void makeMoveSecondPhaseB2(int colFrom, int rowFrom, int rowSelected, int colSelected) {
		if (grid[rowSelected][colSelected] == currentTurn
				|| (currentTurn == Dot.WHITE && getDot(rowSelected, colSelected) == Dot.WHITEMILL)
				|| (currentTurn == Dot.BLACK && getDot(rowSelected, colSelected) == Dot.BLACKMILL)) {
			grid[rowSelected][colSelected] = Dot.GRAY;
			grid[rowFrom][colFrom] = currentTurn;
			currentGameState = GameState.PLAYING2b1;
			return;
		}
		if (grid[rowSelected][colSelected] != Dot.EMPTY) {
			grid[rowSelected][colSelected] = currentTurn;
		}
		grid[rowFrom][colFrom] = Dot.EMPTY;
		currentGameState = GameState.PLAYING2a;

		currentTurn = (currentTurn == Dot.WHITE) ? Dot.BLACK : Dot.WHITE;

	}

	public void makeMoveThirdPhase(int colFrom, int rowFrom) {
		if (grid[rowFrom][colFrom] == currentTurn && grid[rowFrom][colFrom] != Dot.NOTUSED
				&& grid[rowFrom][colFrom] != Dot.EMPTY) {
			currentGameState = GameState.PLAYING3a;
			return;
		} else if (grid[rowFrom][colFrom] != currentTurn && currentGameState == GameState.PLAYING3a
				&& grid[rowFrom][colFrom] != Dot.NOTUSED && grid[rowFrom][colFrom] != Dot.EMPTY) {
			if ((currentTurn == Dot.WHITE && grid[rowFrom][colFrom] == Dot.BLACKMILL) || !notInTheMillAvailible()) {
				currentGameState = GameState.PLAYING3a;
				return;
			}
			if (currentTurn == Dot.BLACK && grid[rowFrom][colFrom] == Dot.WHITEMILL || !notInTheMillAvailible()) {
				currentGameState = GameState.PLAYING3a;
				return;
			}
			grid[rowFrom][colFrom] = Dot.EMPTY;
			currentGameState = GameState.PLAYING1;
			currentTurn = (currentTurn == Dot.WHITE) ? Dot.BLACK : Dot.WHITE;
		} else if (grid[rowFrom][colFrom] != currentTurn && currentGameState == GameState.PLAYING3b
				&& grid[rowFrom][colFrom] != Dot.NOTUSED && grid[rowFrom][colFrom] != Dot.EMPTY) {
			grid[rowFrom][colFrom] = Dot.EMPTY;
			currentGameState = GameState.PLAYING2a;
			currentTurn = (currentTurn == Dot.WHITE) ? Dot.BLACK : Dot.WHITE;
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
					return false; // a white cell found, not win
				}
				if (grid[row][col] == Dot.WHITE) {
					return false; // a black cell found, not win
				}
			}
		}
		return true;
	}

	public boolean checkValidMoveNoFlying(int rowFrom, int colFrom, int rowTo, int colTo) {
		int indexFrom = indexOf(rowFrom, colFrom);
		int indexTo = indexOf(rowTo, colTo);

		int i = 0;
		for (int[] neighbors : neighborsArray) {
			if (i == indexFrom) {
				for (int neighbor : neighbors) {
					if (neighbor == indexTo) {
						return true;
					}
				}
			}
			i++;
		}
		return false;
	}

	public boolean notInTheMillAvailible() {

		for (int row = 0; row < TOTALROWS; ++row) {
			for (int col = 0; col < TOTALCOLUMNS; ++col) {
				if (grid[row][col] == Dot.WHITE && currentTurn == Dot.BLACK) {
					return true;
				}
				if (grid[row][col] == Dot.BLACK && currentTurn == Dot.WHITE) {
					return true;
				}
			}
		}

		return false;

	}

	public boolean checkMill(int colTo, int rowTo) {
		int indexTo = indexOf(colTo, rowTo);

		for (int[] mill : millsArray) {
			List<Integer> millList = Arrays.stream(mill).boxed().collect(Collectors.toList());

			int[] dots = new int[4];

			if (millList.contains(indexTo)) {
				int i = 0;

				for (int neighbor : millList) {
					int row = positionOfCells[neighbor][0];
					int col = positionOfCells[neighbor][1];
					if (currentTurn == getDot(row, col)
							|| (currentTurn == Dot.WHITE && getDot(row, col) == Dot.WHITEMILL)
							|| (currentTurn == Dot.BLACK && getDot(row, col) == Dot.BLACKMILL)) {
						i++;
						dots[i] = indexOf(row, col);
						if (i == 3) {
							for (int j = 1; j < dots.length; j++) {

								int rowM = positionOfCells[dots[j]][0];
								int colM = positionOfCells[dots[j]][1];
								if (currentTurn == Dot.BLACK) {
									grid[rowM][colM] = Dot.BLACKMILL;
								}
								if (currentTurn == Dot.WHITE) {
									grid[rowM][colM] = Dot.WHITEMILL;
								}
							}
							return true;
						}
					}
				}

			}

		}
		return false;

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
