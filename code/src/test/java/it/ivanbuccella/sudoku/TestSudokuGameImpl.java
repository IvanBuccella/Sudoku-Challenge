package it.ivanbuccella.sudoku;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import it.ivanbuccella.sudoku.implementations.Message;
import it.ivanbuccella.sudoku.implementations.SudokuGameImpl;
import it.ivanbuccella.sudoku.interfaces.MessageListener;

public class TestSudokuGameImpl {

	protected SudokuGameImpl peer0, peer1, peer2, peer3;
	Integer[][] board = {
			{ 0, 6, 1, 7, 0, 4, 0, 5, 2 },
			{ 3, 0, 2, 1, 0, 8, 0, 4, 9 },
			{ 4, 9, 0, 0, 5, 0, 1, 0, 6 },
			{ 0, 1, 8, 0, 7, 5, 6, 0, 4 },
			{ 6, 7, 0, 3, 4, 0, 9, 2, 8 },
			{ 9, 3, 4, 6, 8, 2, 0, 0, 7 },
			{ 0, 0, 6, 8, 1, 9, 4, 0, 3 },
			{ 7, 0, 3, 0, 2, 0, 8, 0, 1 },
			{ 1, 8, 0, 4, 3, 7, 2, 6, 0 } };

	public TestSudokuGameImpl() throws Exception {
		class MessageListenerImpl implements MessageListener {
			int peerid;
			HashMap<String, List<String>> msgList = new HashMap<>();

			public MessageListenerImpl(int peerid) {
				this.peerid = peerid;
			}

			public Object parseMessage(Object obj) {
				if (!(obj instanceof Message))
					return "failed";

				Message message = (Message) obj;

				msgList.get(message.getGameName()).add(message.getMessage(peerid));

				return "success";
			}
		}

		peer0 = new SudokuGameImpl(0, "127.0.0.1", new MessageListenerImpl(0));
		peer1 = new SudokuGameImpl(1, "127.0.0.1", new MessageListenerImpl(1));
		peer2 = new SudokuGameImpl(2, "127.0.0.1", new MessageListenerImpl(2));
		peer3 = new SudokuGameImpl(3, "127.0.0.1", new MessageListenerImpl(3));
	}

	@Test
	void testCaseGenerateNewSudoku(TestInfo testInfo) {
		Integer[][] createdBoard;
		createdBoard = peer0.generateNewSudoku("Game 1");
		assertTrue(Arrays.deepEquals(board, createdBoard));

		createdBoard = peer1.generateNewSudoku("Game 1");
		assertEquals(null, createdBoard);
	}

	@Test
	void testCaseJoin(TestInfo testInfo) {
		boolean result;

		peer0.generateNewSudoku("Game 1");

		result = peer1.join("Game 1", "PeerName1");
		assertTrue(result);

		peer2.generateNewSudoku("Game 2");

		result = peer3.join("Game 50", "PeerName3");
		assertFalse(result);
	}

	@Test
	void testCaseGetSudoku(TestInfo testInfo) {
		Integer[][] obtainedBoard;

		peer0.generateNewSudoku("Game 1");

		obtainedBoard = peer0.getSudoku("Game 1");
		assertTrue(Arrays.deepEquals(board, obtainedBoard));

		peer1.join("Game 1", "PeerName1");
		obtainedBoard = peer1.getSudoku("Game 1");
		assertTrue(Arrays.deepEquals(board, obtainedBoard));

		obtainedBoard = peer2.getSudoku("Game 50");
		assertFalse(Arrays.deepEquals(board, obtainedBoard));
	}

	@Test
	void testCasePlaceNumber(TestInfo testInfo) {
		Integer score;

		peer0.generateNewSudoku("Game 1");
		score = peer0.placeNumber("Game 1", 0, 0, 7);
		assertEquals(-1, score);

		peer1.join("Game 1", "PeerName1");
		score = peer1.placeNumber("Game 1", 0, 0, 8);
		assertEquals(1, score);

		peer2.generateNewSudoku("Game 2");
		score = peer2.placeNumber("Game 2", 0, 1, 6);
		assertEquals(0, score);

		peer3.join("Game 2", "PeerName3");
		score = peer3.placeNumber("Game 2", 1, 1, 5);
		assertEquals(1, score);
	}

	@Test
	void testCaseLeaveGame(TestInfo testInfo) {
		boolean result;

		peer0.generateNewSudoku("Game 1");
		peer1.join("Game 1", "PeerName1");

		result = peer0.leaveGame("Game 1");
		assertTrue(result);

		result = peer1.leaveGame("Game 1");
		assertTrue(result);

		result = peer2.leaveGame("Game 50");
		assertFalse(result);
	}
}
