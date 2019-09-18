package sprint1.product;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import sprint1.product.Board;
import sprint1.product.Board.Dot;
import sprint1.product.Board.GameState;
import java.awt.event.MouseMotionAdapter;

@SuppressWarnings("serial")
public class GUI extends JFrame {


	public static final int CELL_SIZE = 100; // cell width and height (square)
	public static final int GRID_WIDTH = 8; // Grid-line's width
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
		setTitle("Nine Men's Morris");
		setVisible(true);
	}

	public Board getBoard() {
		return board;
	}

	private void setContentPane() {
		gameBoardCanvas = new GameBoardCanvas();
		CANVAS_WIDTH = CELL_SIZE * board.getTotalRows() + 100;
		CANVAS_HEIGHT = CELL_SIZE * board.getTotalColumns() + 100;
		gameBoardCanvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(gameBoardCanvas, BorderLayout.CENTER);
	}

	class GameBoardCanvas extends JPanel {

		GameBoardCanvas() {
			this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

			addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (board.getGameState() == GameState.PLAYING1) {
						int rowSelected = ((e.getY() + 10) / CELL_SIZE) - 1;
						int colSelected = ((e.getX() + 10) / CELL_SIZE) - 1;
						board.makeMoveFirstPhase(colSelected, rowSelected);

					} else if (board.getGameState() == GameState.PLAYING2a) {
						int rowSelected = ((e.getY() + 10) / CELL_SIZE) - 1;
						int colSelected = ((e.getX() + 10) / CELL_SIZE) - 1;
						board.makeMoveSecondPhaseA(colSelected, rowSelected);

					} else if (board.getGameState() == GameState.PLAYING2b) {
						int rowSelectedTo = ((e.getY() + 10) / CELL_SIZE) - 1;
						int colSelectedTo = ((e.getX() + 10) / CELL_SIZE) - 1;
						board.makeMoveSecondPhaseB(colSelectedTo, rowSelectedTo);

					} else { // game over
						board.initBoard(); // restart the game
					}
					repaint(); // Call-back paintComponent().
				}

			});

		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			setBackground(Color.CYAN);
			drawDots(g);
		}

		private void drawDots(Graphics g) {
			g.setColor(Color.BLACK);

			g.drawRect(100, 100, 600, 600);
			g.drawRect(200, 200, 400, 400);
			g.drawRect(300, 300, 200, 200);

			g.drawLine(400, 100, 400, 300);
			g.drawLine(100, 400, 300, 400);
			g.drawLine(400, 500, 400, 700);
			g.drawLine(500, 400, 700, 400);

			for (int row = 0; row < board.getTotalRows(); ++row) {
				for (int col = 0; col < board.getTotalColumns(); ++col) {
					if (board.getDot(row, col) == Dot.EMPTY) {
						g.setColor(Color.LIGHT_GRAY);

						g.fillOval(CELL_SIZE * (row + 1) - GRID_WIDHT_HALF, CELL_SIZE * (col + 1) - GRID_WIDHT_HALF, 10,
								10);
					}
					if (board.getDot(row, col) == Dot.WHITE && board.getGameState() == GameState.PLAYING1) {
						g.setColor(Color.WHITE);

						g.fillOval(CELL_SIZE * (row + 1) - GRID_WIDHT_HALF - 10,
								CELL_SIZE * (col + 1) - GRID_WIDHT_HALF - 10, 30, 30);
					}
					if (board.getDot(row, col) == Dot.BLACK && board.getGameState() == GameState.PLAYING1) {
						g.setColor(Color.BLACK);

						g.fillOval(CELL_SIZE * (row + 1) - GRID_WIDHT_HALF - 10,
								CELL_SIZE * (col + 1) - GRID_WIDHT_HALF - 10, 30, 30);
					}
					if (board.getDot(row, col) == Dot.GRAY) {
						g.setColor(Color.GRAY);

						g.fillOval(CELL_SIZE * (row + 1) - GRID_WIDHT_HALF - 10,
								CELL_SIZE * (col + 1) - GRID_WIDHT_HALF - 10, 30, 30);
					}
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
