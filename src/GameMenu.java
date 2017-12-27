import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GameMenu {

    public static void runMenu() {
        BorderPane menulayout = new BorderPane();
        Scene menuScene = new Scene(menulayout, 600, 400);
        Main.window.setScene(menuScene);

        // HBox for top-title
        HBox titleBox = new HBox();
        menulayout.setTop(titleBox);
        Label title = new Label("Main Menu");
        Font font = Font.font("Times New Roman", FontWeight.BOLD, 48);
        title.setFont(font);
        titleBox.getChildren().add(title);
        titleBox.setAlignment(Pos.CENTER);

        // Grid for Buttons and Text-fields in the center
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        menulayout.setCenter(grid);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        // Textfields for entering height and width for the game
        TextField heightText = new TextField();
        heightText.setPromptText("Enter the number of rows for the game-board. (default: 6)");
        heightText.setPrefWidth(400);
        GridPane.setConstraints(heightText, 1, 0);
        grid.getChildren().add(heightText);

        TextField widthText = new TextField();
        widthText.setPromptText("Enter the number of columns for the game-board. (default: 7)");
        widthText.setPrefWidth(400);
        GridPane.setConstraints(widthText, 1, 1);
        grid.getChildren().add(widthText);

        // Button to start the game
        Button startButton = new Button();
        startButton.setText("Start Game");
        startButton.setOnAction(event -> {
            int height;
            try {
                height = Integer.parseInt(heightText.getText());
            } catch (NumberFormatException e) {
                height = Game.HEIGHT;
            }

            int width;
            try {
                width = Integer.parseInt(widthText.getText());
            } catch (NumberFormatException e) {
                width = Game.WIDTH;
            }

            Game.runGame(width, height);
        });
        GridPane.setConstraints(startButton, 0, 0);
        grid.getChildren().add(startButton);

        // Button to access the Help-Panel
        Button helpButton = new Button();
        helpButton.setText("Help");
        helpButton.setOnAction(event -> Help.runHelp());
        GridPane.setConstraints(helpButton, 0, 2);
        grid.getChildren().add(helpButton);

        // Button to quit/ exit the game (without further CancelBox)
        Button quitButton = new Button();
        quitButton.setText("Quit Game");
        quitButton.setOnAction(event -> Main.window.close());
        GridPane.setConstraints(quitButton, 0, 3);
        grid.getChildren().add(quitButton);


    }
}
