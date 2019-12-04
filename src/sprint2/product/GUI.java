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
	JButton vsHuman;
	JButton vsAI;
	JButton white;
	JButton black; 

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
					     int row = ((e.getY() + 10) / CELL_SIZE) - 1;
						 int col = ((e.getX() + 10) / CELL_SIZE) - 1;
						 int dotNum = board.indexOf(row,col);
						board.makeMoveFirstPhase(dotNum);
						

					} else if (board.getGameState() == GameState.PLAYING2a) {// selecting the piece to place in the next
																				// phase(a) or change it in the next
																				// phase(b)
						 int row = ((e.getY() + 10) / CELL_SIZE) - 1;
						 int col = ((e.getX() + 10) / CELL_SIZE) - 1;
						 int dotNum = board.indexOf(row, col);
						moveFromRow = row;
						moveFromCol = col;

						board.makeMoveSecondPhaseA(dotNum);

					} else if (board.getGameState() == GameState.PLAYING2b1) {// after selecting a piece in phase 2
																				// place the piece
						int rowSelectedTo = ((e.getY() + 10) / CELL_SIZE) - 1;
						int colSelectedTo = ((e.getX() + 10) / CELL_SIZE) - 1;
						 int dotFrom = board.indexOf(moveFromRow,moveFromCol);
						 int dotTo = board.indexOf(rowSelectedTo, colSelectedTo);
						board.makeMoveSecondPhaseB(dotFrom, dotTo);

					} else if (board.getGameState() == GameState.PLAYING3a
							|| board.getGameState() == GameState.PLAYING3b) {
						int  rowSelectedTo= ((e.getY() + 10) / CELL_SIZE) - 1;
						int  colSelectedTo= ((e.getX() + 10) / CELL_SIZE) - 1;
						int dotTo = board.indexOf(rowSelectedTo,colSelectedTo );
						
						board.makeMoveThirdPhase(dotTo);

					}
					repaint();
					
				}

			});

			restartChange = new JButton("Start New Game");
			add(restartChange);
			
			resignChange = new JButton("Resign");
			add(resignChange);
			resignChange.setEnabled(false);
			
			vsHuman = new JButton("Player vs Player");
			add(vsHuman);
			vsHuman.setVisible(false);
			
			vsAI = new JButton("Player vs Computer");
			add(vsAI);
			vsAI.setVisible(false);
			
			white = new JButton("White");
			add(white);
			white.setVisible(false);
			
			black = new JButton("Black");
			add(black);
			black.setVisible(false);
			
			restartChange.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					resignChange.setEnabled(true);
					vsHuman.setVisible(true);
					vsAI.setVisible(true);
					resignChange.setEnabled(false);
					restartChange.setEnabled(false);
					restartChange.setVisible(false);
					resignChange.setVisible(false);
					board.reset();
					board.setGameState(GameState.CHOOSEOPPONENT);
					repaint();
					vsHuman.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {

							board.setCurrentGameRegime(GameRegime.P1vP2);
							vsHuman.setVisible(false);
							vsAI.setVisible(false);
							white.setVisible(true);
							black.setVisible(true);
							board.setGameState(GameState.CHOOSECOLOR);

						}
					});
					vsAI.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							restartChange.setVisible(false);
							resignChange.setVisible(false);

							board.setCurrentGameRegime(GameRegime.PvsAI);
							vsHuman.setVisible(false);
							vsAI.setVisible(false);
							white.setVisible(true);
							black.setVisible(true);
							board.setGameState(GameState.CHOOSECOLOR);

						}
					});
					
					white.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							restartChange.setVisible(true);
							resignChange.setVisible(true);
						resignChange.setEnabled(true);
						restartChange.setEnabled(true);
						white.setVisible(false);
						black.setVisible(false);
						board.setCurrentTurn(Dot.WHITE);
						board.setAiPlayer(Dot.BLACK);

						board.setGameState(GameState.PLAYING1);
						}
					});
					
					black.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							restartChange.setVisible(true);
							resignChange.setVisible(true);
						resignChange.setEnabled(true);
						restartChange.setEnabled(true);
						white.setVisible(false);
						black.setVisible(false);
						board.setCurrentTurn(Dot.BLACK);
						board.setAiPlayer(Dot.WHITE);

						board.setGameState(GameState.PLAYING1);
						}
					});
	
					//board.reset();
					repaint();
				
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
			int i = 0;
			for (int row = 0; row < board.getTotalRows(); ++row) {
				for (int col = 0; col < board.getTotalColumns(); ++col) {

					
					if (board.getDot(row, col) == Dot.EMPTY) {
						g.setColor(Color.LIGHT_GRAY);
						
						g.fillOval(CELL_SIZE * (row + 1) - GRID_WIDHT_HALF - 5,
								CELL_SIZE * (col + 1) - GRID_WIDHT_HALF - 5, 20, 20);
						g.setColor(Color.BLACK);

						g.drawString(Integer.toString(i), CELL_SIZE * (col + 1) - GRID_WIDHT_HALF - 5,CELL_SIZE * (row + 1) - GRID_WIDHT_HALF - 5);
						i++;
					}
					if (board.getDot(row, col) == Dot.WHITE) {
						g.setColor(Color.WHITE);

						g.fillOval(CELL_SIZE * (row + 1) - GRID_WIDHT_HALF - 15,
								CELL_SIZE * (col + 1) - GRID_WIDHT_HALF - 15, 40, 40);
						g.setColor(Color.BLACK);

						g.drawString(Integer.toString(i), CELL_SIZE * (col + 1) - GRID_WIDHT_HALF - 5,CELL_SIZE * (row + 1) - GRID_WIDHT_HALF - 5);
						i++;
					}
					if (board.getDot(row, col) == Dot.BLACK) {
						g.setColor(Color.BLACK);

						g.fillOval(CELL_SIZE * (row + 1) - GRID_WIDHT_HALF - 15,
								CELL_SIZE * (col + 1) - GRID_WIDHT_HALF - 15, 40, 40);
						g.setColor(Color.BLACK);

						g.drawString(Integer.toString(i), CELL_SIZE * (col + 1) - GRID_WIDHT_HALF - 5,CELL_SIZE * (row + 1) - GRID_WIDHT_HALF - 5);
						i++;
					}
					if (board.getDot(row, col) == Dot.GRAY && (board.getGameState() == GameState.PLAYING2b1
							|| board.getGameState() == GameState.PLAYING2a
							|| board.getGameState() == GameState.WHITE_WON
							|| board.getGameState() == GameState.BLACK_WON)) {
						g.setColor(Color.GRAY);

						g.fillOval(CELL_SIZE * (row + 1) - GRID_WIDHT_HALF - 15,
								CELL_SIZE * (col + 1) - GRID_WIDHT_HALF - 15, 40, 40);
						g.setColor(Color.BLACK);

						g.drawString(Integer.toString(i), CELL_SIZE * (col + 1) - GRID_WIDHT_HALF - 5,CELL_SIZE * (row + 1) - GRID_WIDHT_HALF - 5);
						i++;
					}
					if (board.getDot(row, col) == Dot.WHITEMILL) {
						g.setColor(Color.WHITE);

						g.fillOval(CELL_SIZE * (row + 1) - GRID_WIDHT_HALF - 15,
								CELL_SIZE * (col + 1) - GRID_WIDHT_HALF - 15, 40, 40);
						g.setColor(Color.RED);

						g.fillOval(CELL_SIZE * (row + 1) - GRID_WIDHT_HALF - 5,
								CELL_SIZE * (col + 1) - GRID_WIDHT_HALF - 5, 20, 20);
						g.setColor(Color.BLACK);

						g.drawString(Integer.toString(i), CELL_SIZE * (col + 1) - GRID_WIDHT_HALF - 5,CELL_SIZE * (row + 1) - GRID_WIDHT_HALF - 5);
						i++;
					}
					if (board.getDot(row, col) == Dot.BLACKMILL) {
						g.setColor(Color.BLACK);

						g.fillOval(CELL_SIZE * (row + 1) - GRID_WIDHT_HALF - 15,
								CELL_SIZE * (col + 1) - GRID_WIDHT_HALF - 15, 40, 40);
						g.setColor(Color.RED);

						g.fillOval(CELL_SIZE * (row + 1) - GRID_WIDHT_HALF - 5,
								CELL_SIZE * (col + 1) - GRID_WIDHT_HALF - 5, 20, 20);
						g.setColor(Color.BLACK);

						g.drawString(Integer.toString(i), CELL_SIZE * (col + 1) - GRID_WIDHT_HALF - 5,CELL_SIZE * (row + 1) - GRID_WIDHT_HALF - 5);
						i++;
					}
					if (board.getDot(row, col) == Dot.HIGHLIGHTWHITE||board.getDot(row, col) == Dot.HIGHLIGHTBLACK) {
						g.setColor(Color.ORANGE);

						g.fillOval(CELL_SIZE * (row + 1) - GRID_WIDHT_HALF - 5,
								CELL_SIZE * (col + 1) - GRID_WIDHT_HALF - 5, 20, 20);
						g.setColor(Color.BLACK);

						g.drawString(Integer.toString(i), CELL_SIZE * (col + 1) - GRID_WIDHT_HALF - 5,CELL_SIZE * (row + 1) - GRID_WIDHT_HALF - 5);
						i++;
					}
				}
			}

		}

	}
	/**
	 * This method is used to print the status of the game in the status bar.
	 * */
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
			gameStatusBar.setText("Moving Phase. Pick a Place To Move The Chip. Note that the First-Touch rule is used!");
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
			resignChange.setEnabled(false);

			gameStatusBar.setForeground(Color.RED);
			gameStatusBar.setText("Draw");
		}
		else if (board.getGameState() == GameState.START) {

			gameStatusBar.setForeground(Color.BLACK);
			gameStatusBar.setText("Welcome to Nine Men's Morris");
		}
		else if (board.getGameState() == GameState.CHOOSEOPPONENT) {

			gameStatusBar.setForeground(Color.BLACK);
			gameStatusBar.setText("Pick the regime");
		}
		
		else if (board.getGameState() == GameState.CHOOSECOLOR) {
			gameStatusBar.setForeground(Color.BLACK);
			gameStatusBar.setText("Choose Your Side");
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
