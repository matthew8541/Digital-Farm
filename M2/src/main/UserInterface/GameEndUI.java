package main.UserInterface;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;

public class GameEndUI {
    public static void createScene4(GameRunner mainGame) {

        Image character = mainGame.getPlayer().getCharacter();
        ImageView characterView = new ImageView(character);
        characterView.setFitHeight(80);
        characterView.setFitWidth(80);

        Label gameOver = new Label();
        gameOver.setText("GAME OVER!");
        gameOver.setStyle("-fx-font-size: 60");
        Button exit = new Button();
        exit.setText("Exit");
        exit.setStyle("-fx-font-size: 30");
        Button newGame = new Button();
        newGame.setText("New Game");
        newGame.setStyle("-fx-font-size: 30");
        newGame.setId("newGame");

        exit.setOnAction(e -> {
            System.exit(1);
        });

        newGame.setOnAction(e -> {
            mainGame.getWindow().setScene(mainGame.getScene1());
        });

        VBox root2 = new VBox(gameOver, characterView, exit, newGame);
        root2.setPrefSize(700, 700);
        root2.setAlignment(Pos.CENTER);
        root2.setSpacing(60);
        root2.setBackground(new Background(new BackgroundFill(
                Color.rgb(157, 242, 177), null, null)));
        mainGame.setScene4(new Scene(root2, 750, 750));
    }
}
