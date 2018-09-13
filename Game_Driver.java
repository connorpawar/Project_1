import javax.swing.*;
import java.util.Random;

public class Game_Driver{
    JFrame mGameBoard;
    private static Tile mTileArray[][];
    private Random random;
    static int mNumRows;
    static int mNumCols;
    static int mNumMines;
    JLabel mFlagCounter;

    public Game_Driver(JFrame board, Tile[][] tileArray,int numRows, int numCols, int mineCount, JLabel flagCount){
        mGameBoard = board;
        mTileArray = tileArray;
        mNumRows = numRows;
        mNumCols = numCols;
        mFlagCounter = flagCount;
        mNumMines = mineCount;
        initBoard();
    }


    public static void revealExpanding(int row, int col){
        revealAbove(row, col);
        revealBelow(row, col);
        revealSides(row, col);
    }

    //shows all bombs and disables all buttons
    public static void gameOver(){
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

        //if there are tiles right of the positiongetHasSurroundingMine() == true
        if(row != mNumRows-1)
            mTileArray[row+1][col].increaseMineCount();
    }



    ///////////////////////////////
    /*Helpers for revealExpanding() */
    //////////////////////////////

    private static void revealAbove(int row, int col){
        //if there are no tiles above the postion
        if(col == 0)
            return;

        //if there are tiles left of the postion
        if(row != 0 && mTileArray[row-1][col+1].getMineCount() > 0)
            mTileArray[row-1][col+1].displaySurroundingMines();
        else
            revealAbove(row-1, col+1);

        if(mTileArray[row-1][col+1].getMineCount() > 0)
            mTileArray[row][col+1].displaySurroundingMines();
        else
            revealAbove(row-1, col+1);

        //if there are tiles right of the position
        if(row != mNumRows-1 && mTileArray[row+1][col+1].getMineCount() > 0)
            mTileArray[row+1][col+1].displaySurroundingMines();
        else
            revealAbove(row+1, col+1);
    }

    private static void revealBelow(int row, int col){
        //if there are no tiles below the postion
        if(col == mNumCols-1)
            return;

        ///if there are tiles left of the postion
        if(row != 0 && mTileArray[row-1][col-1].getMineCount() > 0)
            mTileArray[row-1][col-1].displaySurroundingMines();
        else
            revealAbove(row-1, col-1);

        if(mTileArray[row-1][col-1].getMineCount() > 0)
            mTileArray[row][col-1].displaySurroundingMines();
        else
            revealAbove(row-1, col-1);

        //if there are tiles right of the position
        if(row != mNumRows-1 && mTileArray[row+1][col-1].getMineCount() > 0)
            mTileArray[row+1][col-1].displaySurroundingMines();
        else
            revealAbove(row+1, col-1);
    }

    private static void revealSides(int row, int col){
        //if there are tiles left of the postion
        if(row != 0 && mTileArray[row-1][col].getMineCount() > 0)
            mTileArray[row-1][col].displaySurroundingMines();
        else
            revealSides(row-1, col);

        //if there are tiles right of the position
        if(row != mNumRows-1 && mTileArray[row+1][col].getMineCount() > 0)
            mTileArray[row+1][col].displaySurroundingMines();
        else
            revealSides(row+1, col);

    }

    ///////////////////////////////
    /*Methods that initialize the board*/
    //////////////////////////////
        /*
         * Initializes the board for mTileArray by cleaning all of the tiles, then calls
         * placeMines which randomly sets in the desired amount of mines,
         * it then will set the risk of nearby mines
         */
        public void initBoard() {
            //We don't need to clean all the tiles
            /*for (int i = 0; i < mNumRows; i++) {
                for (int j = 0; j < mNumCols; j++) {
                    mTileArray[i][j].cleanTile();
                }
            }*/
            placeMines();
            setRiskNum();
        }

        /*
         * Places all of the mines to mNumMines
         * it calls setMine to be placed randomly
         */
        private void placeMines() {
            for (int i = 0; i < mNumMines; i++) {
                setMine();
            }
        }

        /*
         * setMine() is a helper function of placeMines()
         * randomly places a mine inside the board
         */
        private void setMine() {
            int x = random.nextInt(mNumRows);
            int y = random.nextInt(mNumCols);

            if (!mTileArray[x][y].getIsMine()) {
                mTileArray[x][y].setIsMine(true);
            } else {
                setMine();
            }
        }

        /*
         * setRiskNum() accesses the number of mines around a tile
         */
        private void setRiskNum() {
            for (int i = 0; i < mNumRows; i++) {
                int leftOne = i - 1;
                int rightOne = i + 1;

                for (int j = 0; j < mNumCols; j++) {
                    int downOne = j - 1;
                    int upOne = j + 1;

                    int mineRisk = 0;

                    /*
                    *Starts from bottom left of tile and checks in a clockwise pattern
                    */
                    if (leftOne >= 0 && downOne >= 0 && mTileArray[leftOne][downOne].getIsMine()) {
                        mineRisk++;
                    }
                    if (leftOne >= 0 && mTileArray[leftOne][j].getIsMine()) {
                        mineRisk++;
                    }
                    if (leftOne >= 0 && upOne < mNumCols && mTileArray[leftOne][upOne].getIsMine()) {
                        mineRisk++;
                    }
                    if (upOne < mNumCols && mTileArray[i][upOne].getIsMine()) {
                        mineRisk++;
                    }
                    if (rightOne < mNumRows && upOne < mNumCols && mTileArray[rightOne][upOne].getIsMine()) {
                        mineRisk++;
                    }
                    if (rightOne < mNumRows && mTileArray[rightOne][j].getIsMine()) {
                        mineRisk++;
                    }
                    if (rightOne < mNumRows && mTileArray[rightOne][downOne].getIsMine()) {
                        mineRisk++;
                    }
                    if (downOne >= 0 && mTileArray[i][downOne].getIsMine()) {
                        mineRisk++;
                    }


                    mTileArray[i][j].setMineCount(mineRisk);
                    System.out.print(mineRisk);
                }
            }
        }


}
