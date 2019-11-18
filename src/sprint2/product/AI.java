package sprint2.product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import sprint2.product.*;

public class AI {
	private Board board;
	Random rand = new Random();
	private int move = 25;
	private int moveFrom = 25;
	private int moveTo = 25;

	List<List<Integer>> closedMills = new ArrayList<List<Integer>>();
	List<List<Integer>> checkedMills = new ArrayList<List<Integer>>();
	int counterForm2 = 0;

	public AI(Board board) {
		this.board = board;
	}

	public boolean makeMove() {

		if (board.getGameState() == GameState.PLAYING1) {
			boolean status = board.makeMoveFirstPhase(move);
			return status;

		}
		if (board.getGameState() == GameState.PLAYING2a) {

			boolean status = board.makeMoveSecondPhaseA(moveFrom);

			return status;

		}
		if (board.getGameState() == GameState.PLAYING2b1) {

			board.makeMoveSecondPhaseB(moveFrom, moveTo);
			;
			return false;
		}
		if (board.getGameState() == GameState.PLAYING3a||board.getGameState() == GameState.PLAYING3b) {
			boolean status = board.makeMoveThirdPhase(move);

			return status;

		}
		return false;

	}

	public void moveDecider() {
		if (board.getGameState() == GameState.PLAYING2b1) {
			System.out.println("hello from moving");
			moveThePiece();
			makeMove();

		} else if (board.getGameState() == GameState.PLAYING2a) {
			choosePieceToMove();
			makeMove();

		} else if (board.getGameState() == GameState.PLAYING3a||board.getGameState() == GameState.PLAYING3b) {
			System.out.println("Hello From Remove Piece");
			removePiece();
			makeMove();
			System.out.println(board.getGameState());
		} else if (formMill()) {

			makeMove();
		} else if (blockTwo()) {

			makeMove();
		} else if (formTwo()) {
			this.counterForm2++;
			makeMove();

		} else {
			while (true) {
				move = rand.nextInt(24);
				boolean status = makeMove();
				if (status)
					break;
			}
		}
	}

	private void choosePieceToMove() {
		printTheBoard();
		for (int row = 0; row < Board.SIZE; ++row) {
			for (int col = 0; col < Board.SIZE; ++col) {
				if ((board.grid[row][col] == Dot.WHITE || board.grid[row][col] == Dot.WHITEMILL)
						&& board.currentTurn == Dot.WHITE) {
					move = board.indexOf(col, row);
					board.highlightValidMoves(row, col);
					if (anyDotHighlightedFormMill()) {
						moveFrom = move;
						board.setGray();
						board.clearMills();
						board.checkMillsOnTheBoard();
						return;
					}}}}
			for (int row = 0; row < Board.SIZE; ++row) {
				for (int col = 0; col < Board.SIZE; ++col) {
					if ((board.grid[row][col] == Dot.WHITE || board.grid[row][col] == Dot.WHITEMILL)
							&& board.currentTurn == Dot.WHITE) {
					 if (anyDotHighlightedBlockTwo()) {
						moveFrom = move;
						board.setGray();
						board.clearMills();
						board.checkMillsOnTheBoard();
						return;
					}}}}
			for (int row = 0; row < Board.SIZE; ++row) {
				for (int col = 0; col < Board.SIZE; ++col) {
					if ((board.grid[row][col] == Dot.WHITE || board.grid[row][col] == Dot.WHITEMILL)
							&& board.currentTurn == Dot.WHITE) {
					 if (anyDotHighlightedFormTwo()) {
						moveFrom = move;
						board.setGray();
						board.clearMills();
						board.checkMillsOnTheBoard();
						return;
					}

				}}}
			for (int row = 0; row < Board.SIZE; ++row) {
				for (int col = 0; col < Board.SIZE; ++col) {
				if ((board.grid[row][col] == Dot.BLACK || board.grid[row][col] == Dot.BLACKMILL)
						&& board.currentTurn == Dot.BLACK) {
					move = board.indexOf(col, row);
					board.highlightValidMoves(row, col);
					if (anyDotHighlightedFormMill()) {
						moveFrom = move;
						board.setGray();
						board.clearMills();
						board.checkMillsOnTheBoard();
						return;
					}}}}
				for (int row = 0; row < Board.SIZE; ++row) {
					for (int col = 0; col < Board.SIZE; ++col) {
					if ((board.grid[row][col] == Dot.BLACK || board.grid[row][col] == Dot.BLACKMILL)
							&& board.currentTurn == Dot.BLACK) { if (anyDotHighlightedBlockTwo()) {
						moveFrom = move;
						board.setGray();
						board.clearMills();
						board.checkMillsOnTheBoard();
						return;
					}}}}
				for (int row = 0; row < Board.SIZE; ++row) {
					for (int col = 0; col < Board.SIZE; ++col) {
					if ((board.grid[row][col] == Dot.BLACK || board.grid[row][col] == Dot.BLACKMILL)
							&& board.currentTurn == Dot.BLACK) { if (anyDotHighlightedFormTwo()) {
						moveFrom = move;
						board.setGray();
						board.clearMills();
						board.checkMillsOnTheBoard();
						return;
					}
				}
			}}
				board.updateGameState(board.getCurrentTurn());

		}
	

