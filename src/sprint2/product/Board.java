package sprint2.product;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The class that is used for board control. Does not include GUI which is in
 * another class.
 *
 */
public class Board {
	public static final int SIZE = 7;
	Dot currentTurn;
	private int numBlackPieces = 4;
	private int numWhitePieces = 4;
	private int numWhitePiecesPhase2 = 0;
	private int numBlackPiecesPhase2 = 0;
	private Dot aiPlayer;
	private GameRegime currentGameRegime;
	AI ai = new AI(this);
	int[][] positionOfCells = { { 0, 0 }, { 0, 3 }, { 0, 6 }, { 1, 1 }, { 1, 3 }, { 1, 5 }, { 2, 2 }, { 2, 3 },
			{ 2, 4 }, { 3, 0 }, { 3, 1 }, { 3, 2 }, { 3, 4 }, { 3, 5 }, { 3, 6 }, { 4, 2 }, { 4, 3 }, { 4, 4 },
			{ 5, 1 }, { 5, 3 }, { 5, 5 }, { 6, 0 }, { 6, 3 }, { 6, 6 } };
	private final int[][] neighborsArray = { { 1, 9 }, { 0, 2, 4 }, { 1, 14 }, { 4, 10 }, { 1, 3, 5, 7 }, { 4, 13 },
			{ 7, 11 }, { 4, 6, 8 }, { 7, 12 }, { 0, 10, 21 }, { 3, 9, 11, 18 }, { 6, 10, 15 }, { 8, 13, 17 },
			{ 5, 12, 14, 20 }, { 2, 13, 23 }, { 11, 16 }, { 15, 17, 19 }, { 12, 16 }, { 10, 19 }, { 16, 18, 20, 22 },
			{ 13, 19 }, { 9, 22 }, { 19, 21, 23 }, { 14, 22 }, };
	final int[][] millsArray = { { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 }, { 9, 10, 11 }, { 12, 13, 14 }, { 15, 16, 17 },
			{ 18, 19, 20 }, { 21, 22, 23 }, { 0, 9, 21 }, { 3, 10, 18 }, { 6, 11, 15 }, { 1, 4, 7 }, { 16, 19, 22 },
			{ 8, 12, 17 }, { 5, 13, 20 }, { 2, 14, 23 }, };

	private GameState currentGameState;

	public Dot[][] grid;

	/**
	 * This method is used to create a new grid of size SIZE.
	 */
	public Board() {
		grid = new Dot[SIZE][SIZE];
		reset();
	}

	public Board(int numPieces) {
		grid = new Dot[SIZE][SIZE];
		reset(numPieces);

	}

	/**
	 * This method is used to initialize the grid with either NOTUSED or EMPTY
	 * cells. Since the grid is 7x7 but not all the dots are used so the NOTUSED is
	 * used for those. This method is used for testing.
	 */
	public void reset() {
		for (Dot[] dots : grid) {
			Arrays.fill(dots, Dot.NOTUSED);
		}

		for (int i = 0; i != SIZE; i++) {
			grid[SIZE / 2][i] = Dot.EMPTY;
			grid[i][SIZE / 2] = Dot.EMPTY;
			grid[i][i] = Dot.EMPTY;
			grid[SIZE - i - 1][i] = Dot.EMPTY;
		}

		grid[SIZE / 2][SIZE / 2] = Dot.NOTUSED;
		currentGameState = GameState.START;

		// currentTurn = Dot.WHITE;
		numBlackPieces = 4;
		numWhitePieces = 4;
		numWhitePiecesPhase2 = 0;
		numBlackPiecesPhase2 = 0;
	}

	/**
	 * This method is used to initialize the grid with either NOTUSED or EMPTY
	 * cells. Since the grid is 7x7 but not all the dots are used so the NOTUSED is
	 * used for those. This method is used for testing.
	 */
	public void reset(int numPieces) {
		for (Dot[] dots : grid) {
			Arrays.fill(dots, Dot.NOTUSED);
		}

		for (int i = 0; i != SIZE; i++) {
			grid[SIZE / 2][i] = Dot.EMPTY;
			grid[i][SIZE / 2] = Dot.EMPTY;
			grid[i][i] = Dot.EMPTY;
			grid[SIZE - i - 1][i] = Dot.EMPTY;
		}

		grid[SIZE / 2][SIZE / 2] = Dot.NOTUSED;

		currentGameState = GameState.CHOOSEOPPONENT;
		// currentTurn = Dot.WHITE;
		numBlackPieces = numPieces;
		numWhitePieces = numPieces;
		numWhitePiecesPhase2 = 0;
		numBlackPiecesPhase2 = 0;
	}

