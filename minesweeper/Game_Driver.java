package minesweeper;

//Swing imports
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.Dimension;
import java.awt.FlowLayout;
//AWT imports
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
//Non-GUI related imports
import java.util.Random;

/**
 * Receives required objects from {@link Board} and takes the user input and
 * manipulates the objects as necessary to play Minesweeper.
 * */
class Game_Driver {
    /* Objects for Game_Driver */
    /** set equal to JFrame board in constructor from {@link Board}, game window that user interacts with **/
    private static JFrame mGame;
    /** set equal to JFrame board in constructor from {@link Board}, game window that user interacts with **/
    private static JFrame mcheatGame;
    /** 2D Tile array of used for game logic, equal coordinates to nXm game board **/
    private static Tile mTileArray[][];
    /** 2D Tile array of used for game logic, equal coordinates to nXm game board **/
    private static Tile mcopyTileArray[][];
    /** creates a random value, used for x,y coordinates **/
    private Random random = new Random();

    private static Random random2 = new Random();

    public static boolean mCheatActive = false;

    public static boolean mOver = false;
    /* Member Variables for Game_Driver */
    /** holds the number of rows **/
    private static int mNumRows;
    /** holds the number of columns **/
    private static int mNumCols;
    /** holds the number of mines **/
    private static int mNumMines;
    private static int mTurnsOg;
    private static int mTurns;

    /////////////////////////////////////////////////////////
    //Constructor
    /////////////////////////////////////////////////////////
     /**
     * This constructor is called in {@link Board} to initialize the board with the already
     * checked input from the user.
     *
     * @ms.Pre-condition passes in ready to go frame with buttons already associated to tileArray
     * @ms.Post-condition Game is fully initialized
     *
     * @see #initBoard
     *
     * @param game JFrame of the nXm board
     * @param tileArray {@link Tile} object array already associated with buttons
     * @param numRows value of amount of rows in the board
     * @param numCols value of amount of columns in the board
     * @param mineCount amount of mines user wants to place in their board
     *
     * */
    Game_Driver(JFrame game, Tile[][] tileArray, int numRows, int numCols, int turns, int mineCount) {
        mGame = game;
        mTileArray = tileArray;
        mNumRows = numRows;
        mNumCols = numCols;
        mNumMines = mineCount;
        mTurns = turns;
        mTurnsOg = turns;
        initBoard();
    }

    /////////////////////////////////////////////////////////
    //End of game methods
    /////////////////////////////////////////////////////////

