package User;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Console console = new Console();

        console.GetDataFromUser();



        console.oldprintGrid();

        console.PrintGrid();
        console.startMove();
        console.PrintGrid();
        console.startMove();
        console.PrintGrid();
        console.startMove();
        console.PrintGrid();
        console.startMove();

    }
}
