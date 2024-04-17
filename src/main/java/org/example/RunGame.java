package org.example;

public class RunGame {
    private int gridSize;
    private Board board;
    public void startGame(){
        //Here we recursively play the game until the user either wins or loses the game.
        board.boardPrint();
        if (board.winGame()==1){
            return;
        }
        String playOfFlag="Would you like to reveal a tile or place/remove a flag? " +
                "\n(1 for reveal a tile 2 for place/remove a flag)";
        String yOfInput=("Please enter your next Y coordinate: ");
        String xOfInput=("Please enter your next X coordinate: ");
        //Ask for a coordinate as well as asking if they would like to reveal a tile or place a flag.
        int playFlag = InputCorrector.yN(playOfFlag);
        int xInput = coordChecker(xOfInput);
        int yInput = coordChecker(yOfInput);
        int play=board.boardPlay(yInput,xInput,playFlag);
        if (play==2 ) {
            startGame();
        }
    }
    public void setUp(){
        //Text detailing to the user information about the game.
        System.out.println("""
                On the player grid the X will represent an unchecked tile.\s
                Numbers will represent the adjacent bombs to the tile.
                 F will represent a flag on a tile.""");
        System.out.println("""
                An easy game of Minesweeper would be on an 8x8 grid with 10 mines.
                A hard game of Minesweeper would be on an 16x16 grid with 40 mines.
                However for this version you can submit custom sized grids and mines.""");
        String gridOfSize = "What size grid would you like? (nxn with n being 20 or less)";
        boolean nGrid = true;
        int gridLen=0;
        //A while loop which ensures the user enters a valid grid size.
        while (nGrid){
            gridLen = InputCorrector.convDouble(gridOfSize);
            if (gridLen>20){
                System.out.println("You cannot set a grid bigger than 20x20 tiles!");
            }else if (gridLen<1){
                System.out.println("You cannot set a grid which is 1x1!");
            }
            else{nGrid=false;}
        }
        String bombS = "How many bombs would you like? ";
        int amountBomb=0;
        boolean nBomb = true;
        //amountBomb = InputCorrector.convDouble(bombS);
        //A while loop which checks the bomb size and makes sure you cannot exceed bomb amount inside the grid.
        while(nBomb){
            amountBomb = InputCorrector.convDouble(bombS);
            if (amountBomb>=gridLen*gridLen){
                System.out.println("You cannot set more bombs than the grid can handle! (nxn - 1 bombs allowed)");
            }else{nBomb=false;}
        }
        board = new Board(gridLen,amountBomb);
        gridSize = board.boardSetUp();
        board.boardPrint();
        String firstOfX = "Please enter the first X coordinate that you would like to play:";
        String firstOfY = "Please enter the first Y coordinate that you would like to play:";
        //Asking for the first move from the user.
        int firstX = coordChecker(firstOfX);
        int firstY = coordChecker(firstOfY);
        board.bombSetup(firstY,firstX);
        board.revealAdjacent((firstY-1),(firstX-1));
        int n =0;
    }
    public int coordChecker(String q){
        boolean n =true;
        int coord = 0;
        while(n){
            coord = InputCorrector.convDouble(q);
            if (coord<gridSize+1) {
                n=false;
            }
            else{
                System.out.println("Incorrect coordinates entered. Try again");
            }
        }
        return coord;
    }
}
