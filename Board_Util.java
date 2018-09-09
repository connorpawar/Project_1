import javax.swing.*;
//import Board.java;

public class Board_Util{

    Board mGameBoard;
    //Tile mTileArray[][];
    int mNumRows;
    int mNumCols;

    public Board_Util(){//Board board, Tile[][] tileArray,int numRows, int numCols, JLable flag){
        /*mGameBoard = board;
        mTileArray = tileArray;
        mNumRows = numRows;
        mNumCols = numCols;*/
    }

    public void revealExpanding(){

    }

    //shows all bombs and disables all buttons
    public void gameOver(){
        for(int i=0; i<0; i++){
            for(int j=0; j<0; j++){
                /*if(mTileArray[i][j]->getIsMine())
                    mTileArray[i][j]->setMineImage(true);

                mTileArray[i][j]->setEnabled(false);*/
            }
        }
    }

    public void setNumberOfMines(){
        for(int i=0; i<0; i++){
            for(int j=0; j<0; j++){
                /*if(mTileArray[i][j]->getIsMine())
                    incrementSurroundingMineCount(i,j);*/
            }
        }
    }

    //adds one to the mine count to the surrounding tiles
    private void incrementSurroundingMineCount(int row, int col){

    }
}
