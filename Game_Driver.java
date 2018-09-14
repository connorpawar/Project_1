import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class Game_Driver {
    private static JFrame mGame;
    private static Tile mTileArray[][];
    private Random random = new Random();
    private static int mNumRows;
    private static int mNumCols;
    private static int mNumMines;

    Game_Driver(JFrame game, Tile[][] tileArray, int numRows, int numCols, int mineCount) {
        mGame = game;
        mTileArray = tileArray;
        mNumRows = numRows;
        mNumCols = numCols;
        mNumMines = mineCount;
        initBoard();
    }

    //shows all bombs and disables all buttons
    static void gameOver() {
        for (int i = 0; i < mNumRows; i++) {
            for (int j = 0; j < mNumCols; j++) {
                if (mTileArray[i][j].getIsMine()) {
                    mTileArray[i][j].setMine();
                }
                mTileArray[i][j].setDisable();
            }
        }
        Board.getInfoFrame().dispose();
        mGame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        JFrame loseFrame = new JFrame("You Lose!");
        loseFrame.setLocationRelativeTo(null);
        loseFrame.setSize(250, 150);
        loseFrame.setLayout(new GridLayout(2, 1));
        JLabel loseText = new JLabel("You lost! Would you like to play again?");
        JButton loseButton = new JButton("Replay?");
        loseFrame.add(loseText);
        loseFrame.add(loseButton);
        loseFrame.setResizable(false);
        loseFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        loseFrame.setVisible(true);
        loseButton.addActionListener(e -> {
            mGame.dispose();
            loseFrame.dispose();
            Board newgame = new Board(mNumCols, mNumRows, mNumMines);
        });
        loseFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                mGame.dispose();
                Menu.open();
            }
        });

    }

    static void gameWin() {
        if (Board.getFlagCount() == 0) {
            Boolean win = true;
            for (int i = 0; i < mNumRows; i++) {
                for (int j = 0; j < mNumCols; j++) {
                    if (mTileArray[i][j].getIsMine()) {
                        win &= mTileArray[i][j].getFlagged();
                    }
                }
            }
            if (win == true) {
                for (int i = 0; i < mNumRows; i++) {
                    for (int j = 0; j < mNumCols; j++) {
                        mTileArray[i][j].setDisable();
                    }
                }
                Board.getInfoFrame().dispose();
                JFrame winFrame = new JFrame("You Win!");
                winFrame.setLocationRelativeTo(null);
                winFrame.setSize(250, 150);
                winFrame.setLayout(new GridLayout(2, 1));
                JLabel winText = new JLabel("You win! Would you like to play again?");
                JButton winButton = new JButton("Replay?");
                winFrame.add(winText);
                winFrame.add(winButton);
                winFrame.setResizable(false);
                winFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                winFrame.setVisible(true);
                winButton.addActionListener(e -> {
                    mGame.dispose();
                    winFrame.dispose();
                    Board newgame = new Board(mNumCols, mNumRows, mNumMines);
                });
                winFrame.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        mGame.dispose();
                        Menu.open();
                    }
                });
            }
        }
    }

    static boolean isEndPossible() {
        boolean isPossible = true;
        for (int i = 0; i < mNumRows; i++) {
            for (int j = 0; j < mNumCols; j++) {
                if (!(mTileArray[i][j].getIsMine())) {
                    isPossible &= !(mTileArray[i][j].isEnabled());
                }
            }
        }
        return (isPossible);
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
