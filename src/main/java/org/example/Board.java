package org.example;

import java.util.*;

public class Board {
    private final int gridLen;
    private final int amountBomb;
    private int firY;
    private int firX;
    //private int firstX;
    //private int firstY;
    private final int [][] gridHidden;
    private final boolean [][] mineHidden;
    private final String [][] userVisible;
    private final boolean[][] flagGrid;
    private int flagAmount;
    //Constructor class.
    public Board(int gridLen, int amountBomb){
        this.gridLen = gridLen;
        this.amountBomb = amountBomb;
        this.gridHidden = new int [gridLen][gridLen];
        this.mineHidden = new boolean[gridLen][gridLen];
        this.userVisible = new String[gridLen][gridLen];
        this.flagGrid = new boolean[gridLen][gridLen];
    }
    public boolean[][] returnBombGrid(){
        return mineHidden;
    }
//    public String [][] returnVisibleGrid(){
//        return userVisible;
//    }
    public int boardSetUp() {
        //Initialising each grid we will use for Minesweeper.
        for (int i = 0; i < gridLen; i++) {
            for (int j = 0; j<gridLen; j++){
                gridHidden[i][j] = 0;
                mineHidden[i][j] = false;
                userVisible[i][j] = "X";
                flagGrid[i][j] = false;
            }
        }
        return gridLen;
    }
    public void placeFlag(int yCoord, int xCoord) {
        if (flagAmount == amountBomb) {
            System.out.println("You have placed the max amount of flags." +
                    "\nTo place a new flag you must remove one!");
        }else if(!(userVisible[yCoord - 1][xCoord - 1]=="X") ){System.out.println("You cannot place a flag on a revealed tile!");}
        else {
            if (!flagGrid[yCoord - 1][xCoord - 1]) {
                flagGrid[yCoord - 1][xCoord - 1] = true;
                System.out.println("You have placed a flag at (" + xCoord + "," + (yCoord) + ").");
                flagAmount++;
            } else {
                System.out.println("You have removed a flag at (" + xCoord + "," + (yCoord) + ").");
                flagGrid[yCoord - 1][xCoord - 1] = false;
                flagAmount -= 1;
            }
        }
    }
    public void bombSetup(int firstY,int firstX){
        //Here we will set the bombs up in the grid after the user has input their first move.
        //This makes sure that the first move cannot lose the game instantly.
        Random random = new Random();
        int yRand;
        int xRand;
        userVisible[firstY-1][firstX-1] = "0";
        firY=firstY-1;
        firX=firstX-1;
        //An element in the 2d array mineHidden holds a true value if there is a bomb on that coordinate.
        for (int i =0;i<amountBomb;i++){
            do {
                yRand = random.nextInt(gridLen)+1;
                xRand = random.nextInt(gridLen)+1;
            }
            while ((yRand==firstY && xRand==firstX) || mineHidden[yRand-1][xRand-1]);
            mineHidden[yRand-1][xRand-1] = true;
            gridHidden[yRand-1][xRand-1] = 9;
        }
//        for (int i = 0; i < gridLen;i++) {
//            System.out.print("|"+ (i + 1) + "|");
//            for (int j=0;j<gridLen;j++){
//                if (mineHidden[i][j]){
//                    System.out.print("X|");
//                }else{
//                    System.out.print("0|");
//                }
//            }
//            System.out.println();
//        }
//        System.out.println();
        //Here we are determining adjacent bombs for each coordinate.
        for (int i=0;i<gridLen;i++){
            for (int j=0;j<gridLen;j++){
                int count = 0;
                    if (!mineHidden[i][j]){
                        for (int k=0;k<3;k++) {
                            for(int l=0;l<3;l++){
                                try{
                                if (mineHidden[i + (k - 1)][j + (l - 1)]) {
                                    count+=1;
                                }
                                }
                                catch (ArrayIndexOutOfBoundsException e){}
                                gridHidden[i][j] = count;
                            }
                    }
                    }

            }
        }
            //System.out.println();
    }
    public boolean[][] returnMineHidden(){
        return mineHidden;
    }
    public void boardPrint(){
        //System.out.print(" |");
        //Printing the visible board for the user.
        String reset = "\u001B[0m";
        String boldGreen = "\033[1;32m";
        String red = "\033[0;31m";
        System.out.print(boldGreen+"  |1|"+reset);
        for (int i = 0; i < gridLen-1;i++) {
            System.out.print(boldGreen+(i + 2) + "|"+reset);}
        System.out.println();
        for (int i = 0; i < gridLen;i++) {
            System.out.print(boldGreen+"|"+ (i + 1) + "|"+reset);
            for (int j=0;j<gridLen;j++){
                //if ( i==0 && j==0){
                //    System.out.print("0|");
                //}
                //else
                if(flagGrid[i][j]){
                    System.out.print(red+"F"+reset+"|");
                }
                else if (userVisible[i][j] == "X") {
                        System.out.print("X|");
                }else{
                        System.out.print(gridHidden[i][j]+"|");
                    }

            }
            //System.out.print("|");
            System.out.println();
        }
    }
    public int boardPlay(int yCoord, int xCoord, int moveChoice) {
//        for (int i = 0; i < gridLen;i++) {
//            System.out.print("|"+ (i + 1) + "|");
//            for (int j=0;j<gridLen;j++){
//                System.out.print(userVisible[i][j]+"|");
//            }
//            System.out.println();
//        }
        //Checking the validity of a move as well as if they have hit a bomb on this turn and lost.
        if (moveChoice == 1) {
            if(flagGrid[yCoord-1][xCoord-1]){
                System.out.println("You can't reveal a tile where a flag is! ");
                return 2;
            }else if(!(userVisible[yCoord-1][xCoord-1]=="X")){
                System.out.println("You can't reveal a revealed tile!");
                return 2;
            }
            else if (mineHidden[yCoord - 1][xCoord - 1]) {
                System.out.println("YOU HAVE LOST MUAHAHAHAHHAHAHAHAH UNLUCKY");
                return 1;
            } else {
                String str = String.valueOf(gridHidden[yCoord - 1][xCoord - 1]);
                userVisible[yCoord - 1][xCoord - 1] = str;
                revealAdjacent((yCoord - 1), (xCoord - 1));
                return 2;
            }
        } else {
            placeFlag(yCoord, xCoord);
            return 2;
        }
    }
    public int winGame(){
        //Checking if the user has won the game.
        int xChar = 0;
        for (int i = 0; i < gridLen; i++) {
            for (int j = 0; j < gridLen; j++) {
                if (userVisible[i][j] == "X") {
                    xChar++;
                }
            }
        }
        if (xChar == amountBomb) {
            System.out.println("YOU HAVE WON! WELL DONE WOOP");
            return 1;
        }
        return 2;
    }
    public void revealAdjacent(int yCoord,int xCoord){
        Stack<int[]> stack = new Stack<>();
        stack.push(new int[] {yCoord,xCoord});
        //While the stack is not empty.
        while(!stack.isEmpty()){
            //Pop the coordinates on top of the stack.
            int[] coords = stack.pop();
            int yVal = coords[0];
            int xVal = coords[1];
            //Check the coordinates are not the first input from the user, and that they are within the allowed grid.
            if(yVal==firY&&xVal==firX) {
                //Skip here also if the position has already been revealed.
            }else if (yVal<0||yVal>=gridLen||xVal<0||xVal>=gridLen||userVisible[yVal][xVal] == "0"){
                continue;}
            //If the revealed tile is a 0 tile, we will show that on the user grid.
            try {
            if (gridHidden[yVal][xVal] == 0) {
                userVisible[yVal][xVal]= "0";
                for (int i=0;i<3;i++) {
                    for (int j = 0; j < 3; j++) {
                        //If the chosen coordinate is safe without a number of adjacent bombs, we push the surrounding
                        //3x3 tiles around it to the stack.
                            if ((i - 1) == 0 && (j - 1) == 0) {
                            } else {
                                stack.push(new int[]{yVal + (i - 1), xVal + (j - 1)});
                            }
                    }
                }
                } else {
                //Here we also reveal values of adjacent bombs to a tile.
                String str = String.valueOf(gridHidden[yVal][xVal]);
                userVisible[yVal][xVal]=str;
            }
            } catch (ArrayIndexOutOfBoundsException e){}
        }
    }
}
