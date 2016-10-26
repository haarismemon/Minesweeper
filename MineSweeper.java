import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Created by Haaris on 16/10/2016.
 */
public class MineSweeper extends Application {

    private Button[][] buttonGrid;
    private ArrayList<Button> difficultyButtons;
    private MineSweeperBoard mineSweeperBoard;
    private Label flagLabel;
    private Label cellsLabel;
    private Label winLossLabel;
    private GridPane grid;
    private Button easyBtn;
    private Button mediumBtn;
    private Button hardBtn;

    public void start(Stage primaryStage) {
        mineSweeperBoard = new MineSweeperBoard();
        mineSweeperBoard.newGame(9, 9, 10);
        buttonGrid = new Button[mineSweeperBoard.getRows()][mineSweeperBoard.getCols()];
        difficultyButtons = new ArrayList<Button>();

        mineSweeperBoard.setLevel(0);

        easyBtn = new Button("Easy");
        difficultyButtons.add(easyBtn);
        mediumBtn = new Button("Medium");
        difficultyButtons.add(mediumBtn);
        hardBtn = new Button("Hard");
        difficultyButtons.add(hardBtn);
        Button exitBtn = new Button("Exit");
        flagLabel = new Label("0/" + mineSweeperBoard.getTotalMines() + " flags");
        cellsLabel = new Label("0/" + (mineSweeperBoard.getRows() * mineSweeperBoard.getCols()) + " cells");
        winLossLabel = new Label("Game Outcome");

        primaryStage.setTitle("MineSweeper by Haaris Memon");

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10, 12, 10, 12));
        hBox.setSpacing(20);
        hBox.setAlignment(Pos.CENTER);

        hBox.getChildren().add(easyBtn);
        hBox.getChildren().add(mediumBtn);
        hBox.getChildren().add(hardBtn);
        hBox.getChildren().add(flagLabel);
        hBox.getChildren().add(cellsLabel);
        hBox.getChildren().add(winLossLabel);
        hBox.getChildren().add(exitBtn);


        BorderPane border = new BorderPane();
        border.setTop(hBox);

        grid = newGrid();
        border.setCenter(grid);

        for(Button btn : difficultyButtons) {
            btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(btn.getText().equals("Easy")) {
                        mineSweeperBoard.newGame(9, 9, 10);
                        mineSweeperBoard.setLevel(0);
                    } else if(btn.getText().equals("Medium")) {
                        mineSweeperBoard.newGame(16,16,40);
                        mineSweeperBoard.setLevel(1);
                    } else if(btn.getText().equals("Hard")) {
                        mineSweeperBoard.newGame(20, 16, 99);
                        mineSweeperBoard.setLevel(2);
                    }

                    grid = newGrid();
                    border.setCenter(grid);
                    disableAllButtons(false);
                    updateBoard();
                }
            });
        }

        exitBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.close();
            }
        });

        updateBoard();

        Scene scene = new Scene(border, 1000, 580);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private GridPane newGrid() {
        buttonGrid = new Button[mineSweeperBoard.getRows()][mineSweeperBoard.getCols()];

        GridPane gridButtons = new GridPane();
        gridButtons.setAlignment(Pos.CENTER);
        gridButtons.setHgap(5);
        gridButtons.setVgap(5);
        gridButtons.setPadding(new Insets(10, 10, 10, 10));

        for(int row = 0; row < mineSweeperBoard.getRows(); ++row) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(100.0 / mineSweeperBoard.getCols());
            gridButtons.getRowConstraints().add(rowConstraints);
        }

        for(int cols = 0; cols < mineSweeperBoard.getCols(); ++cols) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPercentWidth(100.0 / mineSweeperBoard.getRows());
            gridButtons.getColumnConstraints().add(columnConstraints);
        }

        for(int j = 0; j < mineSweeperBoard.getCols(); ++j) {
            for(int i = 0; i < mineSweeperBoard.getRows(); ++i) {
                Button cellBtn = new Button(" ");
                cellBtn.setMaxWidth(Double.MAX_VALUE);
                cellBtn.setMaxHeight(Double.MAX_VALUE);
                buttonGrid[i][j] = cellBtn;
                gridButtons.add(cellBtn, i, j);

                final int x = i;
                final int y = j;

                cellBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        Cell cellClicked = mineSweeperBoard.getCell(x, y);

                        if(event.getButton() == MouseButton.PRIMARY) {
                            mineSweeperBoard.reveal(x, y);
                        }
                        else if (event.getButton() == MouseButton.SECONDARY) {
                            if (mineSweeperBoard.getFlagsCount() <= 10) {
                                if (!cellClicked.isFlag()) {
                                    mineSweeperBoard.flag(x, y);
                                } else {
                                    mineSweeperBoard.unflag(x, y);
                                }
                            }
                        }
                        updateBoard();
                    }
                });
            }
        }

        return gridButtons;
    }

    private void updateBoard() {
        //loop through size of board
        for(int j = 0; j < mineSweeperBoard.getCols(); ++j) {
            for (int i = 0; i < mineSweeperBoard.getRows(); ++i) {
                Cell cell = mineSweeperBoard.getCell(i, j);
                Button cellBtn =  buttonGrid[i][j];

                if(cell.isRevealed()) {
                    //if the cell is a revealed mine cell
                    if (cell.isMine()) {
                        cellBtn.setStyle("-fx-background-color: red; -fx-text-fill: white");
                        cellBtn.setText("M");
                        disableAllButtons(true);
                    }
                    //if the cell is a revealed cell
                    else {
                        cellBtn.setStyle("-fx-background-color: dimgrey; -fx-text-fill: white");
                        if(cell.getAdjacent() != 0) {
                            cellBtn.setText("" + cell.getAdjacent());
                        }
                    }
                } else {
                    //if the cell is a unrevealed flagged cell
                    if(cell.isFlag()) {
                        cellBtn.setStyle("-fx-background-color: green; -fx-text-fill: white");
                        cellBtn.setText("F");
                    }
                    //if the cell is a unrevealed cell
                    else {
                        cellBtn.setStyle((new Button()).getStyle());
                        cellBtn.setText(" ");
                    }
                }
            }
        }

        if(mineSweeperBoard.getLevel() == 0) {
            easyBtn.setStyle("-fx-background-color: cornflowerblue");
            mediumBtn.setStyle((new Button()).getStyle());
            hardBtn.setStyle((new Button()).getStyle());
        } else if(mineSweeperBoard.getLevel() == 1) {
            easyBtn.setStyle((new Button()).getStyle());
            mediumBtn.setStyle("-fx-background-color: cornflowerblue");
            hardBtn.setStyle((new Button()).getStyle());
        } else if(mineSweeperBoard.getLevel() == 2) {
            easyBtn.setStyle((new Button()).getStyle());
            mediumBtn.setStyle((new Button()).getStyle());
            hardBtn.setStyle("-fx-background-color: cornflowerblue");
        }

        flagLabel.setText(mineSweeperBoard.getFlagsCount() + "/" + mineSweeperBoard.getTotalMines() + " flags");
        cellsLabel.setText(mineSweeperBoard.getRevealedCells() + "/" + (mineSweeperBoard.getRows() * mineSweeperBoard.getCols()) + " cells");

        if(mineSweeperBoard.isGameLost()) {
            winLossLabel.setText("You Lost!");
            winLossLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        } else if (mineSweeperBoard.isGameWon()) {
            winLossLabel.setText("You Won!");
            winLossLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
            disableAllButtons(true);
        } else {
            winLossLabel.setText("Game Outcome");
            winLossLabel.setStyle("-fx-text-fill: black; -fx-font-weight: normal;");
        }
    }

    private void disableAllButtons(boolean bool) {
        for(int j = 0; j < mineSweeperBoard.getCols(); ++j) {
            for (int i = 0; i < mineSweeperBoard.getRows(); ++i) {
                buttonGrid[i][j].setDisable(bool);
            }
        }
    }

    private void setDifficulty(int level) {

    }

    public static void main(String[] args) {
        launch(args);
    }

}
