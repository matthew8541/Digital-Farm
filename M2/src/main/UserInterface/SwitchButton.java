package main.UserInterface;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class SwitchButton extends StackPane {
    private Rectangle back = new Rectangle(90, 30, Color.RED);
    private Button button = new Button();
    private String buttonStyleOff = "-fx-effect: dropshadow(three-pass-box"
        + ", rgba(0,0,0,0.2), 0.2, 0.0, 0.0, 2); -fx-background-color: orange;";
    private String buttonStyleOn = "-fx-effect: dropshadow(three-pass-box"
        + ", rgba(0,0,0,0.2), 0.2, 0.0, 0.0, 2); -fx-background-color: #00893d;";
    private boolean state;

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
        if (state) {
            button.setStyle(buttonStyleOn);
            back.setFill(Color.valueOf("#80C49E"));
            setAlignment(button, Pos.CENTER_RIGHT);
        } else {
            button.setStyle(buttonStyleOff);
            back.setFill(Color.valueOf("#ced5da"));
            setAlignment(button, Pos.CENTER_LEFT);
        }
    }

    public Button getButton() {
        return button;
    }

    public Rectangle getBack() {
        return back;
    }

    public String getButtonStyleOff() {
        return buttonStyleOff;
    }

    public String getButtonStyleOn() {
        return buttonStyleOn;
    }

    private void init() {
        getChildren().addAll(back, button);
        setMinSize(90, 45);
        back.maxWidth(90);
        back.minWidth(90);
        back.maxHeight(30);
        back.minHeight(30);
        back.setArcHeight(back.getHeight());
        back.setArcWidth(back.getHeight());
        back.setFill(Color.valueOf("#ced5da"));
        Double r = 6.0;
        button.setShape(new Circle(r));
        setAlignment(button, Pos.CENTER_LEFT);
        button.setMaxSize(45, 45);
        button.setMinSize(45, 45);
        button.setStyle(buttonStyleOff);
    }

    public SwitchButton() {
        this.state = false;
        init();
        EventHandler<Event> click = new EventHandler<Event>() {
            @Override
            public void handle(Event e) {
                System.out.println("Switch Btn state: " + state);
                if (state) {
                    button.setStyle(buttonStyleOff);
                    back.setFill(Color.valueOf("#ced5da"));
                    setAlignment(button, Pos.CENTER_LEFT);
                    state = false;
                } else {
                    button.setStyle(buttonStyleOn);
                    back.setFill(Color.valueOf("#80C49E"));
                    setAlignment(button, Pos.CENTER_RIGHT);
                    state = true;
                }
            }
        };

        button.setFocusTraversable(false);
        setOnMouseClicked(click);
        button.setOnMouseClicked(click);
    }
}
