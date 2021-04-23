package User;

import ClassPackage.NormalGame;
import ClassPackage.Player;
import javafx.print.PageLayout;

import java.util.ArrayList;

public class GUI extends NormalGame {
    public GUI() {
        CurrentPlayer = new Player();
        df.setWidth(4);
        df.setHeight(5);
        df.setMinesCount(1);
    }
}