	/**
	 * The placement phase. Players are allowed to place pieces but not move them.
	 * The mills are allowed
	 * 
	 * @param rowSelected the row selected to place the chip
	 * @param colSelected the column selected to place the chip
	 */
	public boolean makeMoveFirstPhase(int point) {// placement phase
		int[] coord = this.indexOf(point);
		int rowSelected = coord[1];
		int colSelected = coord[0];
		if (rowSelected >= 0 && rowSelected < SIZE && colSelected >= 0 && colSelected < SIZE
				&& grid[rowSelected][colSelected] == Dot.EMPTY && grid[rowSelected][colSelected] != Dot.NOTUSED) {

			grid[rowSelected][colSelected] = currentTurn;
			if (currentTurn == Dot.WHITE) {
				numWhitePieces -= 1;
				numWhitePiecesPhase2 += 1;
			} else if (currentTurn == Dot.BLACK) {
				numBlackPieces -= 1;
				numBlackPiecesPhase2 += 1;
			}
			if (checkMill(rowSelected, colSelected)) {
				currentGameState = GameState.PLAYING3a;
				if (getCurrentGameRegime() == GameRegime.PvsAI && getCurrentTurn() == getAiPlayer()) {
					ai.moveDecider();
				}
				return true;
			}
			if (numWhitePieces == 0 && numBlackPieces == 0) {
				currentGameState = GameState.PLAYING2a;
				updateGameState(currentTurn);

				currentTurn = (currentTurn == Dot.WHITE) ? Dot.BLACK : Dot.WHITE;

				return true;

			}

			currentTurn = (currentTurn == Dot.WHITE) ? Dot.BLACK : Dot.WHITE;
		}

		if (getCurrentGameRegime() == GameRegime.PvsAI && getCurrentTurn() == getAiPlayer()) {

			ai.moveDecider();
			updateGameState(currentTurn);

		}
		return true;

	}

	/**
	 * The initial phase of moving pieces. The player selects the piece to move but
	 * not moving it yet until the phase B. The piece gets grayed out.
	 * 
	 * @param rowSelected the row selected to place the chip
	 * @param colSelected the column selected to place the chip
	 */
	public boolean makeMoveSecondPhaseA(int point) { // moving phase
		int[] coord = this.indexOf(point);
		int rowSelected = coord[1];
		int colSelected = coord[0];
		if (rowSelected >= 0 && rowSelected < SIZE && colSelected >= 0 && colSelected < SIZE
				&& (grid[rowSelected][colSelected] == currentTurn
						|| (currentTurn == Dot.WHITE && getDot(rowSelected, colSelected) == Dot.WHITEMILL)
						|| (currentTurn == Dot.BLACK && getDot(rowSelected, colSelected) == Dot.BLACKMILL))
				&& grid[rowSelected][colSelected] != Dot.NOTUSED) {
			int countValidSpaces = highlightValidMoves(rowSelected, colSelected);
			if (currentGameState == GameState.PLAYING2a && countValidSpaces != 0) {
				grid[rowSelected][colSelected] = Dot.GRAY;

				currentGameState = GameState.PLAYING2b1;
				if (getCurrentGameRegime() == GameRegime.PvsAI && getCurrentTurn() == getAiPlayer()) {
					ai.moveDecider();
				}
				return true;
			}
		}
		return false;

	}

