package sample;

import ClassPackage.*;
import User.GUI;
import com.sun.glass.ui.CommonDialogs;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.*;
import javafx.util.Duration;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static java.lang.System.exit;


public class Main extends Application {
    MainSavaLoad mgui ;

    final int[] counter = {0};
    MainSavaLoad m;
    ImageView array[][] = new ImageView[30][16];
    public class Timer extends Thread
    {
        int i =0;
        @Override
        public void run() {
            super.run();

                while(i < 12)
                {
                    int z = i;
                    Platform.runLater(new Runnable() {
                        @Override public void run() {
                            if (counter[0] != 11) {
                                counter[0]++;
                                if(mgui.getmGui().getCurrentPlayer() instanceof ComputerPlayer){
                                    if(counter[0] == 5){
                                        AIPlayer();
                                        UpdateGrid(((ComputerPlayer) mgui.getmGui().getCurrentPlayer()).getX(),((ComputerPlayer) mgui.getmGui().getCurrentPlayer()).getY());
                                        if(FilteredNextPlyer() || mgui.getmGui().EndGame()){
                                            GameResult();

                                            return;
                                        }
                                        counter[0] = 0;

                                        }
                                    }

                                }


                            else{
            /*   if(FilteredNextPlyer() || mgui.getmGui().EndGame()){
                    GameResult();
                    finalFiveSecondsWonder.stop();
                }*/
                                FilteredNextPlyer();
                                counter[0] = 0;
                            }
                        }
                    });
                    i--;

                    try {
                        // thread to sleep for 1000 milliseconds
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        System.out.println(e);
                    }




                }
                if(i==0)
                {
                    FilteredNextPlyer();
                    Platform.runLater(new Runnable() {
                        @Override public void run() {

                        }
                    });

                }


        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        mgui = new MainSavaLoad();

        StartMenu(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);

    }


