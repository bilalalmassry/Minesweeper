package ClassPackage;

import java.io.Serializable;

public class SquareOutStatus implements Serializable {
    private String mSquareOUTStatus = "Closed";

    public String getmSquareOUTStatus() {
        return mSquareOUTStatus;
    }

    public void setmSquareOUTStatus(String mSquareOUTStatus) {
        this.mSquareOUTStatus = mSquareOUTStatus;
    }
}
