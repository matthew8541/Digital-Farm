package tests;

import main.Items.Seed;
import main.UserInterface.FarmUI;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxRobotException;
import org.testfx.framework.junit.ApplicationTest;


public class TestGameEndUI extends ApplicationTest {
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
    public void testEndGame() throws FxRobotException {

        test.getC1().getPlayer().setMoney(0);

        for (int i = 3; i < 7; i++) {
            clickOn("#Inventory1");
            clickOn("#CropNum" + i + "3");
        }
        clickOn("#Inventory1");
        clickOn("#CropNum44");

        Seed[][] cropField = test.getC1().getCropField();

        boolean empty = false;
        int counter;

        while (!empty) {
            counter = 0;
            for (int i = 3; i < 7; i++) {
                for (int j = 3; j < 5; j++) {
                    if (cropField[i][j] == null) {
                        counter++;
                    } else if (cropField[i][j].getKilled()) {
                        clickOn("#CropNum" + i + "" + j);
                    }
                }
            }
            if (counter == 8) {
                empty = true;
            }
            try {
                clickOn("#nextDay");
                if (FarmUI.getRainOccured() || FarmUI.getLocustsKilled()) {
                    clickOn("#okbtn");
                }
            } catch (FxRobotException fe) {
                clickOn("#newGame");
            }

        }










    }
}
