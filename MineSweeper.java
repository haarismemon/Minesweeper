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

/**
 * Created by Haaris on 16/10/2016.
 */
public class MineSweeper extends Application {

    private Button[][] buttonGrid;
    private MineSweeperBoard mineSweeperBoard;
    private Label flagLabel;
    private Label cellsLabel;
    private Label winLossLabel;

    public void start(Stage primaryStage) {
        mineSweeperBoard = new MineSweeperBoard(10, 10, 10);
        buttonGrid = new Button[10][10];

        Button newGameBtn = new Button("New Game");
        Button exitBtn = new Button("Exit");
        flagLabel = new Label("0/10 flags");
        cellsLabel = new Label("0/100 cells");
        winLossLabel = new Label("Game Outcome");

        primaryStage.setTitle("MineSweeper by Haaris Memon");

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10, 12, 10, 12));
        hBox.setSpacing(20);
        hBox.setAlignment(Pos.CENTER);

        hBox.getChildren().add(newGameBtn);
        hBox.getChildren().add(flagLabel);
        hBox.getChildren().add(cellsLabel);
        hBox.getChildren().add(winLossLabel);
        hBox.getChildren().add(exitBtn);


        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(10, 10, 10, 10));

        for(int row = 0; row < mineSweeperBoard.getRows(); ++row) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(100.0 / mineSweeperBoard.getCols());
            grid.getRowConstraints().add(rowConstraints);
        }

        for(int cols = 0; cols < mineSweeperBoard.getCols(); ++cols) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPercentWidth(100.0 / mineSweeperBoard.getCols());
            grid.getColumnConstraints().add(columnConstraints);
        }

        for(int j = 0; j < 10; ++j) {
            for(int i = 0; i < 10; ++i) {
                Button cellBtn = new Button(" ");
                cellBtn.setMaxWidth(Double.MAX_VALUE);
                cellBtn.setMaxHeight(Double.MAX_VALUE);
                buttonGrid[i][j] = cellBtn;
                grid.add(cellBtn, i, j);

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

        newGameBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mineSweeperBoard.newGame(10,10);
                disableAllButtons(false);
                updateBoard();
            }
        });

        exitBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.close();
            }
        });

        updateBoard();

        BorderPane border = new BorderPane();
        border.setTop(hBox);
        border.setCenter(grid);

        Scene scene = new Scene(border, 420, 470);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateBoard() {
        //loop through size of board
        for(int j = 0; j < 10; ++j) {
            for (int i = 0; i < 10; ++i) {
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

        flagLabel.setText(mineSweeperBoard.getFlagsCount() + "/10 flags");
        cellsLabel.setText(mineSweeperBoard.getRevealedCells() + "/100 cells");

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
        for(int j = 0; j < 10; ++j) {
            for (int i = 0; i < 10; ++i) {
                buttonGrid[i][j].setDisable(bool);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
