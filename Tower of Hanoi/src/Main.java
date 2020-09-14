/*
Ismat Syah Imran
Mr. Tully
Period 4
 */

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class Main extends Application {

    //number of discs
    private int discs;

    private GraphicsContext gc;
    private int selected = -1;

    //arraylist that stores 3 stacks
    private ArrayList<MyStack<Integer>> stacks;
    private long moves = 0;
    private boolean gameOver = false;

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        stacks = new ArrayList<MyStack<Integer>>();
        stacks.add(0,new MyStack<Integer>());
        stacks.add(1,new MyStack<Integer>());
        stacks.add(2,new MyStack<Integer>());
        Scanner sc = new Scanner(System.in);

        //takes input until valid number of discs is entered
        do {
            System.out.println("How many discs would you like to play with (1-7)?");
            discs = sc.nextInt();
        } while(discs < 1 || discs > 7);

        reset();
        Group group = new Group();
        Canvas canvas = new Canvas(500,300);
        group.getChildren().add(canvas);
        Scene scene = new Scene(group);
        gc = canvas.getGraphicsContext2D();
        canvas.setOnKeyTyped(event -> {
            System.out.println(event.getCharacter());
            if(!gameOver)
                controls(event);
            draw(gc);
        });
        draw(gc);
        canvas.requestFocus();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void controls(KeyEvent event) {
        if(selected == -1) {
            switch (event.getCharacter()) {
                case "1":
                    selected = 0;
                    break;
                case "2":
                    selected = 1;
                    break;
                case "3":
                    selected = 2;
                    break;
            }
        }
        else {
            switch (event.getCharacter()) {
                case "1":
                    if (!stacks.get(selected).empty() && (stacks.get(0).empty() || stacks.get(0).peek() > stacks.get(selected).peek())) {
                        stacks.get(0).push(stacks.get(selected).pop());
                        moves++;
                    }
                    break;
                case "2":
                    if (!stacks.get(selected).empty() && (stacks.get(1).empty() || stacks.get(1).peek() > stacks.get(selected).peek())) {
                        stacks.get(1).push(stacks.get(selected).pop());
                        moves++;
                    }
                    break;
                case "3":
                    if (!stacks.get(selected).empty() && (stacks.get(2).empty() || stacks.get(2).peek() > stacks.get(selected).peek())) {
                        stacks.get(2).push(stacks.get(selected).pop());
                        moves++;
                    }
                    break;
            }
            selected = -1;
        }
        if(stacks.get(2).size() == discs)
            gameOver = true;
    }

    public void reset() {
        for(int i = discs; i > 0; i--) {
            stacks.get(0).push(i);
        }
    }

    public void draw(GraphicsContext gc) {
        int height = 200/discs;
        int minWidth = 40;
        int maxWidth = 124;

        //difference in side width between discs
        int wVar;

        //distance between each stack
        int distanceBetweenCols = 154;

        //sets the difference in side width according to how many discs there are
        if(discs == 1)
            wVar = 0;
        else
            wVar = (maxWidth-minWidth)/(2*(discs-1));


        //draws background
        gc.setFill(Color.WHITE);
        gc.fillRect(0,0,500,300);
        //draws 3 poles
        gc.setFill(Color.GRAY);
        gc.fillRoundRect(89,50,14,230,15,15);
        gc.fillRoundRect(89+distanceBetweenCols,50,14,230,15,15);
        gc.fillRoundRect(89+(2*distanceBetweenCols),50,14,230,15,15);

        //colors the selected pole black
        if(selected != -1) {
            gc.setFill(Color.BLACK);
            gc.fillRoundRect(89 +(selected*distanceBetweenCols),50,14,230,15,15);
        }

        //draws bottom layer
        gc.setFill(Color.GRAY);
        gc.fillRoundRect(19,270,462,20,20,20);

        //goes through array list of stacks
        for(int i = 0; i < stacks.size(); i++) {
            //goes through discs in a stack
            for(int j = 0; j < stacks.get(i).size(); j++) {

                //if and else ifs to decide color of disc
                if(stacks.get(i).get(j) == 1)
                    gc.setFill(Color.RED);
                else if(stacks.get(i).get(j) == 2)
                    gc.setFill(Color.ORANGE);
                else if(stacks.get(i).get(j) == 3)
                    gc.setFill(Color.YELLOW);
                else if(stacks.get(i).get(j) == 4)
                    gc.setFill(Color.GREEN);
                else if(stacks.get(i).get(j) == 5)
                    gc.setFill(Color.CYAN);
                else if(stacks.get(i).get(j) == 6)
                    gc.setFill(Color.BLUE);
                else if(stacks.get(i).get(j) == 7)
                    gc.setFill(Color.PURPLE);

                //draws disc at correct position
                gc.fillRoundRect(96+(distanceBetweenCols*i)-(maxWidth/2)+((discs-stacks.get(i).get(j))*wVar),270-(((stacks.get(i).size()-j))*height),maxWidth-(wVar*(discs-stacks.get(i).get(j))*2),height,15,15);
            }
        }

        //displays game over text
        if(gameOver) {
            gc.setFill(Color.RED);
            gc.setFont(new Font("Courier New", 30));
            gc.fillText("You won in " + moves + " moves", 89, 30);
        }

        System.out.println("col1 " + stacks.get(0).toString());
        System.out.println("col2 " + stacks.get(1).toString());
        System.out.println("col3 " + stacks.get(2).toString());

        gc.setFill(Color.BLACK);
        gc.setFont(new Font("Courier New", 15));
        gc.fillText("Ismat Syah Imran", 10, 10);
    }
}
