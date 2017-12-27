import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;

public class Game {

    // constant for number of columns of the board
    private static int width = 7;
    // constant for number of rows of the board
    private static int height = 6;

    // constant for horizontal space between columns of the board
    private static final int HSPACE = 20;
    // constant for vertical space between rows of the board
    private static final int VSPACE = 10;

    // constant for radius of one circular cell in the board
    // WHEN CHANGED PNGs FOR DISKS HAVE TO BE UPDATED
    private static final int CELLRADIUS = 100;

    // height of pointer-area below the board
    private static final int POINTERAREAHEIGHT = 100;

    public static void runGame() {
        GameLogic gameLogic = new GameLogic(width, height);

        BorderPane layout = new BorderPane();

        Scene gameScene = new Scene(layout);
        Main.window.setScene(gameScene);

        Main.window.setOnCloseRequest(event -> {
            boolean answer = CancelBox.display();
            event.consume();
            if (answer)
                Main.window.close();
        });

        HBox topMenu = new HBox();
        layout.setTop(topMenu);

        // config Topbar
        Button backButton = new Button();
        topMenu.getChildren().add(backButton);
        backButton.setText("Back To Menu");
        backButton.setOnAction(event -> {
            boolean answer = CancelBox.display();
            if (answer)
                GameMenu.runMenu();
        });


        Canvas canvas = new Canvas(HSPACE + width * (HSPACE + CELLRADIUS), VSPACE + height * (VSPACE + CELLRADIUS) + POINTERAREAHEIGHT);
        layout.setCenter(canvas);

        // config Game-Panel
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Font font = Font.font("Times New Roman", FontWeight.BOLD, 48);
        gc.setFont(font);

        Image redDiskPicture = new Image("media/RedDisk.png");
        Image yellowDiskPicture = new Image("media/YellowDisk.png");
        Image whiteDiskPicture = new Image("media/WhiteDisk.png");
        Image greenDiskPicture = new Image("media/GreenDisk.png");

        gameLogic.dropDisk(Player.RED, 0);
        gameLogic.dropDisk(Player.YELLOW, 1);
        gameLogic.dropDisk(Player.RED, 1);
        gameLogic.dropDisk(Player.YELLOW, 2);
        gameLogic.dropDisk(Player.RED, 2);
        gameLogic.dropDisk(Player.YELLOW, 5);

        ArrayList<String> input = new ArrayList<>();

        gameScene.setOnKeyPressed(
                new EventHandler<KeyEvent>() {
                    public void handle(KeyEvent e) {
                        String code = e.getCode().toString();

                        // only add once... prevent duplicates
                        if ( !input.contains(code) )
                            input.add( code );
                    }
                });

        gameScene.setOnKeyReleased(
                new EventHandler<KeyEvent>() {
                    public void handle(KeyEvent e) {
                        String code = e.getCode().toString();
                        input.remove( code );
                    }
                });

        final NanoTime lastNanoTime = new NanoTime(System.nanoTime());

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                // draw board background
                gc.setFill(Color.BLUE);
                gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight() - POINTERAREAHEIGHT);

                // draw all cells
                int xCoord = HSPACE;
                int yCoord = VSPACE;
                for (int rows = 0; rows < height; rows++) {
                    for (int cols = 0; cols < width; cols++) {

                        switch (gameLogic.getCellValue(rows, cols)) {
                            case REDDISK:
                                gc.drawImage(redDiskPicture, xCoord, yCoord);
                                break;
                            case YELLOWDISK:
                                gc.drawImage(yellowDiskPicture, xCoord, yCoord);
                                break;
                            case EMPTY:
                                gc.drawImage(whiteDiskPicture, xCoord, yCoord);
                        }
                        // next column coordinates
                        xCoord += HSPACE + CELLRADIUS;
                    }
                    // next row coordinates
                    yCoord += VSPACE + CELLRADIUS;
                    // start column from the beginning
                    xCoord = HSPACE;
                }

                // check for new pointer movements every 100ms
                if (currentNanoTime - lastNanoTime.getTime() > 100000000) {
                    // draw pointer-background
                    gc.setFill(Color.WHITE);
                    gc.fillRect(0, canvas.getHeight() - POINTERAREAHEIGHT, canvas.getWidth(), canvas.getHeight());

                    lastNanoTime.setTime(currentNanoTime);

                    // update pointer based on input
                    if (input.contains("LEFT")) {
                        gameLogic.movePointerLeft();
                    }
                    if (input.contains("RIGHT")) {
                        gameLogic.movePointerRight();
                    }

                    // draw current pointer
                    int pointerPosition = gameLogic.getPointerPosition();
                    gc.setFill(Color.BLACK);
                    // these dumb factors come from the font size so that the pointerPosition is still displayed about centered in a column
                    gc.fillText(Integer.toString(pointerPosition + 1), HSPACE + CELLRADIUS * 0.4 + pointerPosition * (HSPACE + CELLRADIUS), VSPACE + height * (VSPACE + CELLRADIUS) + POINTERAREAHEIGHT * 0.75);
                }
            }
        }.start();
    }
}