	private boolean anyDotHighlightedFormMill() {

		for (int row = 0; row < Board.SIZE; ++row) {
			for (int col = 0; col < Board.SIZE; ++col) {

				if (board.grid[row][col] == Dot.HIGHLIGHTWHITE && board.currentTurn == Dot.WHITE) {
					int index = board.indexOf(row, col);

					if (formMillSecondPhase(index)) {
						

						return true;
					}
				}
				
				if (board.grid[row][col] == Dot.HIGHLIGHTBLACK && board.currentTurn == Dot.BLACK) {
					int index = board.indexOf(row, col);

					if (formMillSecondPhase(index)) {
						System.out.println("form mill");
						System.out.println("index " + index);
						System.out.println("move " + move);
						System.out.println("moveTo " + moveTo);
						return true;
					}
				}
			}
		}
		return false;}
	private boolean anyDotHighlightedBlockTwo() {
		for (int row = 0; row < Board.SIZE; ++row) {
			for (int col = 0; col < Board.SIZE; ++col) {
				if (board.grid[row][col] == Dot.HIGHLIGHTWHITE && board.currentTurn == Dot.WHITE) {
					int index = board.indexOf(row, col);

					if (blockTwoSecondPhase(index)) {
						System.out.println("block two");
						System.out.println("index " + index);
						System.out.println("move " + move);
						System.out.println("moveTo " + moveTo);
						return true;
					}
				}
				if (board.grid[row][col] == Dot.HIGHLIGHTBLACK && board.currentTurn == Dot.BLACK) {
					int index = board.indexOf(row, col);

					if (blockTwoSecondPhase(index)) {
						System.out.println("block two");
						System.out.println("index " + index);
						System.out.println("move " + move);
						System.out.println("moveTo " + moveTo);
						return true;
					}
				}
			}
		}
		return false;}
	private boolean anyDotHighlightedFormTwo() {
		for (int row = 0; row < Board.SIZE; ++row) {
			for (int col = 0; col < Board.SIZE; ++col) {
				if (board.grid[row][col] == Dot.HIGHLIGHTWHITE && board.currentTurn == Dot.WHITE) {
					int index = board.indexOf(row, col);

					if (formTwoSecondPhase(index)) {
						System.out.println("form two");

						return true;
					}
				}
				if (board.grid[row][col] == Dot.HIGHLIGHTBLACK && board.currentTurn == Dot.BLACK) {
					int index = board.indexOf(row, col);

					if (formTwoSecondPhase(index)) {
						System.out.println("form two");
						System.out.println("index " + index);
						System.out.println("move " + move);
						System.out.println("moveTo " + moveTo);
						return true;
					}
				}
			}
		}
		System.out.println("random");
		moveTo = rand.nextInt(24);
		// boolean status = makeMove();
		return true;

	}

	private boolean routineCheck(int row, int col) {

		int index = board.indexOf(row, col);

		if (formMillSecondPhase(index)) {
			System.out.println("form mill");

			return true;
		}

		if (blockTwoSecondPhase(index)) {
			System.out.println("block two");

			return true;
		}

		if (formTwoSecondPhase(index)) {
			System.out.println("form two");

			return true;
		}

		return false;
	}

	private void moveThePiece() {

		for (int row = 0; row < Board.SIZE; ++row) {
			for (int col = 0; col < Board.SIZE; ++col) {
				if (board.grid[row][col] == Dot.HIGHLIGHTWHITE && board.currentTurn == Dot.WHITE) {
					move = board.indexOf(col, row);
					return;

				}
				if (board.grid[row][col] == Dot.HIGHLIGHTBLACK && board.currentTurn == Dot.BLACK) {
					move = board.indexOf(col, row);
					return;

				}
			}
		}
	}

