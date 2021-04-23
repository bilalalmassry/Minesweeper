package ClassPackage;

//import com.sun.xml.internal.bind.v2.runtime.reflect.ListIterator;
//import com.sun.xml.internal.ws.api.ha.StickyFeature;

import java.util.ArrayList;
import java.util.Iterator;

public class NormalGame  extends Game {
    protected DefultRules df;

    public class DefultRules extends GameRules {
     //   Iterator<Player> iterator = PlayerList.iterator();

        public DefultRules() {
            height = 0;
            width = 0;
            MinesCount = 0;
            ShieldsInGrid = 0;
            ShieldsforPlayer = 0;
            GameType = "MineGame";
        }


        @Override
        public Player DecideNextPlayer() {
            for (int i = 0; i < getPlayerList().size(); i++) {
                if (PlayerList.get(i).getPlayercolor().equals(getCurrentPlayer().getPlayercolor())) {
                    if(i+1  == getPlayerList().size())
                    return PlayerList.get(0);
                    else
                        return PlayerList.get(i+1);
                }

            }
            return null;
        }

    }
        public DefultRules getDf() {
            return df;
        }


    @Override
    public boolean AcceptMove(PlayerMove playerMove) {
        int x = playerMove.getmSquare().getX();
        int y = playerMove.getmSquare().getY();
        if(x  >= mGrid.getWidth() || x < 0)
            return  false;
        if(y >= mGrid.getHeight() || y < 0)
            return  false;
        if(playerMove.getMoveType().getType().equals("Reveal")){
            if(mGrid.getSqArrayLists().get(x).get(y).getOUTSquareStatus().getmSquareOUTStatus().equals("Opened"))
                return false;
            if(mGrid.getSqArrayLists().get(x).get(y).getOUTSquareStatus().getmSquareOUTStatus().equals("Marked"))
                return false;
            else
                return true;

        }
        else{

            if(mGrid.getSqArrayLists().get(x).get(y).getOUTSquareStatus().getmSquareOUTStatus().equals("Opened"))
                return false;
            else
                return true;
        }



    }

    @Override
    public void ApplytMove(PlayerMove playermove) {
        Visited = new boolean[mGrid.getWidth()][mGrid.getHeight()];
        int x = playermove.getmSquare().getX();
        int y = playermove.getmSquare().getY();
        if(AcceptMove(playermove)){
            Visited[x][y]=true;
            if(playermove.getMoveType().getType().equals("Reveal")){
                if(mGrid.getSqArrayLists().get(x).get(y).getOUTSquareStatus().getmSquareOUTStatus().equals("Closed")){
                    if(mGrid.getSqArrayLists().get(x).get(y).getINSquareStatus().getmSquareInStatus().equals("Blank")){
                        FillFlood(mGrid.getSqArrayLists().get(x).get(y).getX(),mGrid.getSqArrayLists().get(x).get(y).getY());
                    }
                    else if (mGrid.getSqArrayLists().get(x).get(y).getINSquareStatus().getmSquareInStatus().equals("Number")){
                        getCurrentPlayer().setCurrentScore(mGrid.getSqArrayLists().get(x).get(y).getNumber());
                    }
                    else if(mGrid.getSqArrayLists().get(x).get(y).getINSquareStatus().getmSquareInStatus().equals("Shield")){
                        getCurrentPlayer().setShields(getCurrentPlayer().Shields +1);
                    }
                    else{
                        if(getCurrentPlayer().getShields() == 0){
                            if(GameType.equals("ScoreGame")){
                                getCurrentPlayer().setCurrentScore(-25);
                                if(getCurrentPlayer().getCurrentScore().getScore() < -10){
                                    getCurrentPlayer().LoseStatus = true;
                                }

                            }
                            else
                                getCurrentPlayer().LoseStatus = true;
                            OpenedMines++;
                        }
                        else
                            getCurrentPlayer().setShields(getCurrentPlayer().Shields -1);
                    }


                    mGrid.getSqArrayLists().get(x).get(y).getOUTSquareStatus().setmSquareOUTStatus("Opened");


                }
            }
            else{
                if(mGrid.getSqArrayLists().get(x).get(y).getOUTSquareStatus().getmSquareOUTStatus().equals("Marked")){
                    mGrid.getSqArrayLists().get(x).get(y).getOUTSquareStatus().setmSquareOUTStatus("Closed");
                }
                else if(mGrid.getSqArrayLists().get(x).get(y).getOUTSquareStatus().getmSquareOUTStatus().equals("Closed")){
                    mGrid.getSqArrayLists().get(x).get(y).getOUTSquareStatus().setmSquareOUTStatus("Marked");
                    if (mGrid.getSqArrayLists().get(x).get(y).getINSquareStatus().getmSquareInStatus().equals("Mine"))
                        getCurrentPlayer().setCurrentScore(5);
                    else
                        getCurrentPlayer().setCurrentScore(-1);



                }
            }
            moves.add(playermove);
        }

    }

