package User;

import ClassPackage.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Console extends NormalGame {
    public Console() {
        CurrentPlayer = new ConsolePlayer();
    }




    public void oldprintGrid() {
        ArrayList<ArrayList<Square>> tempSquareArray = new ArrayList<ArrayList<Square>>();


        tempSquareArray = getmGrid().getSqArrayLists();
        int Width = getmGrid().getWidth();
        int Height = getmGrid().getHeight();
        for (int i = 0; i < Width; i++) {
            for (int j = 0; j < Height; j++) {
                if (tempSquareArray.get(i).get(j).getINSquareStatus().getmSquareInStatus() == "Mine") {
                    System.out.print("*");
                } else if (tempSquareArray.get(i).get(j).getINSquareStatus().getmSquareInStatus() == "Blank") {
                    System.out.print("-");
                } else
                    System.out.print(Integer.toString(tempSquareArray.get(i).get(j).getNumber()));


            }
            System.out.print("\n");
        }
    }

    public void startMove() {
        PlayerMove p = new PlayerMove();
        p = CurrentPlayer.GetPlayerMove();
        if (AcceptMove(p))
            ApplytMove(p);


    }

    public void GetDataFromUser() {
        Scanner sc = new Scanner(System.in);
        Scanner scl = new Scanner(System.in);
        //Get Player Number
        System.out.println("Enter Players Number");
        int playernum = sc.nextInt();
        PlayerCount = playernum;


        // Get Players Info
        PlayerList = new ArrayList<Player>();
        for (int i = 0; i < PlayerCount; i++) {
            System.out.println("Enter Player " + i + " name");

            String name = scl.nextLine();
            Player p = new ConsolePlayer();
            p.setName(name);
            PlayerList.add(p);
        }

        //Get Height and Width

        int x, y;
        System.out.println("Width");
        x = sc.nextInt();
        System.out.println("Height");
        y = sc.nextInt();
        mGrid.setWidth(x);
        mGrid.setHeight(y);


        //Get Mines Count
        System.out.println("Mines Count");
        int c = 0;
        do {
            c = sc.nextInt();
        } while (c >= (x * y));
        mGrid.setMinescount(c);

        Visited = new boolean[mGrid.getWidth()][mGrid.getHeight()];
        initGame();
    }


    public void PrintGrid() {
        ArrayList<ArrayList<Square>> tempSquareArray = new ArrayList<ArrayList<Square>>();


        tempSquareArray = getmGrid().getSqArrayLists();
        int Width = getmGrid().getWidth();
        int Height = getmGrid().getHeight();
        for (int i = 0; i < Width; i++) {
            for (int j = 0; j < Height; j++) {
                if(tempSquareArray.get(i).get(j).getOUTSquareStatus().getmSquareOUTStatus().equals("Marked"))
                    System.out.print("F");
                if(tempSquareArray.get(i).get(j).getOUTSquareStatus().getmSquareOUTStatus().equals("Closed")){
                        System.out.print("o");
                }

                else if(tempSquareArray.get(i).get(j).getOUTSquareStatus().getmSquareOUTStatus().equals("Opened")) {
                    if (tempSquareArray.get(i).get(j).getINSquareStatus().getmSquareInStatus().equals("Number"))
                        System.out.print(tempSquareArray.get(i).get(j).getNumber());
                    else if (tempSquareArray.get(i).get(j).getINSquareStatus().getmSquareInStatus().equals("Blank")) {
                        if (tempSquareArray.get(i).get(j).getINSquareStatus().getmSquareInStatus().equals("Blank"))
                            System.out.print("-");
                    }
                    else
                        System.out.print("o");
                }
            }
            System.out.print("\n");
        }

    }
}