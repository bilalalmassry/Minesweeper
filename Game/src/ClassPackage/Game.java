package ClassPackage;

import java.io.Serializable;
import java.util.ArrayList;

public abstract  class Game implements Serializable {

    protected ArrayList<Player> PlayerList ;
    protected Player CurrentPlayer ;
    protected Grid mGrid;
    protected int PlayerCount;
    protected ArrayList<PlayerMove> moves;
    protected boolean Visited[][];
    protected int  autoOpenedSq;
    String GameType;
    int OpenedMines = 0;
   // protected PlayerMove playerMove;

    public ArrayList<PlayerMove> getMoves() {
        return moves;
    }

    public void setmGrid(Grid mGrid) {
        this.mGrid = mGrid;
    }

    public Game() {
        autoOpenedSq = 0;
        mGrid = new Grid();
        PlayerCount = 0;
       // playerMove = new PlayerMove();
        moves = new ArrayList<>();
        GameType = "MineGame";
    }

    public Grid getmGrid() {
        return mGrid;
    }

    protected abstract class GameRules implements Serializable{
        protected int height ,width;
        protected  int MinesCount;
        protected  int ShieldsInGrid;
        protected int ShieldsforPlayer;
        protected String GameType;


        public GameRules(){

        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getMinesCount() {
            return MinesCount;
        }

        public void setMinesCount(int minesCount) {
            MinesCount = minesCount;
        }

        public int  GetScoreChange(ArrayList<PlayerMove> mPlayerMoveList){

            return 0;
        }

        public Player DecideNextPlayer(){


            return null;
        }


        public int getShieldsInGrid() {
            return ShieldsInGrid;
        }

        public void setShieldsInGrid(int shieldsInGrid) {
            ShieldsInGrid = shieldsInGrid;
        }

        public int getShieldsforPlayer() {
            return ShieldsforPlayer;
        }

        public void setShieldsforPlayer(int shieldsforPlayer) {
            ShieldsforPlayer = shieldsforPlayer;
        }

        public String getGameType() {
            return GameType;
        }

        public void setGameType(String gameType) {
            GameType = gameType;
        }
    }


    public Player getCurrentPlayer() {
        return CurrentPlayer;
    }

    public boolean AcceptMove (PlayerMove playerMove ) {


        return true;
    }
    public void ApplytMove (PlayerMove mPlayerMove ) {




    }

    public int getPlayerCount() {
        return PlayerCount;
    }

    public void setPlayerCount(int playerCount) {
        PlayerCount = playerCount;
    }

    public void SetPLayersnames(int PlayerCount , String name){
            Player player = new Player();
            player.SetPlayer(name , 0);

            PlayerList.add(player);
    }




    /*public void GetMove(){
        playerMove = CurrentPlayer.GetPlayerMove();

    }*/
public void AddPlayer(Player p){
    PlayerList.add(p);
}

    public void setCurrentPlayer(Player currentPlayer) {
        CurrentPlayer = currentPlayer;
    }

    public ArrayList<Player> getPlayerList() {
        return PlayerList;
    }


    public boolean[][] getVisited() {
        return Visited;
    }

    public String getGameType() {
        return GameType;
    }

    public void setGameType(String gameType) {
        GameType = gameType;
    }
}