    public void StartMenu(Stage primaryStage){
        //Design Scene


        VBox root = new VBox(10);


        Button start = new Button("Start");
        Image image = new Image(getClass().getResourceAsStream("ic_play_96dp.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        start.setGraphic(imageView);
        start.setPrefSize(100,30);
        start.setContentDisplay(ContentDisplay.RIGHT);


        Button Options = new Button("Options");
        image = new Image(getClass().getResourceAsStream("ic_settings_32dp.png"));
        imageView = new ImageView(image);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        Options.setGraphic(imageView);
        Options.setPrefSize(100,30);
        Options.setContentDisplay(ContentDisplay.RIGHT);



        Button Quit = new Button("Quit  ");
        image = new Image(getClass().getResourceAsStream("ic_exit_32dp.png"));
        imageView = new ImageView(image);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        Quit.setGraphic(imageView);
        Quit.setPrefSize(100,30);
        Quit.setContentDisplay(ContentDisplay.RIGHT);

        root.setAlignment(Pos.CENTER);




        //Buttons Actions

        start.setOnAction((ActionEvent e)-> {
            StartGame(primaryStage);
        });

        Options.setOnAction((ActionEvent e)-> {
            OptionsMenu(primaryStage);
        });



        Button load = new Button("Load");
        load.setOnAction((ActionEvent e)->{
           // DirectoryChooser directoryChooser = new DirectoryChooser();
        //    File selectedDirectory =directoryChooser.showDialog(primaryStage);
            Load(primaryStage);
         /*   FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Extension sre","*.ser")
            );
            File file = new File("C:\\Users\\Bcc\\Desktop\\Java Project\\Game1.9");
            fileChooser.setInitialDirectory(file);
            File selectedDirectory =fileChooser.showOpenDialog(primaryStage);
            if(selectedDirectory == null){

            }else{
                System.out.println(selectedDirectory.getAbsolutePath());
              mgui.mGui =  mgui.Load(selectedDirectory.getAbsolutePath());
            }*/
        });

        root.getChildren().addAll(start,Options,Quit , load);

        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


public void StartGame(Stage primaryStage){
        VBox vBox1 = new VBox(2);
    HBox root = new HBox();
    VBox vbox = new VBox(2);
    GridPane gridPane = new GridPane();
    if(mgui.getmGui().getVisited() == null)
    mgui.getmGui().initGame();
    if(mgui.getmGui().getPlayerList().size() == 0)
    {
        Player p =new Player();
        p.setName("Player");
        p.setPlayercolor("Blue");
        mgui.getmGui().getPlayerList().add(p);
    }
    Text[] l  = new Text[mgui.getmGui().getPlayerList().size()];
    final int[] counter = {0};

    mgui.getmGui().setCurrentPlayer(mgui.getmGui().getPlayerList().get(0));


    Label timelaLabel = new Label();
    Timeline fiveSecondsWonder = new Timeline();
    Timeline finalFiveSecondsWonder = fiveSecondsWonder;
    fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if (counter[0] != 11) {
                timelaLabel.setText(String.valueOf(counter[0]++));
                timelaLabel.setStyle("-fx-font-size: 20;");
                if(mgui.getmGui().getCurrentPlayer() instanceof ComputerPlayer){
                    if(counter[0] == 5){
                        AIPlayer();
                        UpdateGrid(((ComputerPlayer) mgui.getmGui().getCurrentPlayer()).getX(),((ComputerPlayer) mgui.getmGui().getCurrentPlayer()).getY());
                        if(FilteredNextPlyer() || mgui.getmGui().EndGame()){
                            GameResult();
                            mgui.CheckScore();
                            mgui.Save();
                            try {
                                mgui.SaveApp();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            primaryStage.close();

                            return;
                        }
                        counter[0] = 0;
                        vbox.getChildren().clear();
                        for (int c = 0; c < mgui.getmGui().getPlayerList().size(); c++) {
                            l[c] = new Text(mgui.getmGui().getPlayerList().get(c).getName() + "  Score  " + mgui.getmGui().getPlayerList().get(c).getCurrentScore().getScore() + "  " +
                                    mgui.getmGui().getPlayerList().get(c).getPlayercolor() + "     "
                                  +"Sheilds"  + mgui.getmGui().getPlayerList().get(c).getShields());
                            if(mgui.getmGui().getCurrentPlayer() == mgui.getmGui().getPlayerList().get(c))
                                l[c].setFill(Color.RED);
                            ImageView imageViewsh = new ImageView(new Image(getClass().getResourceAsStream("shield.png")));
                            imageViewsh.setFitWidth(20);
                            imageViewsh.setFitHeight(20);
                            // l[c].setGraphic(imageViewsh);
                            //   l[c].setContentDisplay(ContentDisplay.RIGHT);
                            vbox.getChildren().addAll(l[c]);
                        }
                    }

                }

            }
            else{
            /*   if(FilteredNextPlyer() || mgui.getmGui().EndGame()){
                    GameResult();
                    finalFiveSecondsWonder.stop();
                }*/
               FilteredNextPlyer();
                counter[0] = 0;
                vbox.getChildren().clear();
                for (int c = 0; c < mgui.getmGui().getPlayerList().size(); c++) {
                    l[c] = new Text(mgui.getmGui().getPlayerList().get(c).getName() + "     Score  " + mgui.getmGui().getPlayerList().get(c).getCurrentScore().getScore() + "  " +
                            mgui.getmGui().getPlayerList().get(c).getPlayercolor() + "    Sheilds "
                            + mgui.getmGui().getPlayerList().get(c).getShields() );
                    if(mgui.getmGui().getCurrentPlayer() == mgui.getmGui().getPlayerList().get(c))
                        l[c].setFill(Color.RED);
                    ImageView imageViewsh = new ImageView(new Image(getClass().getResourceAsStream("shield.png")));
                    imageViewsh.setFitWidth(20);
                    imageViewsh.setFitHeight(20);
                    // l[c].setGraphic(imageViewsh);
                    //   l[c].setContentDisplay(ContentDisplay.RIGHT);
                    vbox.getChildren().addAll(l[c]);
                }
            }
        }


    }));
    fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
    fiveSecondsWonder.play();


    for (int c = 0; c < mgui.getmGui().getPlayerList().size(); c++) {
        l[c] = new Text(mgui.getmGui().getPlayerList().get(c).getName() + "   Score     " + mgui.getmGui().getPlayerList().get(c).getCurrentScore().getScore() + "  " +
                mgui.getmGui().getPlayerList().get(c).getPlayercolor() + "   Sheilds  "
                + mgui.getmGui().getPlayerList().get(c).getShields());

        if(mgui.getmGui().getCurrentPlayer() == mgui.getmGui().getPlayerList().get(c))
            l[c].setFill(Color.RED);
        if(mgui.getmGui().getPlayerList().get(c).isLoseStatus())
            l[c].setFill(Color.BLUE);
        ImageView imageViewsh = new ImageView(new Image(getClass().getResourceAsStream("shield.png")));
        imageViewsh.setFitWidth(20);
        imageViewsh.setFitHeight(20);
        // l[c].setGraphic(imageViewsh);
        //   l[c].setContentDisplay(ContentDisplay.RIGHT);
        vbox.getChildren().addAll(l[c]);
    }

    for(int i = 0 ; i < mgui.getmGui().getmGrid().getWidth() ; i++){
        for(int j = 0 ; j < mgui.getmGui().getmGrid().getHeight() ; j++) {
            array[i][j] = new ImageView();
            array[i][j].setImage(new Image(getClass().getResourceAsStream("cell_closed.png")));
            array[i][j].setFitHeight(30);
            array[i][j].setFitWidth(30);
            gridPane.add(array[i][j],i,j);

            int finalI = i;
            int finalJ = j;

            array[i][j].addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
                if(!(mgui.getmGui().getCurrentPlayer() instanceof ComputerPlayer)){
                    PlayerMove playerMove = new PlayerMove();
                    if( e.isPrimaryButtonDown()) {
                        playerMove = mgui.getmGui().setPlayerMoveDirctly(finalI,finalJ,"Reveal",mgui.getmGui().getCurrentPlayer());
                    } else if( e.isSecondaryButtonDown()) {
                        playerMove=  mgui.getmGui().setPlayerMoveDirctly(finalI,finalJ,"Mark",mgui.getmGui().getCurrentPlayer());
                    }

                    if(mgui.getmGui().AcceptMove(playerMove)) {
                        mgui.getmGui().ApplytMove(playerMove);
                        UpdateGrid(finalI,finalJ);
                        if(FilteredNextPlyer() || mgui.getmGui().EndGame()){
                            GameResult();
                            mgui.CheckScore();
                            try {
                                mgui.SaveApp();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            return;
                        }


                        counter[0]  =0;
                        vbox.getChildren().clear();
                        for (int c= 0 ; c < mgui.getmGui().getPlayerList().size() ; c++)
                        {
                            l[c].setText(mgui.getmGui().getPlayerList().get(c).getName() + "  Score    " + mgui.getmGui().getPlayerList().get(c).getCurrentScore().getScore() + "  " +
                                    mgui.getmGui().getPlayerList().get(c).getPlayercolor() + "    Sheilds    " + mgui.getmGui().getPlayerList().get(c).getShields());
                            if(mgui.getmGui().getPlayerList().get(c).isLoseStatus())
                                l[c].setFill(Color.BLUE);
                            ImageView imageViewsh = new ImageView(new Image(getClass().getResourceAsStream("shield.png")));
                            imageViewsh.setFitWidth(20);
                            imageViewsh.setFitHeight(20);
                            // l[c].setGraphic(imageViewsh);
                            // l[c].setContentDisplay(ContentDisplay.RIGHT);
                            if(mgui.getmGui().getCurrentPlayer() == mgui.getmGui().getPlayerList().get(c))
                                l[c].setFill(Color.RED);
                            else
                                l[c].setFill(Color.BLACK);
                            vbox.getChildren().addAll(l[c]);
                        }
                    }
                }

            });
        }
    }
    Button save = new Button();
    save.setText("Save");
    save.setOnAction((ActionEvent e)->{
        MainSavaLoad m = new MainSavaLoad(mgui.getmGui());
        m.Save();

    });