	public void printTheBoard() {
		for (int row = 0; row < Board.SIZE; ++row) {
			for (int col = 0; col < Board.SIZE; ++col) {
				System.out.println(board.getDot(row, col) + " " + row + " " + col);
			}
		}
	}

	public void removePiece() {
		if (board.notInTheMillAvailible()) {
			for (int row = 0; row < Board.SIZE; ++row) {
				for (int col = 0; col < Board.SIZE; ++col) {
					if (board.grid[row][col] == Dot.WHITE && board.currentTurn == Dot.BLACK) {
						move = board.indexOf(col, row);
					}
					if (board.grid[row][col] == Dot.BLACK && board.currentTurn == Dot.WHITE) {
						move = board.indexOf(col, row);
					}
				}
			}
		} else {
			for (int row = 0; row < Board.SIZE; ++row) {
				for (int col = 0; col < Board.SIZE; ++col) {
					if (board.grid[row][col] == Dot.WHITE && board.currentTurn == Dot.BLACK
							|| board.grid[row][col] == Dot.WHITEMILL && board.currentTurn == Dot.BLACK) {
						move = board.indexOf(col, row);
					}
					if (board.grid[row][col] == Dot.BLACK && board.currentTurn == Dot.WHITE
							|| board.grid[row][col] == Dot.BLACKMILL && board.currentTurn == Dot.WHITE) {
						move = board.indexOf(col, row);
					}
				}
			}
		}
	}

	public boolean formMill() {

		boolean millcheck = false;

		for (int indexTo = 0; indexTo < board.positionOfCells.length; indexTo++) {
			for (int[] mill : board.millsArray) {
				List<Integer> millList = Arrays.stream(mill).boxed().collect(Collectors.toList());

				int[] dots = new int[4];
				if (checkedMills.contains(millList)) {
					millcheck = false;
				}
				if (millList.contains(indexTo)) {
					int i = 0;

					for (int neighbor : millList) {
						int row = board.positionOfCells[neighbor][0];
						int col = board.positionOfCells[neighbor][1];
						board.checkMill(col, row);

						if ((board.currentTurn == Dot.BLACK && board.getDot(col, row) == Dot.BLACK)
								|| (board.currentTurn == Dot.WHITE && board.getDot(col, row) == Dot.WHITE)) {
							{
								dots[i] = board.indexOf(row, col);
								i++;

								List<Integer> dotsList = Arrays.stream(dots).boxed().collect(Collectors.toList());

								if (i == 2) {
									millcheck = true;

									for (int neighborInMill : millList) {
										int rowE = board.positionOfCells[neighborInMill][0];
										int colE = board.positionOfCells[neighborInMill][1];

										if ((board.currentTurn == Dot.BLACK && board.getDot(colE, rowE) == Dot.WHITE)
												|| (board.currentTurn == Dot.WHITE
														&& board.getDot(colE, rowE) == Dot.BLACK)
												|| (board.currentTurn == Dot.BLACK
														&& board.getDot(colE, rowE) == Dot.WHITEMILL)
												|| (board.currentTurn == Dot.WHITE
														&& board.getDot(colE, rowE) == Dot.BLACKMILL)) {
											if (!checkedMills.contains(millList))
												checkedMills.add(millList);

											millcheck = false;
										}
									}
									for (int neighborInMill : millList) {
										if (!dotsList.contains(neighborInMill) && millcheck != false) {
											move = neighborInMill;

											return true;
										}
									}
								}

							}
						}

					}
				}
			}
		}
		return millcheck;

	}

