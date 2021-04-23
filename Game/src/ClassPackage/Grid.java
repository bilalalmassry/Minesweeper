package ClassPackage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Grid implements Serializable{
private ArrayList<ArrayList<Square>> SqArrayLists;
private int minescount;
private Game CurrentGame;
private ArrayList<Mine> MinesList;
private int height,width;
private int ShieldCount = 5;

    public ArrayList<ArrayList<Square>> getSqArrayLists() {
        return SqArrayLists;
    }

    private class Mine implements Serializable {
        int x,y;

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

        public void makemine(){
            if(checkmine() == false){
                ApplyMine();
            }

        }

        public void setRandom(){
            Random rand = new Random();
            do{
                x = rand.nextInt(width);
                y = rand.nextInt(height);
            }while(x >= width || y >= height);

        }


        public boolean checkmine(){
             boolean ismine = SqArrayLists.get(x).get(y).IsMine();
             if(ismine == true)
                 return true;

                 return  false;
        }




        public void ApplyMine(){
            SqArrayLists.get(x).get(y).make_mine();
        }



    }


    public Grid() {
        MinesList = new ArrayList<Mine>();
        SqArrayLists = new ArrayList<ArrayList<Square>>();


    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int heightt) {
        height = heightt;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getMinescount() {
        return minescount;
    }

    public void setMinescount(int minescount) {
        this.minescount = minescount;
    }




    public void AcceptMove(PlayerMove mpPlayerMove){ }

    //Fill Grid Square With Indexes
    public void  fillGrid(){
    for(int i =0 ; i < width ; i++){
        ArrayList<Square> tempList= new ArrayList<Square>();
        for(int j = 0 ; j < height ; j++){
            Square tempsq = new Square();
            tempsq.setX(i);
            tempsq.setY(j);
            tempList.add(tempsq);
        }
        SqArrayLists.add(tempList);
    }

}

    //Spread the Mines
    public void MinesSpreader(){

        int z = minescount;
        while(z != 0){
            Mine mine = new Mine();
            mine.setRandom();
            if(mine.checkmine() == false){
                mine.makemine();
                MinesList.add(mine);
                z--;
            }
                else continue;


        }
    }

    //Spread Numbers in Square
    public void NumberSpreader(){
        int X[] = new int[] {0,0,1,-1,1,-1,1,-1};
        int Y[] = new int[] {1,-1,0,0,1,-1,-1,+1};
        for(int i = 0 ; i < minescount ; i++){
            int x = MinesList.get(i).getX();
            int y = MinesList.get(i).getY();
            for(int j = 0 ; j < 8 ;j++){
                if(x + X[j] >= width || x + X[j] < 0)
                    continue;
                if(y + Y[j] >= height || y + Y[j] < 0)
                    continue;
                int tempx = x + X[j];
                int tempy = y + Y[j];
                if(SqArrayLists.get(tempx).get(tempy).getINSquareStatus().getmSquareInStatus() != "Mine"){
                    if(SqArrayLists.get(tempx).get(tempy).getINSquareStatus().getmSquareInStatus() != "Shield") {
                        SqArrayLists.get(tempx).get(tempy).getINSquareStatus().setmSquareInStatus("Number");
                        int tempsquarenumber = SqArrayLists.get(tempx).get(tempy).getNumber();
                        tempsquarenumber++;
                        SqArrayLists.get(tempx).get(tempy).setNumber(tempsquarenumber);
                    }
                }


            }
        }
    }

    public void ShieldSpreader(){

        int z = ShieldCount;
        while(z != 0){
            Shield shield = new Shield();
            shield.setRandomShield();
            if(shield.checkShield() == false){
                shield.makeShield();
                z--;
            }
            else continue;


        }
    }


    public void initGrid(){
        fillGrid();
        MinesSpreader();
        ShieldSpreader();
        NumberSpreader();
    }

    private class Shield implements Serializable{
        int x ,y;

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


        public void makeShield(){
            if(checkShield() == false){
                ApplyShield();
            }

        }

        public void setRandomShield(){
            Random rand = new Random();
            do {
                    x = rand.nextInt(width);
                    y = rand.nextInt(height);

            }while(x >= width || y >= height || SqArrayLists.get(x).get(y).getINSquareStatus().getmSquareInStatus() == "Shield");

        }


        public boolean checkShield(){
            boolean isShield = SqArrayLists.get(x).get(y).IsMine();
            if(isShield == true)
                return true;

            return  false;
        }




        public void ApplyShield(){
            SqArrayLists.get(x).get(y).make_Shield();
        }



    }


    public int getShieldCount() {
        return ShieldCount;
    }

    public void setShieldCount(int shieldCount) {
        ShieldCount = shieldCount;
    }
}