	/**
	 * The method to actually move the piece after it was selected. the rowFrom and
	 * colFrom are needed to remove the piece from last location
	 * 
	 * @param rowFrom     row to remove the piece from
	 * @param colFrom     column to remove the piece from
	 * @param rowSelected row to put the piece to
	 * @param colSelected column to put the piece to
	 */
	public void makeMoveSecondPhaseB(int pointFrom, int pointTo) {
		int[] coordTo = this.indexOf(pointTo);
		int rowSelected = coordTo[1];
		int colSelected = coordTo[0];
		int[] coordFrom = this.indexOf(pointFrom);
		int rowFrom = coordFrom[1];
		int colFrom = coordFrom[0];

		if (grid[rowSelected][colSelected] == currentTurn
				|| (currentTurn == Dot.WHITE && getDot(rowSelected, colSelected) == Dot.WHITEMILL)
				|| (currentTurn == Dot.BLACK && getDot(rowSelected, colSelected) == Dot.BLACKMILL)) {
			currentGameState = GameState.PLAYING2b1;
			return;

		}

		if ((grid[rowSelected][colSelected] == Dot.HIGHLIGHTWHITE
				|| grid[rowSelected][colSelected] == Dot.HIGHLIGHTBLACK)
				&& grid[rowSelected][colSelected] != Dot.NOTUSED
				&& (checkValidMoveNoFlying(rowFrom, colFrom, rowSelected, colSelected)
						|| (currentTurn == Dot.BLACK && numBlackPiecesPhase2 < 4) // flying
						|| (currentTurn == Dot.WHITE && numWhitePiecesPhase2 < 4))) {
			grid[rowSelected][colSelected] = currentTurn;
			grid[rowFrom][colFrom] = Dot.EMPTY;
			currentGameState = GameState.PLAYING2a;
			if (checkMill(rowSelected, colSelected)) {
				setGray();
				currentGameState = GameState.PLAYING3b;
				if (getCurrentGameRegime() == GameRegime.PvsAI && getCurrentTurn() == getAiPlayer()) {
					ai.moveDecider();
				}
				//updateGameState(currentTurn);

				return;
			}
			//updateGameState(currentTurn);

			currentTurn = (currentTurn == Dot.WHITE) ? Dot.BLACK : Dot.WHITE;
			if (getCurrentGameRegime() == GameRegime.PvsAI && getCurrentTurn() == getAiPlayer()) {
				ai.moveDecider();
			}

		}
	}

	/**
	 * The method to handle mill, when there are 3 pieces in a row. Removes the
	 * piece from selected location, even from the mill if other pieces are not
	 * available.
	 * 
	 * @param colFrom column to remove the piece from.
	 * @param rowFrom row to remove the piece from.
	 */
	public boolean makeMoveThirdPhase(int pointFrom) {
		int[] coordFrom = this.indexOf(pointFrom);
		int rowFrom = coordFrom[1];
		int colFrom = coordFrom[0];
		if ((grid[rowFrom][colFrom] == currentTurn
				|| currentTurn == Dot.WHITE && grid[rowFrom][colFrom] == Dot.WHITEMILL
				|| currentTurn == Dot.BLACK && grid[rowFrom][colFrom] == Dot.BLACKMILL)
				&& grid[rowFrom][colFrom] != Dot.NOTUSED && grid[rowFrom][colFrom] != Dot.EMPTY) {
			currentGameState = GameState.PLAYING3a;
			return false;
		} else if (grid[rowFrom][colFrom] != currentTurn && currentGameState == GameState.PLAYING3a
				&& grid[rowFrom][colFrom] != Dot.NOTUSED && grid[rowFrom][colFrom] != Dot.EMPTY) {
			if ((currentTurn == Dot.WHITE && grid[rowFrom][colFrom] == Dot.BLACKMILL) && notInTheMillAvailible()) {
				currentGameState = GameState.PLAYING3a;
				return false;
			}
			if (currentTurn == Dot.BLACK && grid[rowFrom][colFrom] == Dot.WHITEMILL && notInTheMillAvailible()) {
				currentGameState = GameState.PLAYING3a;
				return false;
			}
			if (currentTurn == Dot.WHITE) {
				numBlackPiecesPhase2 -= 1;
			}
			if (currentTurn == Dot.BLACK) {
				numWhitePiecesPhase2 -= 1;
			}
			grid[rowFrom][colFrom] = Dot.EMPTY;

			currentGameState = GameState.PLAYING1;
			clearMills();
			updateGameState(currentTurn);
			if (numWhitePieces == 0 && numBlackPieces == 0) {
				currentGameState = GameState.PLAYING2a;
				updateGameState(currentTurn);

				currentTurn = (currentTurn == Dot.WHITE) ? Dot.BLACK : Dot.WHITE;
				if (getCurrentGameRegime() == GameRegime.PvsAI && getCurrentTurn() == getAiPlayer()) {

					ai.moveDecider();
				}
				// clearMills();

				return true;

			}

			currentTurn = (currentTurn == Dot.WHITE) ? Dot.BLACK : Dot.WHITE;
			if (getCurrentGameRegime() == GameRegime.PvsAI && getCurrentTurn() == getAiPlayer()) {
				ai.moveDecider();
			}
		} else if ((grid[rowFrom][colFrom] != currentTurn && currentGameState == GameState.PLAYING3b
				&& grid[rowFrom][colFrom] != Dot.NOTUSED && grid[rowFrom][colFrom] != Dot.EMPTY)
				|| !notInTheMillAvailible()) {
			if ((currentTurn == Dot.WHITE && grid[rowFrom][colFrom] == Dot.BLACKMILL) && notInTheMillAvailible()) {
				currentGameState = GameState.PLAYING3b;
				return true;
			}
			if (currentTurn == Dot.BLACK && grid[rowFrom][colFrom] == Dot.WHITEMILL && notInTheMillAvailible()) {
				currentGameState = GameState.PLAYING3b;
				return true;
			}
			grid[rowFrom][colFrom] = Dot.EMPTY;
			if (currentTurn == Dot.WHITE) {
				numBlackPiecesPhase2 -= 1;
			}
			if (currentTurn == Dot.BLACK) {
				numWhitePiecesPhase2 -= 1;
			}
			currentGameState = GameState.PLAYING2a;
			updateGameState(currentTurn);
			// clearMills();
			currentTurn = (currentTurn == Dot.WHITE) ? Dot.BLACK : Dot.WHITE;
			if (getCurrentGameRegime() == GameRegime.PvsAI && getCurrentTurn() == getAiPlayer()) {
				ai.moveDecider();
			}

		}
		return true;
	}

