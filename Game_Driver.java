import javax.swing.*;
import java.util.Random;

class Game_Driver {
    private static Tile mTileArray[][];
    private Random random = new Random();
    private static int mNumRows;
    private static int mNumCols;
    private static int mNumMines;

    Game_Driver(Tile[][] tileArray, int numRows, int numCols, int mineCount) {
        mTileArray = tileArray;
        mNumRows = numRows;
        mNumCols = numCols;
        mNumMines = mineCount;
        initBoard();
    }

    //shows all bombs and disables all buttons
    static void gameOver() {
        for (int i = 0; i < 0; i++) {
            for (int j = 0; j < 0; j++) {
                if (mTileArray[i][j].getIsMine())
                    mTileArray[i][j].setMine();

                mTileArray[i][j].setEnabled(false);
            }
        }
    }

    ///////////////////////////////
    /*HELPER METHODS*/
    //////////////////////////////

    ///////////////////////////////
    /*Methods that initialize the board*/
    //////////////////////////////
    /*
     * Initializes the board for mTileArray by cleaning all of the tiles, then calls
     * placeMines which randomly sets in the desired amount of mines,
     * it then will set the risk of nearby mines
     */
    private void initBoard() {
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
                if (rightOne < mNumRows && downOne >= 0 && mTileArray[rightOne][downOne].getIsMine()) {
                    mineRisk++;
                }
                if (downOne >= 0 && mTileArray[i][downOne].getIsMine()) {
                    mineRisk++;
                }


                mTileArray[i][j].setSurroundingMines(mineRisk);
                // System.out.println(mineRisk);
            }
        }
    }

    static void openTile(int i, int j) {

        mTileArray[i][j].setIsOpened();
        mTileArray[i][j].displaySurroundingMines();

        if (mTileArray[i][j].getSurroundingMines() == 0) {
            int leftOne = i - 1;
            int rightOne = i + 1;
            int downOne = j - 1;
            int upOne = j + 1;

            if (leftOne >= 0 && downOne >= 0 && mTileArray[leftOne][downOne].canOpen())
                openTile(leftOne, downOne);
            if (leftOne >= 0 && mTileArray[leftOne][j].canOpen())
                openTile(leftOne, j);
            if (leftOne >= 0 && upOne < mNumCols && mTileArray[leftOne][upOne].canOpen())
                openTile(leftOne, upOne);
            if (downOne >= 0 && mTileArray[i][downOne].canOpen())
                openTile(i, downOne);
            if (upOne < mNumCols && mTileArray[i][upOne].canOpen())
                openTile(i, upOne);
            if (rightOne < mNumRows && downOne >= 0 && mTileArray[rightOne][downOne].canOpen())
                openTile(rightOne, downOne);
            if (rightOne < mNumRows && mTileArray[rightOne][j].canOpen())
                openTile(rightOne, j);
            if (rightOne < mNumRows && upOne < mNumCols && mTileArray[rightOne][upOne].canOpen())
                openTile(rightOne, upOne);

        }


    }


}
