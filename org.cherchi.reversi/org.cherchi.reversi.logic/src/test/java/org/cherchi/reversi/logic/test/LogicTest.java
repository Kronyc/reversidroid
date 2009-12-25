package org.cherchi.reversi.logic.test;

import static org.junit.Assert.*;


import org.cherchi.reversi.logic.GameLogic;
import org.cherchi.reversi.logic.internal.GameLogicImpl;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test of the logic
 * 
 * @author Fernando Cherchi
 * 
 */
public class LogicTest {

	/**
	 * This is the object to test
	 */
	GameLogic game = null;

	/**
	 * initializing
	 * 
	 */
	@Before
	public void beforeTest() {
		game = new GameLogicImpl();
	}

	/**
	 * Tests that cannot set a chip in a busy cell
	 */
	@Test
	public void testCannotSetWhenCellIsBusy() {

		// player one sets a chip into 2,2
		game.setChip(GameLogicImpl.PLAYER_ONE, 2, 2);
		assertFalse("Should not put a chip in a busy cell", game.canSet(
				GameLogicImpl.PLAYER_TWO, 2, 2));
	}

	/**
	 * Test in the upwards direction if a chip can be put
	 */
	@Test
	public void testCanSetIfOwnChipUpwards() {
		game.setChip(GameLogicImpl.PLAYER_ONE, 1, 0);
		game.setChip(GameLogicImpl.PLAYER_TWO, 1, 1);
		assertTrue("Should be able to put a chip if encloses an opponent chip",
				game.canSet(GameLogicImpl.PLAYER_ONE, 1, 2));
		//but if there is an empty space, should not be able to put
		game.setChip(GameLogicImpl.EMPTY, 1, 1);
		assertFalse("Should be able to put a chip if encloses an opponent chip after empty spaces",
				game.canSet(GameLogicImpl.PLAYER_ONE, 1, 2));
	}

	/**
	 * Test in the upwards direction if a chip can be put
	 */
	@Test
	public void testCannotSetIfNoOwnChipUpwards() {
		game.setChip(GameLogicImpl.PLAYER_TWO, 1, 1);
		assertFalse(
				"Should not be able to put a chip if encloses an opponent chip",
				game.canSet(GameLogicImpl.PLAYER_ONE, 1, 2));
	}

	/**
	 * Test in the downwards direction if a chip can be put
	 */
	@Test
	public void testCanSetIfOwnChipDownwards() {
		game.setChip(GameLogicImpl.PLAYER_ONE, 1, 3);
		game.setChip(GameLogicImpl.PLAYER_TWO, 1, 2);
		game.setChip(GameLogicImpl.PLAYER_TWO, 1, 1);
		assertTrue("Should be able to put a chip if encloses an opponent chip",
				game.canSet(GameLogicImpl.PLAYER_ONE, 1, 0));
		
		//but if there is an empty space, should not be able to put
		game.setChip(GameLogicImpl.EMPTY, 1, 2);
		assertFalse("Should be able to put a chip if encloses an opponent chip after empty spaces",
				game.canSet(GameLogicImpl.PLAYER_ONE, 1, 0));
		
	}

	/**
	 * Test in the downwards direction if a chip can be put
	 */
	@Test
	public void testCanSetIfOwnChipRight() {
		game.setChip(GameLogicImpl.PLAYER_ONE, 7, 1);
		game.setChip(GameLogicImpl.PLAYER_TWO, 6, 1);
		game.setChip(GameLogicImpl.PLAYER_TWO, 5, 1);
		
		assertTrue("Should be able to put a chip if encloses an opponent chip",
				game.canSet(GameLogicImpl.PLAYER_ONE, 4, 1));
		
		//but if there is an empty space, should not be able to put
		game.setChip(GameLogicImpl.EMPTY, 5, 1);
		assertFalse("Should not be able to put a chip if encloses an opponent chip after empty spaces",
				game.canSet(GameLogicImpl.PLAYER_ONE, 4, 1));
		
	}
	
	/**
	 * Test in the downwards direction if a chip can be put
	 */
	@Test
	public void testConquerPositions() {
		game.setChip(GameLogicImpl.PLAYER_ONE, 7, 2);
		game.setChip(GameLogicImpl.PLAYER_TWO, 6, 2);
		game.setChip(GameLogicImpl.PLAYER_TWO, 5, 2);
		game.conquerPosition(GameLogicImpl.PLAYER_ONE, 4, 2);
		assertEquals("Position 5 in row 2 should change color",
				GameLogicImpl.PLAYER_ONE, game.getGameMatrix()[5][2]);
		assertEquals("Position 6 in row 2 should change color",
				GameLogicImpl.PLAYER_ONE, game.getGameMatrix()[5][2]);
	}