	public boolean formMillSecondPhase(int indexTo) {

		boolean millcheck = false;
		boolean check = true;
		for (int[] mill : board.millsArray) {
			List<Integer> millList = Arrays.stream(mill).boxed().collect(Collectors.toList());

			int[] dots = new int[4];

			if (millList.contains(indexTo)) {
				int i = 0;
				check = true;
				for (int neighbor : millList) {
					if (!check) {
						break;
					}
					int row = board.positionOfCells[neighbor][0];
					int col = board.positionOfCells[neighbor][1];
					board.checkMill(col, row);

					if ((board.currentTurn == Dot.BLACK && board.getDot(col, row) == Dot.BLACK)
							|| (board.currentTurn == Dot.WHITE && board.getDot(col, row) == Dot.WHITE)) {
						{
							dots[i] = board.indexOf(row, col);
							i++;

							List<Integer> dotsList = Arrays.stream(dots).boxed().collect(Collectors.toList());

							if (i == 2) {
								millcheck = true;

								for (int neighborInMill : millList) {
									int rowE = board.positionOfCells[neighborInMill][0];
									int colE = board.positionOfCells[neighborInMill][1];

									if ((board.currentTurn == Dot.BLACK && board.getDot(colE, rowE) == Dot.WHITE)
											|| (board.currentTurn == Dot.WHITE && board.getDot(colE, rowE) == Dot.BLACK)
											|| (board.currentTurn == Dot.BLACK
													&& board.getDot(colE, rowE) == Dot.WHITEMILL)
											|| (board.currentTurn == Dot.WHITE
													&& board.getDot(colE, rowE) == Dot.BLACKMILL)) {
										//if (!checkedMills.contains(millList))
											// checkedMills.add(millList);

											millcheck = false;
									}
								}
								for (int neighborInMill : millList) {
									int rowE = board.positionOfCells[neighborInMill][0];
									int colE = board.positionOfCells[neighborInMill][1];
									if ((board.currentTurn == Dot.BLACK && board.getDot(colE, rowE) == Dot.WHITE)
											|| (board.currentTurn == Dot.WHITE && board.getDot(colE, rowE) == Dot.BLACK)
											|| (board.currentTurn == Dot.BLACK
													&& board.getDot(colE, rowE) == Dot.WHITEMILL)
											|| (board.currentTurn == Dot.WHITE
													&& board.getDot(colE, rowE) == Dot.BLACKMILL)) {
										check = false;
										break;
									}
									if(neighborInMill==move) {
										System.out.println(neighborInMill+" "+move+" "+dotsList.contains(neighborInMill)+" "+dotsList.contains(move)+" "+millcheck);

										continue;}
									if (!dotsList.contains(neighborInMill) && millcheck != false
											&& !dotsList.contains(move)&&check) {
										moveTo = neighborInMill;
										System.out.println(moveTo);
										return true;
									}
								}
							}

						}
					}

				}
			}
		}
		return false;

	}

	public boolean blockTwo() {

		boolean millcheck = false;
		for (int indexTo = 0; indexTo < board.positionOfCells.length; indexTo++) {

			for (int[] mill : board.millsArray) {

				List<Integer> millList = Arrays.stream(mill).boxed().collect(Collectors.toList());
				int[] dots = new int[4];
				if (closedMills.contains(millList)) {
					continue;
				}
				if (millList.contains(indexTo)) {
					int i = 0;

					for (int neighbor : millList) {
						int row = board.positionOfCells[neighbor][0];
						int col = board.positionOfCells[neighbor][1];

						if ((board.currentTurn == Dot.BLACK && board.getDot(col, row) == Dot.WHITE)
								|| (board.currentTurn == Dot.WHITE && board.getDot(col, row) == Dot.BLACK)) {

							i++;
							dots[i] = board.indexOf(row, col);
							List<Integer> dotsList = Arrays.stream(dots).boxed().collect(Collectors.toList());

							if (i == 2) {

								int j = 0;
								for (int neighborInMill : millList) {
									if (dotsList.contains(neighborInMill)) {
										j++;
									}

								}

								if (j == 2) {

									for (int neighborInMill : millList) {

										if (!dotsList.contains(neighborInMill)) {
											closedMills.add(millList);
											move = neighborInMill;

											return true;
										}
									}
								}
							}

						}

					}

				}
			}

		}
		return millcheck;
	}

	public boolean blockTwoSecondPhase(int indexTo) {

		boolean millcheck = false;

		for (int[] mill : board.millsArray) {

			List<Integer> millList = Arrays.stream(mill).boxed().collect(Collectors.toList());
			int[] dots = new int[4];
			if (closedMills.contains(millList)) {
				continue;
			}
			if (millList.contains(indexTo)) {
				int i = 0;

				for (int neighbor : millList) {
					int row = board.positionOfCells[neighbor][0];
					int col = board.positionOfCells[neighbor][1];

					if ((board.currentTurn == Dot.BLACK && board.getDot(col, row) == Dot.WHITE)
							|| (board.currentTurn == Dot.WHITE && board.getDot(col, row) == Dot.BLACK)) {

						i++;
						dots[i] = board.indexOf(row, col);
						List<Integer> dotsList = Arrays.stream(dots).boxed().collect(Collectors.toList());

						if (i == 2) {

							int j = 0;
							for (int neighborInMill : millList) {
								if (dotsList.contains(neighborInMill)) {
									j++;
								}

							}

							if (j == 2) {

								for (int neighborInMill : millList) {

									if (!dotsList.contains(neighborInMill)) {
										closedMills.add(millList);

										moveTo = neighborInMill;
										return true;
									}
								}
							}
						}

					}

				}

			}
		}

		return false;
	}

