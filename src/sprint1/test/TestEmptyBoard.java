package sprint1.test;

import static org.junit.Assert.*;

import static  org.hamcrest.CoreMatchers.*;

import org.junit.Test;

import sprint1.product.Board;
import sprint1.product.Dot;

public class TestEmptyBoard {
	private final Board board = new Board();

	@Test
	public void testNewBoard() {
		for (int row = 0; row != board.getTotalRows(); ++row) {
			for (int col = 0; col != board.getTotalColumns(); ++col) {
				assertThat(board.getDot(row, col), anyOf(is(Dot.EMPTY), is(Dot.NOTUSED)));
			}
		}
	}

	@Test
	public void testInvalidRow() {
		assertThat(board.getDot(board.getTotalRows(), 0), is(nullValue())); 
	}

	@Test
	public void testInvalidColumn() {
		assertThat(board.getDot(0, board.getTotalColumns()), is(nullValue())); 
	}
}
