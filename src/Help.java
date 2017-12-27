import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Help {

    public static void runHelp() {
        BorderPane helpLayout = new BorderPane();
        Scene helpScene = new Scene(helpLayout, 600, 400);
        Main.window.setScene(helpScene);

        // HBox for top-title
        HBox titleBox = new HBox();
        helpLayout.setTop(titleBox);
        Label title = new Label("Help");
        Font font = Font.font("Times New Roman", FontWeight.BOLD, 48);
        title.setFont(font);
        titleBox.getChildren().add(title);
        titleBox.setAlignment(Pos.CENTER);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        helpLayout.setCenter(grid);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        Button backButton = new Button();
        backButton.setText("Back to Menu");
        backButton.setOnAction(event -> GameMenu.runMenu());
        GridPane.setConstraints(backButton, 0, 2);
        grid.getChildren().add(backButton);
    }
}