	public boolean formTwo() {
		boolean millcheck = false;

		for (int indexTo = 0; indexTo < board.positionOfCells.length; indexTo++) {
			for (int[] mill : board.millsArray) {
				List<Integer> millList = Arrays.stream(mill).boxed().collect(Collectors.toList());

				int[] dots = new int[4];
				if (checkedMills.contains(millList)) {
					millcheck = false;
				}
				if (millList.contains(indexTo)) {
					int i = 0;

					for (int neighbor : millList) {

						int row = board.positionOfCells[neighbor][0];
						int col = board.positionOfCells[neighbor][1];
						if ((board.currentTurn == Dot.BLACK && board.getDot(col, row) == Dot.BLACK)
								|| (board.currentTurn == Dot.WHITE && board.getDot(col, row) == Dot.WHITE)
								|| (board.currentTurn == Dot.BLACK && board.getDot(col, row) == Dot.BLACKMILL)
								|| (board.currentTurn == Dot.WHITE && board.getDot(col, row) == Dot.WHITEMILL)) {
							{
								dots[i] = board.indexOf(row, col);
								i++;

								List<Integer> dotsList = Arrays.stream(dots).boxed().collect(Collectors.toList());

								if (i == 1) {
									millcheck = true;

									for (int neighborInMill : millList) {
										int rowE = board.positionOfCells[neighborInMill][0];
										int colE = board.positionOfCells[neighborInMill][1];

										if ((board.currentTurn == Dot.BLACK && board.getDot(colE, rowE) == Dot.WHITE)
												|| (board.currentTurn == Dot.WHITE
														&& board.getDot(colE, rowE) == Dot.BLACK)
												|| (board.currentTurn == Dot.BLACK
														&& board.getDot(colE, rowE) == Dot.WHITEMILL)
												|| (board.currentTurn == Dot.WHITE
														&& board.getDot(colE, rowE) == Dot.BLACKMILL)) {
											if (!checkedMills.contains(millList))
												checkedMills.add(millList);

											millcheck = false;
										}
									}
									for (int neighborInMill : millList) {
										if (!dotsList.contains(neighborInMill) && millcheck != false) {
											move = neighborInMill;

											return true;
										}
									}
								}

							}
						}

					}
				}
			}
		}
		return millcheck;

	}

	public boolean formTwoSecondPhase(int indexTo) {
		boolean millcheck = false;

		for (int[] mill : board.millsArray) {
			List<Integer> millList = Arrays.stream(mill).boxed().collect(Collectors.toList());

			int[] dots = new int[4];
			if (checkedMills.contains(millList)) {
				millcheck = false;
			}
			if (millList.contains(indexTo)) {
				int i = 0;

				for (int neighbor : millList) {

					int row = board.positionOfCells[neighbor][0];
					int col = board.positionOfCells[neighbor][1];
					if ((board.currentTurn == Dot.BLACK && board.getDot(col, row) == Dot.BLACK)
							|| (board.currentTurn == Dot.WHITE && board.getDot(col, row) == Dot.WHITE)
							|| (board.currentTurn == Dot.BLACK && board.getDot(col, row) == Dot.BLACKMILL)
							|| (board.currentTurn == Dot.WHITE && board.getDot(col, row) == Dot.WHITEMILL)) {
						{
							dots[i] = board.indexOf(row, col);
							i++;

							List<Integer> dotsList = Arrays.stream(dots).boxed().collect(Collectors.toList());

							if (i == 1) {
								millcheck = true;

								for (int neighborInMill : millList) {
									int rowE = board.positionOfCells[neighborInMill][0];
									int colE = board.positionOfCells[neighborInMill][1];

									if ((board.currentTurn == Dot.BLACK && board.getDot(colE, rowE) == Dot.WHITE)
											|| (board.currentTurn == Dot.WHITE && board.getDot(colE, rowE) == Dot.BLACK)
											|| (board.currentTurn == Dot.BLACK
													&& board.getDot(colE, rowE) == Dot.WHITEMILL)
											|| (board.currentTurn == Dot.WHITE
													&& board.getDot(colE, rowE) == Dot.BLACKMILL)) {
										if (!checkedMills.contains(millList))
											checkedMills.add(millList);

										millcheck = false;
									}
								}
								for (int neighborInMill : millList) {
									if (!dotsList.contains(neighborInMill) && millcheck != false
											&& !dotsList.contains(move)) {
										moveTo = neighborInMill;

										return true;
									}
								}
							}

						}
					}

				}
			}
		}

		return false;

	}
}
