package ClassPackage;

import java.util.Scanner;

public class ConsolePlayer extends Player{
    @Override
    public PlayerMove GetPlayerMove() {
        PlayerMove playerMove = new PlayerMove();
        Square  mSquare = new Square();
        MoveType moveType = new MoveType();



        System.out.println("Enter the square : ");



        Scanner inputDataReader = new Scanner(System.in);
        int Sum = 0;
        char Letter = ' ';
        String s;
        s = inputDataReader.next();
        if (s.charAt(0) != '-') {
            ///Revealed
            moveType.setType("Reveal");
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                    Sum = Sum * 10;
                    Sum += (((int) s.charAt(i)) - 48 );
                } else {
                    Letter = s.charAt(i);
                }

            }
        } else {
            ///Marked
            moveType.setType("Mark");
            for (int i = 1; i < s.length(); i++) {
                if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                    Sum = Sum * 10;
                    Sum += (((int) s.charAt(i)) - 48);
                } else {
                    Letter = s.charAt(i);
                }

            }
        }

        mSquare.setX(Sum);
        mSquare.setY(((int) Letter) - 65);
        playerMove.setmSquare(mSquare);
        playerMove.setMoveType(moveType);
        return playerMove;
    }
}
