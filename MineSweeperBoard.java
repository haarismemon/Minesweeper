import java.util.Random;

public class MineSweeperBoard {
	
	private Cell[][] cells;
	private int xSize;
	private int ySize;
	
	public MineSweeperBoard(int x, int y) {
		cells = new Cell[x][y];
		xSize = x;
		ySize = y;
		
		for (int j = 0; j < x; ++j) {
			for (int i = 0; i < y; ++i) {	
				cells[i][j] = new Cell();
			}
		}

		newGame(x, y);
	}

	private void newGame(int x, int y) {
		//loops 10 times		
		for (int i = 0; i < 10; ++i) {
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
		for(int j = 0; j < ySize; ++j) {
			for(int i = 0; i < xSize; ++i) {
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
				if(i < 0 || i >= xSize || j < 0 || j >= ySize) {
					continue;
				}
				//if the indexs are equal to the coordinate being checked, then skip iteration
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
		cells[x][y].setFlag(true);
	}
	
	public void unflag(int x, int y) {
		cells[x][y].setFlag(false);
	}

	public String toString() {
		String grid = "";
		for (int y = 0; y < 8; ++y) {
			for (int x = 0; x < 8; ++x) {
				if (cells[x][y].isMine()) {
					grid += "x";
				}
				else if(cells[x][y].isFlag()) {
					grid += "X";
				}
				else {
					grid += "0";
				}
			}
			grid += "\n";
		}
		
		return grid;
	}

	public static void main(String[] args) {
		MineSweeperBoard mineSweeperBoard = new MineSweeperBoard(8, 8);
		
		System.out.println(mineSweeperBoard);
	}
	
}