	/**
	 * Method to highlight moves around the selected chip. Also is used for the
	 * first-touch rule to handle the winning or losing situation.
	 * 
	 * @param rowSelected row of the chip selected.
	 * @param colSelected column of the chip selected.
	 */
	public int highlightValidMoves(int rowSelected, int colSelected) {
		int index = indexOf(colSelected, rowSelected);
		int i = 0;
		int highlightCount = 0;
		for (int[] neighbors : neighborsArray) {
			if (i == index || ((currentTurn == Dot.WHITEMILL || currentTurn == Dot.WHITE) && numWhitePiecesPhase2 < 4)
					|| ((currentTurn == Dot.BLACKMILL || currentTurn == Dot.BLACK) && numBlackPiecesPhase2 < 4)) {
				for (int neighbor : neighbors) {
					int row = positionOfCells[neighbor][0];
					int col = positionOfCells[neighbor][1];

					if (grid[col][row] == Dot.EMPTY && currentTurn == Dot.WHITE) {
						grid[col][row] = Dot.HIGHLIGHTWHITE;
						highlightCount++;
					}
					if (grid[col][row] == Dot.EMPTY && currentTurn == Dot.BLACK) {
						grid[col][row] = Dot.HIGHLIGHTBLACK;
						highlightCount++;
					}

				}
			}
			i++;
		}

		return highlightCount;
	}

	/**
	 * Sets the highlighted cells to gray after the move is done.
	 */
	public void setGray() {
		for (int row = 0; row < SIZE; ++row) {
			for (int col = 0; col < SIZE; ++col) {
				if (grid[row][col] == Dot.HIGHLIGHTWHITE || grid[row][col] == Dot.HIGHLIGHTBLACK) {
					grid[row][col] = Dot.EMPTY;
				}

			}
		}
	}

	/**
	 * Changes the game state to win or draw given that the conditions met.
	 * 
	 * @param turn current turn(black or white).
	 */
	public void updateGameState(Dot turn) {
		if (hasWon()) { // check for win
			currentGameState = (turn == Dot.WHITE) ? GameState.WHITE_WON : GameState.BLACK_WON;

		} /*else if (isDraw()) {
			currentGameState = GameState.DRAW;
			clearMills();

		}*/
		// clearMills();
		clearMills();

		setGray();
	}

