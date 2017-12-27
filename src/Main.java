import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    // constant for number of columns of the board
    private int width = 7;
    // constant for number of rows of the board
    private int height = 6;

    // constant for horizontal space between columns of the board
    private final int HSPACE = 20;
    // constant for vertical space between rows of the board
    private final int VSPACE = 10;

    // constant for radius of one circular cell in the board
    // WHEN CHANGED PNGs FOR DISKS HAVE TO BE UPDATED
    private final int CELLRADIUS = 100;

    // height of pointer-area below the board
    private final int POINTERAREAHEIGHT = 100;

    private GameLogic gameLogic;

    protected static Stage window;

    public static void main(String[] args) {
        launch(args);
    }


    private void MainMenu() {

    }




    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;

        window.setTitle("Connect Four");

        Game.runGame();

        window.show();
    }
}