    public void  FillFlood(int x, int y){
        int X[]={0,0,1,-1,1,-1,1,-1};
        int Y[]={1,-1,0,0,1,-1,-1,1};
        if(mGrid.getSqArrayLists().get(x).get(y).getOUTSquareStatus().getmSquareOUTStatus().equals("Closed"))
        {
            Visited[x][y]=true;
            mGrid.getSqArrayLists().get(x).get(y).getOUTSquareStatus().setmSquareOUTStatus("Opened");
            autoOpenedSq++;
            if(mGrid.getSqArrayLists().get(x).get(y).getINSquareStatus().getmSquareInStatus().equals("Shield"))
                getCurrentPlayer().setShields(getCurrentPlayer().getShields()+1);
            getCurrentPlayer().setCurrentScore(1);
            if(mGrid.getSqArrayLists().get(x).get(y).getINSquareStatus().getmSquareInStatus().equals("Blank"))
            {
                for(int i=0;i<8;i++)
                {
                    int cx=x+X[i];
                    int cy=y+Y[i];
                    if( cx<0 ||cx>=mGrid.getWidth() || cy<0 || cy>=mGrid.getHeight() )
                        continue;
                    if(Visited[cx][cy]==false)
                        FillFlood(cx,cy);
                }
            }
        }
    }

    public void initGame() {
        mGrid.setHeight(df.getHeight());
        mGrid.setWidth(df.getWidth());
        mGrid.setMinescount(df.getMinesCount());
        mGrid.setShieldCount(df.getShieldsInGrid());
        GameType = df.getGameType();
        mGrid.initGrid();
    }

    public NormalGame() {
        PlayerList = new ArrayList<Player>();
        df = new DefultRules();
    }


    public boolean EndGame(){
        int closedcell = 0 ;
        int closedcellmine= 0;
        int cell = 0;
        for(int i = 0 ; i< mGrid.getWidth() ; i++){
    for(int j = 0 ; j < mGrid.getHeight() ; j++){
        if(mGrid.getSqArrayLists().get(i).get(j).getOUTSquareStatus().getmSquareOUTStatus().equals("Closed") ||
                mGrid.getSqArrayLists().get(i).get(j).getOUTSquareStatus().getmSquareOUTStatus().equals("Marked")){
                closedcell++;
                if(mGrid.getSqArrayLists().get(i).get(j).getINSquareStatus().getmSquareInStatus().equals("Mine"))
                    closedcellmine++;
        }
    }

}
        if(closedcell == closedcellmine)
            return true;
        else
            return  false;
    }


    public PlayerMove setPlayerMoveDirctly(int x, int y, String s,Player p){
        PlayerMove playerMove = new PlayerMove();
        MoveType moveType = new MoveType();
        Square square = new Square();
        Player pp =new Player();
        pp = p;

        square.setY(y);
        square.setX(x);
moveType.setType(s);
        playerMove.setmSquare(square);
        playerMove.setMoveType(moveType);
        playerMove.setmPlayer(p);

        return playerMove;
    }
}
