package tests;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import main.Items.Seed;
import main.UserInterface.FarmUI;
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

//import javafx.scene.control.Button;

public class TestFarmUI extends ApplicationTest {
    private TestGameRunner test = new TestGameRunner();

    @Override
    public void start(Stage stage) throws Exception {
        test.start(stage);
    }

    @Before
    public void setUp() {
        test.testValidInput2();
    }

    @Test
    public void testLabelInfo() {
        Label money = GuiTest.find("#money");
        Label day = GuiTest.find("#day");
        Label season = GuiTest.find("#season");
        assertEquals("$1000.0", money.getText());
        assertEquals("1", day.getText());
        assertEquals("Spring", season.getText());

    }
    /*
    @Test
    public void testPlot() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Button square = GuiTest.find("#square" + i + "" + j);
                clickOn(square);
                assertEquals("-fx-background-color: #9b7653;", square.getStyle());
            }
        }
        }
     */
    /*
    @Test
    public void testHarvest() {
        Random rand = new Random();
        for (int i = 0; i < 40; i++) {
            int x = rand.nextInt(15);
            int y = rand.nextInt(10);

            VBox crop = GuiTest.find("#CropNum" + x + "" + y);
            clickOn(crop);
        }
        ConfigurationUI config = test.getC1();
        Inventory inv = config.getPlayer().getInventory();

        Item cropNum = inv.get("CornCrop");
        Label cornLabel = GuiTest.find("#itemQuantityCornCrop");

        assertEquals(Integer.parseInt(cornLabel.getText()), cropNum.getQuantity());
    }
    */

    @Test
    public void testPlant() {
        clickOn("#Inventory1");
        Label cornLabel;
        int numSeeds = 5;
        int x;
        int y;

        Random rand = new Random();
        while (numSeeds > 0) {
            x = rand.nextInt(5);
            y = rand.nextInt(5);

            StackPane crop = GuiTest.find("#CropNum" + x + "" + y);
            clickOn(crop);
            clickOn("#Inventory1");

            numSeeds--;
            if (numSeeds > 0) {
                cornLabel = GuiTest.find("#itemQuantityCornSeed");
                assertEquals(numSeeds, Integer.parseInt(cornLabel.getText()));
            }
        }

        clickOn("#market");
        ComboBox comboSpecies = GuiTest.find("#comboSpecies");
        TextField quantity = GuiTest.find("#quantity");

        clickOn(comboSpecies);
        clickOn(comboSpecies.getItems().get(0).toString());
        clickOn(quantity);
        write("5");
        clickOn("#purchaseBtn");

        clickOn(comboSpecies);
        clickOn(comboSpecies.getItems().get(4).toString());
        clickOn(quantity);
        write("3");
        clickOn("#purchaseBtn");
        clickOn("#return");

        numSeeds = 8;
        while (numSeeds > 0) {
            x = rand.nextInt(10);
            y = rand.nextInt(5);

            StackPane crop = GuiTest.find("#CropNum" + x + "" + y);
            clickOn(crop);
            if (numSeeds > 5) {
                clickOn("#Inventory1");
            } else if (numSeeds > 4) {
                clickOn("#Inventory2");
            }
            numSeeds--;
        }
    }

    @Test
    public void testInvalidPlant() {
        boolean repeat = true;
        for (int i = 0; i < 3; i++) {
            clickOn("#Inventory1");
            clickOn("#CropNum0" + i);
            if (i == 2 && repeat) {
                i = 0;
                repeat = false;
            }
        }
    }

    @Test
    public void testTimeProgression() {
        Label day1 = GuiTest.find("#day");
        assertEquals("1", day1.getText());
        
        // Click on Next Day Button once, expecting the Game day to progress ot day 2
        clickOn("#nextDay");
        Label day2 = GuiTest.find("#day");
        assertEquals("2", day2.getText());
        assertEquals(2, test.getC1().getPlayer().getCurrGame().getDay());

        // Click on Next Day Button consecutively 5 times. Expecting to progress to day 7
        for (int i = 0; i < 5; i++) {
            clickOn("#nextDay");
        }
        Label day7 = GuiTest.find("#day");
        assertEquals("7", day7.getText());
        assertEquals(7, test.getC1().getPlayer().getCurrGame().getDay());
    }

