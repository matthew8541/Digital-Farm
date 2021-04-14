package main.UserInterface;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import main.Games.Game;
import main.Games.Player;

public class InfoUI {
    private GridPane info;
    private Player player;
    private Game game;

    public InfoUI(Player player, Game game) {
        this.player = player;
        this.game = game;
        info = new GridPane();
    }

    public GridPane getInfoUI() {
        this.updateInfoUI();

        Label name = new Label(player.getName());
        name.setId("userName");
        Label money = new Label("$" + Math.round(100 * player.getMoney()) / 100.0);
        money.setId("money");
        Label day = new Label(Integer.toString(game.getDay()));
        day.setId("day");
        Label season = new Label(game.getSeason());
        season.setId("season");

        info.add(new Label("Player:"), 0, 0);
        info.add(new Label("Money:"), 0, 1);
        info.add(new Label("Day:"), 0, 2);
        info.add(new Label("Season:"), 0, 3);

        info.add(name, 1, 0);
        info.add(money, 1, 1);
        info.add(day, 1, 2);
        info.add(season, 1, 3);
        info.setStyle("-fx-background-color: #caa472; -fx-font-size: 15;");

        info.setHgap(10);
        info.setMaxWidth(200);
        info.setMaxHeight(60);

        return info;
    }


    public void updateInfoUI() {
        info.getChildren().clear();

        Label name = new Label(player.getName());
        name.setId("userName");
        Label money = new Label("$" + Math.round(100 * player.getMoney()) / 100.0);
        money.setId("money");
        Label day = new Label(Integer.toString(game.getDay()));
        day.setId("day");
        Label season = new Label(game.getSeason());
        season.setId("season");

        info.add(new Label("Player:"), 0, 0);
        info.add(new Label("Money:"), 0, 1);
        info.add(new Label("Day:"), 0, 2);
        info.add(new Label("Season:"), 0, 3);

        info.add(name, 1, 0);
        info.add(money, 1, 1);
        info.add(day, 1, 2);
        info.add(season, 1, 3);

        info.setHgap(10);
        info.setMaxWidth(200);
        info.setMaxHeight(60);
        info.setStyle("-fx-background-color: #caa472; -fx-font-size: 15;");
    }
}
