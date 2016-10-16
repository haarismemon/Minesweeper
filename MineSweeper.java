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

    private Button[][] buttonGrid;

    public void start(Stage primaryStage) {
        buttonGrid = new Button[10][10];

        primaryStage.setTitle("MineSweeper");

        BorderPane border = new BorderPane();

        Scene scene = new Scene(border, 510, 580);
        primaryStage.setScene(scene);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(15);
        grid.setVgap(15);
//        grid.setPadding(new Insets(25, 25, 25, 25));

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
