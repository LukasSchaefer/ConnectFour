import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CancelBox {

    static boolean answer;

    public static boolean display() {
        Stage window = new Stage();
        window.setTitle("Cancel Game");

        Label label = new Label("Are you sure you want to cancel this game?");

        Button yesButton = new Button();
        yesButton.setText("Yes");
        yesButton.setOnAction(event -> {
            answer = true;
            window.close();
        });

        Button noButton = new Button();
        noButton.setText("No");
        noButton.setOnAction(event -> {
            answer = false;
            window.close();
        });

        VBox cancelLayout = new VBox();
        cancelLayout.setAlignment(Pos.CENTER);
        cancelLayout.getChildren().addAll(label, yesButton, noButton);

        Scene scene = new Scene(cancelLayout, 400, 100);

        window.setScene(scene);
        window.showAndWait();

        return answer;
    }
}
