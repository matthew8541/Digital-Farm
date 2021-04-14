package main.Items;
import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import main.UserInterface.GameRunner;

public class Item {
    private int quantity;
    private String name;
    private double value;
    private Image icon;
    private static final int MAX_QUANTITY = 100;
    private String fileExt;

    public Item(int quantity, String name, double value) {
        this.quantity = quantity;
        this.name = name;
        this.value = value;
        GameRunner c1 = new GameRunner();
        if (c1.getFileExt().equals("")) {
            fileExt = "";
        } else {
            fileExt = "M2/";
        }
        try {
            icon = new Image(new FileInputStream(fileExt + "src/main/images/" + name + ".png"));
        } catch (FileNotFoundException e) {
            try {
                icon = new Image(new FileInputStream(fileExt + "src/main/images/missingImage.png"));
            } catch (FileNotFoundException ignored) {
            }
        }
    }

    public Item copy() {
        Item copy = new Item(this.quantity, this.name, this.value);
        return copy;
    }

    public void setIcon(String filepath) {
        try {
            icon = new Image(new FileInputStream(filepath));
        } catch (FileNotFoundException e) {
            try {
                icon = new Image(new FileInputStream(fileExt + filepath));
            } catch (FileNotFoundException e2) {
                try {
                    icon = new Image(new FileInputStream(fileExt
                            + "src/main/images/missingImage.png"));
                } catch (FileNotFoundException ignored) {
                    System.out.println("ERROR: Set Icon could not find file");
                    icon = null;
                }
            }
        }
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int newQuantity) {
        if (newQuantity < MAX_QUANTITY) {
            quantity = newQuantity;
        }
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double newPrice) {
        this.value = newPrice;
    }

    public Image getIcon() {
        return icon;
    }

    public int getMaxQuantity() {
        return MAX_QUANTITY;
    }

    @Override
    public String toString() {
        return "Name: " + name + " - Quantity: " + quantity + "\n";
    }
}
