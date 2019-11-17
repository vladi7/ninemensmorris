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
			
				int move = rand.nextInt(24);
				boolean status = board.makeMoveSecondPhaseA(move);
				
			return status;

			
		}
		if (board.getGameState() == GameState.PLAYING2b1) {
			
				int move = rand.nextInt(24);
				boolean status = board.makeMoveSecondPhaseA(move);
			
			
			return status;

		}
		if (board.getGameState() == GameState.PLAYING3a) {
				int move = rand.nextInt(24);
				boolean status = board.makeMoveThirdPhase(move);
				
			return status;

		}
		return false;

	}

	public void moveDecider() {

		if (formMill()) {
			System.out.println("hello form mill");

			makeMove();
		} else if(blockTwo() ) {
			System.out.println("hello block two");
			
			makeMove();
		}
		else if(formTwo()) {
			System.out.println("hello form two");
			this.counterForm2++;
			makeMove();

		}
		else {
			System.out.println("hello random");
			while(true) {
			move = rand.nextInt(24);
			boolean status = makeMove();
			if(status) break;
			}
		}
	}

	public boolean formMill() {

		boolean millcheck = false;

		for (int indexTo = 0; indexTo < board.positionOfCells.length; indexTo++) {
			for (int[] mill : board.millsArray) {
				List<Integer> millList = Arrays.stream(mill).boxed().collect(Collectors.toList());

				int[] dots = new int[4];
				if(checkedMills.contains(millList)) {
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
								|| (board.currentTurn == Dot.WHITE && board.getDot(col, row) == Dot.WHITEMILL)) {{
							dots[i] = board.indexOf(row, col);
							i++;

							List<Integer> dotsList = Arrays.stream(dots).boxed().collect(Collectors.toList());

							if (i == 2) {
								millcheck=true;

								for (int neighborInMill : millList) {
									int rowE = board.positionOfCells[neighborInMill][0];
									int colE = board.positionOfCells[neighborInMill][1];
									
									if ((board.currentTurn == Dot.BLACK && board.getDot(colE, rowE) == Dot.WHITE)
											|| (board.currentTurn == Dot.WHITE && board.getDot(colE, rowE) == Dot.BLACK)
											|| (board.currentTurn == Dot.BLACK && board.getDot(colE, rowE) == Dot.WHITEMILL)
											|| (board.currentTurn == Dot.WHITE && board.getDot(colE, rowE) == Dot.BLACKMILL)) {
										if(!checkedMills.contains(millList))
										checkedMills.add(millList);

											millcheck = false;	
											}		
								}
								for (int neighborInMill : millList) {
									if (!dotsList.contains(neighborInMill)&&millcheck!=false) {
										System.out.println(dotsList);
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
	

	public boolean blockTwo() {
	
		boolean millcheck = false;
		for (int indexTo = 0; indexTo < board.positionOfCells.length; indexTo++) {
			
			
			for (int[] mill : board.millsArray) {

				List<Integer> millList = Arrays.stream(mill).boxed().collect(Collectors.toList());
				int[] dots = new int[4];
				if(closedMills.contains(millList)) {
					continue;
				}
				if (millList.contains(indexTo)) {
					int i = 0;

					for (int neighbor : millList) {
						int row = board.positionOfCells[neighbor][0];
						int col = board.positionOfCells[neighbor][1];

						if ((board.currentTurn == Dot.BLACK && board.getDot(col, row) == Dot.WHITE)
								|| (board.currentTurn == Dot.WHITE && board.getDot(col, row) == Dot.BLACK)
								|| (board.currentTurn == Dot.BLACK && board.getDot(col, row) == Dot.WHITEMILL)
								|| (board.currentTurn == Dot.WHITE && board.getDot(col, row) == Dot.BLACKMILL)) {

							i++;
							dots[i] = board.indexOf(row,col);
							List<Integer> dotsList = Arrays.stream(dots).boxed().collect(Collectors.toList());

							if (i == 2) {

								int j = 0;
								for (int neighborInMill : millList) {
									if(dotsList.contains(neighborInMill))
									{
										j++;
									}
									
								}

								if(j == 2) {

								for (int neighborInMill : millList) {

								if (!dotsList.contains(neighborInMill)) {
									closedMills.add(millList);

									move = neighborInMill;
									return true;
								}
								}}
							}

						}
						
					}

				}
			}

		}
		return millcheck;
	}

	public boolean formTwo() {
		boolean millcheck = false;

		for (int indexTo = 0; indexTo < board.positionOfCells.length; indexTo++) {
			for (int[] mill : board.millsArray) {
				List<Integer> millList = Arrays.stream(mill).boxed().collect(Collectors.toList());

				int[] dots = new int[4];
				if(checkedMills.contains(millList)) {
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
								|| (board.currentTurn == Dot.WHITE && board.getDot(col, row) == Dot.WHITEMILL)) {{
							dots[i] = board.indexOf(row, col);
							i++;

							List<Integer> dotsList = Arrays.stream(dots).boxed().collect(Collectors.toList());

							if (i == 1) {
								millcheck=true;

								for (int neighborInMill : millList) {
									int rowE = board.positionOfCells[neighborInMill][0];
									int colE = board.positionOfCells[neighborInMill][1];
									
									if ((board.currentTurn == Dot.BLACK && board.getDot(colE, rowE) == Dot.WHITE)
											|| (board.currentTurn == Dot.WHITE && board.getDot(colE, rowE) == Dot.BLACK)
											|| (board.currentTurn == Dot.BLACK && board.getDot(colE, rowE) == Dot.WHITEMILL)
											|| (board.currentTurn == Dot.WHITE && board.getDot(colE, rowE) == Dot.BLACKMILL)) {
										if(!checkedMills.contains(millList))
										checkedMills.add(millList);

											millcheck = false;	
											}		
								}
								for (int neighborInMill : millList) {
									if (!dotsList.contains(neighborInMill)&&millcheck!=false) {
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

}}
