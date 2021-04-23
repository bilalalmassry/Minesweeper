package ClassPackage;

import java.io.Serializable;

public class MoveResult implements Serializable {
    private int ScoreChange;
    private SquareOutStatus SquareOutStatus;

    public MoveResult() {
        ScoreChange = 0;
        SquareOutStatus = new SquareOutStatus();
    }

    public MoveResult(int scoreChange, SquareOutStatus SquareOutStatus) {
        ScoreChange = scoreChange;
        this.SquareOutStatus = SquareOutStatus;
    }

    public int getScoreChange() {
        return ScoreChange;
    }

    public void setScoreChange(int scoreChange) {
        ScoreChange = scoreChange;
    }

    public SquareOutStatus getNewSquareStatus() {
        return SquareOutStatus;
    }

    public void setNewSquareStatus(SquareOutStatus newSquareStatus) {
        this.SquareOutStatus = newSquareStatus;
    }
}
