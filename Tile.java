/* Tile.java -- Extension of the JButton class to act as a tile in a minesweeper game.
 *
 * getFlagged() -- returns mFlagged
 * getSurroundingMines() -- returns mSurroundingMines
 * getIsMine() -- returns mIsMine
 *
 * canOpen() -- returns true if a tile is not opened and not flagged
 *
 * setNullIcon() -- sets a tile icon to null
 * setMineIcon() -- sets a tile icon to mMineIcon
 * setDisable() -- this sets a button to disabled and removes the mouseListener from it disabling user input to the tile
 * setFlagged() -- flags/unflags tiles as appropriate and will recognize a gameWin() on a flag placement
 * setIcons() -- sets all of the ImageIcons used in the operation of the minesweeper game, called in Menu.java main()
 * setX() -- sets the x value of a tile for use in openTile(), called upon tile creation in Board.java
 * setY() -- sets the y value of a tile for use in openTile(), called upon tile creation in Board.java
 * setSurroundingMines() -- sets mSurroundingMines
 * setIsMine() -- sets mIsMine
 * displaySurroundingMines() -- when called displays the correct tile related to being pressed, disables tile after
 * setIsOpened() -- sets mOpened true
 * */

//Swing imports
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.ImageIcon;
//AWT imports
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.event.InputEvent;
//Non-GUI related imports
import java.io.IOException;


class Tile extends JButton {
    /* Constants for Tiles */
    private static final int mTileSize = 30;

    /* Member variables of tiles */
    private int mSurroundingMines;
    private boolean mFlagged;
    private boolean mIsMine;
    private boolean mOpened;
    private int x;
    private int y;

    /* The ImageIcons used to display the icons */
    private static ImageIcon mFlaggedIcon;
    private static ImageIcon mMineIcon;
    private static ImageIcon mTileIcon;
    private static ImageIcon mPressedIcon;

    /*MouseListener that controls the tiles*/
    private MouseListener mouseListener = new MouseAdapter() {
        public void mousePressed(MouseEvent mouseEvent) {
            int modifiers = mouseEvent.getModifiers();
            //left button clicked
            if ((modifiers & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
                if (!mFlagged) {
                    if (mIsMine) {
                        setMineIcon();
                        Game_Driver.gameOver();
                    } else {
                        if (mSurroundingMines > 0) {
                            displaySurroundingMines();
                        } else {
                            setNullIcon();
                            Game_Driver.openTile(x, y);
                        }
                        if (Game_Driver.isEndPossible()) {
                            Game_Driver.gameWin();
                        }
                    }
                }
            }
            //right button clicked
            if ((modifiers & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) {
                setFlagged(mFlagged);
            }
        }
    };

    /////////////////////////////////////////////////////////
    //Constructor
    /////////////////////////////////////////////////////////
    Tile() {
        super();

        mSurroundingMines = 0;
        mFlagged = false;
        mIsMine = false;
        setText("");
        setMargin(new Insets(0, 0, 0, 0));
        setPreferredSize(new Dimension(mTileSize, mTileSize));
        setIcon(mTileIcon);
        setSize(mTileSize, mTileSize);
        setVisible(true);

        addMouseListener(mouseListener);
    }

    /////////////////////////////////////////////////////////
    //Getters
    /////////////////////////////////////////////////////////

    boolean getFlagged() {
        return mFlagged;
    }

    Integer getSurroundingMines() {
        return mSurroundingMines;
    }

    boolean getIsMine() {
        return mIsMine;
    }

    boolean canOpen() {
        return (!mOpened && !mFlagged);
    }

    /////////////////////////////////////////////////////////
    //Setters
    /////////////////////////////////////////////////////////

    private void setNullIcon() {
        setIcon(null);
        setDisable();
    }

    void setMineIcon() {
        setIcon(mMineIcon);
    }

    void setDisable() {
        setEnabled(false);
        removeMouseListener(mouseListener);
    }

    private void setFlagged(boolean flagged) {
        if (!flagged && Board.getFlagCount() != 0) {
            setIcon(mFlaggedIcon);
            mFlagged = true;
            Board.decrementFlagCount();
            if (Board.getFlagCount() == 0) {
                if (Game_Driver.isEndPossible()) {
                    Game_Driver.gameWin();
                }
            }
        } else if (mFlagged) {
            setIcon(mTileIcon);
            mFlagged = false;
            Board.incrementFlagCount();
        }
    }

    void setIcons() {
        /* Generating the ImageIcons using ImageIO.read */
        try {
            Image img = null;
            try {
                img = ImageIO.read(getClass().getResource("Resources/flag.png"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            mFlaggedIcon = new ImageIcon(img);

            try {
                img = ImageIO.read(getClass().getResource("Resources/MineIcon.png"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            mMineIcon = new ImageIcon(img);

            try {
                img = ImageIO.read(getClass().getResource("Resources/TileIcon.png"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            mTileIcon = new ImageIcon(img);

            try {
                img = ImageIO.read(getClass().getResource("Resources/PressedIcon.png"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            mPressedIcon = new ImageIcon(img);


        } catch (Exception e) {
            System.out.println("ImageIcons not set successfully.");
        }
    }

    void setX(int i) {
        x = i;
    }

    void setY(int j) {
        y = j;
    }

    void setSurroundingMines(int mineCount) {
        mSurroundingMines = mineCount;
    }

    void setIsMine(boolean isMine) {
        mIsMine = isMine;
    }

    /////////////////////////////////////////////////////////
    //METHODS
    /////////////////////////////////////////////////////////

    //sets the text on the tile showing how many mines are near
    void displaySurroundingMines() {
        //we will not display 0 for number of mines

        if (mSurroundingMines == 0) {
            setIcon(mPressedIcon);
        } else if (mSurroundingMines > 0) {
            try {
                setIcon(null);
                setText(Integer.toString(mSurroundingMines));
            } catch (Exception e) {
                System.out.println("Error in displaying numbered tile.");
            }
        }
        setDisable();
    }

    void setIsOpened() {
        mOpened = true;
    }
}
