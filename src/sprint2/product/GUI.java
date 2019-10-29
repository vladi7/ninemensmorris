package sprint2.product;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import sprint2.product.Board;
import sprint2.product.Dot;
import sprint2.product.GameState;

@SuppressWarnings("serial")
public class GUI extends JFrame {

	public static final int CELL_SIZE = 100; // cell width and height (square)
	public static final int GRID_WIDTH = 8; // Grid-line's width
	public static final int GRID_WIDHT_HALF = GRID_WIDTH / 2; // Grid-line's half-width

	public static final int CELL_PADDING = CELL_SIZE / 6;
	public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2; // width/height
	public static final int SYMBOL_STROKE_WIDTH = 8; // pen's stroke width

	private int CANVAS_WIDTH;
	private int CANVAS_HEIGHT;
	private JLabel gameStatusBar;
	JButton restartChange;
	JButton resignChange;

	private int moveFromCol;
	private int moveFromRow;

	private GameBoardCanvas gameBoardCanvas;
	private Board board;
	
	/**
	 * Creates a GUI for the board given
	 * @param board the given board
	 */
	public GUI(Board board) {
		this.board = board;
		setContentPane();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setTitle("Nine Men's Morris");
		setVisible(true);
	}

	/**
	 * getter for the board
	 * @return board
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 *  Sets content pane
	 */
	private void setContentPane() {
		gameBoardCanvas = new GameBoardCanvas();
		CANVAS_WIDTH = CELL_SIZE * board.getTotalRows() + 100;
		CANVAS_HEIGHT = CELL_SIZE * board.getTotalColumns() + 100;
		gameBoardCanvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
		gameStatusBar = new JLabel("  ");
		gameStatusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 15));
		gameStatusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(gameBoardCanvas, BorderLayout.CENTER);
		contentPane.add(gameStatusBar, BorderLayout.BEFORE_FIRST_LINE);
	}

	/**
	 * Class that build GUI
	 *
	 */
	class GameBoardCanvas extends JPanel {

		/**
		 * Constructor for the class GameBoardCanvas.
		 * Initializes the buttons
		 */
		GameBoardCanvas() {
			this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

			addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {

					if (board.getGameState() == GameState.PLAYING1) {// placement phase no check moves
						int[] point = new int[2];
					     int col = ((e.getY() + 10) / CELL_SIZE) - 1;
						 int row = ((e.getX() + 10) / CELL_SIZE) - 1;
						 int dotNum = board.indexOf(col, row);
						board.makeMoveFirstPhase(dotNum);

					} else if (board.getGameState() == GameState.PLAYING2a) {// selecting the piece to place in the next
																				// phase(a) or change it in the next
																				// phase(b)
						 int col = ((e.getY() + 10) / CELL_SIZE) - 1;
						 int row = ((e.getX() + 10) / CELL_SIZE) - 1;
						 int dotNum = board.indexOf(col, row);
						moveFromRow = row;
						moveFromCol = col;

						board.makeMoveSecondPhaseA(dotNum);

					} else if (board.getGameState() == GameState.PLAYING2b1) {// after selecting a piece in phase 2
																				// place the piece
						int colSelectedTo = ((e.getY() + 10) / CELL_SIZE) - 1;
						int rowSelectedTo = ((e.getX() + 10) / CELL_SIZE) - 1;

						board.makeMoveSecondPhaseB(moveFromRow,moveFromCol , rowSelectedTo,colSelectedTo);

					} else if (board.getGameState() == GameState.PLAYING3a
							|| board.getGameState() == GameState.PLAYING3b) {
						int colSelectedTo = ((e.getY() + 10) / CELL_SIZE) - 1;
						int  rowSelectedTo= ((e.getX() + 10) / CELL_SIZE) - 1;
						board.makeMoveThirdPhase(rowSelectedTo, colSelectedTo);

					}
					repaint();
				}

			});

			restartChange = new JButton("Start New Game");
			resignChange = new JButton("Resign");
			add(restartChange);
			add(resignChange);
			resignChange.setEnabled(false);
			restartChange.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					resignChange.setEnabled(true);
					board.reset();
					repaint();
					if (board.getGameState() == GameState.START) {
						board.setGameState(GameState.PLAYING1);
					}
				}
			});
			resignChange.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (board.getCurrentTurn() == Dot.BLACK) {
						board.setGameState(GameState.WHITE_WON);
						resignChange.setEnabled(false);

					}
					if (board.getCurrentTurn() == Dot.WHITE) {
						board.setGameState(GameState.BLACK_WON);
						resignChange.setEnabled(false);
					}

					repaint();
				}
			});

		}
		
		/**
		 * Sets background color. It sets it to another color when the mill is formed
		 */
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			setBackground(Color.CYAN);
			if (board.getGameState() == GameState.PLAYING3a || board.getGameState() == GameState.PLAYING3b) {
				setBackground(Color.PINK);

			}
			drawDotsLettersNumbers(g);
			printStatusBar();

		}

		/**
		 * The method is used to draw dots, letters, and numbers
		 * @param g Graphics
		 */
		private void drawDotsLettersNumbers(Graphics g) {
			g.setColor(Color.BLACK);
			g.drawString("A", 97, 750);
			g.drawString("B", 197, 750);
			g.drawString("C", 297, 750);
			g.drawString("D", 397, 750);
			g.drawString("E", 497, 750);
			g.drawString("F", 597, 750);
			g.drawString("H", 697, 750);
			g.drawString("1", 50, 705);
			g.drawString("2", 50, 605);
			g.drawString("3", 50, 505);
			g.drawString("4", 50, 405);
			g.drawString("5", 50, 305);
			g.drawString("6", 50, 205);
			g.drawString("7", 50, 105);
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

						g.fillOval(CELL_SIZE * (row + 1) - GRID_WIDHT_HALF - 5,
								CELL_SIZE * (col + 1) - GRID_WIDHT_HALF - 5, 20, 20);
					}
					if (board.getDot(row, col) == Dot.WHITE) {
						g.setColor(Color.WHITE);

						g.fillOval(CELL_SIZE * (row + 1) - GRID_WIDHT_HALF - 15,
								CELL_SIZE * (col + 1) - GRID_WIDHT_HALF - 15, 40, 40);
					}
					if (board.getDot(row, col) == Dot.BLACK) {
						g.setColor(Color.BLACK);

						g.fillOval(CELL_SIZE * (row + 1) - GRID_WIDHT_HALF - 15,
								CELL_SIZE * (col + 1) - GRID_WIDHT_HALF - 15, 40, 40);
					}
					if (board.getDot(row, col) == Dot.GRAY && (board.getGameState() == GameState.PLAYING2b1
							|| board.getGameState() == GameState.PLAYING2a
							|| board.getGameState() == GameState.PLAYING2b2
							|| board.getGameState() == GameState.WHITE_WON
							|| board.getGameState() == GameState.BLACK_WON)) {
						g.setColor(Color.GRAY);

						g.fillOval(CELL_SIZE * (row + 1) - GRID_WIDHT_HALF - 15,
								CELL_SIZE * (col + 1) - GRID_WIDHT_HALF - 15, 40, 40);
					}
					if (board.getDot(row, col) == Dot.WHITEMILL) {
						g.setColor(Color.WHITE);

						g.fillOval(CELL_SIZE * (row + 1) - GRID_WIDHT_HALF - 15,
								CELL_SIZE * (col + 1) - GRID_WIDHT_HALF - 15, 40, 40);
						g.setColor(Color.RED);

						g.fillOval(CELL_SIZE * (row + 1) - GRID_WIDHT_HALF - 5,
								CELL_SIZE * (col + 1) - GRID_WIDHT_HALF - 5, 20, 20);
					}
					if (board.getDot(row, col) == Dot.BLACKMILL) {
						g.setColor(Color.BLACK);

						g.fillOval(CELL_SIZE * (row + 1) - GRID_WIDHT_HALF - 15,
								CELL_SIZE * (col + 1) - GRID_WIDHT_HALF - 15, 40, 40);
						g.setColor(Color.RED);

						g.fillOval(CELL_SIZE * (row + 1) - GRID_WIDHT_HALF - 5,
								CELL_SIZE * (col + 1) - GRID_WIDHT_HALF - 5, 20, 20);
					}
					if (board.getDot(row, col) == Dot.HIGHLIGHT) {
						g.setColor(Color.ORANGE);

						g.fillOval(CELL_SIZE * (row + 1) - GRID_WIDHT_HALF - 5,
								CELL_SIZE * (col + 1) - GRID_WIDHT_HALF - 5, 20, 20);
					}
				}
			}

		}

	}

	private void printStatusBar() {
		if (board.getGameState() == GameState.PLAYING1 && board.getCurrentTurn() == Dot.WHITE) {
			gameStatusBar.setForeground(Color.BLACK);
			gameStatusBar.setText("Placement Phase. WHITE Moves. " + board.getNumWhitePiecesFirstPhase()
					+ " White Pieces Left; " + board.getNumBlackPiecesFirstPhase() + " Black Pieces Left");

		} else if (board.getGameState() == GameState.PLAYING1 && board.getCurrentTurn() == Dot.BLACK) {
			gameStatusBar.setForeground(Color.BLACK);
			gameStatusBar.setText("Placement Phase. BLACK Moves. " + board.getNumWhitePiecesFirstPhase()
					+ " White Pieces Left; " + board.getNumBlackPiecesFirstPhase() + " Black Pieces Left");

		} else if (board.getGameState() == GameState.PLAYING2a && board.getCurrentTurn() == Dot.WHITE) {
			gameStatusBar.setForeground(Color.BLACK);
			gameStatusBar.setText("Moving Phase. Pick a Chip To Move. WHITE Moves. Note that the First-Touch rule is used!");
		} else if (board.getGameState() == GameState.PLAYING2a && board.getCurrentTurn() == Dot.BLACK) {
			gameStatusBar.setForeground(Color.BLACK);
			gameStatusBar.setText("Moving Phase. Pick a Chip To Move. BLACK Moves. Note that the First-Touch rule is used!");

		} else if (board.getGameState() == GameState.PLAYING2b1) {
			gameStatusBar.setForeground(Color.BLACK);
			gameStatusBar.setText("Moving Phase. Pick a Place To Move The Chip Or Pick Another Chip");
		} else if (board.getGameState() == GameState.PLAYING3a || board.getGameState() == GameState.PLAYING3b) {
			gameStatusBar.setForeground(Color.RED);
			gameStatusBar.setText("MILL, Please Remove The Piece Of Opposite Player");
		} else if (board.getGameState() == GameState.BLACK_WON) {
			gameStatusBar.setForeground(Color.RED);
			gameStatusBar.setText("Black Won");
		} else if (board.getGameState() == GameState.WHITE_WON) {
			gameStatusBar.setForeground(Color.RED);
			gameStatusBar.setText("White Won");
		} else if (board.getGameState() == GameState.DRAW) {
			gameStatusBar.setForeground(Color.RED);
			gameStatusBar.setText("Draw");
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
