import java.util.Random;

public class MineSweeperBoard {
	
	private Cell[][] cells;
	
	public MineSweeperBoard(int x, int y) {
		cells = new Cell[x][y];
		
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