    /**
     * Displays all of the bombs and disables all of the buttons,
     * presents the user with a replay option.
     *
     * @ms.Pre-condition User has lost, mine has been hit
     * @ms.Post-condition Game Board is disabled and lose frame pops up
     *
     * @see #gameWin()
     *
     * */
    static void gameOver() {
    	mTurns = mTurnsOg;
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
            if(mCheatActive){
        	       mcheatGame.dispose();
            }
            mGame.dispose();
            loseFrame.dispose();
            Board newgame = new Board(mNumCols, mNumRows, mTurnsOg, mNumMines);
        });
        loseFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if(mCheatActive){
                       mcheatGame.dispose();
                }
                mGame.dispose();
                Menu.open();
            }
        });
    }

    /**
     * Displays all of the bombs and disables all of the buttons,
     * presents the user with a replay option.
     *
     * @ms.Pre-condition User has won, all tiles revealed, flags are placed
     * @ms.Post-condition Win window is revealed
     *
     * @see #gameOver()
     * */
    static void gameWin() {
    	mTurns = mTurnsOg;
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
                	if(mCheatActive) {
            			mcheatGame.dispose();
            		}
                    mGame.dispose();
                    winFrame.dispose();
                    Board newgame = new Board(mNumCols, mNumRows, mTurnsOg, mNumMines);
                });
                winFrame.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                    	if(mCheatActive) {
                			mcheatGame.dispose();
                		}
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

    /**
     * Iterates through the board and checks
     * every tile that is not a mine, if every
     * tile that is not a mine is disabled as a result
     * of being clicked, the game may end. Only called
     * by setting a flag.
     *
     * @return boolean isPossible, depends on if any buttons are still enabled
     *
     * @ms.Pre-condition called in (@link #Tile} #mouselistener to see if one has won
     * @ms.Post-condition the boolean returned checked if the game has been won
     *
     * @see Tile#mouseListener
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

    static void resetMine() {
    	int x = random2.nextInt(mNumRows);
        int y = random2.nextInt(mNumCols);

        if (!mTileArray[x][y].getIsMine() && !mTileArray[x][y].getIsOpened()) {
            mTileArray[x][y].setIsMine(true);
        } else {
            resetMine();
        }
    }

    static void updateMineNums() {
    	if(mTurns == 0) {
    		return;
    	}
    	mTurns --;
    	if(mTurns>0) {
    		return;
    	}
    	mTurns = mTurnsOg;
    	int numreset = 0;
    	for (int i = 0; i < mNumRows; i++) {
            for (int j = 0; j < mNumCols; j++) {
            	if(mTileArray[i][j].getIsMine() && !mTileArray[i][j].getFlagged()) {
            		mTileArray[i][j].removeMine();
            		numreset++;
            	}
            }
        }
    	for(int i = 0; i<numreset; i++) {
    		resetMine();
    	}
        setRiskNum();
    	for (int i = 0; i < mNumRows; i++) {
            for (int j = 0; j < mNumCols; j++) {
            	if(mTileArray[i][j].getIsOpened()) {
            		mTileArray[i][j].displaySurroundingMines();
            	}
            }
        }
    	if(mCheatActive){
            CheatUpdate();
    	}
    }

    /**
     * If a tile is blank, check that the orthogonally adjacent
     * tiles are openable with canOpen(), if true recursively call
     * openTile() until tiles that are unopenable are encountered.
     *
     * @param i coordinate to which tile needs to be opened
     * @param j coordinate to which tile needs to be opened
     *
     * @ms.Pre-condition User clicked on a tile and needs corresponding tiles to open as well
     * @ms.Post-condition Tile is left opened with corresponding tiles opened as well
     *
     * @see Tile#setIsOpened()
     * @see Tile#displaySurroundingMines()
     * @see Tile#canOpen()
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
            if (leftOne >= 0 && upOne < mNumCols && mTileArray[leftOne][upOne].canOpen())
                openTile(leftOne, upOne);
            if (leftOne >= 0 && downOne >= 0 && mTileArray[leftOne][downOne].canOpen())
                openTile(leftOne, downOne);
            if (rightOne < mNumRows && downOne >= 0 && mTileArray[rightOne][downOne].canOpen())
                openTile(rightOne, downOne);
            if (rightOne < mNumRows && upOne < mNumCols && mTileArray[rightOne][upOne].canOpen())
                openTile(rightOne, upOne);
        }
    }

    /////////////////////////////////////
    /*Methods that initialize the board*/
    /////////////////////////////////////

    /**
     * calls placeMines() to randomly place mines throughout
     * the board and then calls setRiskNum() to set the mine
     * count of every tile.
     *
     * @ms.Pre-condition called in constructor after using parameters from {@link Board}
     * @ms.Post-condition game board is initialized through mTileArray
     *
     * @see #placeMines()
     * @see #setRiskNum()
     * */
    private void initBoard() {
        placeMines();
        setRiskNum();
    }

    /**
     * calls setMine() the number of times equal to the
     * number of mines
     *
     * @ms.Pre-condition No guarantees are made before this function is called
     * @ms.Post-condition all mines are set into place
     *
     * @see #setMine()
     * */
    private void placeMines() {
        for (int i = 0; i < mNumMines; i++) {
            setMine();
        }
    }

    /**
     * sets a random x and y bound by the length of
     * the rows and columns then checks if a mine is present,
     * if a mine is present call setMine() again, eventually
     * coming off the stack when all mines are placed.
     *
     * @ms.Pre-condition called number of mines times
     * @ms.Post-condition mine is set in a random tile
     *
     * @see #random
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

    /**
     * checks the surrounding positions of a tile for a mine, if
     * a mine is present increment mineRisk, once all if statements
     * are checked called setSurroundingMines() and pass in mineRisk.
     *
     * @ms.Pre-condition No guarantees are made before this function is called
     * @ms.Post-condition All of the tiles are given a mine risk number
     *
     * @see Tile#getIsMine()
     * */
    public static void setRiskNum() {
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
    static void CheatUpdate() {
        JPanel masterPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 5));
        mcopyTileArray = new Tile [mNumRows][mNumCols];
        try {
            for (int i = 0; i < mNumRows; i++) {
                JPanel tempPanel = new JPanel(new GridLayout(mNumCols, 1));

                for (int j = 0; j < mNumCols; j++) {
                	mcopyTileArray[i][j] = new Tile(mTileArray[i][j]);
                    tempPanel.add(mcopyTileArray[i][j]);
                    mcopyTileArray[i][j].TileCheat();
                }
                masterPanel.add(tempPanel);
            }
            mcheatGame.add(masterPanel);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        mcheatGame.validate();
        mcheatGame.pack();
        mcheatGame.setVisible(true);
    }
	public JFrame CheatMode() {
		if(mCheatActive) {
			mcheatGame.dispose();
		}
		mcheatGame = new JFrame();
		mcheatGame.setTitle("CheatMode");

        /*
         * Because a bigger board placed in the center of the screen would go
         * off screen, this setup below limits the placement of the mGame window
         * which is oriented by the top left of its frame to the upper left quadrant
         * of the users monitor. This will then move further up and further left
         * as the board increases in respective size.
         * */
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        int xOffset = width / 2 - (mNumRows * 15);
        int yOffset = height / 2 - (mNumCols * 15);
        mcheatGame.setLocation(xOffset, yOffset);
        JPanel masterPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 5));
        mcopyTileArray = new Tile [mNumRows][mNumCols];
        try {
            for (int i = 0; i < mNumRows; i++) {
                JPanel tempPanel = new JPanel(new GridLayout(mNumCols, 1));

                for (int j = 0; j < mNumCols; j++) {
                	mcopyTileArray[i][j] = new Tile(mTileArray[i][j]);
                    tempPanel.add(mcopyTileArray[i][j]);
                    mcopyTileArray[i][j].TileCheat();
                }
                masterPanel.add(tempPanel);
            }
            mcheatGame.add(masterPanel);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        mcheatGame.addWindowListener(new WindowAdapter() {
        	public void windowClosing(WindowEvent e) {
    			mCheatActive = false;
        	}
    	});

        mcheatGame.validate();
        mcheatGame.pack();
        mcheatGame.setVisible(true);
        mCheatActive = true;
        return(mcheatGame);
	}
}
