package ClassPackage;

import java.io.Serializable;

public class PlayerMove implements Serializable {
    private Player mPlayer;
    private Square mSquare;
    private MoveType moveType;
    private MoveResult moveResult;

    public PlayerMove() {
        mPlayer = new Player();
        mSquare = new Square();
        moveType = new MoveType();
        moveResult = new MoveResult();
    }

    public PlayerMove(Player mPlayer, Square mSquare, MoveType moveType, MoveResult moveResult) {
        this.mPlayer = mPlayer;
        this.mSquare = mSquare;
        this.moveType = moveType;
        this.moveResult = moveResult;
    }

    public Player getmPlayer() {
        return mPlayer;
    }

    public void setmPlayer(Player mPlayer) {
        this.mPlayer = mPlayer;
    }

    public Square getmSquare() {
        return mSquare;
    }

    public void setmSquare(Square mSquare) {
        this.mSquare = mSquare;
    }

    public MoveType getMoveType() {
        return moveType;
    }

    public void setMoveType(MoveType moveType) {
        this.moveType = moveType;
    }

    public MoveResult getMoveResult() {
        return moveResult;
    }

    public void setMoveResult(MoveResult moveResult) {
        this.moveResult = moveResult;
    }
}
