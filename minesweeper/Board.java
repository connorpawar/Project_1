package minesweeper;

//Swing imports
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.WindowConstants;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
//AWT imports
import java.awt.EventQueue;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
//Non-GUI related imports
import java.io.IOException;
import java.lang.Exception;

/**
 * Creates the JFrame holding the {@link Tile}'s and populates the JFrame
 * with the tiles as well as creates the info frame and the 2D array used by {@link Game_Driver} to
 * control the Minesweeper game eventually passing full control to Game_Driver.
 * */
public class Board {

    /**
      Constant size of a square tile will be
      tileSize * tileSize, or 30x30 pixels
     * */
    final static int tileSize = 30;

    /**
     * Number of mFlags available to the user which is equal to the number
     * of mines on the board. Used by various functions to track mFlags placed by user.
     *
     * @see #incrementFlagCount()
     * @see #decrementFlagCount()
     * @see #getFlagCount()
     * */
    private static int mNumFlags;

    /**
     * JLabel that displays the text containing Flags Available and
     * also the current number of mFlags which is equivalent to {@link #mNumFlags}
     * */
    private static JLabel mFlags;

    /**
     * JFrame that holds the replay JButton as well as the JLabel that tells the
     * user how many mFlags are available to be placed still. Useful as member variable
     * to let other functions manipulate text field indicating available mines.
     *
     * @see #getInfoFrame()
     * */
    private static JFrame mInfo;

    /**
     * JFrame that displays the {@link Tile} objects that correspond to the minesweeper
     * board. Reference to this object is passed to {@link Game_Driver} to allow Game_Driver
     * to control the board.
     * */
    private static JFrame mGame;
    private static JFrame mCheatGame;

    /**
     * This constructor takes in the various input values from the user that is
     * checked in the {@link Menu} object on start up and also in this constructor.
     * Once preconditions are checked and verified, initGame() is added to the EventQueue.
     *
     * @ms.Pre-condition Input fields from Menu have been verified and the user has pressed the "Start" button
     * @ms.Post-condition initGame() is successfully added to the EventQueue once inputs are verified once more
     *
     * @param rowLength An integer representing the length of a row
     * @param colLength An integer representing the length of a column
     * @param mines An integer representing the number of mines in a mGame
     */
    Board(int rowLength, int colLength, int turns, int mines) {
        /*
        * Precondition checks that should be unnecessary due to user input restrictions in menu
        */
        try {
            verifyBoardParam(rowLength, colLength, mines);
            /* EventQueue.invokeLater() is necessary to avoid window hanging */
            EventQueue.invokeLater(() -> initGame(rowLength, colLength, turns, mines));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            Menu.open();
        }
    }

    /////////////////////////////////////////////////////////
    //Methods
    /////////////////////////////////////////////////////////

