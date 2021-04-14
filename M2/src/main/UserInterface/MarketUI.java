package main.UserInterface;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import main.Games.Market;

import java.io.FileInputStream;

public class MarketUI {
    private static Text speciesInvalid;
    private static Text quantityInvalid;
    // private static HBox marketInput;
    // private static VBox marketRoot;

    public static void createMarket(GameRunner mainGame) {
        
        /* Title "Farmer's Market" */
        Text welcomeMarket = new Text("Farmer's Market");
        welcomeMarket.setFill(Color.WHITE);
        welcomeMarket.setFont(Font.font("Verdana", FontWeight.BOLD, 60));
        welcomeMarket.setStyle("-fx-stroke: #f5a42a; -fx-stroke-width: 2;");
        welcomeMarket.setCache(true);
        welcomeMarket.setId("welcomeMarket");

        Image character = mainGame.getPlayer().getCharacter();
        ImageView characterView = new ImageView(character);
        characterView.setFitHeight(80);
        characterView.setFitWidth(80);

        /* Image of the Farmer's Market */
        Image marketImg;
        try {
            marketImg = new Image(new FileInputStream(mainGame.getFileExt()
                    + "src/main/images/market.png"));
        } catch (Exception ignored) {
            System.out.println("ERROR: Could not find market.png");
            marketImg = null;
        }
        ImageView marketImgView = new ImageView(marketImg);
        marketImgView.setFitHeight(350);
        marketImgView.setFitWidth(300);

        HBox shopImgAndSelection = new HBox(marketImgView);
        shopImgAndSelection.setAlignment(Pos.CENTER);
        shopImgAndSelection.setSpacing(10);

        mainGame.setMarketInfobar(new InfoUI(mainGame.getPlayer(), mainGame.getGame()));
        HBox titleAndInfo = new HBox(characterView, mainGame.getMarketInfobar()
                .getInfoUI(), welcomeMarket);
        titleAndInfo.setAlignment(Pos.TOP_CENTER);
        titleAndInfo.setSpacing(10);

        VBox titleAndImage = new VBox(titleAndInfo, shopImgAndSelection);
        titleAndImage.setSpacing(2);

        /* All the warning text:
           1. Please select a type of seed or crop
           2. Please enter a quantity */
        speciesInvalid = new Text("Please select a type of seed or crop");
        speciesInvalid.setId("speciesInvalid");
        speciesInvalid.setFill(Color.rgb(237, 59, 95));
        speciesInvalid.setFont(Font.font("Verdana", 16));
        speciesInvalid.setVisible(false);
        quantityInvalid = new Text("Please enter a quantity");
        quantityInvalid.setId("quantityInvalid");
        quantityInvalid.setFill(Color.rgb(237, 59, 95));
        quantityInvalid.setFont(Font.font("Verdana", 16));
        quantityInvalid.setVisible(false);

        VBox warningBox = new VBox(speciesInvalid, quantityInvalid);
        warningBox.setAlignment(Pos.CENTER);
        warningBox.setSpacing(10);

        /* The switch button to sell & buy */
        Text buyText = new Text("Buy");
        buyText.setId("buyText");
        buyText.setFill(Color.rgb(0, 0, 0));
        buyText.setFont(Font.font("Verdana", 20));

        Text sellText = new Text("Sell");
        sellText.setId("sellText");
        sellText.setFill(Color.rgb(0, 0, 0));
        sellText.setFont(Font.font("Verdana", 20));

        SwitchButton sellOrPurchase = new SwitchButton();
        sellOrPurchase.getButton().setId("sellBuySwitch");

        HBox switchBox = new HBox(buyText, sellOrPurchase, sellText);
        switchBox.setAlignment(Pos.CENTER);
        switchBox.setSpacing(5);
        
        HBox marketInput = new HBox(displayBuyBox(mainGame));
        marketInput.setAlignment(Pos.CENTER);
        marketInput.setSpacing(10);
        // marketInput.getChildren().add(displayBuyBox(mainGame));

        Button returnBtn = new Button();
        returnBtn.setPrefSize(150, 50);
        returnBtn.setText("Return to Farm");
        returnBtn.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        returnBtn.setId("return");
        returnBtn.setOnAction(e -> {
            mainGame.getWindow().setScene(mainGame.getScene3());
            mainGame.getFarmHotbar().updateHotbar();
            FarmUI.updateStaminaBars(mainGame);
        });
        mainGame.setMarketHotbar(new InventoryUI(mainGame.getPlayer()));
        VBox marketRoot = new VBox(titleAndImage, marketImgView, warningBox, switchBox,
                marketInput, mainGame.getMarketHotbar().getInventoryUI(), returnBtn);
        marketRoot.setPrefSize(900, 900);
        marketRoot.setAlignment(Pos.TOP_CENTER);
        marketRoot.setSpacing(30);
        marketRoot.setBackground(new Background(new BackgroundFill(
                Color.rgb(157, 242, 177), null, null)));

        mainGame.setMarketScene(new Scene(marketRoot, 1000, 1000));


        /* Add click action on the switch button */
        sellOrPurchase.getButton().setOnMouseClicked(event -> {
            boolean state = sellOrPurchase.getState();
            System.out.println("Switch Btn state: " + state);
            marketInput.getChildren().clear();
            if (state) { 
                VBox tempBox = displayBuyBox(mainGame);
                sellOrPurchase.getButton().setStyle(sellOrPurchase.getButtonStyleOff());
                sellOrPurchase.getBack().setFill(Color.valueOf("#ced5da"));
                sellOrPurchase.setAlignment(sellOrPurchase.getButton(), Pos.CENTER_LEFT);
                sellOrPurchase.setState(false);
                marketInput.getChildren().add(tempBox);
            } else {
                VBox tempBox = displaySellBox(mainGame);
                sellOrPurchase.getButton().setStyle(sellOrPurchase.getButtonStyleOn());
                sellOrPurchase.getBack().setFill(Color.valueOf("#80C49E"));
                sellOrPurchase.setAlignment(sellOrPurchase.getButton(), Pos.CENTER_RIGHT);
                sellOrPurchase.setState(true);
                marketInput.getChildren().add(tempBox);
            }
        });

        sellOrPurchase.setOnMouseClicked(event -> {
            boolean state = sellOrPurchase.getState();
            System.out.println("Switch Btn state: " + state);
            marketInput.getChildren().clear();
            if (state) {
                VBox tempBox = displayBuyBox(mainGame);
                sellOrPurchase.getButton().setStyle(sellOrPurchase.getButtonStyleOff());
                sellOrPurchase.getBack().setFill(Color.valueOf("#ced5da"));
                sellOrPurchase.setAlignment(sellOrPurchase.getButton(), Pos.CENTER_LEFT);
                sellOrPurchase.setState(false);
                marketInput.getChildren().add(tempBox);
            } else {
                VBox tempBox = displaySellBox(mainGame);
                sellOrPurchase.getButton().setStyle(sellOrPurchase.getButtonStyleOn());
                sellOrPurchase.getBack().setFill(Color.valueOf("#80C49E"));
                sellOrPurchase.setAlignment(sellOrPurchase.getButton(), Pos.CENTER_RIGHT);
                sellOrPurchase.setState(true);
                marketInput.getChildren().add(tempBox);
            }
        });
    }

