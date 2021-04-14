package main.Games;

import main.Items.Item;
import main.Items.Seed;

public class Market {
    private Item[] menu = new Item[20]; //total items ~12 we can expand later
    private Game game;

    public Market(Game game) {
        this.game = game;
        Seed s0 = new Seed(100, "Bean", difficultiesPrice(2, 1, game), 3);
        Seed s1 = new Seed(100, "Rice", difficultiesPrice(3, 2, game), 5);
        Seed s2 = new Seed(100, "Corn", difficultiesPrice(5, 4, game), 4);
        Seed s3 = new Seed(100, "Wheat", difficultiesPrice(4, 3, game), 2);
        Seed s4 = new Seed(100, "Barley", difficultiesPrice(8, 7, game), 3);
        //growth time is arbitrary
        //quantity doesn't really play a role here

        menu[0] = s0;
        menu[1] = s1;
        menu[2] = s2;
        menu[3] = s3;
        menu[4] = s4;

        menu[5] = s0.getCrop();
        menu[6] = s1.getCrop();
        menu[7] = s2.getCrop();
        menu[8] = s3.getCrop();
        menu[9] = s4.getCrop();

        menu[10] = new Item(100, "Fertilizer", difficultiesPrice(5, 2, game));
        menu[11] = new Item(100, "Pesticide", difficultiesPrice(20, 15, game));

        menu[12] = s0.getPesticideCrop();
        menu[13] = s1.getPesticideCrop();
        menu[14] = s2.getPesticideCrop();
        menu[15] = s3.getPesticideCrop();
        menu[16] = s4.getPesticideCrop();

        menu[17] = new Item(3, "Expansion", difficultiesPrice(250, 200, game));

        menu[18] = new Item(100, "Tractor", difficultiesPrice(50, 100, game));
        menu[19] = new Item(100, "Irrigation", difficultiesPrice(50, 100, game));
    }
    // The getItem method always returns Null;
    public Item getItem(String itemName) {
        for (int i = 0; i < menu.length; i++) {
            if (menu[i].getName().equals(itemName)) {
                return menu[i];
            }
        }
        return null;
    }

    // Get the item by its index
    public Item getItem(int index) {
        return menu[index];
    }


    // Generates new seed and crop price after every day in the market
    public void generateNewPriceDaily() {
        menu[0].setValue(difficultiesPrice(2, 1, this.game));
        menu[1].setValue(difficultiesPrice(3, 2, this.game));
        menu[2].setValue(difficultiesPrice(5, 4, this.game));
        menu[3].setValue(difficultiesPrice(4, 3, this.game));
        menu[4].setValue(difficultiesPrice(8, 7, this.game));

        menu[5].setValue(Math.round((menu[0].getValue() * 3) * 100.0) / 100.0);
        menu[6].setValue(Math.round((menu[1].getValue() * 3) * 100.0) / 100.0);
        menu[7].setValue(Math.round((menu[2].getValue() * 3) * 100.0) / 100.0);
        menu[8].setValue(Math.round((menu[3].getValue() * 3) * 100.0) / 100.0);
        menu[9].setValue(Math.round((menu[4].getValue() * 3) * 100.0) / 100.0);

        menu[10].setValue(difficultiesPrice(5, 2, this.game));
        menu[11].setValue(difficultiesPrice(20, 15, this.game));

        menu[12].setValue(Math.round((menu[0].getValue() * 2.5) * 100.0) / 100.0);
        menu[13].setValue(Math.round((menu[1].getValue() * 2.5) * 100.0) / 100.0);
        menu[14].setValue(Math.round((menu[2].getValue() * 2.5) * 100.0) / 100.0);
        menu[15].setValue(Math.round((menu[3].getValue() * 2.5) * 100.0) / 100.0);
        menu[16].setValue(Math.round((menu[4].getValue() * 2.5) * 100.0) / 100.0);

        menu[17].setValue(Math.round((difficultiesPrice(250, 200, game)) * 100.0) / 100.0);

        menu[18].setValue(difficultiesPrice(50, 100, game));
        menu[19].setValue(difficultiesPrice(50, 100, game));
    }

    public void printMarketPrice() {
        for (int i = 0; i < menu.length; i++) {
            System.out.println(menu[i].getName() + " " + menu[i].getValue());
        }
    }


    public boolean sellToPlayer(main.Games.Player player, String item, int quantity) {
        System.out.println("Purchasing: " + this.getItem(item).getName());
        if (this.getItem(item) == null) {
            return false;
        }
        double money = player.getMoney();
        Item product = this.getItem(item);
        double price = product.getValue();
        // Should maybe modify the price algorithm to have slightly
        // higher prices on higher difficulties
        product.setQuantity(quantity);
        if (money >= (quantity * price)) {
            if (this.getItem(item).getName().equals("Expansion")) {
                player.setMoney(money - quantity * product.getValue());
            } else if (product == menu[18]) {
                player.setMoney(money - quantity * product.getValue());
                player.increaseMaxHarvestingStamina();
                return true;
            } else if (product == menu[19]) {
                player.setMoney(money - quantity * product.getValue());
                player.increaseMaxWateringStamina();
                return true;
            } else if (player.getInventory().add(product)) {
                player.setMoney(money - quantity * product.getValue());
                return true;
            }
        }
        return false;
    }

    public void purchaseFromPlayerHash(main.Games.Player player, String item, int quantity) {
        if (player.getInventory().get(item) == null) {
            return;
        }
        double money = player.getMoney();
        double price = player.getInventory().get(item).getValue();
        boolean success = player.getInventory().remove(item, quantity);
        if (success) {
            player.setMoney(money + (price * quantity));
            // Item was successfully sold, and money is added to user inventory
        }
    }

    private static double randomPrice(int upperbound, int lowerbound) {
        double randPrice = Math.round(100 * ((Math.random())
                * (upperbound - lowerbound) + lowerbound)) / 100.0;
        return randPrice;
    }

    private static double difficultiesPrice(int upperbound, int lowerbound, Game game) {
        if (game.getDifficulty().equals("Easy")) {
            return randomPrice(upperbound, lowerbound);
        } else if (game.getDifficulty().equals("Medium")) {
            return 1.5 * randomPrice(upperbound, lowerbound);
        } else {
            return 2.0 * randomPrice(upperbound, lowerbound);
        }
    }
}
