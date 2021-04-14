package main.Items;

import main.UserInterface.GameRunner;

public class Seed extends Item {
    private double maxGrowthTime;
    private double growthTime;
    private double growthRate;
    private int waterLevel;
    private boolean mature;
    private int fertilizeLevel; /* how much fertilizer has been used on this plant */
    private boolean hasPesticide; /* If pesticide has ever been used on the seed */
    private Item crop;
    private Item pesticideCrop;
    private boolean killed;
    
    // We changed the growthTime as 0 when seed is instantiated
    // Growth time of all seeds should be instantiated as 0
    public Seed(int quantity, String name, double value, double maxGrowthTime) {
        super(quantity,  name + "Seed", value);
        this.growthTime = 0;
        this.maxGrowthTime = maxGrowthTime;
        this.waterLevel = 0;
        this.growthRate = 1;
        // Growthrate will help if we need to add fertilizer,
        // and its now being used to show when a crop is spoiled
        this.fertilizeLevel = 0;
        this.hasPesticide = false;
        this.killed = false;
        this.crop = new Item(1, name + "Crop", Math.round((value * 3) * 100.0) / 100.0);
        this.pesticideCrop = new Item(1, name 
            + "PesticideCrop", Math.round((value * 2.5) * 100.0) / 100.0);
        mature = growthTime >= 20;
    }

    public Seed(int quantity, String name, double value, double maxGrowthTime,
                double growthRate, Item crop, boolean mature) {
        super(quantity,  name, value);
        this.maxGrowthTime = maxGrowthTime;
        this.growthTime = 0;
        this.growthRate = growthRate;
        this.crop = crop;
        this.mature = mature;
        this.fertilizeLevel = 0;
        this.hasPesticide = false;
        this.killed = false;
    }

    public Seed copy() {
        Seed seed = new Seed(super.getQuantity(), super.getName(), super.getValue(),
                maxGrowthTime, growthRate, crop.copy(), mature);
        return seed;
    }

    public double getMaxGrowthTime() {
        return maxGrowthTime;
    }

    public double getGrowthTime() {
        return growthTime;
    }

    public double getGrowthRate() {
        return growthRate;
    }

    public void setGrowthRate(double growthRate) {
        this.growthRate = growthRate;
        if (growthRate == 0) {
            killPlant();
        }
    }

    public int getWaterLevel() {
        return waterLevel;
    }

    public void minusWaterLevel() {
        waterPlant(-1);
    }

    public int getFertilizeLevel() {
        return fertilizeLevel;
    }

    public boolean getHasPesticide() {
        return hasPesticide;
    }

    public void setHasPesticide(boolean hasPesticide) {
        this.hasPesticide = hasPesticide;
    }

    public boolean getKilled() {
        return killed;
    }

    public Item getCrop() {
        if (hasPesticide) {
            return pesticideCrop;
        }
        return crop;
    }

    public Item getPesticideCrop() {
        return pesticideCrop;
    }

    // the growthtime should be incremented by a day
    public void addGrowthTime() {
        if (growthRate == 0) {
            return;
        }
        growthTime += growthRate;
        if (growthTime >= maxGrowthTime) {
            this.setMature(true);
        } else if (growthTime >= (maxGrowthTime / 2)) {
            String fileExt;
            if (GameRunner.getFileExt().equals("")) {
                fileExt = "src/main/images/";
            } else {
                fileExt = "M2/src/main/images/";
            }
            this.setIcon(fileExt + getName() + "Immature.png");
        }
        // waterPlant(-1);
    }

    public void setMature(boolean maturity) {
        mature = maturity;
        if (mature) {
            String fileExt;
            if (GameRunner.getFileExt().equals("")) {
                fileExt = "src/main/images/";
            } else {
                fileExt = "M2/src/main/images/";
            }
            this.setIcon(fileExt + getName() + "Mature.png");
        }
    }

    public boolean isMature() {
        if (growthTime >= maxGrowthTime) {
            mature = true;
        }
        return mature;
    }

    public void waterPlant(int amount) {
        if (amount > 0 && waterLevel < 0) {
            waterLevel = 0;
        }
        waterLevel += amount;
        if (waterLevel >= 4 || waterLevel < -2) {
            System.out.println("I AM A DEAD CROP I AM SO DEAD OH GOD THE PAIN IT BURNS");
            killPlant();
        }
    }

    public void killPlant() {
        super.setIcon("src/main/images/DeadCrop.png");
        growthRate = 0;
        killed = true;
        mature = false;
    }

    
    public void addFertilizerLevel() {
        if (!(fertilizeLevel >= 3)) {
            fertilizeLevel++;
        }
    }

    /* This function is to fertilize the plants */
    public void fertilizePlant() {
        fertilizeLevel -= 1;
        addGrowthTime();
    }

    /* This function is to use pesticide on the plants */
    public void usePesticide() {
        if (!hasPesticide) {
            hasPesticide = true;
        }
    }
}
