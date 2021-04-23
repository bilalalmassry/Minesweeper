package ClassPackage;

import User.GUI;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.*;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

public class MainSavaLoad implements Serializable  {
    public transient GUI mGui;
    //ArrayList<Pair<int , String>> arrayListofscore;
    String selectedDirectory = "";
    String DefultName = "";
    ArrayList<Player> TopScroe= new ArrayList<>(3);

    public MainSavaLoad()  throws IOException  {
        mGui = new GUI();
        File f = new File(System.getProperty("user.home") + "\\AppData\\Local\\MineSweeper\\MineSweeper.ser");
        if(!f.exists()){
            SaveApp();
        }
        else
            LoadAPP();

    }

    public GUI getmGui() {
        return mGui;
    }

    public String getSelectedDirectory() {
        return selectedDirectory;
    }

    public MainSavaLoad(GUI mGui) {
        this.mGui = mGui;
    }

    public void Save(){
        System.out.println(System.getProperty("user.home") + "\\AppData");

        String filename = System.getProperty("user.home");
        try
        {
            if(selectedDirectory == ""){
                selectedDirectory = System.getProperty("user.home") + "\\MineSweeper";
                File f = new File(selectedDirectory);
                if(!f.exists()){
                    File s = new File(filename + "\\MineSweeper");
                    s.mkdir();
                }
            }
            DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
            Calendar cal = Calendar.getInstance();

            int second = cal.get(Calendar.SECOND);
            int minute = cal.get(Calendar.MINUTE);
            int hour = cal.get(Calendar.HOUR);
            System.out.println(hour + ":" + (minute) + ":" + second);
            filename = selectedDirectory+ "\\"+"MineSweeper"+ hour+minute+second + ".ser";

            //Saving of object in a file
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of object
            out.writeObject(mGui);

            out.close();
            file.close();

            System.out.println("Object has been serialized");

        }

        catch(IOException ex)
        {
            System.out.println("IOException is caught");
        }

    }

    public GUI Load(String path){
        // Deserialization
        try {
            String filename = path;
            // Reading the object from a file
            FileInputStream file = new FileInputStream
                    (filename);
            ObjectInputStream in = new ObjectInputStream
                    (file);

            // Method for deserialization of object
            mGui = (GUI) in.readObject();

            in.close();
            file.close();
            System.out.println("Object has been deserialized\n"
                    + "Data after Deserialization.");
            System.out.println(mGui.getCurrentPlayer().getCurrentScore().getScore() + "  " + mGui.getDf().getHeight() + " "+ mGui.getmGrid().getMinescount());
            return mGui;
            // System.out.println("z = " + object1.z);
        }

        catch (IOException ex) {
            System.out.println("IOException is caught");
            ex.printStackTrace();
        }
        catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException" +
                    " is caught");
        }
        return  null;
    }

    public String DirectoryChooser() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(new Stage());

        if (selectedDirectory == null) {
            if (this.selectedDirectory.equals("")){
                File f = new File(System.getProperty("user.home") + "\\MineSweeper");
                if(!f.exists() && !f.isDirectory()){
                    new File(System.getProperty("user.home")  + "\\MineSweeper").mkdir();
                }
                selectedDirectory = new File(System.getProperty("user.home") + "\\MineSweeper");
                this.selectedDirectory = selectedDirectory.getAbsolutePath();
            }
            else
                selectedDirectory =new File(this.selectedDirectory);
        }
        this.selectedDirectory = selectedDirectory.getAbsolutePath();
        System.out.println(selectedDirectory);


        return selectedDirectory.getAbsolutePath();
    }

    public void SaveApp() throws IOException {
        String s = System.getProperty("user.home") + "\\AppData\\Local\\MineSweeper";
        File f = new File(System.getProperty("user.home") + "\\AppData\\Local\\MineSweeper");
        if(!f.exists()){
            f.mkdir();
        }
        s += "\\"+"MineSweeper" + ".ser";

            FileOutputStream file = new FileOutputStream(s);
            ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of object
            try {
                out.writeObject(this);
                out.close();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }



            System.out.println("Object has been serialized");

    }
    public void LoadAPP(){
        try {
            String filename = System.getProperty("user.home") + "\\AppData\\Local\\MineSweeper\\MineSweeper.ser";
            // Reading the object from a file
            FileInputStream file = new FileInputStream
                    (filename);
            ObjectInputStream in = new ObjectInputStream
                    (file);

            // Method for deserialization of object
            MainSavaLoad m = (MainSavaLoad) in.readObject();
            this.selectedDirectory  = m.selectedDirectory;


            in.close();
            file.close();
            System.out.println("Object has been deserialized\n");

            // System.out.println("z = " + object1.z);
        }

        catch (IOException ex) {
            System.out.println("IOException is caught");
            ex.printStackTrace();
        }
        catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException" +
                    " is caught");
        }
    }
    public boolean CheckScore(){
       int mx  = -1000;
       int NOW = 0;
       for(int i  = 0 ; i < mGui.getPlayerList().size();++i) {
           if(mx <= mGui.getPlayerList().get(i).getCurrentScore().score) {
               mx = Math.max(mx,mGui.getPlayerList().get(i).getCurrentScore().score);
               NOW = i;
           }

       }
       TopScroe.add(mGui.getPlayerList().get(NOW));
       ArrayList<Player> temp = new ArrayList<>();
       for(int i =0;i < mGui.getPlayerList().size();++i) {
         temp.add(mGui.getPlayerList().get(i));
       }
       TopScroe.clear();
       for(int i = 0 ;i < Math.min(3,temp.size());++i) {
            int maxx = -100000;
            int NOWi = 0;
            for(int j = 0 ;j < temp.size();++j) {
                if(maxx <= temp.get(i).getCurrentScore().score) {
                    maxx = temp.get(i).getCurrentScore().score;
                    NOWi = i;
                }
            }
            TopScroe.add(mGui.getPlayerList().get(NOWi));
       }
        for(int i =0;i < Math.min(3,TopScroe.size());++i) {
            System.out.println(mGui.getCurrentPlayer().getCurrentScore().score);
        }
        return  false;
    }
}