    final Menu menu1 = new Menu("File");
    MenuItem menuItem= new MenuItem("Save");
    MenuItem menuItem2= new MenuItem("Load");
    MenuItem menuItem3= new MenuItem("Back");
    menuItem.setOnAction((ActionEvent e)->{
        MainSavaLoad m = new MainSavaLoad(mgui.getmGui());
        m.Save();

    });
    menuItem3.setOnAction((ActionEvent e)->{
       StartMenu(primaryStage);

    });
    menu1.getItems().addAll(menuItem,menuItem2,menuItem3);
    javafx.scene.control.MenuBar menuBar = new javafx.scene.control.MenuBar();
    menuBar.getMenus().addAll(menu1);
 //   root.setAlignment(Pos.CENTER);
    root.getChildren().addAll(gridPane,vbox,timelaLabel);
    vBox1.getChildren().addAll(menuBar,root);
   // vBox1.setAlignment(Pos.CENTER);
    for(int i = 0 ; i < mgui.getmGui().getmGrid().getWidth() ; i++){
        for(int j = 0 ; j < mgui.getmGui().getmGrid().getHeight() ; j++) {
            updateSquar(i,j);
        }}
    Platform.setImplicitExit(true);
    primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
        @Override
        public void handle(WindowEvent event) {
            Stage stage = new Stage();

            VBox root = new VBox(5);
           HBox h = new HBox(20);
           Button Save = new Button("Save");

            Button Exit = new Button("Exit Without Saving");

            Save.setOnAction((ActionEvent e)->{
                mgui.Save();
                stage.close();
                primaryStage.close();
            });

            Exit.setOnAction((ActionEvent e)->{
                stage.close();
                primaryStage.close();
            });
            h.getChildren().addAll(Save ,Exit);
            root.getChildren().add(h);
            h.setAlignment(Pos.CENTER);
            root.setAlignment(Pos.CENTER);
            stage.setScene(new Scene(root, 200, 100));
            stage.show();
        }

    });
    primaryStage.setScene(new Scene(vBox1, mgui.getmGui().getmGrid().getWidth()*30+160, mgui.getmGui().getmGrid().getHeight()*30+40));
    primaryStage.show();


}

public void UpdateGrid(int x,int y){
   int c = mgui.getmGui().getVisited().length;
        if(c == 0){
            updateSquar(x,y);
        }
        else
        {
            for(int i = 0 ; i < mgui.getmGui().getVisited().length ; i++){
                for(int j = 0 ; j < mgui.getmGui().getVisited()[i].length ; j++){
                    if(mgui.getmGui().getVisited()[i][j])
                    updateSquar(i,j);
                }
            }
        }


}

