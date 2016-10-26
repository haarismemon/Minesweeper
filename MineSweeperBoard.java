import java.util.Random;
import java.util.Stack;

public class MineSweeperBoard {
	
	private Cell[][] cells;

	private int rows;
	private int cols;
	private int flagsCount;
	private int revealedCells;
	private boolean gameLost;
	private boolean gameWon;
	private int totalMines;
	private int level;
	private Stack<Cell[][]> undoStack;
	private Stack<Cell[][]> redoStack;

//	public MineSweeperBoard(int x, int y, int numOfMines) {
//		cells = new Cell[x][y];
//		rows = x;
//		cols = y;
//		totalMines = numOfMines;
//
//		newGame(x, y, numOfMines);
//	}

	public void newGame(int x, int y, int numOfMines) {
		cells = new Cell[x][y];
		rows = x;
		cols = y;
		totalMines = numOfMines;

		flagsCount = 0;
		revealedCells = 0;
		gameLost = false;
		gameWon = false;

		undoStack = new Stack<>();
		redoStack = new Stack<>();

		for (int j = 0; j < cols; ++j) {
			for (int i = 0; i < rows; ++i) {
				cells[i][j] = new Cell();
			}
		}

		//loops 10 times
		for (int i = 0; i < totalMines; ++i) {
			Random random = new Random();
			int xCell = random.nextInt(x);
			int yCell = random.nextInt(y);

			//if the current Cell is already a mine
			if(cells[xCell][yCell].isMine()) {
				//keep looping until the current Cell is not a mine
				while(cells[xCell][yCell].isMine()) {
					random = new Random();
					//set x as new random number
					xCell = random.nextInt(x);
					//set y as new random number
					yCell = random.nextInt(y);
				}
			}

			//sets the new Cell to have the mine
			cells[xCell][yCell].setMine(true);
		}

		// String s = "";
		for(int j = 0; j < cols; ++j) {
			for(int i = 0; i < rows; ++i) {
				cells[i][j].setAdjacent(adjacent(i, j));
				// s += cells[i][j].getAdjacent();
			}
			// s += "\n";
		}
		// System.out.println(s);
	}

	private int adjacent(int x, int y) {
		int count = 0;

		//loops through the adjacent tiles
		for(int j = y - 1; j <= y + 1; ++j) {
			for(int i = x - 1; i <= x + 1; ++i) {
				//if any of the index is out of bounds, then skip iteration
				if(i < 0 || i >= rows || j < 0 || j >= cols) {
					continue;
				}
				//if the indexes are equal to the coordinate being checked, then skip iteration
				else if((i == x) && (j == y)) {
					continue;
				}
				else if(cells[i][j].isMine()) {
					++count;
				}
			}
		}

		return count;
	}

	public void flag(int x, int y) {
		//if the flag count is lower than 10 and the cell has not been revealed, then flag cell
		if(flagsCount < 10 && !cells[x][y].isRevealed()) {
			cells[x][y].setFlag(true);
			++flagsCount;
		}
	}

	public void unflag(int x, int y) {
		//if the the cell has not been revealed, then flag cell
		if(!cells[x][y].isRevealed()) {
			cells[x][y].setFlag(false);
			--flagsCount;
		}
	}

	public void reveal(int x, int y) {
		//Before a cell is revealed, push the state of the board onto undoStack
		undoStack.push(cells);
		
		Cell currentCell = cells[x][y];
		//if the cell clicked is not revealed
		if(!currentCell.isRevealed() && !currentCell.isFlag()) {
			while(getRevealedCells() == 0 && currentCell.isMine()) {
				newGame(rows, cols, totalMines);
			}

			//if the unrevealed cell is a mine
			if (currentCell.isMine()) {
				currentCell.setRevealed(true);
				gameLost = true;
			}
			//if the number of adjacent mines for the cell is 0, then repeat method
			else if(currentCell.getAdjacent() == 0) {
				revealRecursive(x, y);
			}
			else {
				currentCell.setRevealed(true);
				++revealedCells;
			}
		}

		//if the number of revealed cells is equal to the number of non-mine cells
		if(revealedCells == (rows * cols) - totalMines) {
			gameWon = true;
		}

	}

	private void revealRecursive(int x, int y) {
		//loop through adjacent tiles
		for(int j = y - 1; j <= y + 1; ++j) {
			for (int i = x - 1; i <= x + 1; ++i) {
				//if any of the index is out of bounds, then skip iteration
				if(i < 0 || i >= rows || j < 0 || j >= cols) {
					continue;
				}

				Cell currentCell = cells[i][j];

				//if the cell is unrevealed and is not a mine
				if(!currentCell.isRevealed() && !currentCell.isMine() && !currentCell.isFlag()) {
					currentCell.setRevealed(true);
					++revealedCells;

					//if the number of adjacent mines for the cell is 0, then repeat method
					if (currentCell.getAdjacent() == 0) {
						revealRecursive(i, j);
					}
				}
			}
		}
	}

	public Cell getCell(int x, int y) {
		return cells[x][y];
	}

	public int getFlagsCount() {
		return flagsCount;
	}

	public int getRevealedCells() {
		return revealedCells;
	}

	public boolean isGameLost() {
		return gameLost;
	}

	public boolean isGameWon() {
		return gameWon;
	}

	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}

	public int getTotalMines() {
		return totalMines;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String toString() {
		String grid = "";
		for (int y = 0; y < cols; ++y) {
			for (int x = 0; x < rows; ++x) {
				if (cells[x][y].isMine() && cells[x][y].isRevealed()) {
					grid += "O";
				}
				else if(cells[x][y].isRevealed()) {
					grid += "X";
				}
				else if (cells[x][y].isMine()) {
					grid += "x";
				}
				else {
					grid += "0";
				}
			}
			grid += "\n";
		}

		return grid;
	}

}
