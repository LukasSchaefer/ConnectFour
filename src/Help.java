import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Help {

    public static void runHelp() {
        VBox helpLayout = new VBox(40);
        Scene helpScene = new Scene(helpLayout, 400, 400);
        Main.window.setScene(helpScene);

        Label helpLabel = new Label("Help");

        Button backButton = new Button();
        backButton.setText("Back to Menu");
        backButton.setOnAction(event -> GameMenu.runMenu());

        helpLayout.getChildren().addAll(helpLabel, backButton);
        helpLayout.setAlignment(Pos.CENTER);
    }
}
