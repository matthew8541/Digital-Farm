package main.Games;


import javafx.scene.image.Image;

public class Player {
    private double money;
    private String name;
    private Image character;
    private main.Games.Game currGame;
    private main.Games.Inventory inventory;
    private int wateringStamina;
    private int harvestingStamina;
    private int maxWateringStamina;
    private int maxHarvestingStamina;
    // private ArrayList<Seed> seed = new ArrayList<>();

    public Player() {
        money = 1000;
        name = "Player";
        inventory = new main.Games.Inventory(13);
    }

    public Player(String name) {
        this.name = name;
        inventory = new main.Games.Inventory(13);
    }

    // Used in configuration interface only
    public Player(main.Games.Game g1, String name, Image character) {
        currGame = g1;
        this.name = name;
        this.character = character;
        inventory = new main.Games.Inventory(13);

        String difficulty = g1.getDifficulty();
        if (difficulty.equals("Easy")) {
            this.money = 1000;
        } else if (difficulty.equals("Medium")) {
            this.money = 500;
        } else {
            this.money = 300;
        }

        wateringStamina = 5;
        maxWateringStamina = 5;
        harvestingStamina = 5;
        maxHarvestingStamina = 5;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMoney() {
        return this.money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public main.Games.Game getCurrGame() {
        return currGame;
    }

    public void setInventory(main.Games.Inventory inventory) {
        this.inventory = inventory;
    }
    
    public main.Games.Inventory getInventory() {
        return inventory;
    }

    public Image getCharacter() {
        return character;
    }

    // public void setCharacter(Image character) {
    //     try {
    //         c = new Image(new FileInputStream(filepath));
    //     } catch (FileNotFoundException e) {
    //         try {
    //             icon = new Image(new FileInputStream(fileExt + filepath));
    //         } catch (FileNotFoundException e2) {
    //             try {
    //                 icon = new Image(new FileInputStream(fileExt
    //                         + "src/main/images/missingImage.png"));
    //             } catch (FileNotFoundException ignored) {
    //                 System.out.println("ERROR: Set Icon could not find file");
    //                 icon = null;
    //             }
    //         }
    //     }
    // }



    public int getHarvestingStamina() {
        return harvestingStamina;
    }

    public int getWateringStamina() {
        return wateringStamina;
    }

    public int getMaxWateringStamina() {
        return maxWateringStamina;
    }

    public int getMaxHarvestingStamina() {
        return maxHarvestingStamina;
    }

    public void resetStaminas() {
        harvestingStamina = maxHarvestingStamina;
        wateringStamina = maxWateringStamina;
    }

    public void increaseMaxHarvestingStamina() {
        maxHarvestingStamina += 5;
        harvestingStamina += 5;
    }

    public void increaseMaxWateringStamina() {
        maxWateringStamina += 5;
        wateringStamina += 5;
    }

    public void decreaseWateringStamina() {
        wateringStamina--;
    }

    public void decreaseHarvestingStamina() {
        harvestingStamina--;
    }
    // public ArrayList getSeed() {
    //     return seed;
    // }

    // public void addSeed(Seed seed) {
    //     this.seed.add(seed);
    // }
}
