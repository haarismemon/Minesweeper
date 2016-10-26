/**
 * Created by mkhal on 27/10/2016.
 */
public class BoardState {
    private Cell[][] cells;
    private int flagsCount;
    private int revealedCells;
    private boolean gameLost;
    private boolean gameWon;

    public BoardState(Cell[][] cellsIn, int flagsIn, int revealedIn, boolean lostIn, boolean wonIn) {
        cells = cellsIn;
        flagsCount = flagsIn;
        revealedCells = revealedIn;
        gameLost = lostIn;
        gameWon = wonIn;
    }

    public Cell[][] getCells() {
        return cells;
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
}
