package sprint1.product;

import sprint1.product.Board.Dot;

public class Board {
	 private static final int TOTALROWS = 7;
	   private static final int TOTALCOLUMNS = 7;
	   private Dot currentTurn;  

	   public enum Dot {
		      EMPTY, WHITE, BLACK, NOTUSED
		   }		   
	   private Dot[][] grid; 
	 
	   public Board(){
		   grid = new Dot[TOTALROWS][TOTALCOLUMNS]; 
		   initBoard(); 
	   }

	   public void initBoard() {
		   for (int row = 0; row < TOTALROWS; ++row) {
			   for (int col = 0; col < TOTALCOLUMNS; ++col) {
				   grid[row][col] = Dot.EMPTY; 
			   }
		   }
	   }
	   public void makeMove(int rowSelected, int colSelected){
		   if (rowSelected >= 0 && rowSelected < TOTALROWS && colSelected >= 0
				   && colSelected < TOTALCOLUMNS && grid[rowSelected][colSelected] == Dot.EMPTY) {
			   grid[rowSelected][colSelected] = currentTurn; 						   // Place token
			   currentTurn = (currentTurn == Dot.WHITE) ? Dot.BLACK : Dot.WHITE;   // change turn
		   }
	   }	  
	   public int getTotalRows(){
		   return TOTALROWS;
	   }
	   
	   public int getTotalColumns(){
		   return TOTALCOLUMNS;
	   }

	   public Dot getCell(int rowSelected, int colSelected){
		   if (rowSelected >= 0 && rowSelected < TOTALROWS && colSelected >= 0
				   && colSelected < TOTALCOLUMNS) {
			   return grid[rowSelected][colSelected];
		   } else {
			   return null;
		   }
	   }
}