    @Test
    public void testCropGrowth() {
        clickOn("#Inventory1");
        StackPane crop = GuiTest.find("#CropNum00");
        clickOn(crop);
        GameRunner config = test.getC1();
        Seed[][] cropMatrix = config.getCropField();

        // Check if nextday button does increment growthtime
        assertEquals(0.0, cropMatrix[0][0].getGrowthTime(), 0.1);
        clickOn("#nextDay");
        assertEquals(1.0, cropMatrix[0][0].getGrowthTime(), 0.1);

        // Check if every seeds has its own independent growthTime
        clickOn("#Inventory1");
        StackPane crop2 = GuiTest.find("#CropNum34");
        clickOn(crop2);
        clickOn("#nextDay");
        assertEquals(1.0, cropMatrix[3][4].getGrowthTime(), 0.1);
        assertEquals(2.0, cropMatrix[0][0].getGrowthTime(), 0.1);
    }

    @Test
    public void testWater() {
        clickOn("#Inventory1");
        StackPane crop = GuiTest.find("#CropNum00");
        GameRunner config = test.getC1();
        Seed[][] cropMatrix = config.getCropField();
        clickOn(crop);
        clickOn("#Inventory0");
        clickOn(crop);
        assertEquals(1, cropMatrix[0][0].getWaterLevel());
    }

    @Test
    public void testCropDeath() {
        StackPane crop1 = GuiTest.find("#CropNum00");
        StackPane crop2 = GuiTest.find("#CropNum01");
        GameRunner config = test.getC1();
        Seed[][] cropMatrix = config.getCropField();
        clickOn("#Inventory1");
        clickOn(crop1);
        clickOn("#Inventory1");
        clickOn(crop2);
        clickOn("#Inventory0");
        for (int i = 0; i < 7; i++) {
            clickOn(crop1);
        }
        for (int i = 0; i < 3; i++) {
            clickOn("#nextDay");
        }
        assertEquals(0, cropMatrix[0][0].getGrowthRate(), 0.1);
        assertEquals(0, cropMatrix[0][1].getGrowthRate(), 0.1);
    }

    @Test
    public void testFertilizer() throws AWTException {
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
        clickOn(species.get(5));
        clickOn(quantity);
        write("6");
        clickOn("#purchaseBtn");
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
        clickOn(crop1);
        clickOn("#Inventory2");
        clickOn(crop1);

        clickOn("#Inventory2");
        clickOn(crop2);
        clickOn("#Inventory2");
        clickOn(crop2);
        clickOn("#Inventory2");
        clickOn(crop2);

        clickOn("#Inventory0");
        clickOn(crop1);
        clickOn(crop2);
        clickOn("#Inventory3");

        clickOn("#nextDay");
        assertEquals(2.0, cropMatrix[3][4].getGrowthTime(), 0.1);
        assertEquals(2.0, cropMatrix[0][0].getGrowthTime(), 0.1);
        robot.delay(1000);
        robot.keyPress(KeyEvent.VK_ENTER);
     
        clickOn("#nextDay");
        robot.delay(1000);
        robot.keyPress(KeyEvent.VK_ENTER);

        clickOn("#nextDay");
        robot.delay(1000);
        robot.keyPress(KeyEvent.VK_ENTER);

        clickOn(crop1);
        clickOn(crop2);
    }

    @Test
    public void testPesticide() throws AWTException {
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
        
        clickOn("#nextDay");
        robot.delay(1000);
        robot.keyPress(KeyEvent.VK_ENTER);
        assertEquals(true, cropMatrix[3][4].getHasPesticide());
        assertEquals(true, cropMatrix[0][0].getHasPesticide());

        clickOn("#nextDay");
        robot.delay(1000);
        robot.keyPress(KeyEvent.VK_ENTER);
        assertEquals(true, cropMatrix[3][4].getHasPesticide());
        assertEquals(true, cropMatrix[0][0].getHasPesticide());
        
        clickOn("#nextDay");
        robot.delay(1000);
        robot.keyPress(KeyEvent.VK_ENTER);
        assertEquals(true, cropMatrix[3][4].getHasPesticide());
        assertEquals(true, cropMatrix[0][0].getHasPesticide());
        
        clickOn("#nextDay");
        robot.delay(1000);
        robot.keyPress(KeyEvent.VK_ENTER);
        assertEquals(true, cropMatrix[3][4].getHasPesticide());
        assertEquals(true, cropMatrix[0][0].getHasPesticide());

        clickOn("#nextDay");
        robot.delay(1000);
        robot.keyPress(KeyEvent.VK_ENTER);
        assertEquals(true, cropMatrix[3][4].getHasPesticide());
        assertEquals(true, cropMatrix[0][0].getHasPesticide());

        clickOn(crop1);
        clickOn(crop2);
    }

