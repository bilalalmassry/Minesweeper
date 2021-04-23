package ClassPackage;

import java.io.Serializable;

public class SquareInStatus implements Serializable {
    private String mSquareInStatus = "Blank";

    public String getmSquareInStatus() {
        return mSquareInStatus;
    }

    public void setmSquareInStatus(String mSquareInStatus) {
        this.mSquareInStatus = mSquareInStatus;
    }


    public void set_Mine(){
        mSquareInStatus = "Mine";
    }
    public void set_Shield(){
        mSquareInStatus = "Shield";

    }

}
