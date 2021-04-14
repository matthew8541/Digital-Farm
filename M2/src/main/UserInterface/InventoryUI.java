package main.UserInterface;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import main.Games.Player;
import main.Items.Item;

import java.io.FileInputStream;


public class InventoryUI {
    private GridPane hotbar;
    private Player player;
    private String fileExt;
    private VBox lastClicked = null;
    private static String lastClickedName = "";

    public InventoryUI(Player player) {
        this.player = player;
        hotbar = new GridPane();
    }

    public GridPane getInventoryUI() {
        this.updateHotbar();
        hotbar.setHgap(10);
        hotbar.setStyle("-fx-background-color: #D3D3D3;");
        hotbar.setAlignment(Pos.BOTTOM_CENTER);
        return hotbar;
    }

    public void updateHotbar() {
        hotbar.getChildren().clear();
        for (int i = 0; i < player.getInventory().getCapacity(); i++) {
            //StackPane stack = new StackPane();
            //BorderPane bpane2 = new BorderPane();
            Image img;
            Label quantity;
            String name = "";
            Item itemSlot = player.getInventory().get(i);
            lastClicked = null;
            lastClickedName = "";

            GameRunner c1 = new GameRunner();
            if (c1.getFileExt().equals("")) {
                fileExt = "";
            } else {
                fileExt = "M2/";
            }

            if (itemSlot == null) {
                try {
                    img = new Image(new FileInputStream(fileExt + "src/main/images/alpha.png"));
                } catch (Exception ignored) {
                    System.out.println("ERROR: Could not find alpha.png");
                    img = null;
                }
                quantity = new Label("");
            } else {
                img = itemSlot.getIcon();
                quantity = new Label(Integer.toString(itemSlot.getQuantity()));
            }
            try {
                name = itemSlot.getName();
            } catch (Exception e) {
            }

            ImageView imgView = new ImageView(img);
            imgView.setFitHeight(40);
            imgView.setFitWidth(40);
            imgView.setId("itemImage" + name);
            quantity.setId("itemQuantity" + name);

            VBox item = new VBox();
            item.setId("Inventory" + i);
            item.setStyle("-fx-border-color: white;\n"
                    + "-fx-border-width: 3;\n");
            String finalName = name;
            item.setOnMouseClicked(event -> {
                item.setStyle("-fx-border-color: red;\n"
                        + "-fx-border-width: 3;\n");
                if (lastClicked != null) {
                    lastClicked.setStyle("-fx-border-color: white;\n"
                            + "-fx-border-width: 3;\n");
                }
                lastClicked = item;
                lastClickedName = finalName;
            });
            //item.setOnMouseClicked(new EventHandler<MouseEvent>() {
            //@Override
            //public void handle(MouseEvent event) {
            //item.setStyle("-fx-border-color: black;\n"
            //+ "-fx-border-width: 4;\n");
            //}
            //});
            item.getChildren().addAll(imgView, quantity);
            hotbar.add(item, i, 0);
        }
    }

    public static String getLastClickedName() {
        return lastClickedName;
    }
}