	/**
	 * Returns whether the draw condition was met.
	 * 
	 * @return boolean indication whether there is draw or not.
	 */
	private boolean isDraw() {
		if (numWhitePiecesPhase2 == 3 && numBlackPiecesPhase2 == 3 && currentGameState != GameState.PLAYING1) {
			return true;
		}

		return false;
	}

	/**
	 * Returns whether one of the winning conditions was met.
	 * 
	 * @return boolean to show is the player won or not.
	 */
	private boolean hasWon() {
		if ((numWhitePiecesPhase2 < 3 || numBlackPiecesPhase2 < 3) && currentGameState != GameState.PLAYING1) {
			return true; // if the number of pieces less than 3
		}
		for (int row = 0; row < SIZE; ++row) {
			for (int col = 0; col < SIZE; ++col) {
				int indexFrom = indexOf(col, row);

				int i = 0;
				if ((getDot(row, col) == Dot.WHITE && currentTurn == Dot.BLACK)
						|| (getDot(row, col) == Dot.WHITEMILL && currentTurn == Dot.BLACK)
						|| (getDot(row, col) == Dot.BLACKMILL && currentTurn == Dot.WHITE)
						|| (getDot(row, col) == Dot.BLACK && currentTurn == Dot.WHITE)) {
					for (int[] neighbors : neighborsArray) {
						if (i == indexFrom) {
							for (int neighbor : neighbors) {
								int colTo = positionOfCells[neighbor][0];
								int rowTo = positionOfCells[neighbor][1];
								Dot dot = getDot(rowTo, colTo);
								if (dot == Dot.EMPTY || (currentTurn == Dot.BLACK && numBlackPiecesPhase2 < 4) // flying
										|| (currentTurn == Dot.WHITE && numWhitePiecesPhase2 < 4)) {
									return false;
								}
							}
						}
						i++;
					}
				}
			}
		}
		return true;
	}

