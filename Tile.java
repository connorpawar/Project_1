import javax.swing.*;

public class Tile extends JButton {

    static final int mWidth = 30;
    static final int mHieght = 30;

    int mSurroundingMines;
    boolean mFlagged;
    boolean mIsMine;
    private int x;
    private int y;


    final static ImageIcon mFlaggedIcon = new ImageIcon("Resources/flag.png");
    final static ImageIcon mMineIcon = new ImageIcon("Resources/MineIcon.png");
    final static ImageIcon mTileIcon = new ImageIcon("Resources/TileIcon.png");
    final static ImageIcon numberIcon = new ImageIcon("Resources/Number8.png ");
    final static ImageIcon mPressedIcon = new ImageIcon("Resources/PressedIcon.png");

    //constructor
    public Tile() {
        super();


        mSurroundingMines = 0;
        mFlagged = false;
        mIsMine= false;
        setIcon(mTileIcon);
        this.setSize(mWidth, mHieght);
        this.setVisible(true);

        this.addActionListener(e -> {

            if(mIsMine){
                setMineImage(true);
                Game_Driver.gameOver();
            }else{
                if(mSurroundingMines != 0){
                    displaySurroundingMines();
                }else{
                    this.setIcon(mPressedIcon);
                    Game_Driver.revealExpanding(x,y);
                }
            }
        });
    }

    /////////////////////////////////////////////////////////
    //Getters
    /////////////////////////////////////////////////////////

    public boolean getFlagged(){return mFlagged;}
    public Integer getMineCount(){ return mSurroundingMines;}
    public boolean getIsMine(){ return mIsMine;}

    /////////////////////////////////////////////////////////
    //Setters
    /////////////////////////////////////////////////////////

    public void setFlagged(boolean flagged){
        if(flagged){
            this.setIcon(mFlaggedIcon);
        }else{
            this.setIcon(mTileIcon);
        }

        mFlagged = flagged;
    }

    public void setMineImage(boolean show){

        if(show == true)
            this.setIcon(mMineIcon);
        else
            this.setIcon(mTileIcon);
    }

    public void setMineCount(int mineCount){
         mSurroundingMines = mineCount;
     }

    public void setIsMine(boolean isMine){ mIsMine = isMine;}

    /////////////////////////////////////////////////////////
    //METHODS
    /////////////////////////////////////////////////////////

    public void increaseMineCount(){ mSurroundingMines += 1;}

    //sets the text on the tile showing how many mines are near
    public void displaySurroundingMines(){
        //we will not display 0 for number of mines

        if(mSurroundingMines == 0){
            this.setIcon(mPressedIcon);
        }else if(mSurroundingMines > 0){
            /*ImageIcon numberIcon = new ImageIcon("Resources/Number" + Integer.toString(mSurroundingMines) + ".png ");
            this.setIcon( numberIcon );*/

            this.setIcon(null);
            this.setText(Integer.toString(mSurroundingMines));
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
                this.setEnabled(false);
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
}
