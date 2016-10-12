import java.util.Random;

public class MineSweeperBoard {
	
	private Cell[][] cells;
	
	public MineSweeperBoard(int x, int y) {
		cells = new Cell[x][y];
		
		for (int j = 0; j < 10; ++j) {
			for (int i = 0; i < 10; ++i) {
				
				cells[i][j] = new Cell();
			}
		}

		newGame();
	}

	private void newGame() {
		//loops 10 times		
		for (int i = 0; i < 10; ++i) {
			Random random = new Random();
			int x = random.nextInt(10);
			int y = random.nextInt(10);
			
			//if the current Cell is already a mine
			if(cells[x][y].isMine()) {
				//keep looping until the current Cell is not a mine
				while(cells[x][y].isMine()) {
					random = new Random();
					//set x as new random number
					x = random.nextInt(10);
					//set y as new random number
					y = random.nextInt(10);
				}
			}
			
			//sets the new Cell to have the mine
			cells[x][y].setMine(true);
		}
	}

	public String toString() {
		String grid = "";
		for (int y = 0; y < 10; ++y) {
			for (int x = 0; x < 10; ++x) {
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
	
}