public void updateSquar(int i , int j){
    Image image = new Image(getClass().getResourceAsStream("cell_closed.png"));

    if(mgui.getmGui().getmGrid().getSqArrayLists().get(i).get(j).getOUTSquareStatus().getmSquareOUTStatus().equals("Marked"))
    {
        image = new Image(getClass().getResourceAsStream("cell_flagged.png"));
        array[i][j].setImage(image);
        array[i][j].setFitHeight(30);
        array[i][j].setFitWidth(30);
        //button.setContentDisplay(ContentDisplay.CENTER);
    }
    else if(mgui.getmGui().getmGrid().getSqArrayLists().get(i).get(j).getOUTSquareStatus().getmSquareOUTStatus().equals("Closed")){
    image = new Image(getClass().getResourceAsStream("cell_closed.png"));
    array[i][j].setImage(image);
    array[i][j].setFitHeight(30);
    array[i][j].setFitWidth(30);
    }
    if(mgui.getmGui().getmGrid().getSqArrayLists().get(i).get(j).getOUTSquareStatus().getmSquareOUTStatus().equals("Opened")){
        if(mgui.getmGui().getmGrid().getSqArrayLists().get(i).get(j).getINSquareStatus().getmSquareInStatus().equals("Blank")){
            String temp = "cell_opend";
            temp += mgui.getmGui().getCurrentPlayer().getPlayercolor() +".png";
            image = new Image(getClass().getResourceAsStream(temp));
            array[i][j].setImage(image);
            array[i][j].setFitHeight(30);
            array[i][j].setFitWidth(30);
        }
        else if(mgui.getmGui().getmGrid().getSqArrayLists().get(i).get(j).getINSquareStatus().getmSquareInStatus().equals("Number")){
            if(mgui.getmGui().getmGrid().getSqArrayLists().get(i).get(j).getNumber() == 1){
                String temp = "one";
                temp += mgui.getmGui().getCurrentPlayer().getPlayercolor() + ".png";
                    image = new Image(getClass().getResourceAsStream(temp));
            }
            if(mgui.getmGui().getmGrid().getSqArrayLists().get(i).get(j).getNumber() == 2){
                String temp = "two";
                temp += mgui.getmGui().getCurrentPlayer().getPlayercolor() + ".png";
                image = new Image(getClass().getResourceAsStream(temp));
            }
            if(mgui.getmGui().getmGrid().getSqArrayLists().get(i).get(j).getNumber() == 3){
                String temp = "three";
                temp += mgui.getmGui().getCurrentPlayer().getPlayercolor() + ".png";
                image = new Image(getClass().getResourceAsStream(temp));
            }
            if(mgui.getmGui().getmGrid().getSqArrayLists().get(i).get(j).getNumber() == 4){
                String temp = "four";
                temp += mgui.getmGui().getCurrentPlayer().getPlayercolor() + ".png";
                image = new Image(getClass().getResourceAsStream(temp));
            }if(mgui.getmGui().getmGrid().getSqArrayLists().get(i).get(j).getNumber() == 5){
                String temp = "five";
                temp += mgui.getmGui().getCurrentPlayer().getPlayercolor() + ".png";
                image = new Image(getClass().getResourceAsStream(temp));
            }if(mgui.getmGui().getmGrid().getSqArrayLists().get(i).get(j).getNumber() == 6){
                String temp = "six";
                temp += mgui.getmGui().getCurrentPlayer().getPlayercolor() + ".png";
                image = new Image(getClass().getResourceAsStream(temp));
            }if(mgui.getmGui().getmGrid().getSqArrayLists().get(i).get(j).getNumber() == 7){
                String temp = "seven";
                temp += mgui.getmGui().getCurrentPlayer().getPlayercolor() + ".png";
                image = new Image(getClass().getResourceAsStream(temp));
            }if(mgui.getmGui().getmGrid().getSqArrayLists().get(i).get(j).getNumber() == 8){
                String temp = "eight";
                temp += mgui.getmGui().getCurrentPlayer().getPlayercolor() + ".png";
                image = new Image(getClass().getResourceAsStream(temp));
            }
            array[i][j].setImage(image);
            array[i][j].setFitHeight(28);
            array[i][j].setFitWidth(28);

        }
        else if(mgui.getmGui().getmGrid().getSqArrayLists().get(i).get(j).getINSquareStatus().getmSquareInStatus().equals("Shield")){
            image = new Image(getClass().getResourceAsStream("shield.png"));
            array[i][j].setImage(image);
            array[i][j].setFitHeight(30);
            array[i][j].setFitWidth(30);
        }
        else{
            image = new Image(getClass().getResourceAsStream("cell_bomb.png"));
            array[i][j].setImage(image);
            array[i][j].setFitHeight(30);
            array[i][j].setFitWidth(30);
        }
    }
}


    public void OptionsMenu(Stage primaryStage){
        VBox root = new VBox(10);
        RadioButton Easy = new RadioButton("  Easy");
        RadioButton Medium = new RadioButton("Medium");
        RadioButton Hard = new RadioButton("  Hard");
        RadioButton Custom = new RadioButton("Custom");


        root.getStylesheets().add(getClass().getResource("/sample/radio.css").toExternalForm());



        ToggleGroup toggleGroup = new ToggleGroup();
        Easy.setToggleGroup(toggleGroup);
        Medium.setToggleGroup(toggleGroup);
        Hard.setToggleGroup(toggleGroup);
        Custom.setToggleGroup(toggleGroup);



        toggleGroup.selectedToggleProperty().addListener((observable, oldVal, newVal) ->{
            if(toggleGroup.getSelectedToggle() != null)
            if(Easy.isSelected()) {
                setDifficulty("Easy");
                StartGame(primaryStage);
            }
            else if(Medium.isSelected()){
                setDifficulty("Medium");
                StartGame(primaryStage);

            }

            else if(Hard.isSelected()){
                setDifficulty("Hard");
                StartGame(primaryStage);

            }

            else
                CustomGame(primaryStage);
        });

Button b = new Button("Players");
b.setOnAction((ActionEvent e)->{
    PlayerOptions(primaryStage);
});



        Button b2 = new Button("Advanced");
        b2.setOnAction((ActionEvent e) -> {
            AdvacedOptions(primaryStage);
        });
        root.setAlignment(Pos.CENTER);
        Button Back = new Button("Back ");
        Back.setOnAction((ActionEvent e)->{
            StartMenu(primaryStage);
        });
        root.getChildren().addAll(Easy,Medium,Hard,Custom,b,b2,Back);
        primaryStage.setScene(new Scene(root, 400, 250));
        primaryStage.show();
    }

