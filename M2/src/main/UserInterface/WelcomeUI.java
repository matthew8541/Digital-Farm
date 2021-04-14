package main.UserInterface;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class WelcomeUI {
    public static Scene createScene1(String fileExt, Scene scene1, Scene scene2, Stage window) {
        // Scene 1
        Image img;
        try {
            img = new Image(new FileInputStream(fileExt + "src/main/images/welcomeFarm.png"));
        } catch (Exception ignored) {
            System.out.println("ERROR: Could not find alpha.png");
            img = null;
        }
        ImageView imgView = new ImageView(img);
        imgView.setFitHeight(375);
        imgView.setFitWidth(375);
        VBox imgBox = new VBox();
        imgBox.setAlignment(Pos.CENTER);
        imgBox.getChildren().add(imgView);

        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.color(0.4f, 0.4f, 0.4f));

        Text gameName = new Text("Digital Farm");
        gameName.setFill(Color.WHITE);
        gameName.setFont(Font.font("Verdana", FontWeight.BOLD, 80));
        gameName.setStyle("-fx-stroke: #f5a42a; -fx-stroke-width: 2;");
        gameName.setEffect(ds);
        gameName.setCache(true);
        gameName.setId("gameName");

        Text developer = new Text("\n\nGame Development Team: Haphazard Hackers");
        developer.setFill(Color.BLACK);
        developer.setFont(Font.font("Verdana", 20));
        developer.setId("developers");

        Button playBtn = new Button();
        playBtn.setPrefSize(100, 40);
        playBtn.setStyle("-fx-border-color: white; -fx-border-width: 3px; "
                + "-fx-background-color: #f5a42a");
        playBtn.setText("Play");
        playBtn.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
        playBtn.setId("play");

        VBox root1 = new VBox(imgBox, gameName, playBtn, developer);
        root1.setPrefSize(900, 900);
        root1.setAlignment(Pos.CENTER);
        root1.setSpacing(60);
        root1.setBackground(new Background(new BackgroundFill(
                Color.rgb(157, 242, 177), null, null)));
        root1.setId("scene1vbox");

        scene1 = new Scene(root1, 1000, 1000);
        playBtn.setOnAction(event -> window.setScene(scene2));
        return scene1;
    }
}