    public static VBox displayBuyBox(GameRunner mainGame) {
        /* ComboBox dropdown for the type of items */
        mainGame.setComboSpecies(new ComboBox());
        Market market = mainGame.getMarket();
        if (mainGame.getFarmUI().getExpansionLevel() < 3) {
            mainGame.getComboSpecies().setId("comboSpecies");
            mainGame.getComboSpecies().getItems().add("Bean (Seed: $"
                    + market.getItem(0).getValue() + ")");
            mainGame.getComboSpecies().getItems().add("Rice (Seed: $"
                    + market.getItem(1).getValue() + ")");
            mainGame.getComboSpecies().getItems().add("Wheat (Seed: $"
                    + market.getItem(2).getValue() + ")");
            mainGame.getComboSpecies().getItems().add("Corn (Seed: $"
                    + market.getItem(3).getValue() + ")");
            mainGame.getComboSpecies().getItems().add("Barley (Seed: $"
                    + market.getItem(4).getValue() + ")");
            mainGame.getComboSpecies().getItems().add("Fertilizer $"
                    + market.getItem(10).getValue());
            mainGame.getComboSpecies().getItems().add("Pesticide $"
                    + market.getItem(11).getValue());
            mainGame.getComboSpecies().getItems().add("Expansion $"
                    + market.getItem(17).getValue());
            mainGame.getComboSpecies().getItems().add("Tractor $"
                    + market.getItem(18).getValue());
            mainGame.getComboSpecies().getItems().add("Irrigation $"
                    + market.getItem(19).getValue());
            mainGame.getComboSpecies().setPromptText("Select Items");
        } else {
            mainGame.getComboSpecies().setId("comboSpecies");
            mainGame.getComboSpecies().getItems().add("Bean (Seed: $"
                    + market.getItem(0).getValue() + ")");
            mainGame.getComboSpecies().getItems().add("Rice (Seed: $"
                    + market.getItem(1).getValue() + ")");
            mainGame.getComboSpecies().getItems().add("Wheat (Seed: $"
                    + market.getItem(2).getValue() + ")");
            mainGame.getComboSpecies().getItems().add("Corn (Seed: $"
                    + market.getItem(3).getValue() + ")");
            mainGame.getComboSpecies().getItems().add("Barley (Seed: $"
                    + market.getItem(4).getValue() + ")");
            mainGame.getComboSpecies().getItems().add("Fertilizer $"
                    + market.getItem(10).getValue());
            mainGame.getComboSpecies().getItems().add("Pesticide $"
                    + market.getItem(11).getValue());
            mainGame.getComboSpecies().getItems().add("Tractor $"
                    + market.getItem(18).getValue());
            mainGame.getComboSpecies().getItems().add("Irrigation $"
                    + market.getItem(19).getValue());
            mainGame.getComboSpecies().setPromptText("Select Items");
        }

        /* Quantity textfield */
        TextField quantity = new TextField();
        quantity.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                quantity.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        quantity.setId("quantity");
        quantity.setPromptText("quantity");

        /* Purchase button */
        Button purchaseBtn = new Button();
        purchaseBtn.setPrefSize(150, 20);
        purchaseBtn.setText("Purchase");
        purchaseBtn.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        purchaseBtn.setId("purchaseBtn");
        purchaseBtn.setOnMouseClicked((event -> {
            if (mainGame.getComboSpecies().getSelectionModel().isEmpty()) {
                speciesInvalid.setVisible(true);
            } else {
                speciesInvalid.setVisible(false);
            }
            if (quantity.getText().equals("")) {
                quantityInvalid.setVisible(true);
            } else {
                quantityInvalid.setVisible(false);
            }
            if (!(quantity.getText().equals(""))
                    && !(mainGame.getComboSpecies().getSelectionModel().isEmpty())) {
                String item = mainGame.getComboSpecies().getValue().toString().split(" ")[0];
                System.out.println("item name: " + item);
                if (!(item.equals("Fertilizer") || item.equals("Pesticide")
                        || item.equals("Expansion") || item.equals("Tractor")
                        || item.equals("Irrigation"))) {
                    item += "Seed";
                }
                if (item.equals("Expansion")) {
                    if (Integer.parseInt(quantity.getText())
                            >= 3 - mainGame.getFarmUI().getExpansionLevel()) {
                        quantity.setText(Integer.toString(3
                                - mainGame.getFarmUI().getExpansionLevel()));
                    }
                    mainGame.getFarmUI().farmExpansion(Integer.parseInt(quantity.getText()));
                    mainGame.getFarmUI().farmExpansionUI(Integer
                            .parseInt(quantity.getText()), mainGame, mainGame.getCropField());
                    mainGame.updateMarketScene();
                }
                market.sellToPlayer(mainGame.getPlayer(), item,
                        Integer.parseInt(quantity.getText()));
                System.out.println(mainGame.getPlayer().getInventory());
                System.out.println(mainGame.getPlayer().getMoney());
                mainGame.getMarketHotbar().updateHotbar();
                mainGame.getPlayerInfobar().updateInfoUI();
                mainGame.getMarketInfobar().updateInfoUI();
                quantity.clear();
            }
        }));

        HBox marketSelectionBox = new HBox(mainGame.getComboSpecies(), quantity);
        marketSelectionBox.setAlignment(Pos.TOP_CENTER);
        marketSelectionBox.setSpacing(10);

        VBox marketBox = new VBox(marketSelectionBox, purchaseBtn);
        marketBox.setAlignment(Pos.TOP_CENTER);
        marketBox.setSpacing(10);
        
        return marketBox;
    }