    /**
     * Called only by the constructor of {@link #Board} after appropriate preconditions
     * are checked. Creates the JFrame object that displays the {@link Tile} and generates the 2D
     * array that stores the Tiles for use by the {@link Game_Driver} class in
     * controlling the minesweeper mGame.
     *
     * @ms.Pre-condition Input parameters are verified by constructor and board is ready to be generated from information
     * @ms.Post-condition A JFrame object holding the desired Tiles and a 2D array corresponding to the board are passed to
     *       a {@link Game_Driver} object with the parameters that will take user input and respond accordingly
     *       to play a minesweeper mGame.
     *
     * @param numCols Number of columns which is related to the length of a row
     * @param numRows Number of rows which is related to the length of a column
     * @param mines Number of mines in the board
     * */
    private void initGame(int numCols, int numRows, int turns, int mines) {
        try {
            verifyBoardParam(numCols, numRows, mines);
        }catch(IllegalArgumentException e){
            System.out.println("Well this is unfortunate.");
            e.printStackTrace();
        }

        /*
         *  JFrame mGame is the board window
         *  JFrame mInfo is the information window
         */
        mGame = new JFrame();
        mInfo = new JFrame();
        mNumFlags = mines;

        /*
        Below are the JFrame values being set, documented by the related
        function name used to set the JFrame characteristic
        */
        mGame.setTitle("Definitely not Minesweeper");

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
        int xOffset = width / 2 - (numRows * 15);
        int yOffset = height / 2 - (numCols * 15);
        mGame.setLocation(xOffset, yOffset);

        mGame.setResizable(false);
        mGame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        mInfo.setTitle("Information");
        mInfo.setAlwaysOnTop(true);
        mInfo.setSize(300, 100);
        mInfo.setLocationRelativeTo(mGame);
        mInfo.setLayout(new GridLayout(2, 2));

        /*
         * Below is the JLabel being created to show the current number of mFlags
         * available to the player.
         */
        mFlags = new JLabel();
        mFlags.setText("Flags Available: " + Integer.toString(mines));
        try {
            Image img = ImageIO.read(getClass().getResource("Resources/flag.png"));
            mFlags.setIcon(new ImageIcon(img));
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
         * Below is a button being created to test the decrement of the mFlags JLabel
         * by clicking the Tile updateFlags, this will be implemented as an extension
         * of the Tile class.
         */

        JButton updateFlags = new JButton();
        updateFlags.setText("RESTART");
        updateFlags.addActionListener((ActionEvent event) -> {
            mGame.dispose();
            mInfo.dispose();
            if(Game_Driver.mCheatActive){
                mCheatGame.dispose();
            }
            Board newgame = new Board(numCols, numRows, turns, mines);
        });

        mInfo.add(updateFlags);
        mInfo.add(mFlags);
        mInfo.setResizable(false);
        mInfo.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        /*
        This adds the tiles to a JPanel that is set as a flowlayout that is then
        added to another JPanel to allow modular row/size functionality.
        */
        Tile tileGrid[][] = new Tile[numRows][numCols];
        JPanel masterPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 5));
        try {
            for (int i = 0; i < numRows; i++) {
                JPanel tempPanel = new JPanel(new GridLayout(numCols, 1));

                for (int j = 0; j < numCols; j++) {
                    tileGrid[i][j] = new Tile();
                    tileGrid[i][j].setMargin(new Insets(0, 0, 0, 0));
                    tileGrid[i][j].setPreferredSize(new Dimension(tileSize, tileSize));
                    tileGrid[i][j].setX(i);
                    tileGrid[i][j].setY(j);
                    tempPanel.add(tileGrid[i][j]);
                }
                masterPanel.add(tempPanel);
            }
            mGame.add(masterPanel);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        mGame.validate();
        mGame.pack();
        mGame.setVisible(true);
        mInfo.validate();
        mInfo.setVisible(true);

        /*
        This WindowListener has an Overridden windowClosing event that allows
        the function Menu.open() to get called on the Board window closing.
        */
        mGame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mInfo.dispose();
                if(Game_Driver.mCheatActive){
                    mCheatGame.dispose();
                }
                if (mGame.getDefaultCloseOperation() == WindowConstants.DISPOSE_ON_CLOSE) {
                    Menu.open();
                }
            }
        });

        Game_Driver gameStart = new Game_Driver(mGame, tileGrid, numRows, numCols, turns, mines);
        /*
        This is a new button for the info menu that starts the cheat mode being created
        */
        JButton cheatMode = new JButton();
        cheatMode.setText("Cheat Mode");
        cheatMode.addActionListener((ActionEvent event) -> {
          if (gameStart.CheatModeActive()) {
        		mCheatGame.dispose();
        		gameStart.setCheatMode();
        	}
        	else {
        		mCheatGame = gameStart.CheatMode();
        	}
        });
        mInfo.add(cheatMode);
    }

    /**
     * Takes the information stored in the mFlags JLabel
     * and increments it and updates the Label
     *
     * @ms.Pre-condition The user has unflagged a tile and the JLabel holding flag information must be incremented
     * @ms.Post-condition The JLabel holding the flag information is displayed with a value 1 greater than calling
     *
     * @throws  NumberFormatException If thrown the JLabel holding the display value for the
     *                                number of mFlags remaining for some reason does not parse
     *                                into an integer correctly.
     *
     * @see #decrementFlagCount()
     * */
    static void incrementFlagCount() {
        mNumFlags += 1;

        try {
            if (Integer.parseInt(mFlags.getText().replaceAll("[^\\d]", "")) == mNumFlags) {
                throw new NumberFormatException();
            }
            mFlags.setText("Flags Available: " + Integer.toString(mNumFlags));
        } catch (NumberFormatException e) {
            System.out.println(mFlags.getText());
        }
    }

    /**
     * Takes the information stored in the mFlags JLabel
     * and decrements it and updates the Label
     *
     * @ms.Pre-condition The user has flagged a tile and the JLabel holding flag information must be decremented
     * @ms.Post-condition The JLabel holding the flag information is displayed with a value 1 less than calling
     *
     * @throws  NumberFormatException If thrown the JLabel holding the display value for the
     *                                number of mFlags remaining for some reason does not parse
     *                                into an integer correctly.
     *
     * @see #incrementFlagCount()
     * */
    static void decrementFlagCount() {
        mNumFlags -= 1;
        try {
            if (Integer.parseInt(mFlags.getText().replaceAll("[^\\d]", "")) == 0) {
                throw new NumberFormatException();
            }
            mFlags.setText("Flags Available: " + Integer.toString(mNumFlags));
        } catch (NumberFormatException e) {
            System.out.println(mFlags.getText());
        }
    }

    /**
     * Checks the passed in parameters to ensure that they meet the requirements.
     *
     * @ms.Pre-condition Board values are needing to be verified.
     * @ms.Post-condition Passed in board values were verified and no exception was thrown
     *
     * @param col the number of columns must be 2 or greater, otherwise throw an exception
     * @param row the number of rows must be 2 or greater, otherwise throw an exception
     * @param mines the number of mines must be greater than 0 but less than the number of tiles (row * col)
     *
     * @throws IllegalArgumentException Exception thrown if any of the past in arguments do not pass the specified
     *                                  requirements.
     * */
    private void verifyBoardParam(int col, int row, int mines){
        if(row < 2){
            throw new IllegalArgumentException("Illegal row length: " + row);
        }
        else if(col < 2){
            throw new IllegalArgumentException("Illegal col length: " + col);
        }
        else if(mines < 1 || mines > ((col * row) - 1)){
            throw new IllegalArgumentException("Illegal number of mines: " + mines);
        }
    }


    /**
     * Returns the JFrame mInfo that is created during {@link #initGame(int, int, int)} that
     * holds information about available mFlags and also allows the user to restart the mGame.
     *
     * @return {@link #mInfo} JFrame that holds the replay button and the flag JLabel.
     *
     * @ms.Pre-condition No gaurantees are made before this function is called
     * @ms.Post-condition The JFrame object called mInfo is returned
     *
     * @see #mInfo
     * */
    static JFrame getInfoFrame() {
        return mInfo;
    }

    /**
     * Returns the member variable {@link #mNumFlags} that corresponds to
     * the number of mFlags in the current mGame of minesweeper.
     *
     * @return {@link #mNumFlags} Number of mFlags available to be placed
     *
     * @ms.Pre-condition No gaurantees are made before this function is called
     * @ms.Post-condition The integer stored in mNumFlags is returned
     *
     * @see #mNumFlags
     * */
    static int getFlagCount() {
        return mNumFlags;
    }

    /**
     * Main function of Board.java holds no particular significance and is left blank.
     *
     * @ms.Pre-condition No gaurantees are made before this function is called
     * @ms.Post-condition No result of calling Board.main()
     *
     * */
    public static void main() {
        /* Empty main() */
    }
}
