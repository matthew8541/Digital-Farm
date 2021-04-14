package main.UserInterface;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.Items.Item;
import main.Items.Seed;

import java.util.Random;
import java.io.FileInputStream;

public class FarmUI {
    private static GridPane farm = new GridPane();
    private static HBox staminaBars = new HBox();
    private static boolean rainOccured = false;
    private static boolean locustsKill = false;
    private static int expansionLevel = 0;
    private static int killedCrops = 0;
    public static void createScene3(GameRunner mainGame) {
        Button marketBtn = new Button();
        Seed[][] cropField = mainGame.getCropField();
        createCropField(mainGame, cropField);
        farm.setHgap(2);
        farm.setVgap(2);
        farm.setAlignment(Pos.CENTER);
        Image character = mainGame.getPlayer().getCharacter();
        ImageView characterView = new ImageView(character);
        characterView.setFitHeight(80);
        characterView.setFitWidth(80);
        marketBtn.setPrefSize(200, 50);
        marketBtn.setText("Market");
        marketBtn.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        marketBtn.setId("market");
        marketBtn.setOnAction(e -> {
            mainGame.getWindow().setScene(mainGame.getMarketScene());
            mainGame.getMarketHotbar().updateHotbar();
        });
        InfoUI playerInfobar = new InfoUI(mainGame.getPlayer(), mainGame.getGame());
        mainGame.setPlayerInfobar(playerInfobar);
        HBox infoBar = new HBox();
        infoBar.setAlignment(Pos.TOP_CENTER);
        infoBar.setSpacing(10);
        infoBar.getChildren().addAll(characterView, playerInfobar.getInfoUI(), marketBtn);
        // Increment the day by 1
        Button nextDayBtn = new Button();
        Random rand = new Random();
        nextDayBtn.setPrefSize(200, 50);
        nextDayBtn.setText("Next Day");
        nextDayBtn.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        nextDayBtn.setId("nextDay");
        nextDayBtn.setOnAction(e -> {
            int randomEvent = rand.nextInt(100);
            int rain = rand.nextInt(2) + 1;
            locustsKill = false;
            rainOccured = false;
            killedCrops = 0;
            mainGame.getPlayer().getCurrGame().incrementDay();
            playerInfobar.getInfoUI();
            mainGame.getMarketInfobar().getInfoUI();
            mainGame.getMarket().generateNewPriceDaily();
            mainGame.updateMarketScene();
            mainGame.getPlayer().resetStaminas();
            updateStaminaBars(mainGame);

            // Update growthTime of the seeds in the farm grid
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 8; j++) {
                    if (cropField[i][j] != null) {
                        if (randomEvent < (10 * mainGame.getGame().getDifficultyInt()) / 2) {

                            cropField[i][j].waterPlant(rain);
                        } else if (randomEvent < (20 * mainGame.getGame().getDifficultyInt()) / 2) {
                            int locustChance = rand.nextInt(100);
                            if (locustChance < (20 * mainGame.getGame().getDifficultyInt())
                                    && !cropField[i][j].getHasPesticide()) {
                                cropField[i][j].killPlant();
                                killedCrops++;
                            }
                        }
                        cropField[i][j].addGrowthTime();
                        cropField[i][j].waterPlant(-1);
                        ImageView farmSpot = (ImageView) ((StackPane) farm.getChildren().get(i * 8
                                + j)).getChildren().get(1);
                        farmSpot.setImage(cropField[i][j].getIcon());
                        StackPane overlay = (StackPane) ((StackPane) farm.getChildren().get(i * 8
                                + j)).getChildren().get(2);
                        overlay.setStyle("-fx-background-color: rgba(0, 0, 0, "
                                + cropField[i][j].getWaterLevel() * 0.1 + ");");

                        GridPane fertilizerPane = (GridPane) ((StackPane) farm.getChildren().get(
                                i * 8 + j)).getChildren().get(0);
                        Image blankplot;
                        try {
                            blankplot = new Image(new FileInputStream(GameRunner.getFileExt()
                                    + "src/main/images/alpha.png"));
                        } catch (Exception ignored) {
                            blankplot = null;
                            System.out.println("Could not find alpha.png");
                        }
                        ImageView fertilizer1 = (ImageView) ((VBox) fertilizerPane.getChildren()
                                .get(0)).getChildren().get(0);
                        ImageView fertilizer2 = (ImageView) ((VBox) fertilizerPane.getChildren()
                                .get(0)).getChildren().get(1);
                        ImageView fertilizer3 = (ImageView) ((VBox) fertilizerPane.getChildren()
                                .get(0)).getChildren().get(2);

                        if (cropField[i][j].getFertilizeLevel() == 3) {
                            System.out.println("Fertilizer3 wear off...");
                            cropField[i][j].fertilizePlant();
                            fertilizer3.setImage(blankplot);
                        } else if (cropField[i][j].getFertilizeLevel() == 2) {
                            System.out.println("Fertilizer2 wear off...");
                            cropField[i][j].fertilizePlant();
                            fertilizer2.setImage(blankplot);
                        } else if (cropField[i][j].getFertilizeLevel() == 1) {
                            System.out.println("Fertilizer1 wear off...");
                            cropField[i][j].fertilizePlant();
                            fertilizer1.setImage(blankplot);
                        }

                        System.out.printf("%d, %d\n", i, j);
                        System.out.println(cropField[i][j].getName());
                        System.out.println(cropField[i][j].getGrowthTime());
                    }
                }
            }
            if (randomEvent < (20 * mainGame.getGame().getDifficultyInt()) / 2) {
                if (randomEvent < (10 * mainGame.getGame().getDifficultyInt()) / 2) {
                    rainOccured = true;
                    createAlert("Last night it rained. Each of your crops water level"
                            + " increased by " + rain + ".");
                } else if (randomEvent < (20 * mainGame.getGame().getDifficultyInt()) / 2) {
                    locustsKill = true;
                    createAlert("Last night Locusts attacked your crops. "
                            + killedCrops + " crops died.");
                }

            }
            boolean empty = true;
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 8; j++) {
                    if (cropField[i][j] != null) {
                        empty = false;
                        break;
                    }
                }
            }
            if (empty
                    && mainGame.getPlayer().getMoney() <= mainGame.getMarket().getItem(5).getValue()
                    && !mainGame.getPlayer().getInventory().checkSeeds()) {
                GameEndUI.createScene4(mainGame);
                mainGame.getWindow().setScene(mainGame.getScene4());
            }
        });
        VBox root3 = new VBox();
        root3.setPrefSize(900, 900);
        root3.setAlignment(Pos.TOP_CENTER);
        root3.setSpacing(30);
        root3.setBackground(new Background(new BackgroundFill(
                Color.rgb(157, 242, 177), null, null)));
        mainGame.setFarmHotbar(new InventoryUI(mainGame.getPlayer()));
        staminaBars = drawBars(mainGame);
        root3.getChildren().addAll(infoBar, farm,
                mainGame.getFarmHotbar().getInventoryUI(), nextDayBtn, staminaBars);

        // stack.getChildren().addAll(infoBar, farmPane);
        mainGame.setScene3(new Scene(root3, 1000, 1000));
    }

    private static void createCropField(GameRunner mainGame, Seed[][] cropField) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 8; j++) {
                final int size = 80;
                StackPane container = new StackPane();
                GridPane fertilizerPane = new GridPane();
                container.setId("CropNum" + i + "" + j);
                GridPane pesticidePane = new GridPane();
                Image blankplot;
                Image treePlot;
                try {
                    blankplot = new Image(new FileInputStream(GameRunner.getFileExt()
                            + "src/main/images/alpha.png"));
                    treePlot = new Image(new FileInputStream(GameRunner.getFileExt()
                            + "src/main/images/tree.png"));
                } catch (Exception ignored) {
                    blankplot = null;
                    treePlot = null;
                    System.out.println("Could not find alpha/tree.png");
                }
                ImageView plot = new ImageView(blankplot);

                ImageView fertilizerView1 = new ImageView(blankplot);
                ImageView fertilizerView2 = new ImageView(blankplot);
                ImageView fertilizerView3 = new ImageView(blankplot);
                ImageView pesticideView = new ImageView(blankplot);
                VBox fertilizerVbox = new VBox();
                VBox pesticideVbox = new VBox();
                fertilizerVbox.getChildren().addAll(fertilizerView1, fertilizerView2,
                        fertilizerView3);
                fertilizerPane.getChildren().addAll(fertilizerVbox);

                pesticideVbox.getChildren().addAll(pesticideView);
                pesticidePane.getChildren().addAll(pesticideVbox);
                Image finalBlankplot = blankplot;
                int finalI = i;
                int finalJ = j;

                container.setOnMouseClicked(event -> {
                    ImageView image = (ImageView) container.getChildren().get(1);
                    String hotBarSelection = InventoryUI.getLastClickedName();
                    int hbLen = hotBarSelection.length();
                    if (image.getImage().equals(finalBlankplot) && !hotBarSelection.equals("")
                            && hotBarSelection.substring(hbLen - 4).equals("Seed")) {
                        try {
                            Seed seed = (Seed)
                                    mainGame.getPlayer().getInventory().get(hotBarSelection);
                            cropField[finalI][finalJ] = new Seed(1, hotBarSelection.substring(0,
                                    hbLen - 4), seed.getValue(), seed.getMaxGrowthTime());
                            plot.setImage(seed.getIcon());
                            mainGame.getPlayer().getInventory().remove(hotBarSelection, 1);
                            mainGame.getFarmHotbar().updateHotbar();
                        } catch (NullPointerException ne) {
                        }
                    } else if (hotBarSelection.equals("WateringCan")) {
                        if (mainGame.getPlayer().getWateringStamina() > 0) {
                            Seed plant = cropField[finalI][finalJ];
                            if (plant != null) {
                                System.out.println("WATERING");
                                mainGame.getPlayer().decreaseWateringStamina();
                                plant.waterPlant(1);
                                if (plant.getWaterLevel() <= 5) {
                                    container.getChildren().get(2).setStyle("-fx-background-col"
                                            + "or: rgba(0, 0, 0, "
                                            + plant.getWaterLevel() * 0.1 + ");");
                                }
                            }
                        } else {
                            createAlert("You are exhausted, considering buying irrigation "
                                    + "to increase your daily maximum watering stamina");
                        }
                    } else if (hotBarSelection.equals("Fertilizer")) {
                        Item fertilizer =
                                mainGame.getPlayer().getInventory().get(hotBarSelection);
                        Seed plant = cropField[finalI][finalJ];
                        if (plant != null && !plant.isMature()
                                && !fertilizerView1.getImage().equals(fertilizer.getIcon())) {
                            updateFP(mainGame, fertilizerView1,
                                    hotBarSelection, fertilizer, plant);
                        } else if (plant != null && !plant.isMature()
                                && !fertilizerView2.getImage().equals(fertilizer.getIcon())) {
                            updateFP(mainGame, fertilizerView2,
                                    hotBarSelection, fertilizer, plant);
                        } else if (plant != null && !plant.isMature()
                                && !fertilizerView3.getImage().equals(fertilizer.getIcon())) {
                            updateFP(mainGame, fertilizerView3,
                                    hotBarSelection, fertilizer, plant);
                        }
                    } else if (hotBarSelection.equals("Pesticide")) {
                        Item pesticide =
                                mainGame.getPlayer().getInventory().get(hotBarSelection);
                        Seed plant = cropField[finalI][finalJ];
                        if (plant != null && !plant.isMature()
                                && !pesticideView.getImage().equals(pesticide.getIcon())) {
                            System.out.println("Pesticide has been implemented");
                            plant.setHasPesticide(true);
                            updateP(mainGame, pesticideView, hotBarSelection, pesticide);
                        }
                    } else if (!image.getImage().equals(finalBlankplot)) {
                        if (mainGame.getPlayer().getHarvestingStamina() > 0) {
                            Seed crop = cropField[finalI][finalJ];
                            if (crop.isMature() && crop.getGrowthRate() != 0) {
                                Random rand = new Random();
                                double getExtraCrop = rand.nextDouble();
                                if (getExtraCrop >= 0.75 && crop.getFertilizeLevel() > 0) {
                                    mainGame.getPlayer().getInventory().add(crop.getCrop());
                                }
                                mainGame.getPlayer().decreaseHarvestingStamina();
                                mainGame.getPlayer().getInventory().add(crop.getCrop());
                                cropField[finalI][finalJ] = null;
                                setImages(plot, fertilizerView1, fertilizerView2,
                                        fertilizerView3, finalBlankplot);
                                pesticideView.setImage(finalBlankplot);
                                container.getChildren().get(2).setStyle("-fx-background-color: "
                                        + "rgba(0, 0, 0, 0);");
                                mainGame.getFarmHotbar().updateHotbar();
                            } else if (crop.getGrowthRate() == 0) {
                                System.out.println("last else if");
                                cropField[finalI][finalJ] = null;
                                plot.setImage(finalBlankplot);
                                mainGame.getPlayer().decreaseHarvestingStamina();
                                setImages(fertilizerView1, fertilizerView2,
                                        fertilizerView3, pesticideView, finalBlankplot);
                                container.getChildren().get(2).setStyle("-fx-background-color: "
                                        + "rgba(0, 0, 0, 0);");
                            }
                        } else {
                            createAlert("You are exhausted, consider buying a tractor "
                                    + "to increase your daily maximum harvesting stamina");
                        }
                    }
                    updateStaminaBars(mainGame);
                });

                StackPane overlay = new StackPane();
                setSizes(size, fertilizerPane, pesticidePane, plot, overlay);
                fertilizerPane.setAlignment(Pos.BOTTOM_LEFT);
                pesticidePane.setAlignment(Pos.BOTTOM_RIGHT);
                container.setStyle("-fx-border-color: green;" + "-fx-border-width: 2;");
                container.getChildren().addAll(fertilizerPane, plot, overlay, pesticidePane);
                if (!(i + expansionLevel >= 3 && j + expansionLevel >= 3
                        && i - expansionLevel < 10 - 3 && j - expansionLevel < 8 - 3)) {
                    plot.setImage(treePlot);
                    container.setDisable(true);
                }
                farm.add(container, i, j);
            }
        }
    }

    public static void farmExpansionUI(int expansion, GameRunner mainGame, Seed[][] cropField) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 8; j++) {

                Image blankplot;
                try {
                    blankplot = new Image(new FileInputStream(GameRunner.getFileExt()
                            + "src/main/images/alpha.png"));
                } catch (Exception ignored) {
                    blankplot = null;
                    System.out.println("Could not find alpha.png");
                }
                if (i + expansionLevel >= 3 && j + expansionLevel >= 3
                        && i - expansionLevel < 10 - 3
                        && j - expansionLevel < 8 - 3) {
                    if (!(i + expansionLevel - expansion >= 3 && j + expansionLevel - expansion >= 3
                            && i - expansionLevel + expansion < 10 - 3
                            && j - expansionLevel + expansion < 8 - 3)) {
                        StackPane container = (StackPane) farm.getChildren().get(i * 8 + j);
                        container.setDisable(false);
                        ImageView plot = (ImageView) ((StackPane) farm.getChildren().get(i * 8
                                + j)).getChildren().get(1);
                        plot.setImage(blankplot);
                        int finalI = i;
                        int finalJ = j;
                        Image finalBlankplot = blankplot;
                        ImageView fertilizerView1 = (ImageView) ((VBox) ((GridPane) ((StackPane)
                                farm.getChildren().get(i * 8 + j)).getChildren().get(0))
                                .getChildren().get(0)).getChildren().get(0);

                        ImageView fertilizerView2 = (ImageView) ((VBox) ((GridPane) ((StackPane)
                                farm.getChildren().get(i * 8 + j)).getChildren().get(0))
                                .getChildren().get(0)).getChildren().get(1);

                        ImageView fertilizerView3 = (ImageView) ((VBox) ((GridPane) ((StackPane)
                                farm.getChildren().get(i * 8 + j)).getChildren().get(0))
                                .getChildren().get(0)).getChildren().get(2);

                        ImageView pesticideView = (ImageView) ((VBox) ((GridPane) ((StackPane)
                                farm.getChildren().get(i * 8 + j)).getChildren().get(3))
                                .getChildren().get(0)).getChildren().get(0);
                        container.setOnMouseClicked(event -> {
                            ImageView image = (ImageView) container.getChildren().get(1);
                            String hotBarSelection = InventoryUI.getLastClickedName();
                            int hbLen = hotBarSelection.length();
                            if (image.getImage().equals(finalBlankplot) && !hotBarSelection
                                    .equals("") && hotBarSelection.substring(hbLen - 4)
                                    .equals("Seed")) {
                                try {
                                    Seed seed = (Seed)
                                            mainGame.getPlayer().getInventory()
                                                    .get(hotBarSelection);
                                    cropField[finalI][finalJ] = new Seed(1,
                                            hotBarSelection.substring(0,
                                            hbLen - 4), seed.getValue(), seed.getMaxGrowthTime());
                                    plot.setImage(seed.getIcon());
                                    mainGame.getPlayer().getInventory().remove(hotBarSelection, 1);
                                    mainGame.getFarmHotbar().updateHotbar();
                                } catch (NullPointerException ne) {
                                }
                            } else if (hotBarSelection.equals("WateringCan")) {
                                if (mainGame.getPlayer().getWateringStamina() > 0) {
                                    Seed plant = cropField[finalI][finalJ];
                                    if (plant != null) {
                                        System.out.println("WATERING");
                                        mainGame.getPlayer().decreaseWateringStamina();
                                        plant.waterPlant(1);
                                        if (plant.getWaterLevel() <= 5) {
                                            container.getChildren().get(2).setStyle("-fx-background"
                                                    + "-" + "col" + "or: rgba(0, 0, 0, "
                                                    + plant.getWaterLevel() * 0.1 + ");");
                                        }
                                    }
                                } else {
                                    createAlert("You are exhausted, considering buying irrigation "
                                            + "to increase your daily maximum watering stamina");
                                }
                            } else if (hotBarSelection.equals("Fertilizer")) {
                                Item fertilizer =
                                        mainGame.getPlayer().getInventory().get(hotBarSelection);
                                Seed plant = cropField[finalI][finalJ];
                                if (plant != null && !plant.isMature()
                                        && !fertilizerView1.getImage().equals(fertilizer
                                        .getIcon())) {
                                    updateFP(mainGame, fertilizerView1,
                                            hotBarSelection, fertilizer, plant);
                                } else if (plant != null && !plant.isMature()
                                        && !fertilizerView2.getImage().equals(fertilizer
                                        .getIcon())) {
                                    updateFP(mainGame, fertilizerView2,
                                            hotBarSelection, fertilizer, plant);
                                } else if (plant != null && !plant.isMature()
                                        && !fertilizerView3.getImage().equals(fertilizer
                                        .getIcon())) {
                                    updateFP(mainGame, fertilizerView3,
                                            hotBarSelection, fertilizer, plant);
                                }
                            } else if (hotBarSelection.equals("Pesticide")) {
                                Item pesticide =
                                        mainGame.getPlayer().getInventory()
                                                .get(hotBarSelection);
                                Seed plant = cropField[finalI][finalJ];
                                if (plant != null && !plant.isMature()
                                        && !pesticideView.getImage().equals(pesticide
                                        .getIcon())) {
                                    System.out.println("Pesticide has been implemented");
                                    plant.setHasPesticide(true);
                                    updateP(mainGame, pesticideView, hotBarSelection, pesticide);
                                }
                            } else if (!image.getImage().equals(finalBlankplot)) {
                                if (mainGame.getPlayer().getHarvestingStamina() > 0) {
                                    Seed crop = cropField[finalI][finalJ];
                                    if (crop.isMature() && crop.getGrowthRate() != 0) {
                                        Random rand = new Random();
                                        double getExtraCrop = rand.nextDouble();
                                        if (getExtraCrop >= 0.75 && crop.getFertilizeLevel() > 0) {
                                            mainGame.getPlayer().getInventory().add(crop.getCrop());
                                        }
                                        mainGame.getPlayer().decreaseHarvestingStamina();
                                        mainGame.getPlayer().getInventory().add(crop.getCrop());
                                        cropField[finalI][finalJ] = null;
                                        setImages(plot, fertilizerView1, fertilizerView2,
                                                fertilizerView3, finalBlankplot);
                                        pesticideView.setImage(finalBlankplot);
                                        container.getChildren().get(2).setStyle("-fx-background"
                                                + "-color" + ": " + "rgba(0, 0, 0, 0);");
                                        mainGame.getFarmHotbar().updateHotbar();
                                    } else if (crop.getGrowthRate() == 0) {
                                        System.out.println("last else if");
                                        cropField[finalI][finalJ] = null;
                                        plot.setImage(finalBlankplot);
                                        mainGame.getPlayer().decreaseHarvestingStamina();
                                        setImages(fertilizerView1, fertilizerView2,
                                                fertilizerView3, pesticideView, finalBlankplot);
                                        container.getChildren().get(2).setStyle("-fx-background"
                                                + "-color" + ": " + "rgba(0, 0, 0, 0);");
                                    }
                                } else {
                                    createAlert("You are exhausted, consider buying a tractor "
                                            + "to increase your daily maximum harvesting stamina");
                                }
                            }
                            updateStaminaBars(mainGame);
                        });
                    }
                }
            }
        }
    }

    private static void setSizes(int size, GridPane fertilizerPane,
                                 GridPane pesticidePane, ImageView plot, StackPane overlay) {
        overlay.setMinWidth(size - 30);
        overlay.setMinHeight(size - 30);
        plot.setFitWidth(size - 30);
        plot.setFitHeight(size - 30);
        fertilizerPane.setMinWidth(size);
        fertilizerPane.setMinHeight(size);
        pesticidePane.setMinWidth(size);
        pesticidePane.setMinHeight(size);
    }

    private static void setImages(ImageView fertilizerView1, ImageView fertilizerView2,
                                  ImageView fertilizerView3, ImageView pesticideView,
                                  Image finalBlankplot) {
        fertilizerView1.setImage(finalBlankplot);
        fertilizerView2.setImage(finalBlankplot);
        fertilizerView3.setImage(finalBlankplot);
        pesticideView.setImage(finalBlankplot);
    }

    private static void updateP(GameRunner mainGame,
                                ImageView pesticideView, String hotBarSelection, Item pesticide) {
        pesticideView.setImage(pesticide.getIcon());
        pesticideView.setFitHeight(15);
        pesticideView.setFitWidth(15);

        mainGame.getPlayer().getInventory().remove(hotBarSelection, 1);
        mainGame.getFarmHotbar().updateHotbar();
    }

    private static void updateFP(GameRunner mainGame,
                                 ImageView fertilizerView1, String hotBarSelection,
                                 Item fertilizer, Seed plant) {
        fertilizerView1.setImage(fertilizer.getIcon());
        fertilizerView1.setFitHeight(12);
        fertilizerView1.setFitWidth(12);
        plant.addFertilizerLevel();
        mainGame.getPlayer().getInventory().remove(hotBarSelection, 1);
        mainGame.getFarmHotbar().updateHotbar();
    }

    public static void updateFarmUI(GameRunner mainGame, Seed[][] cropField) {
        farm.getChildren().clear();
        createCropField(mainGame, cropField);
    }

    private static HBox drawBars(GameRunner mainGame) {
        StackPane wateringBar = new StackPane();
        StackPane harvestingBar = new StackPane();
        Rectangle wateringBorder = new Rectangle(400, 50);
        Rectangle harvestingBorder = new Rectangle(400, 50);
        wateringBorder.setStroke(Color.DARKBLUE);
        wateringBorder.setFill(Color.TRANSPARENT);
        harvestingBorder.setStroke(Color.BROWN);
        harvestingBorder.setFill(Color.TRANSPARENT);

        double wateringPercent =  ((double) mainGame.getPlayer().getWateringStamina()
                /  mainGame.getPlayer().getMaxWateringStamina());
        double harvestingPercent =  ((double) mainGame.getPlayer().getHarvestingStamina()
                /  mainGame.getPlayer().getMaxHarvestingStamina());


        Rectangle wateringStamina = new Rectangle((int) (wateringPercent * 400), 50);
        wateringStamina.setFill(Color.BLUE);
        Rectangle harvestingStamina = new Rectangle((int) (harvestingPercent * 400), 50);
        harvestingStamina.setFill(Color.SADDLEBROWN);

        HBox hbox = new HBox();

        hbox.setSpacing(20);
        wateringBar.getChildren().addAll(wateringStamina, wateringBorder);
        harvestingBar.getChildren().addAll(harvestingStamina, harvestingBorder);
        StackPane.setAlignment(wateringStamina, Pos.CENTER_LEFT);
        StackPane.setAlignment(harvestingStamina, Pos.CENTER_LEFT);

        hbox.getChildren().addAll(wateringBar, harvestingBar);
        hbox.setAlignment(Pos.TOP_CENTER);
        return hbox;
    }

    public static void updateStaminaBars(GameRunner mainGame) {
        double wateringPercent =  ((double) mainGame.getPlayer().getWateringStamina()
                /  mainGame.getPlayer().getMaxWateringStamina());
        double harvestingPercent =  ((double) mainGame.getPlayer().getHarvestingStamina()
                /  mainGame.getPlayer().getMaxHarvestingStamina());
        Rectangle waterBar = (Rectangle) ((StackPane) staminaBars
                .getChildren().get(0)).getChildren().get(0);
        Rectangle harvestingBar = (Rectangle) ((StackPane) staminaBars
                .getChildren().get(1)).getChildren().get(0);

        waterBar.setWidth(400 * wateringPercent);
        harvestingBar.setWidth(400 * harvestingPercent);
    }

    public static boolean getLocustsKilled() {
        return locustsKill;
    }
    public static boolean getRainOccured() {
        return rainOccured;
    }
    public static int getKilledCrops() {
        return killedCrops;
    }

    public static void farmExpansion(int increment) {
        if (expansionLevel <= 3) {
            expansionLevel += increment;
        }
    }

    public static int getExpansionLevel() {
        return expansionLevel;
    }

    private static void createAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        alert.getDialogPane().getButtonTypes().add(type);
        Button okButton = (Button) alert.getDialogPane().lookupButton(type);
        alert.setContentText(message);
        okButton.setId("okbtn");
        alert.showAndWait();
    }
}

