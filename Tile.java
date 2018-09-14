import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.event.InputEvent;


public class Tile extends JButton {
    /* Contants for Tiles */
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
                    }
                }
            }
            //right button clicked
            if ((modifiers & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) {
                setFlagged(mFlagged);
            }
        }
    };

    //constructor
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

    public boolean getFlagged() {
        return mFlagged;
    }

    Integer getSurroundingMines() {
        return mSurroundingMines;
    }

    boolean getIsMine() {
        return mIsMine;
    }

    public boolean getIsOpened() {
        return mOpened;
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

    private void setMineIcon() {
        setIcon(mMineIcon);
    }

    private void setDisable() {
        setEnabled(false);
        removeMouseListener(mouseListener);
    }

    private void setFlagged(boolean flagged) {
        if (!flagged && Board.getFlagCount() != 0) {
            setIcon(mFlaggedIcon);
            mFlagged = true;
            Board.decrementFlagCount();
            if (Board.getFlagCount() == 0) {
                Game_Driver.gameWin();
            }
        } else if (mFlagged) {
            setIcon(mTileIcon);
            mFlagged = false;
            Board.incrementFlagCount();
        } else {

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

    void setMine() {
        setIcon(mMineIcon);
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