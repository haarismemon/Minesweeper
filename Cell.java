
public class Cell {
	private boolean mine;
	private boolean flag;
	private boolean revealed;
	private int adjacent;
	
	public Cell() {
		mine = false;
		flag = false;
		revealed = false;
		adjacent = 0;
	}
	
	public boolean isMine() {
		return mine;
	}
	public boolean isFlag() {
		return flag;
	}
	public boolean isRevealed() {
		return revealed;
	}
	public int getAdjacent() {
		return adjacent;
	}
	public void setMine(boolean mine) {
		this.mine = mine;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public void setRevealed(boolean revealed) {
		this.revealed = revealed;
	}
	public void setAdjacent(int adjacent) {
		this.adjacent = adjacent;
	}
	 
}
