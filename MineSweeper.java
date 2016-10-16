import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Created by Haaris on 16/10/2016.
 */
public class MineSweeper extends Application {

    public void start(Stage primaryStage) {
        Button[][] buttonGrid = new Button[10][10];

        Button newGameBtn = new Button("New Game");
        Button exitBtn = new Button("Exit");
        Label flagLabel = new Label("0/10 flags");
        Label cellsLabel = new Label("0/100 cells");
        Label winLossLabel = new Label("Game Outcome");

        primaryStage.setTitle("MineSweeper");

        BorderPane border = new BorderPane();
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(15, 12, 15, 12));
        hBox.setSpacing(30);
        hBox.setAlignment(Pos.CENTER);
        border.setTop(hBox);

        hBox.getChildren().add(newGameBtn);
        hBox.getChildren().add(flagLabel);
        hBox.getChildren().add(cellsLabel);
        hBox.getChildren().add(winLossLabel);
        hBox.getChildren().add(exitBtn);

        Scene scene = new Scene(border, 520, 580);
        primaryStage.setScene(scene);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(15);
        grid.setVgap(15);

        for(int j = 0; j < 10; ++j) {
            for(int i = 0; i < 10; ++i) {
                Button button = new Button("C");
                button.setPrefSize(35, 35);
                buttonGrid[i][j] = button;
                grid.add(button, i, j);
            }
        }

        border.setCenter(grid);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
