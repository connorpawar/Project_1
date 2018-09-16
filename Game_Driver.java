//Swing imports
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.JLabel;
import javax.swing.JButton;
//AWT imports
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
//Non-GUI related imports
import java.util.Random;

class Game_Driver {
    private static JFrame mGame;
    private static Tile mTileArray[][];
    private Random random = new Random();
    private static int mNumRows;
    private static int mNumCols;
    private static int mNumMines;

    /////////////////////////////////////////////////////////
    //Constructor
    /////////////////////////////////////////////////////////
    Game_Driver(JFrame game, Tile[][] tileArray, int numRows, int numCols, int mineCount) {
        mGame = game;
        mTileArray = tileArray;
        mNumRows = numRows;
        mNumCols = numCols;
        mNumMines = mineCount;
        initBoard();
    }

    /////////////////////////////////////////////////////////
    //End of game methods
    /////////////////////////////////////////////////////////

    /*gameOver()
     * @Return Void
     *
     * Displays all of the bombs and disables all of the buttons,
     * presents the user with a replay option.
     * */
    static void gameOver() {
        for (int i = 0; i < mNumRows; i++) {
            for (int j = 0; j < mNumCols; j++) {
                if (mTileArray[i][j].getIsMine()) {
                    mTileArray[i][j].setMineIcon();
                }
                mTileArray[i][j].setDisable();
            }
        }
        mGame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        Board.getInfoFrame().dispose();
        JFrame loseFrame = new JFrame("You Lose!");
        loseFrame.setLocationRelativeTo(mGame);
        loseFrame.setSize(250, 150);
        loseFrame.setLayout(new GridLayout(2, 1));
        JLabel loseText = new JLabel("You lost! Would you like to play again?");
        JButton loseButton = new JButton("Replay?");
        loseFrame.add(loseText);
        loseFrame.add(loseButton);
        loseFrame.setResizable(false);
        loseFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        loseFrame.setAlwaysOnTop(true);
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

    /*gameWin()
     * @Return Void
     *
     * Displays all of the bombs and disables all of the buttons,
     * presents the user with a replay option.
     * */
    static void gameWin() {
        if (Board.getFlagCount() == 0) {
            boolean win = true;
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
                mGame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                Board.getInfoFrame().dispose();
                JFrame winFrame = new JFrame("You Win!");
                winFrame.setLocationRelativeTo(mGame);
                winFrame.setSize(250, 150);
                winFrame.setLayout(new GridLayout(2, 1));
                JLabel winText = new JLabel("You win! Would you like to play again?");
                JButton winButton = new JButton("Replay?");
                winFrame.add(winText);
                winFrame.add(winButton);
                winFrame.setResizable(false);
                winFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                winFrame.setAlwaysOnTop(true);
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

    //////////////////////////////
    /*HELPER METHODS            */
    //////////////////////////////

    /*isEndPossible()
     * @Return boolean
     *
     * Iterates through the board and checks
     * every tile that is not a mine, if every
     * tile that is not a mine is disabled as a result
     * of being clicked, the game may end. Only called
     * by setting a flag.
     * */
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

    /*openTile()
     * @Return void
     *
     * If a tile is blank, check that the orthogonally adjacent
     * tiles are openable with canOpen(), if true recursively call
     * openTile() until tiles that are unopenable are encountered.
     * */
    static void openTile(int i, int j) {

        mTileArray[i][j].setIsOpened();
        mTileArray[i][j].displaySurroundingMines();

        if (mTileArray[i][j].getSurroundingMines() == 0) {
            int leftOne = i - 1;
            int rightOne = i + 1;
            int downOne = j - 1;
            int upOne = j + 1;

            if (leftOne >= 0 && mTileArray[leftOne][j].canOpen())
                openTile(leftOne, j);
            if (downOne >= 0 && mTileArray[i][downOne].canOpen())
                openTile(i, downOne);
            if (upOne < mNumCols && mTileArray[i][upOne].canOpen())
                openTile(i, upOne);
            if (rightOne < mNumRows && mTileArray[rightOne][j].canOpen())
                openTile(rightOne, j);
        }
    }

    /////////////////////////////////////
    /*Methods that initialize the board*/
    /////////////////////////////////////

    /*initBoard()
     * @Return void
     *
     * calls placeMines() to randomly place mines throughout
     * the board and then calls setRiskNum() to set the mine
     * count of every tile.
     * */
    private void initBoard() {
        placeMines();
        setRiskNum();
    }

    /*placeMines()
     * @Return void
     *
     * calls setMine() the number of times equal to the
     * number of mines
     * */
    private void placeMines() {
        for (int i = 0; i < mNumMines; i++) {
            setMine();
        }
    }

    /*setMine()
     * @Return void
     *
     * sets a random x and y bound by the length of
     * the rows and columns then checks if a mine is present,
     * if a mine is present call setMine() again, eventually
     * coming off the stack when all mines are placed.
     * */
    private void setMine() {
        int x = random.nextInt(mNumRows);
        int y = random.nextInt(mNumCols);

        if (!mTileArray[x][y].getIsMine()) {
            mTileArray[x][y].setIsMine(true);
        } else {
            setMine();
        }
    }

    /*setRiskNum()
     * @Return void
     *
     * checks the surrounding positions of a tile for a mine, if
     * a mine is present increment mineRisk, once all if statements
     * are checked called setSurroundingMines() and pass in mineRisk.
     * */
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
            }
        }
    }
}