	/**
	 * test the allowed positions are well calculated
	 */
	@Test
	public void testAllowedPositions() {
		
		//playing with initial positions
		int[][] allowed = game
				.getAllowedPositionsForPlayer(GameLogicImpl.PLAYER_ONE);

		assertEquals("position 5, 3 should be allowed",
				GameLogicImpl.PLAYER_ONE, allowed[5][3]);
		assertEquals("position 2, 4 should be allowed",
				GameLogicImpl.PLAYER_ONE, allowed[2][4]);
		assertEquals("position 4, 2 should be allowed",
				GameLogicImpl.PLAYER_ONE, allowed[4][2]);
		assertEquals("position 3, 5 should be allowed",
				GameLogicImpl.PLAYER_ONE, allowed[3][5]);

		boolean otherAllowed = false;
		// scanning the grid
		for (int col = 0; col < GameLogic.COLS; col++) {
			for (int row = 0; row < GameLogic.ROWS; row++) {
				// if other is allowed then fails
				if (allowed[col][row] == GameLogicImpl.PLAYER_ONE) {
					if (!(col == 2 && row == 4 || col == 5 && row == 3) &&
							(col == 4 && row == 2) && (col == 2 && row == 5)) {
						otherAllowed = true;
					}
				}
			}
		}
		assertFalse("should not be more allowed places", otherAllowed);

	}

	/**
	 * Test in the downwards direction if a chip can be put
	 */
	@Test
	public void testCanSetIfOwnChipLeft() {
		game.setChip(GameLogicImpl.PLAYER_ONE, 4, 4);
		game.setChip(GameLogicImpl.PLAYER_TWO, 5, 4);
		game.setChip(GameLogicImpl.PLAYER_TWO, 6, 4);
		assertTrue("Should be able to put a chip if encloses an opponent chip",
				game.canSet(GameLogicImpl.PLAYER_ONE, 7, 4));
		//but if there is an empty space, should not be able to put
		game.setChip(GameLogicImpl.EMPTY, 5, 4);
		assertFalse("Should be able to put a chip if encloses an opponent chip after empty spaces",
				game.canSet(GameLogicImpl.PLAYER_ONE, 7, 4));
	}
	
	/**
	 * Test in the left-up direction if a chip can be put
	 */
	@Test
	public void testCanSetIfOwnChipLeftUp() {
		game.setChip(GameLogicImpl.PLAYER_TWO, 2, 2);
		assertTrue("Should be able to put a chip if encloses an opponent chip",
				game.canSet(GameLogicImpl.PLAYER_TWO, 5, 5));

		//but if there is an empty space, should not be able to put
		game.setChip(GameLogicImpl.EMPTY, 4, 4);
		assertFalse("Should be able to put a chip if encloses an opponent chip after empty spaces",
				game.canSet(GameLogicImpl.PLAYER_TWO, 5, 5));
	}
	
	/**
	 * Test in the left-down direction if a chip can be put
	 */
	@Test
	public void testCanSetIfOwnChipLeftDown() {
		game.setChip(GameLogicImpl.PLAYER_ONE, 0, 3);
		game.setChip(GameLogicImpl.PLAYER_TWO, 1, 2);
		game.setChip(GameLogicImpl.PLAYER_TWO, 2, 1);
		assertTrue("Should be able to put a chip if encloses an opponent chip",
				game.canSet(GameLogicImpl.PLAYER_ONE, 3, 0));

		//but if there is an empty space, should not be able to put
		game.setChip(GameLogicImpl.EMPTY, 5, 2);
		assertFalse("Should be able to put a chip if encloses an opponent chip after empty spaces",
				game.canSet(GameLogicImpl.PLAYER_ONE, 1, 0));
	}
	
	/**
	 * Test in the right-down direction if a chip can be put
	 */
	@Test
	public void testCanSetIfOwnChipRightDown() {
		game.setChip(GameLogicImpl.PLAYER_ONE, 4, 3);
		game.setChip(GameLogicImpl.PLAYER_TWO, 2, 1);
		game.setChip(GameLogicImpl.PLAYER_TWO, 3, 2);
		assertTrue("Should be able to put a chip if encloses an opponent chip",
				game.canSet(GameLogicImpl.PLAYER_ONE, 1, 0));
		
		//but if there is an empty space, should not be able to put
		game.setChip(GameLogicImpl.EMPTY, 3, 2);
		assertFalse("Should be able to put a chip if encloses an opponent chip after empty spaces",
				game.canSet(GameLogicImpl.PLAYER_ONE, 1, 0));
	}
	
	/**
	 * Test in the right-down direction if a chip can be put
	 */
	@Test
	public void testCanSetIfOwnChipRightUp() {
		game.setChip(GameLogicImpl.PLAYER_ONE, 3, 0);
		game.setChip(GameLogicImpl.PLAYER_TWO, 2, 1);
		game.setChip(GameLogicImpl.PLAYER_TWO, 1, 2);
		assertTrue("Should be able to put a chip if encloses an opponent chip",
				game.canSet(GameLogicImpl.PLAYER_ONE, 0, 3));
		
		//but if there is an empty space, should not be able to put
		game.setChip(GameLogicImpl.EMPTY, 2, 1);
		assertFalse("Should be able to put a chip if encloses an opponent chip after empty spaces",
				game.canSet(GameLogicImpl.PLAYER_ONE, 1, 0));
	}
	
	
}
