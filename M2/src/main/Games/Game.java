package main.Games;

public class Game {
    private String difficulty;
    private String season;
    private int day;

    public Game() {
        difficulty = "Easy";
        season = "Fall";
        day = 1;
    }

    // public Game(String difficulty, String season, Seed seed) {
    //     this.difficulty = difficulty;
    //     this.season = season;
    //     this.day = 1;
    //     this.seed.add(seed);
    // }

    public Game(String difficulty, String season) {
        this.difficulty = difficulty;
        this.season = season;
        this.day = 1;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public int getDifficultyInt() {
        if (difficulty.equals("Easy")) {
            return 1;
        }
        if (difficulty.equals("Medium")) {
            return 2;
        }
        if (difficulty.equals("Hard")) {
            return 3;
        }
        return 0;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public int getDay() {
        return day;
    }

    public void incrementDay() {
        day++;
    }

}
