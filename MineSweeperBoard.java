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
	}	
	
}
