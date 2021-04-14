package tests;

import main.UserInterface.GameRunner;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.loadui.testfx.GuiTest;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.ComboBox;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class TestGameRunner extends ApplicationTest {
    private GameRunner c1 = new GameRunner();
    @Override
    public void start(Stage stage) throws Exception {
        //        GameRunner.setFileExt();
        c1.start(stage);
        stage.setScene(c1.getScene2());
        stage.show();
    }

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testComboConstruction() {
        ComboBox season = GuiTest.find("#comboSeason");
        ComboBox level = GuiTest.find("#comboLevel");
        ComboBox seed = GuiTest.find("#comboSeed");

        ArrayList<String> expected = new ArrayList<>();
        expected.add("Fall");
        expected.add("Summer");
        expected.add("Spring");
        expected.add("Winter");

        int counter = 0;
        for (Object s: season.getItems()) {
            assertEquals(s.toString(), expected.get(counter));
            counter++;
        }

        expected = new ArrayList<>();
        expected.add("Easy");
        expected.add("Medium");
        expected.add("Hard");

        counter = 0;
        for (Object s: level.getItems()) {
            assertEquals(s.toString(), expected.get(counter));
            counter++;
        }

        expected = new ArrayList<>();
        expected.add("Rice");
        expected.add("Corn");
        expected.add("Wheat");
        expected.add("Bean");
        expected.add("Barley");

        counter = 0;
        for (Object s: seed.getItems()) {
            assertEquals(s.toString(), expected.get(counter));
            counter++;
        }



    }



    @Test
    public void testInvalidName() {
        TextField nameText = GuiTest.find("#nametext");
        clickOn(nameText);
        write("");
        clickOn("#proceed");
        try {
            Text nameInvalid = GuiTest.find("#nameInvalid");
            assertEquals("Player name cannot be empty!", nameInvalid.getText());
        } catch (Exception e) {
            throw e;
        }
        assertEquals(null, c1.getPlayer());
        assertEquals(null, c1.getGame());
    }

    @Test
    public void testEmptySeason() {
        ComboBox season = GuiTest.find("#comboSeason");
        clickOn(season);
        clickOn("#proceed");
        try {
            Text seasonInvalid = GuiTest.find("#seasonInvalid");
            assertEquals("Please select a season!", seasonInvalid.getText());
        } catch (Exception e) {
            throw e;
        }
        assertEquals(null, c1.getPlayer());
        assertEquals(null, c1.getGame());
    }

    @Test
    public void testEmptyLevel() {
        ComboBox level = GuiTest.find("#comboLevel");
        clickOn(level);
        clickOn("#proceed");
        try {
            Text levelInvalid = GuiTest.find("#levelInvalid");
            assertEquals("Please select a difficulty!", levelInvalid.getText());
        } catch (Exception e) {
            throw e;
        }
        assertEquals(null, c1.getPlayer());
        assertEquals(null, c1.getGame());
    }

    @Test
    public void testEmptySeed() {
        ComboBox seed = GuiTest.find("#comboSeed");
        clickOn(seed);
        clickOn("#proceed");
        try {
            Text seedInvalid = GuiTest.find("#seedInvalid");
            assertEquals("Please select a type of seed!", seedInvalid.getText());
        } catch (Exception e) {
            throw e;
        }
        assertEquals(null, c1.getPlayer());
        assertEquals(null, c1.getGame());
    }

    @Test
    public void testValidInput1() {

        TextField nameText = GuiTest.find("#nametext");
        ComboBox season = GuiTest.find("#comboSeason");
        ComboBox level = GuiTest.find("#comboLevel");
        ComboBox seed = GuiTest.find("#comboSeed");

        clickOn(nameText);
        write("Akshay");

        clickOn(season);
        clickOn("Fall");

        clickOn(level);
        clickOn("Hard");

        clickOn(seed);
        clickOn("Wheat");

        clickOn("#proceed");

        assertEquals("Akshay", c1.getPlayer().getName());
        assertEquals(300, c1.getPlayer().getMoney(), 0);


        assertEquals("Hard", c1.getGame().getDifficulty());
        assertEquals("Fall", c1.getGame().getSeason());


    }

    @Test
    public void testValidInput2() {

        TextField nameText = GuiTest.find("#nametext");

        ComboBox season = GuiTest.find("#comboSeason");
        ComboBox level = GuiTest.find("#comboLevel");
        ComboBox seed = GuiTest.find("#comboSeed");

        clickOn(nameText);
        write("Akshay");

        clickOn(season);
        clickOn("Spring");

        clickOn(level);
        clickOn("Hard");

        clickOn(seed);
        clickOn("Corn");

        clickOn("#proceed");

        assertEquals("Akshay", c1.getPlayer().getName());
        assertEquals(300, c1.getPlayer().getMoney(), 0);


        assertEquals("Hard", c1.getGame().getDifficulty());
        assertEquals("Spring", c1.getGame().getSeason());


    }

    public GameRunner getC1() {
        return c1;
    }


}