public void PlayerOptions(Stage primaryStage){
    TextField textField = new TextField();
    ListView listView = new ListView();
    ComboBox comboBox = new ComboBox();
    comboBox.getItems().addAll("Red", "Blue", "Green", "Pink", "Orange");

    String red = "Red";
    String blue = "Blue";
    String green = "Green";
    String pink = "Pink";
    String orange = "Orange";
    ArrayList<String> Colors = new ArrayList<>();
    Colors.add(red);
    Colors.add(blue);
    Colors.add(green);
    Colors.add(pink);
    Colors.add(orange);
    ArrayList<String> tempColors = new ArrayList<>();

    Button addButton = new Button("Add Item");
    Button addAI = new Button("Computer PLayer");
    Button removeButton = new Button("Remove Selected Item");
    Button removeAllButton = new Button("Remove All Items");
    Label counterLabel = new Label("Total items = 0");

   HBox root = new HBox();
   VBox vbox = new VBox(10);

    Button Back = new Button("Back");
   vbox.getChildren().addAll(textField , comboBox,addButton ,addAI,removeButton ,removeAllButton, Back);


    final int[] computercount = {0};
   root.getChildren().addAll(vbox, listView);

    addButton.setOnAction((ActionEvent e) -> {
        if( !textField.getText().equals("") ) {
            if(comboBox.getSelectionModel().isEmpty()) {
                System.out.println("No Color selected");
            }
            else {
                String s= textField.getText()+ " | "+comboBox.getSelectionModel().getSelectedItem().toString();
                listView.getItems().add(s);
                listView.getSelectionModel().selectLast();
                counterLabel.setText("Total Items: " + listView.getItems().size());
                String tempColor =  comboBox.getSelectionModel().getSelectedItem().toString();
                comboBox.getItems().clear();
                tempColors.clear();
                Player p = new Player();
                p.setName(textField.getText());
                p.setPlayercolor(tempColor);
                mgui.getmGui().AddPlayer(p);
                mgui.getmGui().setPlayerCount(listView.getItems().size());
                textField.setText("");

                for(int i = 0  ; i < Colors.size() ;i++){
                    if(!Colors.get(i).equals(tempColor)){
                        comboBox.getItems().add(Colors.get(i));
                        comboBox.getSelectionModel().select(i);
                        tempColors.add(Colors.get(i));
                    }

                }
                Colors.clear();
                for(int i = 0  ; i < tempColors.size() ;i++)
                    Colors.add(tempColors.get(i));
            }
        }
    });

    removeButton.setOnAction((ActionEvent e) -> {
        if( !listView.getSelectionModel().isEmpty() ) {
            listView.getItems().remove(listView.getSelectionModel().getSelectedIndex());
            counterLabel.setText("Total Items: " + listView.getItems().size());
        }
    });

    removeAllButton.setOnAction((ActionEvent e) -> {
        listView.getItems().clear();
        counterLabel.setText("Total Items: " + listView.getItems().size());
    });

    addAI.setOnAction((ActionEvent e) -> {
        tempColors.clear();
        if(Colors.isEmpty())
            System.out.println("we cant add any more Players");
        else{
            ComputerPlayer p = new ComputerPlayer();
            p.setName("Computer "+ computercount[0]);
            p.setPlayercolor(Colors.get(0));
            mgui.getmGui().AddPlayer(p);
            listView.getItems().add(p.getName());
            comboBox.getItems().clear();
            mgui.getmGui().setPlayerCount(listView.getItems().size());
            computercount[0]++;
            for(int i = 0  ; i < Colors.size() ;i++){
                if(!Colors.get(i).equals(Colors.get(0))){
                    comboBox.getItems().add(Colors.get(i));
                    comboBox.getSelectionModel().select(i);
                    tempColors.add(Colors.get(i));
                }

            }
            Colors.clear();
            for(int i = 0  ; i < tempColors.size() ;i++)
                Colors.add(tempColors.get(i));
        }

    });


    Back.setOnAction((ActionEvent e) -> {
        OptionsMenu(primaryStage);
    });
    primaryStage.setScene(new Scene(root, 400, 250));
    primaryStage.show();

}

    public void setDifficulty(String difficultstatus){
        if(difficultstatus.equals("Easy")){
            mgui.getmGui().getDf().setHeight(9);
            mgui.getmGui().getDf().setWidth(9);
            mgui.getmGui().getDf().setMinesCount(10);
            System.out.println("Easy");
        }
        else if(difficultstatus.equals("Medium")){
            mgui.getmGui().getDf().setHeight(16);
            mgui.getmGui().getDf().setWidth(16);
            mgui.getmGui().getDf().setMinesCount(40);
            System.out.println("Medium");
        }
        else if(difficultstatus.equals("Hard")){
            mgui.getmGui().getDf().setHeight(16);
            mgui.getmGui().getDf().setWidth(30);
            mgui.getmGui().getDf().setMinesCount(99);
            System.out.println("Hard");
        }
    }

    public void CustomGame(Stage primaryStage){
        Slider Minesslider = new Slider();
        Slider Heightslider = new Slider();
        Slider Widthslider = new Slider();
        Slider SheildSlider = new Slider();

        VBox root = new VBox(10);
        HBox hbox1 = new HBox(5);
        HBox hbox2 = new HBox(5);
        HBox hbox3 = new HBox(5);
        HBox hbox4 = new HBox(5);
//Width
        Label width = new Label("Width");
        Label currentlabel = new Label("4");
        Widthslider.setMin(4);
        Widthslider.setMax(30);
        Widthslider.setShowTickMarks(true);
        Widthslider.valueProperty().addListener((ChangeListener<Number>) (arg0, arg1, arg2) ->{
            currentlabel.textProperty().setValue(
                String.valueOf((int) Widthslider.getValue()));
            int x =(int) Heightslider.getValue()*(int) Widthslider.getValue();
            Minesslider.setMax(x-10);
    });

        hbox1.getChildren().addAll(width,Widthslider,currentlabel);
        hbox1.setAlignment(Pos.CENTER);

       //Height
        Label height = new Label("Height");
        Label currentlabelh = new Label("5");
        Heightslider.setMin(5);
        Heightslider.setMax(16);
        Heightslider.setShowTickMarks(true);
        Heightslider.valueProperty().addListener((ChangeListener<Number>) (arg0, arg1, arg2) ->{

                currentlabelh.textProperty().setValue(
                String.valueOf((int) Heightslider.getValue()));
            int x =(int) Heightslider.getValue()*(int) Widthslider.getValue();
            Minesslider.setMax(x-10);
        });

        hbox2.getChildren().addAll(height,Heightslider,currentlabelh);
        hbox2.setAlignment(Pos.CENTER);


        //Mines
        Label Mines = new Label("Mines");
        Label currentlabelm = new Label("1");
        Minesslider.setMin(1);
        int x =(int) Heightslider.getValue()*(int) Widthslider.getValue();
        Minesslider.setMax(x-10);
        Minesslider.setShowTickMarks(true);
        Minesslider.valueProperty().addListener((ChangeListener<Number>) (arg0, arg1, arg2) -> currentlabelm.textProperty().setValue(
                String.valueOf((int) Minesslider.getValue()-5)));

        hbox3.getChildren().addAll(Mines,Minesslider,currentlabelm);
        hbox3.setAlignment(Pos.CENTER);

//Shiled
        Label Shiled = new Label("Shiled");
        Label currentShiled = new Label("0");
        SheildSlider.setMin(0);
        int y =(int) Heightslider.getValue()*(int) Widthslider.getValue();
        int z = (int)Minesslider.getValue();
        SheildSlider.setMax(5);
        SheildSlider.setShowTickMarks(true);
        SheildSlider.valueProperty().addListener((ChangeListener<Number>) (arg0, arg1, arg2) -> currentShiled.textProperty().setValue(
                String.valueOf((int) SheildSlider.getValue())));

        hbox4.getChildren().addAll(Shiled,SheildSlider,currentShiled);
        hbox4.setAlignment(Pos.CENTER);




        // Buttons
        HBox hbuttons = new HBox(10);
        //Buttons
        Button Apply = new Button("Apply");
        Apply.setOnAction((ActionEvent e)-> {
            mgui.getmGui().getDf().setWidth((int) Widthslider.getValue());
            mgui.getmGui().getDf().setHeight((int) Heightslider.getValue());
            mgui.getmGui().getDf().setMinesCount((int) Minesslider.getValue());
            mgui.getmGui().getDf().setShieldsInGrid((int) SheildSlider.getValue());
            //System.out.println(mgui.getmGui().getDf().getHeight() + "  " + mgui.getmGui().getDf().getWidth() +"  " + mgui.getmGui().getDf().getMinesCount());
        });
        Button Back = new Button("Back");
        Back.setOnAction((ActionEvent e)->{
           Apply.fire();
           OptionsMenu(primaryStage);
        });

        Button StartGameButton = new Button("Start");
        StartGameButton.setOnAction((ActionEvent e)-> {
            Apply.fire();
            StartGame(primaryStage);
        });
        hbuttons.getChildren().addAll(Back,Apply,StartGameButton);
        hbuttons.setAlignment(Pos.CENTER);
        root.getChildren().addAll(hbox1,hbox2,hbox3,hbox4,hbuttons);

        root.setAlignment(Pos.CENTER);
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

public boolean FilteredNextPlyer(){
    for(int f = 0 ; f < mgui.getmGui().getPlayerList().size() ; f++){
        mgui.getmGui().setCurrentPlayer(mgui.getmGui().getDf().DecideNextPlayer());
        if(mgui.getmGui().getCurrentPlayer().isLoseStatus() == false)
            return false;
        if(f == mgui.getmGui().getPlayerList().size()-1)
            return true;
    }
    return true;
}



public void GameResult(){
    VBox root = new VBox(20);
    HBox h = new HBox(20);
    Player p  =new Player();
    int maxscore = -2000;
    for(int i =  0 ; i < mgui.getmGui().getPlayerList().size() ; i++){
       if(maxscore < mgui.getmGui().getPlayerList().get(i).getCurrentScore().getScore()){
           maxscore = mgui.getmGui().getPlayerList().get(i).getCurrentScore().getScore();
           p = mgui.getmGui().getPlayerList().get(i);
       }

    }
    Text c = new Text("Winner Winner Chicken Dinner");
    c.setFill(Color.RED);
    Text Winner = new Text(p.getName() + "  "  + p.getCurrentScore().getScore());
    root.getChildren().addAll(c,Winner);
    Stage s = new Stage();
    s.setScene(new Scene(root, 500, 500));
    s.show();
}




public void AdvacedOptions(Stage primaryStage){
        VBox root = new VBox(5);


        Label l2 = new Label("Game Type");
    RadioButton Score = new RadioButton("Score");
    RadioButton Mines = new RadioButton("Mines");
    ToggleGroup toggleGroup = new ToggleGroup();
    Score.setToggleGroup(toggleGroup);
    Mines.setToggleGroup(toggleGroup);
    toggleGroup.selectedToggleProperty().addListener((observable, oldVal, newVal) ->{
        if(toggleGroup.getSelectedToggle() != null)
            if(Score.isSelected()) {
               mgui.getmGui().getDf().setGameType("ScoreGame");
            }
            else if(Mines.isSelected()){
                mgui.getmGui().getDf().setGameType("MineGame");
            }
    });
    Button Back = new Button("Back");
    Back.setOnAction((ActionEvent e) -> {
        OptionsMenu(primaryStage);
    });

    Button Directory = new Button("Directory");
    Directory.setOnAction((ActionEvent e) -> {
        Dirc(primaryStage);
    });

    root.getChildren().addAll(l2,Score,Mines,Directory,Back);
    primaryStage.setScene(new Scene(root, 400, 250));
    primaryStage.show();
}

public void AIPlayer(){
    ((ComputerPlayer)mgui.getmGui().getCurrentPlayer()).Randomselect(mgui.getmGui().getDf().getWidth(), mgui.getmGui().getDf().getHeight(),mgui.getmGui().getmGrid());

PlayerMove playerMove = new PlayerMove();
playerMove  = mgui.getmGui().setPlayerMoveDirctly(((ComputerPlayer) mgui.getmGui().getCurrentPlayer()).getX(),((ComputerPlayer) mgui.getmGui().getCurrentPlayer()).getY(),"Reveal",mgui.getmGui().getCurrentPlayer());
if(mgui.getmGui().AcceptMove(playerMove)){
    mgui.getmGui().ApplytMove(playerMove);
}
    }

    public void Dirc(Stage primaryStage){
        VBox root = new VBox(5);
        HBox hBox = new HBox(0);
        TextField path;
        if(mgui.getSelectedDirectory()!= null)
             path = new TextField(mgui.getSelectedDirectory());
        else
            path = new TextField(mgui.getSelectedDirectory());
path.setDisable(true);
        Button button = new Button(":");
        button.setOnAction((ActionEvent e)->{
            path.setText( mgui.DirectoryChooser());
        });
        Button Back = new Button("Back ");
        Back.setOnAction((ActionEvent e)->{
            AdvacedOptions(primaryStage);
        });
        hBox.getChildren().addAll(path,button,Back);
        root.getChildren().add(hBox);
        primaryStage.setScene(new Scene(root, 400, 250));
        primaryStage.show();
    }

    public void Load(Stage primaryStage){
        VBox root = new VBox(5);
        Button Specific = new Button("Load from Directory");
        ArrayList<String> result =   new ArrayList<String>();
        try {

            result = (ArrayList<String>) Files.find(Paths.get(mgui.getSelectedDirectory()), 100,
                    (p, a) -> p.toString().toLowerCase().endsWith(".ser"))
                    .map(path -> path.toString())
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("sd");
        ListView<String> listView = new ListView<>();

        // يمثل مجموعة العناصر التي ستظهر في القائمة ObservableList هنا قمنا بإنشاء كائن من الكلاس
        ObservableList<String> items = FXCollections.observableArrayList();

        for(int i = 0 ; i< result.size();i++){
            items.add(result.get(i));
        }
        // listView كعناصر للكائن items هنا قمنا بوضع عناصر الكائن
        listView.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends String> ov, String old_val, String new_val) -> {
                    // label هنا قلناأنه سيتم وضع إسم العنصر الذي تم إختياره كنص للكائن
                    mgui.Load(new_val);
                    if(FilteredNextPlyer() || mgui.getmGui().EndGame()){
                        ShowEndedGame(primaryStage);
                    }
                    else{
                        File file = new File(new_val);
                        file.delete();
                        StartGame(primaryStage);
                    }
                });
        listView.setItems(items);
        Button button = new Button("Back");
        button.setOnAction((ActionEvent e)->{
            StartMenu(primaryStage);
        });
        root.getChildren().addAll(listView,button);

        primaryStage.setScene(new Scene(root, 400, 250));
        primaryStage.show();
    }

    @Override
    public void stop() throws IOException {
        mgui.SaveApp();

    }

    public void ShowEndedGame(Stage primaryStage){

        VBox vBox1 = new VBox(2);
        HBox root = new HBox();
        VBox vbox = new VBox(2);
        GridPane gridPane = new GridPane();
        for(int i = 0 ; i < mgui.getmGui().getmGrid().getWidth() ; i++){
            for(int j = 0 ; j < mgui.getmGui().getmGrid().getHeight() ; j++) {
                mgui.getmGui().getmGrid().getSqArrayLists().get(i).get(j).getOUTSquareStatus().setmSquareOUTStatus("Closed");
                array[i][j] = new ImageView(new Image(getClass().getResourceAsStream("cell_closed.png")));
                //array[i][j].setImage(new Image(getClass().getResourceAsStream("cell_closed.png")));
                array[i][j].setFitHeight(30);
                array[i][j].setFitWidth(30);
                gridPane.add(array[i][j],i,j);

            }
        }
//        for(int t= 0 ;  t < mgui.getmGui().getMoves().size() ;t++){
//            UpdateGrid(mgui.getmGui().getMoves().get(t).getmSquare().getX(),mgui.getmGui().getMoves().get(t).getmSquare().getY());
//        }
        final int[] CounterMove = {0};

        Button Pre = new Button("Exit");
        Button Next = new Button("Next");


        int size = mgui.getmGui().getMoves().size();

       Next.setOnAction((ActionEvent e)-> {
                mgui.getmGui().ApplytMove(mgui.getmGui().getMoves().get(CounterMove[0]));
                CounterMove[0]++;
           mgui.getmGui().setCurrentPlayer(mgui.getmGui().getMoves().get(CounterMove[0]).getmPlayer());
                System.out.println((mgui.getmGui().getMoves().get(CounterMove[0]).getmSquare().getX()+"   "+mgui.getmGui().getMoves().get(CounterMove[0]).getmSquare().getY()));
         /*      for(int i = 0 ; i < mgui.getmGui().getmGrid().getWidth() ; i++){
            for(int j = 0 ; j < mgui.getmGui().getmGrid().getHeight() ; j++) {

                    mgui.getmGui().setCurrentPlayer(mgui.getmGui().getMoves().get(CounterMove[0]).getmPlayer());
                    updateSquar(i,j);


            }}*/
           mgui.getmGui().setCurrentPlayer(mgui.getmGui().getMoves().get(CounterMove[0]).getmPlayer());
           UpdateGrid(mgui.getmGui().getMoves().get(CounterMove[0]).getmSquare().getX()
                   ,mgui.getmGui().getMoves().get(CounterMove[0]).getmSquare().getY());

        });
        Pre.setOnAction((ActionEvent e)-> {
          StartMenu(primaryStage);

        });
        root.getChildren().addAll(gridPane,vbox,Next,Pre);
        vBox1.getChildren().addAll(root);
        primaryStage.setScene(new Scene(vBox1, mgui.getmGui().getmGrid().getWidth()*30+160, mgui.getmGui().getmGrid().getHeight()*30+40));
        primaryStage.show();

    }

}
