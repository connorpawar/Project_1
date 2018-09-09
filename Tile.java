import javax.swing.*;

public class Tile extends JButton {

    static final int mWidth = 20;
    static final int mHieght = 20;

    int mSurroundingMines;
    boolean mHasSurroundingMine;
    boolean mFlagged;
    boolean mIsMine;
    private int x;
    private int y;

    ImageIcon mFlaggedIcon;
    ImageIcon mMineIcon;

    //constructor
    public Tile() {
        super();

        mFlaggedIcon = new ImageIcon("americanFlagIcon.png");
        mMineIcon = new ImageIcon("MineIcon.png");
        mSurroundingMines = 0;
        mFlagged = false;
<<<<<<< HEAD
        mIsMine = false;
    }

    public Tile(boolean isMine) {
        super();

        mFlaggedIcon = new ImageIcon("americanFlagIcon.png");
        mMineIcon = new ImageIcon("MineIcon.png");
        mSurroundingMines = 0;
        mFlagged = false;
        mIsMine = false;
    }

    public void finishConstucting() {
        this.setVisible(true);
        this.setSize(mWidth, mHieght);
=======
        mIsMine= false;
        mHasSurroundingMine = false;

        this.setVisible(true);
        this.setSize(mWidth, mHieght);
    }

    public void finishConstucting(){
>>>>>>> better wokring versions of Tile and Board_Util many sections commented out on Board_Util
        //this.addActionListener(this);
    }

    //Getters
    public boolean getFlagged() {
        return mFlagged;
    }

    public int getMineCount() {
        return mSurroundingMines;
    }

    public boolean getIsMine() {
        return mIsMine;
    }

    //Setters
    public void setFlagged(boolean flagged){
        if(flagged){
            this.setIcon(mFlaggedIcon);
        }else{
            this.setIcon(null);
            this.setText("");
        }

        mFlagged = flagged;
    }

    public void setMineImage(boolean show){
        if(!this.getIsMine())
            return ;

        if(show)
            this.setIcon(mMineIcon);
        else{
            this.setIcon(null);
            this.setText("");
        }
    }

    public void setMineCount(int mineCount){
         mSurroundingMines = mineCount;
         if(mSurroundingMines > 0 )
            mHasSurroundingMine = true;
     }
    public void setIsMine(boolean isMine){ mIsMine = isMine;}

    public void increaseMineCount(){
        mSurroundingMines += 1;
        mHasSurroundingMine = true;
    }

<<<<<<< HEAD
    public void increaseMineCount() {
        mSurroundingMines += 1;
    }

    public void displaySurroundingMines() {
=======
    //sets the text on the tile showing how many mines are near
    public void displaySurroundingMines(){
        //we will not display 0 for number of mines
        if(mSurroundingMines == 0)
            return;

>>>>>>> better wokring versions of Tile and Board_Util many sections commented out on Board_Util
        String SurroundingMinesString = Integer.toString(mSurroundingMines);
        this.setText(SurroundingMinesString);
    }

    /*public void actionPerformed(ActionEvent e) {

        if(SwingUtilities.isLeftMouseButton(MouseEvent)){//TODO get MouseEvent
            if(mIsMine){
                //TODO game over
            }else{
                if(mHasSurroundingMine)
                    displaySurroundingMines();
                else
                    //revealExpanding();

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
    }
}
