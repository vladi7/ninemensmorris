package sprint1.product;

import java.awt.*;

import javax.swing.*;

import sprint1.product.Board;

@SuppressWarnings("serial")
public class GUI extends JFrame {

	public static final int CELL_SIZE = 100; // cell width and height (square)
	public static final int GRID_WIDTH = 8;                   // Grid-line's width
	public static final int GRID_WIDHT_HALF = GRID_WIDTH / 2; // Grid-line's half-width

	// Symbols (black/nought) are displayed inside a cell, with padding from border
	public static final int CELL_PADDING = CELL_SIZE / 6;
	public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2; // width/height
	public static final int SYMBOL_STROKE_WIDTH = 8; // pen's stroke width

	private int CANVAS_WIDTH;
	private int CANVAS_HEIGHT;

	private GameBoardCanvas gameBoardCanvas; 

	private Board board;

	public GUI(Board board) {
		this.board = board;
		setContentPane();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack(); 
		setTitle("Nine Mens Morris");
		setVisible(true);  
	}
	
	public Board getBoard(){
		return board;
	}

	private void setContentPane(){
		gameBoardCanvas = new GameBoardCanvas();  
		CANVAS_WIDTH = CELL_SIZE * board.getTotalRows() + 100;  
		CANVAS_HEIGHT = CELL_SIZE * board.getTotalColumns() + 100;
		gameBoardCanvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(gameBoardCanvas, BorderLayout.CENTER);
	}

	class GameBoardCanvas extends JPanel {
		
		GameBoardCanvas(){

		}
		
		@Override
		public void paintComponent(Graphics g) { 
			super.paintComponent(g);   
			setBackground(Color.WHITE);
			drawDots(g);
		}
		
		private void drawDots(Graphics g){
			g.setColor(Color.BLACK);

			g.drawRect(100, 100, 600, 600);
			g.drawRect(200, 200, 400, 400);
			g.drawRect(300, 300, 200, 200);
			
			g.drawLine(400, 100, 400, 300);
			g.drawLine(100, 400, 300, 400);
			g.drawLine(400, 500, 400, 700);
			g.drawLine(500, 400, 700, 400);
			
			g.setColor(Color.LIGHT_GRAY);
			for (int row = 1; row < board.getTotalRows() + 1; ++row) {
				for (int col = 1; col < board.getTotalColumns() +1 ; ++col) {
					g.fillOval(CELL_SIZE * row - GRID_WIDHT_HALF,CELL_SIZE * col - GRID_WIDHT_HALF,10,10);
				}
			}

		}

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new GUI(new Board()); 
			}
		});
	}
}
