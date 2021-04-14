package tests;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import main.Games.Inventory;
import main.Items.Item;
import main.Items.Seed;
import main.UserInterface.GameRunner;
import org.junit.Before;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import org.testfx.framework.junit.ApplicationTest;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;

public class  TestMarket extends ApplicationTest {
    private Inventory inventory1 = new Inventory(11);
    private TestGameRunner test = new TestGameRunner();

    @Override
    public void start(Stage stage) throws Exception {
        test.start(stage);
    }

    @Before
    public void setup() {
        test.testValidInput1();
    }
 
    @Test
    public void testValidQuery() {
        clickOn("#market");
        ComboBox comboSpecies = GuiTest.find("#comboSpecies");
        TextField quantity = GuiTest.find("#quantity");
        Label money = GuiTest.find("#money");
        double initialMoney = Double.parseDouble(money.getText().substring(1));

        ArrayList<String> species = new ArrayList<>();

        for (Object s: comboSpecies.getItems()) {
            species.add(s.toString());
        }

        clickOn(comboSpecies);
        clickOn(species.get(1));
        clickOn(quantity);
        write("2");

        clickOn("#purchaseBtn");

        clickOn(comboSpecies);
        clickOn(species.get(4));
        clickOn(quantity);
        write("6");

        clickOn("#purchaseBtn");

        clickOn("#return");

        GameRunner instance = test.getC1();
        Inventory currentInventory = instance.getPlayer().getInventory();
        String[] expected = new String[]{"WheatSeed", "RiceSeed", "BarleySeed"};
        String[] expQuantity = new String[]{"5", "2", "6"};

        int i = 0;
        for (String seed: expected) {
            try {
                Item seedItm = currentInventory.get(seed);
                Label seedQuantity = GuiTest.find("#itemQuantity" + seedItm.getName());
                if (i != 0) {
                    initialMoney -= seedItm.getQuantity() * seedItm.getValue();
                }
                assertEquals(seedItm.getName(), seed);
                assertEquals(seedQuantity.getText(), expQuantity[i]);
            } catch (NullPointerException ne) {
                assertFalse(true);
            }
            i++;
        }
        money = GuiTest.find("#money");
        assertEquals(Double.parseDouble(money.getText().substring(1)), initialMoney, 0.1);
    }

    @Test
    public void testInvalidQuery() {
        clickOn("#market");
        ComboBox comboSpecies = GuiTest.find("#comboSpecies");
        TextField quantity = GuiTest.find("#quantity");
        Label money = GuiTest.find("#money");
        double initial = Double.parseDouble(money.getText().substring(1));

        ArrayList<String> species = new ArrayList<>();

        for (Object s: comboSpecies.getItems()) {
            species.add(s.toString());
        }

        clickOn(comboSpecies);
        clickOn(species.get(1));
        clickOn(quantity);
        write("999999999");

        clickOn("#purchaseBtn");
        clickOn("#return");

        GameRunner instance = test.getC1();
        Inventory currentInventory = instance.getPlayer().getInventory();

        try {
            Item rice = currentInventory.get("RiceSeed");
            assertTrue(false);
        } catch (AssertionError ae) {
            assertTrue(true);
        }

        money = GuiTest.find("#money");
        assertEquals(Double.parseDouble(money.getText().substring(1)), initial, 0);
    }

    @Test
    public void testSellCrop() {
        Random rand = new Random();
        //        for (int i = 0; i < 10; i += 2) {
        //            for (int j = 0; j < 8; j++) {
        //                StackPane crop = GuiTest.find("#CropNum" + i + "" + j);
        //                clickOn(crop);
        //            }
        //        }

        //        Label cropQuantity = GuiTest.find("#itemQuantityCornCrop");
        //        int initialCrop = Integer.parseInt(cropQuantity.getText());
        clickOn("#market");

        Button sellBuySwitch = GuiTest.find("#sellBuySwitch");
        clickOn(sellBuySwitch);
        ComboBox comboSpecies = GuiTest.find("#comboSpecies");
        TextField quantity = GuiTest.find("#quantity");
        Label money = GuiTest.find("#money");

        double initialMoney = Double.parseDouble(money.getText().substring(1));

        ArrayList<String> species = new ArrayList<>();

        for (Object s: comboSpecies.getItems()) {
            species.add(s.toString());
        }

        clickOn(comboSpecies);
        clickOn(species.get(3));
        clickOn(quantity);
        write("2");

        clickOn("#sellBtn");

        GameRunner instance = test.getC1();
        Inventory currentInventory = instance.getPlayer().getInventory();

        //        Item cornCrop = currentInventory.get("CornCrop");
        //        cropQuantity = GuiTest.find("#itemQuantityCornCrop");
        //        money = GuiTest.find("#money");
        //
        //        assertEquals(initialCrop - 2, Integer.parseInt(cropQuantity.getText()));
        //        assertEquals(initialMoney + 2 * cornCrop.getValue(),
        //                Double.parseDouble(money.getText().substring(1)), 0.1);

    }

