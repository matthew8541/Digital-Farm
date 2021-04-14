package tests;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import main.UserInterface.GameRunner;
import main.Games.Player;
import org.junit.Test;
import javafx.stage.Stage;
import org.testfx.framework.junit.ApplicationTest;
import org.loadui.testfx.GuiTest;

import main.Games.Inventory;
import main.Items.Item;
import main.Items.Seed;

import java.io.FileInputStream;

import static org.junit.Assert.*;

public class TestInventory extends ApplicationTest {
    private Inventory inventory1 = new Inventory(11);
    private TestGameRunner test = new TestGameRunner();

    @Override
    public void start(Stage stage) throws Exception {
        test.start(stage);
    }

    @Test
    public void testConstruction() {
        assertEquals(inventory1.getCapacity(), 11);
        assertEquals(null, inventory1.getSelectedItem());
        for (int i = 0; i < 11; i++) {
            assertEquals(null, inventory1.get(i));
        }
    }

    @Test
    public void testAdd() {
        Item item1 = new Item(10, "Fertilizer", 3);
        Item seed = new Seed(12, "Barley", 23, 2);
        Item seed2 = new Seed(2, "Barley", 23, 2);

        inventory1.add(item1);
        inventory1.add(seed);
        inventory1.add(seed2);

        assertEquals("BarleySeed", inventory1.get("BarleySeed").getName());
        assertEquals(14, inventory1.get("BarleySeed").getQuantity());
    }

    @Test
    public void testRemove() {
        testAdd();

        inventory1.remove("BarleySeed", 10);
        assertEquals(4, inventory1.get("BarleySeed").getQuantity());

        inventory1.remove("Fertilizer", 10);

    }

    @Test(expected = NullPointerException.class)
    public void testRemoveToEmpty() {
        testAdd();
        inventory1.remove("Fertilizer", 10);
        inventory1.get("Fertilizer");

    }

    @Test
    public void testInitialInventory() {
        test.testValidInput1();

        GameRunner c1 = test.getC1();
        ImageView im1 = GuiTest.find("#itemImageWheatSeed");
        Label lbl = GuiTest.find("#itemQuantityWheatSeed");

        Player p1 = c1.getPlayer();
        Item first = p1.getInventory().get("WheatSeed");

        assertEquals(lbl.getText(), Integer.toString(first.getQuantity()));

        Image seedPic = im1.getImage();
        Image actualImg;
        Image stored = first.getIcon();

        try {
            actualImg = new Image(new FileInputStream("main/images/WheatSeed.png"));
        } catch (Exception ignored) {
            System.out.println("ERROR: Could not find alpha.png");
            actualImg = null;
        }

        for (int i = 0; i < actualImg.getWidth(); i++) {
            for (int j = 0; j < actualImg.getHeight(); j++) {
                assertEquals(seedPic.getPixelReader().getColor(i, j),
                        actualImg.getPixelReader().getColor(i, j));
                assertEquals(stored.getPixelReader().getColor(i, j),
                        actualImg.getPixelReader().getColor(i, j));
            }
        }
    }
}
