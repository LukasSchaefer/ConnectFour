import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class GameMenu {

    public static void runMenu() {
        VBox menuLayout = new VBox(40);
        Scene menuScene = new Scene(menuLayout, 400, 400);
        Main.window.setScene(menuScene);

        Label title = new Label("Main Menu");

        Button startButton = new Button();
        startButton.setText("Start Game");
        startButton.setOnAction(event -> Game.runGame());

        Button helpButton = new Button();
        helpButton.setText("Help");
        helpButton.setOnAction(event -> Help.runHelp());

        Button quitButton = new Button();
        quitButton.setText("Quit Game");
        quitButton.setOnAction(event -> Main.window.close());

        menuLayout.getChildren().addAll(title, startButton, helpButton, quitButton);
        menuLayout.setAlignment(Pos.CENTER);
    }
}