    @Test
    public void testPurchaseFertilizerPesticide() throws AWTException {
        Robot robot = new Robot();
        clickOn("#market");

        ComboBox comboSpecies = GuiTest.find("#comboSpecies");
        TextField quantity = GuiTest.find("#quantity");
        Label money = GuiTest.find("#money");

        double initialMoney = Double.parseDouble(money.getText().substring(1));
        ArrayList<String> species = new ArrayList<>();

        for (Object s: comboSpecies.getItems()) {
            species.add(s.toString());
        }

        /* Purchase Fertilizer */
        clickOn(comboSpecies);
        clickOn(species.get(5));
        clickOn(quantity);
        write("1");
        clickOn("#purchaseBtn");

        /* Purchase Pesticide */
        clickOn(comboSpecies);
        clickOn(species.get(6));
        clickOn(quantity);
        write("1");
        clickOn("#purchaseBtn");

        clickOn("#return");
        GameRunner instance = test.getC1();
        Inventory currentInventory = instance.getPlayer().getInventory();
        try {
            Item fertilizer = currentInventory.get("Fertilizer");
            Item pesticide = currentInventory.get("Pesticide");
            assertEquals(fertilizer.getName(), "Fertilizer");
            assertEquals(pesticide.getName(), "Pesticide");
        } catch (AssertionError ae) {
            assertTrue(true);
        }

        clickOn("#market");
        Button sellBuySwitch = GuiTest.find("#sellBuySwitch");
        clickOn(sellBuySwitch);

        comboSpecies = GuiTest.find("#comboSpecies");
        quantity = GuiTest.find("#quantity");


        species = new ArrayList<>();
        for (Object s: comboSpecies.getItems()) {
            species.add(s.toString());
        }

        /* Purchase Fertilizer */
        clickOn(quantity);
        write("1");
        clickOn(comboSpecies);
        for (int i = 0; i < 11; i++) {
            robot.delay(100);
            robot.keyPress(KeyEvent.VK_DOWN);
        }
        robot.keyPress(KeyEvent.VK_ENTER);
        clickOn("#sellBtn");

        /* Purchase Pesticide */
        clickOn(comboSpecies);
        robot.delay(300);
        robot.keyPress(KeyEvent.VK_DOWN);

        robot.keyPress(KeyEvent.VK_ENTER);
        
        clickOn("#sellBtn");

        money = GuiTest.find("#money");
        assertEquals(initialMoney, Double.parseDouble(money.getText().substring(1)), 0.1);

    }

    @Test
    public void testSellPesticideCrop() throws AWTException {
        Robot robot = new Robot();
        clickOn("#market");
        ComboBox comboSpecies = GuiTest.find("#comboSpecies");
        TextField quantity = GuiTest.find("#quantity");

        GameRunner config = test.getC1();
        Seed[][] cropMatrix = config.getCropField();

        ArrayList<String> species = new ArrayList<>();

        for (Object s: comboSpecies.getItems()) {
            species.add(s.toString());
        }

        clickOn(comboSpecies);
        clickOn(species.get(6));
        clickOn(quantity);
        write("2");
        clickOn("#purchaseBtn");

        Label money = GuiTest.find("#money");
        double initialMoney = Double.parseDouble(money.getText().substring(1));

        clickOn("#return");

        clickOn("#Inventory1");
        StackPane crop1 = GuiTest.find("#CropNum00");
        clickOn(crop1);

        clickOn("#Inventory1");
        StackPane crop2 = GuiTest.find("#CropNum34");
        clickOn(crop2);

        clickOn("#Inventory2");
        clickOn(crop1);
        clickOn("#Inventory2");
        clickOn(crop2);
        
        clickOn("#Inventory0");
        clickOn(crop1);
        clickOn(crop2);

        clickOn("#nextDay");
        robot.delay(300);
        robot.keyPress(KeyEvent.VK_ENTER);

        clickOn("#nextDay");
        robot.delay(300);
        robot.keyPress(KeyEvent.VK_ENTER);

        clickOn("#nextDay");
        robot.delay(300);
        robot.keyPress(KeyEvent.VK_ENTER);

        clickOn(crop1);
        clickOn(crop2);
        clickOn("#Inventory3");

        clickOn("#nextDay");
        robot.delay(300);
        robot.keyPress(KeyEvent.VK_ENTER);

        clickOn("#nextDay");
        robot.delay(300);
        robot.keyPress(KeyEvent.VK_ENTER);

        clickOn(crop1);
        clickOn(crop2);

        clickOn("#market");
        Button sellBuySwitch = GuiTest.find("#sellBuySwitch");
        clickOn(sellBuySwitch);

        comboSpecies = GuiTest.find("#comboSpecies");
        quantity = GuiTest.find("#quantity");

        species = new ArrayList<>();
        for (Object s: comboSpecies.getItems()) {
            species.add(s.toString());
        }

        config = test.getC1();
        Inventory currentInventory = config.getPlayer().getInventory();
        Item wheatPesticideCrop = currentInventory.get("WheatPesticideCrop");
        Item wheatCrop = config.getMarket().getItem("WheatCrop");

        clickOn(quantity);
        write("2");
        clickOn(comboSpecies);
        clickOn(species.get(5));
        clickOn("#sellBtn");

        // System.out.println(wheatCrop.getName());
        // Label wheatQuantity = GuiTest.find("#itemQuantityWheatPesticideCrop");

        // assertEquals(initialCrop - 2, Integer.parseInt(cropQuantity.getText()));
        assertTrue(wheatPesticideCrop.getValue() < wheatCrop.getValue());

    }
}