    @Test
    public void testRain() {
        int x = 1;
        int y = 1;
        Seed[][] cropField = test.getC1().getCropField();


        clickOn("#Inventory1");
        clickOn("#CropNum11");

        int prevWaterLevel = cropField[1][1].getWaterLevel();

        while (!FarmUI.getRainOccured()) {
            prevWaterLevel = cropField[x][y].getWaterLevel() - 1;
            clickOn("#nextDay");
            if (FarmUI.getRainOccured()) {
                clickOn("#okbtn");
                break;
            } else if (FarmUI.getLocustsKilled()) {
                clickOn("#okbtn");
            }
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 8; j++) {
                    if ((cropField[i][j] != null && cropField[i][j].getKilled())
                            || (cropField[i][j] != null && cropField[i][j].isMature())) {
                        clickOn("#Inventory5");
                        clickOn("#CropNum" + i + "" + j);
                        clickOn("#Inventory1");
                        clickOn("#CropNum" + (i + 1) + "" + (j + 1));
                        x = i + 1;
                        y = j + 1;
                    } else if (cropField[i][j] != null) {
                        clickOn("#Inventory0");
                        clickOn("#CropNum" + i + "" + j);
                        x = i;
                        y = j;
                    }
                }
            }
            if (x > 5) {
                break;
            }
        }
        if (cropField[x][y] != null) {
            assertTrue(prevWaterLevel < cropField[x][y].getWaterLevel());
        } else if (x > 5) {
            testRain();
        }

    }

    @Test
    public void testLocusts() {
        int x = 1;
        int y = 1;
        Seed[][] cropField = test.getC1().getCropField();
        boolean killed = false;


        clickOn("#Inventory1");
        clickOn("#CropNum11");
        while (FarmUI.getKilledCrops() == 0) {
            clickOn("#nextDay");
            if (FarmUI.getLocustsKilled()) {
                killed = cropField[x][y].getKilled();
                clickOn("#okbtn");
                break;
            } else if (FarmUI.getRainOccured()) {
                clickOn("#okbtn");
            }
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 8; j++) {
                    if ((cropField[i][j] != null && cropField[i][j].getKilled())
                            || (cropField[i][j] != null && cropField[i][j].isMature())) {
                        clickOn("#Inventory5");
                        clickOn("#CropNum" + i + "" + j);
                        clickOn("#Inventory1");
                        clickOn("#CropNum" + (i + 1) + "" + (j + 1));
                        x = i + 1;
                        y = j + 1;
                    } else if (cropField[i][j] != null) {
                        clickOn("#Inventory0");
                        clickOn("#CropNum" + i + "" + j);
                        x = i;
                        y = j;
                    }
                }
            }
            if (x > 5) {
                break;
            }
        }
        if (x > 5) {
            testLocusts();
        } else {
            if (FarmUI.getKilledCrops() == 0) {
                assertFalse(killed);
            } else {
                assertTrue(killed);
            }
        }
    }

    @Test
    public void testIrrigation() {
        StackPane crop =  GuiTest.find("#CropNum33");
        Seed[][] cropField = test.getC1().getCropField();

        clickOn("#Inventory1");
        clickOn(crop);
        clickOn("#Inventory0");

        for (int i = 0; i < 6; i++) {
            clickOn(crop);
        }

        clickOn("#okbtn");

        assertEquals(5, cropField[3][3].getWaterLevel());

        clickOn("#market");
        ComboBox comboSpecies = GuiTest.find("#comboSpecies");
        clickOn(comboSpecies);
        clickOn(comboSpecies.getItems().get(9).toString());
        clickOn("#quantity");
        write("1");
        clickOn("#purchaseBtn");
        clickOn("#return");
        clickOn("#nextDay");

        if (FarmUI.getLocustsKilled()) {
            clickOn("#okbtn");
        } else if (FarmUI.getRainOccured()) {
            clickOn("#okbtn");
        }

        crop = GuiTest.find("#CropNum34");
        clickOn("#Inventory1");
        clickOn(crop);
        clickOn("#Inventory0");

        for (int i = 0; i < 11; i++) {
            clickOn(crop);
        }

        clickOn("#okbtn");

        assertEquals(10, cropField[3][4].getWaterLevel());
    }

    @Test
    public void testTractors() {
        clickOn("#market");

        Seed[][] cropField = test.getC1().getCropField();
        ComboBox comboSpecies = GuiTest.find("#comboSpecies");

        clickOn(comboSpecies);
        clickOn(comboSpecies.getItems().get(3).toString());
        clickOn("#quantity");
        write("3");
        clickOn("#purchaseBtn");
        clickOn("#return");

        for (int i = 3; i < 5; i++) {
            for (int j = 3; j < 7; j++) {
                clickOn("#Inventory1");
                clickOn("#CropNum" + j + i);
            }
        }

        for (int i = 0; i < 10; i++) {
            clickOn("#nextDay");
            if (FarmUI.getLocustsKilled()) {
                clickOn("#okbtn");
            } else if (FarmUI.getRainOccured()) {
                clickOn("#okbtn");
            }
        }
        clickOn("#Inventory3");
        int x = 0;
        for (int i = 3; i < 5; i++) {
            for (int j = 3; j < 7; j++) {
                clickOn("#CropNum" + j + i);
                x++;
                if (x > 5) {
                    clickOn("#okbtn");
                    assertNotNull(cropField[i][j]);
                    break;
                }
            }
        }

        clickOn("#market");
        clickOn(comboSpecies);
        clickOn(comboSpecies.getItems().get(3).toString());
        clickOn("#quantity");
        write("5");
        clickOn("#purchaseBtn");
        clickOn(comboSpecies);
        clickOn(comboSpecies.getItems().get(8).toString());
        clickOn("#quantity");
        write("1");
        clickOn("#purchaseBtn");
        clickOn("#return");
        clickOn("#nextDay");

        if (FarmUI.getLocustsKilled()) {
            clickOn("#okbtn");
        } else if (FarmUI.getRainOccured()) {
            clickOn("#okbtn");
        }

        x = 0;
        for (int i = 3; i < 5; i++) {
            for (int j = 3; j < 7; j++) {
                clickOn("#Inventory1");
                clickOn("#CropNum" + j + i);
                x++;
                if (x >= 5) {
                    break;
                }
            }
        }

        for (int i = 0; i < 10; i++) {
            clickOn("#nextDay");
            if (FarmUI.getLocustsKilled()) {
                clickOn("#okbtn");
            } else if (FarmUI.getRainOccured()) {
                clickOn("#okbtn");
            }
        }

        clickOn("#Inventory4");

        for (int i = 3; i < 5; i++) {
            for (int j = 3; j < 7; j++) {
                clickOn("#CropNum" + j + i);
            }
        }
        assertNull(cropField[6][4]);
    }

    @Test
    public void testExpansion() {

        clickOn("#Inventory1");
        StackPane crop1 = GuiTest.find("#CropNum00");
        clickOn(crop1);

        StackPane crop2 = GuiTest.find("#CropNum34");
        clickOn(crop2);

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
        clickOn(species.get(7));
        clickOn(quantity);
        write("1");
        clickOn("#purchaseBtn");
        clickOn("#return");

        clickOn("#Inventory1");

        StackPane crop3 = GuiTest.find("#CropNum35");
        clickOn(crop3);

    }

    @Test
    public void testInvalidExpansion() {

        clickOn("#market");
        ComboBox comboSpecies = GuiTest.find("#comboSpecies");
        TextField quantity = GuiTest.find("#quantity");

        ArrayList<String> species = new ArrayList<>();
        for (Object s: comboSpecies.getItems()) {
            species.add(s.toString());
        }

        clickOn(comboSpecies);
        clickOn(species.get(7));
        clickOn(quantity);
        write("1");
        clickOn("#purchaseBtn");
        clickOn("#return");


        clickOn("#market");
        clickOn(comboSpecies);
        clickOn(species.get(7));
        clickOn(quantity);
        write("5");

        clickOn("#purchaseBtn");
        clickOn("#return");

    }
}