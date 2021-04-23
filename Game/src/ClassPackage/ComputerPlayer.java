package ClassPackage;

import java.util.Random;

public class ComputerPlayer extends Player {

    int x, y;

    public void Randomselect(int width, int height, Grid mGrid){

            Random rand = new Random();
            do{
                x = rand.nextInt(width);
                y = rand.nextInt(height);
            }while(x >= width || y >= height || mGrid.getSqArrayLists().get(x).get(y).getOUTSquareStatus().getmSquareOUTStatus().equals("Opened") ||
            mGrid.getSqArrayLists().get(x).get(y).getOUTSquareStatus().getmSquareOUTStatus().equals("Marked"));


    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
