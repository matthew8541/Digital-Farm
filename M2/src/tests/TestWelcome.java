package tests;


import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.loadui.testfx.GuiTest;
import javafx.geometry.Pos;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import main.UserInterface.GameRunner;


import static org.junit.Assert.assertEquals;

public class TestWelcome extends ApplicationTest {
    @Override
    public void start(Stage stage) throws Exception {
        GameRunner c1 = new GameRunner();
        c1.setFileExt();
        c1.start(stage);
        stage.setScene(c1.getScene1());
        stage.show();
    }

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testScene1() {
        Text gameName = GuiTest.find("#gameName");
        Text devs = GuiTest.find("#developers");
        Button playBtn = GuiTest.find("#play");
        VBox vbox = GuiTest.find("#scene1vbox");

        //Tests for gameName
        assertEquals(gameName.getText(), "Digital Farm");
        assertEquals(Color.WHITE, gameName.getFill());
        assertEquals(Font.font("Verdana", FontWeight.BOLD, 80), gameName.getFont());
        assertEquals("-fx-stroke: #f5a42a; -fx-stroke-width: 2;", gameName.getStyle());

        //Tests for devs
        assertEquals("\n\nGame Development Team: Haphazard Hackers", devs.getText());
        assertEquals(Color.BLACK, devs.getFill());

        //Tests for playBtn
        assertEquals("Play", playBtn.getText());
        assertEquals(Font.font("Verdana", FontWeight.BOLD, 16), playBtn.getFont());

        //Tests for vbox
        assertEquals(Pos.CENTER, vbox.getAlignment());
        assertEquals(60, vbox.getSpacing(), 0.0);
        assertEquals(700, vbox.getPrefHeight(), 0.0);
        assertEquals(700, vbox.getPrefWidth(), 0.0);


    }

}