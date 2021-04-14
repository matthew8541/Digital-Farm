package main.UserInterface;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import main.Games.Game;
import main.Games.Market;
import main.Games.Player;
import main.Items.Item;
import main.Items.Seed;

import java.io.FileInputStream;
import java.util.concurrent.atomic.AtomicReference;


public class GameRunner extends Application {
    private Stage window;
    private Scene scene1;
    private Scene scene2;
    private Scene scene3;
    private Scene scene4;
    private Scene marketScene;
    private Game game;
    private Player player;
    // private Inventory inventory;
    private Market market;
    private InfoUI playerInfobar;
    private InfoUI marketInfobar;
    private InventoryUI marketHotbar;
    private InventoryUI farmHotbar;
    private FarmUI farm;
    private static String fileExt = "M2/";
    private ComboBox comboSpecies;
    private Seed[][] cropField;

    public static void main(String[] args) {
        launch(args);
    }


    public void start(Stage stage) {
        window = stage;
        stage.setTitle("Digital Farm");

        // Create scene 1 Objects
        ConfigUI.createScene2(this);
        scene1 = WelcomeUI.createScene1(fileExt, scene1, scene2, window);
        // End of Scene 1 Objects
        AudioClip music = new AudioClip("https://vgmsite.com/soundtracks/minecraft/cbxfqqxf/Volume%20Alpha%20-%2008%20-%20Minecraft.mp3");
        music.setCycleCount(AudioClip.INDEFINITE);
        music.play();

        window.setScene(scene1);
        window.show();
    }

    /*
     * create game and player instance once the proceed button is hit
     */
    public void createInstance(String name, String season, String difficulty,
                               String seed, AtomicReference<String> characImg) {
        game = new Game(difficulty, season);

        Image character;
        try {
            character = new Image(new FileInputStream(getFileExt()
                    + "src/main/images/configFarmer" + characImg + ".png"));
        } catch (Exception ignored) {
            System.out.println("ERROR: Could not find alpha.png");
            character = null;
        }

        player = new Player(game, name, character);
        player.getInventory().add(new Item(1, "WateringCan", 1));
        player.getInventory().add(new Seed(5, seed, 5, 5));
        market = new Market(game);
        cropField = null;


        // See the input in the terminal for debugging reference
        System.out.printf("Game Difficulty: %s\n", game.getDifficulty());
        System.out.printf("Game Season: %s\n", game.getSeason());
        System.out.printf("Seed Choice: %s\n", seed);
        System.out.printf("Player Name: %s\n", player.getName());
        System.out.printf("Player Money: %f\n", player.getMoney());
        System.out.println(player.getInventory().toString());
        this.market.printMarketPrice();

    }

    public Stage getWindow() {
        return window;
    }


    public Scene getScene1() {
        return scene1;
    }


    public Scene getScene2() {
        return scene2;
    }

    public void setScene2(Scene scene2) {
        this.scene2 = scene2;
    }


    public Scene getScene3() {
        return scene3;
    }

    public void setScene3(Scene scene3) {
        this.scene3 = scene3;
    }

    public Scene getScene4() {
        return scene4;
    }

    public void setScene4(Scene scene4) {
        this.scene4 = scene4;
    }

    public Game getGame() {
        return game;
    }


    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public static void setFileExt() {
        fileExt = "";
    }

    public static String getFileExt() {
        return fileExt;
    }

    public Market getMarket() {
        return market;
    }

    public Scene getMarketScene() {
        return marketScene;
    }

    public void setMarketScene(Scene scene) {
        marketScene = scene;
    }

    public InfoUI getPlayerInfobar() {
        return playerInfobar;
    }

    public InfoUI getMarketInfobar() {
        return marketInfobar;
    }

    public void setMarketInfobar(InfoUI bar) {
        marketInfobar = bar;
    }

    public InventoryUI getMarketHotbar() {
        return marketHotbar;
    }

    public void setMarketHotbar(InventoryUI bar) {
        marketHotbar = bar;
    }

    public InventoryUI getFarmHotbar() {
        return farmHotbar;
    }

    public void setFarmHotbar(InventoryUI bar) {
        farmHotbar = bar;
    }

    public FarmUI getFarmUI() {
        return farm;
    }

    public void updateMarketScene() {
        comboSpecies.getItems().clear();
        if (this.getFarmUI().getExpansionLevel() < 3) {
            comboSpecies.getItems().add("Bean (Seed: $" + market.getItem(0).getValue() + ")");
            comboSpecies.getItems().add("Rice (Seed: $" + market.getItem(1).getValue() + ")");
            comboSpecies.getItems().add("Wheat (Seed: $" + market.getItem(2).getValue() + ")");
            comboSpecies.getItems().add("Corn (Seed: $" + market.getItem(3).getValue() + ")");
            comboSpecies.getItems().add("Barley (Seed: $" + market.getItem(4).getValue() + ")");
            comboSpecies.getItems().add("Fertilizer $" + market.getItem(10).getValue());
            comboSpecies.getItems().add("Pesticide $" + market.getItem(11).getValue());
            comboSpecies.getItems().add("Expansion $" + market.getItem(17).getValue());
            comboSpecies.getItems().add("Tractor $" + market.getItem(18).getValue());
            comboSpecies.getItems().add("Irrigation $" + market.getItem(19).getValue());

            comboSpecies.setPromptText("Select Items");
        } else {
            comboSpecies.getItems().add("Bean (Seed: $" + market.getItem(0).getValue() + ")");
            comboSpecies.getItems().add("Rice (Seed: $" + market.getItem(1).getValue() + ")");
            comboSpecies.getItems().add("Wheat (Seed: $" + market.getItem(2).getValue() + ")");
            comboSpecies.getItems().add("Corn (Seed: $" + market.getItem(3).getValue() + ")");
            comboSpecies.getItems().add("Barley (Seed: $" + market.getItem(4).getValue() + ")");
            comboSpecies.getItems().add("Fertilizer $" + market.getItem(10).getValue());
            comboSpecies.getItems().add("Pesticide $" + market.getItem(11).getValue());
            comboSpecies.getItems().add("Tractor $" + market.getItem(18).getValue());
            comboSpecies.getItems().add("Irrigation $" + market.getItem(19).getValue());
            comboSpecies.setPromptText("Select Items");
        }
    }

    public Seed[][] getCropField() {
        return cropField;
    }

    public void setCropField(Seed[][] cropField) {
        this.cropField = cropField;
    }

    public ComboBox getComboSpecies() {
        return comboSpecies;
    }

    public void setComboSpecies(ComboBox c) {
        comboSpecies = c;
    }

    public void setPlayerInfobar(InfoUI bar) {
        playerInfobar = bar;
    }
}
