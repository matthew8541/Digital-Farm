package main.UserInterface;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import main.Items.Seed;

import java.io.FileInputStream;
import java.util.concurrent.atomic.AtomicReference;

public class ConfigUI {
    public static void createScene2(GameRunner mainGame) {
        DropShadow configds = new DropShadow();
        configds.setOffsetY(3.0f);
        configds.setColor(Color.color(0.4f, 0.4f, 0.4f));
        Text configHead = new Text("Configuration");
        configHead.setFill(Color.WHITE);
        configHead.setFont(Font.font("Verdana", FontWeight.BOLD, 60));
        configHead.setStyle("-fx-stroke: #f5a42a; -fx-stroke-width: 2;");
        configHead.setEffect(configds);
        configHead.setCache(true);
        configHead.setId("configHead");
        Text nameInvalid = new Text("Player name cannot be empty!");
        nameInvalid.setId("nameInvalid");
        nameInvalid.setFill(Color.rgb(237, 59, 95));
        nameInvalid.setFont(Font.font("Verdana", 20));
        nameInvalid.setVisible(false);
        Text seasonInvalid = new Text("Please select a season!");
        seasonInvalid.setId("seasonInvalid");
        seasonInvalid.setFill(Color.rgb(237, 59, 95));
        seasonInvalid.setFont(Font.font("Verdana", 20));
        seasonInvalid.setVisible(false);
        Text levelInvalid = new Text("Please select a difficulty!");
        levelInvalid.setId("levelInvalid");
        levelInvalid.setFill(Color.rgb(237, 59, 95));
        levelInvalid.setFont(Font.font("Verdana", 20));
        levelInvalid.setVisible(false);
        Text seedInvalid = new Text("Please select a type of seed!");
        seedInvalid.setId("seedInvalid");
        seedInvalid.setFill(Color.rgb(237, 59, 95));
        seedInvalid.setFont(Font.font("Verdana", 20));
        seedInvalid.setVisible(false);
        VBox inputCheckBox = new VBox();
        inputCheckBox.setAlignment(Pos.CENTER);
        inputCheckBox.setSpacing(10);
        inputCheckBox.getChildren().addAll(configHead, nameInvalid,
                seasonInvalid, levelInvalid, seedInvalid);
        Label nameLabel = new Label("Player Name:");
        nameLabel.setFont(Font.font("Verdena", FontWeight.BOLD, 20));
        TextField nameTextField = new TextField();
        nameTextField.setPromptText("Name");
        nameTextField.setId("nametext");
        HBox textfields = new HBox();
        textfields.setAlignment(Pos.CENTER);
        textfields.setSpacing(10);
        textfields.getChildren().addAll(nameLabel, nameTextField);
        ComboBox[] cmbBoxes = createComboBox();
        ComboBox comboLevel = cmbBoxes[0];
        ComboBox comboSeason = cmbBoxes[1];
        ComboBox comboSeed = cmbBoxes[2];
        HBox combos = new HBox();
        combos.setSpacing(10);
        combos.setAlignment(Pos.CENTER);
        combos.getChildren().addAll(comboSeason, comboLevel, comboSeed);

        AtomicReference<String> characImg = new AtomicReference<>("Male");
        Image imgMale;
        try {
            imgMale = new Image(new FileInputStream(mainGame.getFileExt()
                    + "src/main/images/configFarmerMale.png"));
        } catch (Exception ignored) {
            System.out.println("ERROR: Could not find alpha.png");
            imgMale = null;
        }
        ImageView imgViewMale = new ImageView(imgMale);
        imgViewMale.setFitHeight(150);
        imgViewMale.setFitWidth(150);

        Image imgFemale;
        try {
            imgFemale = new Image(new FileInputStream(mainGame.getFileExt()
                    + "src/main/images/configFarmerFemale.png"));
        } catch (Exception ignored) {
            System.out.println("ERROR: Could not find alpha.png");
            imgFemale = null;
        }
        ImageView imgViewFemale = new ImageView(imgFemale);
        imgViewFemale.setFitHeight(120);
        imgViewFemale.setFitWidth(90);
        imgViewFemale.setOpacity(0.5);
        HBox imgBox = new HBox();
        imgBox.setAlignment(Pos.CENTER);
        imgBox.getChildren().addAll(imgViewMale, imgViewFemale);

        imgViewFemale.setPickOnBounds(true); // allows click on transparent areas
        imgViewFemale.setOnMouseClicked((MouseEvent e) -> {
            imgViewMale.setOpacity(0.5); // change functionality
            imgViewFemale.setOpacity(1.0);
            characImg.set("Female");
        });

        imgViewMale.setPickOnBounds(true); // allows click on transparent areas
        imgViewMale.setOnMouseClicked((MouseEvent e) -> {
            imgViewFemale.setOpacity(0.5); // change functionality
            imgViewMale.setOpacity(1.0);
            characImg.set("Male");
        });

        Button proceedBtn = new Button();
        proceedBtn.setId("proceed");
        proceedBtn.setText("Proceed");
        proceedBtn.setDisable(false);
        proceedBtn.setOnAction(event -> {
            if (!nameTextField.getText().isEmpty()
                    && !comboSeason.getSelectionModel().isEmpty()
                    && !comboLevel.getSelectionModel().isEmpty()
                    && !comboSeed.getSelectionModel().isEmpty()) {
                mainGame.createInstance(nameTextField.getText(), comboSeason.getValue().toString(),
                        comboLevel.getValue().toString(), comboSeed.getValue()
                                .toString(), characImg);
                mainGame.setCropField(new Seed[10][8]);
                FarmUI.createScene3(mainGame);
                MarketUI.createMarket(mainGame);
                mainGame.getWindow().setScene(mainGame.getScene3());
            }
            if (nameTextField.getText().isEmpty()) {
                nameInvalid.setVisible(true);
            } else {
                nameInvalid.setVisible(false);
            }

            if (comboSeason.getSelectionModel().isEmpty()) {
                seasonInvalid.setVisible(true);
            } else {
                seasonInvalid.setVisible(false);
            }

            if (comboLevel.getSelectionModel().isEmpty()) {
                levelInvalid.setVisible(true);
            } else {
                levelInvalid.setVisible(false);
            }

            if (comboSeed.getSelectionModel().isEmpty()) {
                seedInvalid.setVisible(true);
            } else {
                seedInvalid.setVisible(false);
            }
        });
        HBox proceedBox = new HBox();
        proceedBox.setAlignment(Pos.CENTER);
        proceedBox.getChildren().addAll(proceedBtn);

        VBox root2 = new VBox(inputCheckBox, textfields, combos, imgBox, proceedBox);
        root2.setPrefSize(900, 900);
        root2.setAlignment(Pos.CENTER);
        root2.setSpacing(60);
        root2.setBackground(new Background(new BackgroundFill(
                Color.rgb(157, 242, 177), null, null)));
        mainGame.setScene2(new Scene(root2, 1000, 1000));
    }

    private static ComboBox[] createComboBox() {
        ComboBox comboSeason = new ComboBox();
        comboSeason.setId("comboSeason");
        comboSeason.getItems().add("Fall");
        comboSeason.getItems().add("Summer");
        comboSeason.getItems().add("Spring");
        comboSeason.getItems().add("Winter");
        comboSeason.setPromptText("Season");

        ComboBox comboLevel = new ComboBox();
        comboLevel.setId("comboLevel");
        comboLevel.getItems().add("Easy");
        comboLevel.getItems().add("Medium");
        comboLevel.getItems().add("Hard");
        comboLevel.setPromptText("Difficulty");

        ComboBox comboSeed = new ComboBox();
        comboSeed.setId("comboSeed");
        comboSeed.getItems().add("Rice");
        comboSeed.getItems().add("Corn");
        comboSeed.getItems().add("Wheat");
        comboSeed.getItems().add("Bean");
        comboSeed.getItems().add("Barley");
        comboSeed.setPromptText("Seed");

        ComboBox[] comboBoxes = new ComboBox[] {comboLevel, comboSeason, comboSeed};

        return comboBoxes;
    }
}