    public static VBox displaySellBox(GameRunner mainGame) {
        /* ComboBox dropdown for the type of items */
        mainGame.setComboSpecies(new ComboBox());
        Market market = mainGame.getMarket();
        mainGame.getComboSpecies().setId("comboSpecies");
        mainGame.getComboSpecies().getItems().add("Bean (Organic Crop: $" 
                                                  + market.getItem(5).getValue() + ")");
        mainGame.getComboSpecies().getItems().add("Bean (Crop: $" 
                                                  + market.getItem(12).getValue() + ")");
        mainGame.getComboSpecies().getItems().add("Rice (Organic Crop: $" 
                                                  + market.getItem(6).getValue() + ")");
        mainGame.getComboSpecies().getItems().add("Rice (Crop: $" 
                                                  + market.getItem(13).getValue() + ")");
        mainGame.getComboSpecies().getItems().add("Wheat (Organic Crop: $" 
                                                  + market.getItem(7).getValue() + ")");
        mainGame.getComboSpecies().getItems().add("Wheat (Crop: $" 
                                                + market.getItem(14).getValue() + ")");
        mainGame.getComboSpecies().getItems().add("Corn (Organic Crop: $" 
                                                + market.getItem(8).getValue() + ")");
        mainGame.getComboSpecies().getItems().add("Corn (Crop: $" 
                                                + market.getItem(15).getValue() + ")");
        mainGame.getComboSpecies().getItems().add("Barley (Organic Crop: $" 
                                                + market.getItem(4).getValue() + ")");
        mainGame.getComboSpecies().getItems().add("Barley (Crop: $" 
                                                + market.getItem(16).getValue() + ")");
        mainGame.getComboSpecies().getItems().add("Fertilizer $" 
                                                + market.getItem(10).getValue());
        mainGame.getComboSpecies().getItems().add("Pesticide $" 
                                                + market.getItem(11).getValue());
        mainGame.getComboSpecies().setPromptText("Select Items");

        TextField quantity = new TextField();
        quantity.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                quantity.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        quantity.setId("quantity");
        quantity.setPromptText("quantity");

        Button sellBtn = new Button();
        sellBtn.setPrefSize(150, 20);
        sellBtn.setText("Sell Crops");
        sellBtn.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        sellBtn.setId("sellBtn");

        sellBtn.setOnMouseClicked((event -> {
            if (mainGame.getComboSpecies().getSelectionModel().isEmpty()) {
                speciesInvalid.setVisible(true);
            } else {
                speciesInvalid.setVisible(false);
            }
            if (quantity.getText().equals("")) {
                quantityInvalid.setVisible(true);
            } else {
                quantityInvalid.setVisible(false);
            }
            if (!(quantity.getText().equals(""))
                    && !(mainGame.getComboSpecies().getSelectionModel().isEmpty())) {
                String item = mainGame.getComboSpecies().getValue().toString().split(" ")[0];
                if (!(item.equals("Fertilizer") || item.equals("Pesticide"))) {
                    if (mainGame.getComboSpecies().getValue().toString()
                            .split(" ")[1].equals("(Organic")) {
                        item += "Crop";
                    } else {
                        item += "PesticideCrop";
                    }
                } 
                mainGame.getMarket().purchaseFromPlayerHash(mainGame.getPlayer(),
                        item, Integer.parseInt(quantity.getText()));
                mainGame.getMarketHotbar().updateHotbar();
                mainGame.getPlayerInfobar().updateInfoUI();
                mainGame.getMarketInfobar().updateInfoUI();
            }
        }));

        HBox marketSelectionBox = new HBox(mainGame.getComboSpecies(), quantity);
        marketSelectionBox.setAlignment(Pos.TOP_CENTER);
        marketSelectionBox.setSpacing(10);

        VBox marketBox = new VBox(marketSelectionBox, sellBtn);
        marketBox.setAlignment(Pos.TOP_CENTER);
        marketBox.setSpacing(10);

        return marketBox;
    }


}
