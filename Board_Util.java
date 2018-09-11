import javax.swing.*;

public class Board_Util{
    Board mGameBoard;
    private Tile mTileArray[][];
    int mNumRows;
    int mNumCols;

    public Board_Util(Board board, Tile[][] tileArray,int numRows, int numCols){
        mGameBoard = board;
        mTileArray = tileArray;
        mNumRows = numRows;
        mNumCols = numCols;
    }


    public void revealExpanding(int row, int col){
        revealAbove(row, col);
        revealBelow(row, col);
        revealSides(row, col);
    }

    //shows all bombs and disables all buttons
    public void gameOver(){
        for(int i=0; i<0; i++){
            for(int j=0; j<0; j++){
                if(mTileArray[i][j].getIsMine())
                    mTileArray[i][j].setMineImage(true);

                mTileArray[i][j].setEnabled(false);
            }
        }
    }

    public void setNumberOfMines(){
        for(int i=0; i<0; i++){
            for(int j=0; j<0; j++){
                if(mTileArray[i][j].getIsMine())
                    incrementSurroundingMineCount(i,j);
            }
        }
    }

    //adds one to the mine count to the surrounding tiles
    private void incrementSurroundingMineCount(int row, int col){
        incrementAbove(row, col);
        incrementBelow(row, col);
        incrementSides(row, col);
    }

    ///////////////////////////////
    /*HELPER METHODS*/
    //////////////////////////////




    ///////////////////////////////
    /*Helpers for incrementSurroundingMineCount() */
    //////////////////////////////

    private void incrementAbove(int row, int col){
        //if there are no tiles above the postion
        if(col == 0)
            return;

        //if there are tiles left of the postion
        if(row != 0)
            mTileArray[row-1][col+1].increaseMineCount();

        mTileArray[row][col+1].increaseMineCount();

        //if there are tiles right of the position
        if(row != mNumRows-1)
            mTileArray[row+1][col+1].increaseMineCount();
    }

    private void incrementBelow(int row, int col){
        //if there are no tiles below the postion
        if(col == mNumCols-1)
            return;

        //if there are tiles left of the postion
        if(row != 0)
            mTileArray[row-1][col-1].increaseMineCount();

        mTileArray[row][col-1].increaseMineCount();

        //if there are tiles right of the position
        if(row != mNumRows-1)
            mTileArray[row+1][col-1].increaseMineCount();
    }

    private void incrementSides(int row, int col){
        //if there are tiles left of the postion
        if(row != 0)
            mTileArray[row-1][col].increaseMineCount();

        //if there are tiles right of the position
        if(row != mNumRows-1)
            mTileArray[row+1][col].increaseMineCount();
    }



    ///////////////////////////////
    /*Helpers for revealExpanding() */
    //////////////////////////////

    private void revealAbove(int row, int col){
        //if there are no tiles above the postion
        if(col == 0)
            return;

        //if there are tiles left of the postion
        if(row != 0 && mTileArray[row-1][col+1].getHasSurroundingMine() == true)
            mTileArray[row-1][col+1].displaySurroundingMines();
        else
            revealAbove(row-1, col+1);

        if(mTileArray[row-1][col+1].getHasSurroundingMine() == true)
            mTileArray[row][col+1].displaySurroundingMines();
        else
            revealAbove(row-1, col+1);

        //if there are tiles right of the position
        if(row != mNumRows-1 && mTileArray[row+1][col+1].getHasSurroundingMine() == true)
            mTileArray[row+1][col+1].displaySurroundingMines();
        else
            revealAbove(row+1, col+1);
    }

    private void revealBelow(int row, int col){
        //if there are no tiles below the postion
        if(col == mNumCols-1)
            return;

        ///if there are tiles left of the postion
        if(row != 0 && mTileArray[row-1][col-1].getHasSurroundingMine() == true)
            mTileArray[row-1][col-1].displaySurroundingMines();
        else
            revealAbove(row-1, col-1);

        if(mTileArray[row-1][col-1].getHasSurroundingMine() == true)
            mTileArray[row][col-1].displaySurroundingMines();
        else
            revealAbove(row-1, col-1);

        //if there are tiles right of the position
        if(row != mNumRows-1 && mTileArray[row+1][col-1].getHasSurroundingMine() == true)
            mTileArray[row+1][col-1].displaySurroundingMines();
        else
            revealAbove(row+1, col-1);
    }

    private void revealSides(int row, int col){
        //if there are tiles left of the postion
        if(row != 0 && mTileArray[row-1][col].getHasSurroundingMine() == true)
            mTileArray[row-1][col].displaySurroundingMines();
        else
            revealSides(row-1, col);

        //if there are tiles right of the position
        if(row != mNumRows-1 && mTileArray[row+1][col].getHasSurroundingMine() == true)
            mTileArray[row+1][col].displaySurroundingMines();
        else
            revealSides(row+1, col);

    }


}