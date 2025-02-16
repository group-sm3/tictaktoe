package com.sm3.connect4.model;

/**
 * This class represents the logic of the Connect 4 game.
 *
 */
public class Game extends Model {

	private final int COLUMNS = 7;
	private final int ROWS = 6;
	private final int red = 1;
	private final int yellow = 2;
	private int difficulty = 0;
	private AI bot;
	private int playerColor;
	private boolean gameOver = false;
	private boolean hasAI = true;
	private boolean playerTurn = true;
	private int[][] grid = new int[COLUMNS][ROWS];
	private int counter[] = { 0, 0, 0, 0, 0, 0, 0 }; // counter counts how many free spaces are left (includes 0)

	/**
	 * Constructor used to start a game.
	 * 
	 * @param diff  The difficulty setting of the game. If no difficulty set, then no bot is created.
	 * @param color The set color of the player.
	 */
	public Game(String diff, String color) {
		if (diff == "Easy")
			difficulty = 1;
		else if (diff == "Normal")
			difficulty = 2;
		else if (diff == "Hard")
			difficulty = 3;
		else
			hasAI = false;

		if (color == "Red") {
			playerColor = red;
			if (hasAI)
				bot = new AI(yellow, ROWS, COLUMNS, grid, counter, difficulty);
		} else {
			playerColor = yellow;
			if (hasAI)
				bot = new AI(red, ROWS, COLUMNS, grid, counter, difficulty);
		}

	}

	/**
	 * Adds a disc to the grid if the column contains an open space.
	 * 
	 * @param disc   An integer that represents the color of the disc to be placed.
	 * @param column An integer that represents the desired column to place a disc.
	 * @return A ModelEvent class object that represents the information of the
	 *         placed disc.
	 */
	private ModelEvent addDisc(int disc, int column) {

		// Checks next available slot in column
		if (counter[column] > 5) {
			System.out.print("full col");
			return null;
		}

		// adds disc to column
		this.grid[column][counter[column]] = disc;

		// return ModelEvent that has content
		char color;
		String msg = "";
		if (disc == 1) {
			color = 'r';
			msg += "Red ";
		} else {
			color = 'y';
			msg += "Yellow ";
		}

		msg += "move on row " + counter[column] + ", column " + column + "\n";
		counter[column]++;

		return new ModelEvent(this, 1, "", column, counter[column] - 1, color, msg, false);
	}

	/**
	 * Checks if there is 4 in a row.
	 * 
	 * @return A boolean that represents if there is a win in the game.
	 */
	private boolean CheckWinCondition() // signals: false = no win, true = win
	{
		// check horzontally & checks if more than 3 rows (for vertical and diagnal)
		int maxrows = ROWS;
		int zeroes;
		int count = 0;
		for (int r = 0; r < ROWS; r++) {
			if (grid[COLUMNS - 1][r] == 0)
				zeroes = 1;
			else
				zeroes = 0;

			for (int c = 0; c < COLUMNS - 1; c++) {

				if (grid[c][r] == 0) {
					zeroes++;
					count = 0;
				} else if (grid[c][r] == grid[c + 1][r])
					count++;
				else
					count = 0;

				if (count == 3)
					return true;
			}
			if (zeroes == COLUMNS) {
				maxrows = r + 1;
				break;
			}
			count = 0;
		}
		if (maxrows <= 4)
			return false;

		// check vertically & finds empty columns for diagnal check
		count = 0;
		for (int c = 0; c < COLUMNS; c++) {
			for (int r = 0; r < maxrows - 1; r++) {
				if (grid[c][r] == grid[c][r + 1] && grid[c][r] != 0)
					count++;
				else
					count = 0;

				if (count == 3)
					return true;
			}
			count = 0;
		}

		// check diagonal right
		// if maxrows = 4 then 1 check, 5 2c, 6 3c, 7 4c
		for (int r = 0; r < maxrows - 3; r++) {
			for (int c = 0; c < COLUMNS - 3; c++) {
				if (grid[c][r] != 0 && grid[c][r] == grid[c + 1][r + 1] && grid[c + 1][r + 1] == grid[c + 2][r + 2]
						&& grid[c + 2][r + 2] == grid[c + 3][r + 3])
					return true;
			}
		}

		// check diagonal left
		for (int r = 0; r < maxrows - 3; r++) {
			for (int c = COLUMNS - 1; c > COLUMNS - 5; c--) {
				if (grid[c][r] != 0 && grid[c][r] == grid[c - 1][r + 1] && grid[c - 1][r + 1] == grid[c - 2][r + 2]
						&& grid[c - 2][r + 2] == grid[c - 3][r + 3])
					return true;
			}
		}
		return false; // else, no win
	}

	/**
	 * Allows for player to make their desired move using the addDisc method.
	 * Notifies the view of changes.
	 * 
	 * @param column An integer that represents the player's desired column choice.
	 */
	public void playerColumnChoice(int column) {
		if (gameOver)
			return;
		ModelEvent me;
		if (playerColor == red)
			me = addDisc(red, column);
		else
			me = addDisc(yellow, column);

		if (!hasAI) {
			if (playerColor == red)
				playerColor = yellow;
			else
				playerColor = red;
		} else
			playerTurn = false;

		if (me.getContent()) {
			if (CheckWinCondition()) {
				gameOver = true;
				me.setWin(true);
			}
		}
		notifyChanged(me);
	}

	/**
	 * Allows for the A.I. to make their desired move using the addDisc method.
	 * Notifies the view of changes.
	 */
	public void botColumnChoice() {
		if (gameOver || !hasAI)
			return;

		int botColumn = bot.BotTurn();
		ModelEvent me;
		if (playerColor == red)
			me = addDisc(yellow, botColumn);
		else
			me = addDisc(red, botColumn);

		if (CheckWinCondition()) {
			gameOver = true;
			me.setWin(true);
		}
		playerTurn = true;
		notifyChanged(me);
	}

	/**
	 * Gets the color of the player.
	 * 
	 * @return A integer that represents the player's color.
	 */
	public int getPlayerColor() {
		return this.playerColor;
	}

	/**
	 * Gets the difficulty of the A.I.
	 * 
	 * @return A integer that represents the A.I. difficulty.
	 */
	public int getDifficulty() {
		return this.difficulty;
	}
}