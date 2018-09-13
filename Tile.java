import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Tile extends JButton {
    /* Contants for Tiles */
    static final int mTileSize = 30;

    /* Member variables of tiles */
    int mSurroundingMines;
    boolean mHasSurroundingMine;
    boolean mFlagged;
    boolean mIsMine;
    boolean mOpened;
    private int x;
    private int y;

    /* The ImageIcons used to display the icons */
    static ImageIcon mFlaggedIcon;
    static ImageIcon mMineIcon;
    static ImageIcon mTileIcon;
    static ImageIcon mPressedIcon;

    //constructor
    public Tile() {
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

        addActionListener(e -> {

            if (mIsMine) {
                setMineImage(true);
                Game_Driver.gameOver();
            } else {
                if (mSurroundingMines != 0) {
                    displaySurroundingMines();
                } else {
                    setIcon(mPressedIcon);
                    Game_Driver.openTile(x, y);
                }
            }
        });
    }

    /////////////////////////////////////////////////////////
    //Getters
    /////////////////////////////////////////////////////////

    public boolean getFlagged() {
        return mFlagged;
    }

    public Integer getMineCount() {
        return mSurroundingMines;
    }

    public boolean getIsMine() {
        return mIsMine;
    }

    public boolean getIsOpened() {
        return mOpened;
    }

    public boolean canOpen() {
        return (!mOpened && mSurroundingMines == 0);
    }

    public boolean getHasSurroundingMine() {
        return mHasSurroundingMine;
    }

    /////////////////////////////////////////////////////////
    //Setters
    /////////////////////////////////////////////////////////

    public void setIcons() {
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

    public void setFlagged(boolean flagged) {
        if (flagged) {
            setIcon(mFlaggedIcon);
        } else {
            setIcon(mTileIcon);
        }

        mFlagged = flagged;
    }

    public void setMineImage(boolean show) {

        if (show == true)
            setIcon(mMineIcon);
        else
            setIcon(mTileIcon);
    }

    public void setMineCount(int mineCount) {
        mSurroundingMines = mineCount;
    }

    public void setIsMine(boolean isMine) {
        mIsMine = isMine;
    }

    /////////////////////////////////////////////////////////
    //METHODS
    /////////////////////////////////////////////////////////

    public void increaseMineCount() {
        mSurroundingMines += 1;
    }

    //sets the text on the tile showing how many mines are near
    public void displaySurroundingMines() {
        //we will not display 0 for number of mines

        if (mSurroundingMines == 0) {
            setIcon(mPressedIcon);
        } else if (mSurroundingMines > 0) {
            try {
                Image img = ImageIO.read(getClass().getResource("Resources/Number" + mSurroundingMines + ".png"));
                setIcon(new ImageIcon(img));
            } catch (Exception e) {
                System.out.println("Error in displaying numbered tile.");
            }
        }
    }


    /*public void actionPerformed(ActionEvent e) {
        if(SwingUtilities.isLeftMouseButton(MouseEvent)){//TODO get MouseEvent
            if(mIsMine){
                //TODO game over
            }
            else{
                if(mSurroundingMines > 0){
                    displaySurroundingMines();
                }
                else{
                    //revealExpanding();
                }
                setEnabled(false);
            }
        }else if(SwingUtilities.isRightMouseButton(MouseEvent)){
            setFlagged(!mFlagged);
        }
    }*/


    public void cleanTile() {
        mSurroundingMines = 0;
        mFlagged = false;
        mIsMine = false;
        setIcon(mTileIcon);
    }

    public void setmHasSurroundingMine() {
        mHasSurroundingMine = true;
    }

    public void setIsOpened() {
        mOpened = true;
    }
}