package ClassPackage;

import java.io.Serializable;

public class Player implements Serializable {


    private  String name;
    private  Score CurrentScore;
    int Shields;
    boolean LoseStatus ;
    Color playercolor;
    private class Color implements Serializable{
        String color;

        public Color() {
            this.color = "blue";
        }
    }
    public class Score implements Serializable{
        int score;

        public Score() {
            score = 0;
        }
        public void changeScore(int scoreincrease){
            CurrentScore.score += scoreincrease;
        }

        public int getScore() {
            return score;
        }
    }


    public Player() {
        CurrentScore = new Score();
        playercolor = new Color();
        LoseStatus = false;
        Shields = 0;
    }

    public void SetPlayer(String name, int currentScore) {
        this.name = name;
        CurrentScore.score = currentScore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Score getCurrentScore() {
        return CurrentScore;
    }

    public void setCurrentScore(int intcurrentScore) {
        CurrentScore.changeScore(intcurrentScore);
    }

    public PlayerMove GetPlayerMove(){

        return null;
    }

    public String getPlayercolor() {
        return playercolor.color;
    }

    public void setPlayercolor(String playercolor) {
        this.playercolor.color = playercolor;
    }

    public int getShields() {
        return Shields;
    }

    public void setShields(int shields) {
        Shields = shields;
    }


    public boolean isLoseStatus() {
        return LoseStatus;
    }

    public void setLoseStatus(boolean loseStatus) {
        LoseStatus = loseStatus;
    }
}
