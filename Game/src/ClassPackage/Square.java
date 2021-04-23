package ClassPackage;

import java.io.Serializable;

public class Square implements Serializable {


    private int x,y;
    private SquareInStatus INSquareStatus;
    private SquareOutStatus OUTSquareStatus;
    private int number = 0;
    String Color = "";

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public Square() {
        INSquareStatus = new SquareInStatus();
        OUTSquareStatus = new SquareOutStatus();
    }
    public Square GetSquare(){
        return this;
    }

    public SquareOutStatus getOUTSquareStatus() {
        return OUTSquareStatus;
    }

    public boolean IsMine(){
                if(INSquareStatus.getmSquareInStatus() == "Mine"){
                    return true;
                }

                return false;
    }
    public void make_mine(){
        INSquareStatus.set_Mine();
    }
    public void make_Shield(){
        INSquareStatus.set_Shield();
    }

    public SquareInStatus getINSquareStatus() {
        return INSquareStatus;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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
