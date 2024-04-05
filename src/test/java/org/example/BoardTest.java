package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    @Test
public void test2dVectorFromBombSetup(){
        //Checking if the vector created is the correct size with the correct amount of elements.
        int firstY=1;
        int firstX=4;
        int gridLen=6;
        int amountBomb =6;
        Board board = new Board(gridLen,amountBomb);
        board.boardSetUp();
        board.bombSetup(firstY,firstX);
    boolean[][] resultGrid = board.returnBombGrid();
    int expectedRows = gridLen;
    int expectedColumns = gridLen;
    int expectedElements = expectedRows*expectedColumns;
        assertEquals(expectedRows, resultGrid.length,"Incorrect number of rows");
        assertEquals(expectedColumns, resultGrid[0].length,"Incorrect number of columns");
    int actualElements = 0;
    for (boolean[] row : resultGrid) {
        actualElements += row.length;
    }
        assertEquals(expectedElements,actualElements,"Incorrect total number of elements");
    int trueCount = 0;
        for (boolean[] row : resultGrid) {
            for (boolean value : row) {
                if (value) {
                    trueCount++;
                }
            }
        }
        assertEquals(amountBomb,trueCount,"Incorrect number of true values");


}

}