	/**
	 * Checks whether there is a valid move available, not implementing flying since
	 * it was implemented in the move method.
	 * 
	 * @param rowFrom selected chip row
	 * @param colFrom selected chip column
	 * @param rowTo   selected to chip row
	 * @param colTo   selected to chip column
	 * @return
	 */
	private boolean checkValidMoveNoFlying(int rowFrom, int colFrom, int rowTo, int colTo) {
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

	/**
	 * Check whether there is a piece not in the mill available for removal
	 * 
	 * @return returns a boolean to show if there is a piece not in the mill
	 *         available to remove.
	 */
	public boolean notInTheMillAvailible() {

		for (int row = 0; row < SIZE; ++row) {
			for (int col = 0; col < SIZE; ++col) {
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

	/**
	 * checks if the move will cause a mill.
	 * 
	 * @param colTo selected column destination of the chip
	 * @param rowTo selected row destination of the chip
	 * @return a boolean whether the move will cause a mill.
	 */
	public boolean checkMill(int colTo, int rowTo) {
		int indexTo = indexOf(colTo, rowTo);
		boolean millcheck = false;

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
							millcheck = true;
						}

					}
				}

			}

		}
		return millcheck;

	}

	public void checkMillsOnTheBoard() {
		for (int rowOf = 0; rowOf < SIZE; ++rowOf) {
			for (int colOf = 0; colOf < SIZE; ++colOf) {
				int indexTo = indexOf(colOf, rowOf);
				boolean millcheck = false;

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
									millcheck = true;
								}

							}
						}

					}

				}
			}
		}

	}

	/**
	 * This method clears the mills on the board which are not mills anymore.
	 */
	void clearMills() {
		int[] millsWhite = new int[24];
		int[] millsBlack = new int[24];

		for (int[] mill : millsArray) {
			List<Integer> millList = Arrays.stream(mill).boxed().collect(Collectors.toList());
			int i = 0;
			int j = 0;
			int[] dots = new int[3];

			int y = 0;
			for (int neighbor : millList) {

				int row = positionOfCells[neighbor][0];
				int col = positionOfCells[neighbor][1];
				dots[y] = indexOf(row, col);
				if (i > 0 && grid[col][row] == Dot.BLACK) {
					grid[col][row] = Dot.BLACKMILL;
				}
				if (grid[col][row] == Dot.BLACKMILL) {
					i++;
				}
				if (j > 0 && grid[col][row] == Dot.WHITE) {
					grid[col][row] = Dot.WHITEMILL;
				}
				if (grid[col][row] == Dot.WHITEMILL) {

					j++;
				}
				y++;
			}
			if (i == 3) {
				for (int neighbor : millList) {
					millsBlack[neighbor] = 1;
				}
			}
			if (j == 3) {
				for (int neighbor : millList) {
					millsWhite[neighbor] = 1;
				}
			}
			if (i < 3) {
				for (int neighbor : millList) {
					int row = positionOfCells[neighbor][0];
					int col = positionOfCells[neighbor][1];
					if (grid[col][row] == Dot.BLACKMILL && millsBlack[neighbor] == 0) {
						grid[col][row] = Dot.BLACK;
					}
				}
			}
			if (j < 3) {
				for (int neighbor : millList) {
					int row = positionOfCells[neighbor][0];
					int col = positionOfCells[neighbor][1];
					if (grid[col][row] == Dot.WHITEMILL && millsWhite[neighbor] == 0) {
						grid[col][row] = Dot.WHITE;
					}
				}
			}
		}

	}

	/**
	 * This method is used to translate the row, column form of dot representation
	 * to 0-23
	 * 
	 * @param row The row of the chip
	 * @param col The column of the chip
	 * @return the index of a dot on a scale from 0-23
	 */
	public int indexOf(int row, int col) {
		int i = 0;
		for (int[] position : positionOfCells) {
			if (position[0] == row && position[1] == col) {
				return i;
			}
			i++;
		}
		return -1;
	}

	/**
	 * This method is used to translate the row, column form of dot representation
	 * to 0-23
	 * 
	 * @param row The row of the chip
	 * @param col The column of the chip
	 * @return the index of a dot on a scale from 0-23
	 */
	public int[] indexOf(int point) {
		int i = 0;
		for (int[] position : positionOfCells) {
			if (i == point) {
				return position;
			}
			i++;
		}
		return null;
	}

	/**
	 * Getter for currentGameState
	 * 
	 * @return currentGameState
	 */
	public GameState getGameState() {
		return currentGameState;
	}

	/**
	 * Setter for currentGameState
	 * 
	 * @param gamestate the game state to set the current game state to. In the form
	 *                  of GameState... since it is ENUM.
	 */
	public void setGameState(GameState gamestate) {
		currentGameState = gamestate;
	}

	/**
	 * getter for total rows used to build GUI
	 * 
	 * @return SIZE = 7
	 */
	public int getTotalRows() {
		return SIZE;
	}

	/**
	 * getter for total column used to build GUI
	 * 
	 * @return SIZE = 7
	 */
	public int getTotalColumns() {
		return SIZE;
	}

	/**
	 * Returns the Dot at the specific location
	 * 
	 * @param row the row of the specific location
	 * @param col the column of the specific location
	 * @return
	 */
	public Dot getDot(int row, int col) {
		if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
			return Dot.NOTUSED;
		}

		return grid[row][col];
	}

	/**
	 * getter for number of black pieces left for the placement phase
	 * 
	 * @return numBlackPieces
	 */
	public int getNumBlackPiecesFirstPhase() {
		return numBlackPieces;
	}

	/**
	 * getter for number of black pieces left for the placement phase
	 * 
	 * @return numWhitePieces
	 */
	public int getNumWhitePiecesFirstPhase() {
		return numWhitePieces;
	}

	/**
	 * getter for the current turn(black or white).
	 * 
	 * @return currentTurn
	 */
	public Dot getCurrentTurn() {
		return currentTurn;
	}

	/**
	 * setter for current turn(black or white)
	 * 
	 */
	public void setCurrentTurn(Dot dotPiece) {
		currentTurn = dotPiece;
	}

	public Dot getAiPlayer() {
		return aiPlayer;
	}

	public void setAiPlayer(Dot aiPlayer) {
		this.aiPlayer = aiPlayer;
	}

	public GameRegime getCurrentGameRegime() {
		return currentGameRegime;
	}

	public void setCurrentGameRegime(GameRegime currentGameRegime) {
		this.currentGameRegime = currentGameRegime;
	}

}
