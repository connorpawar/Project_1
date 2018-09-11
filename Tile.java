import javax.swing.*;
import java.awt.event.*;


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
        mIsMine= false;
        mHasSurroundingMine = false;

        this.setVisible(true);
        this.setSize(mWidth, mHieght);
    }

    public void finishConstucting(){
        this.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){

                if(mIsMine){
                    //TODO game over
                }else{
                    if(mHasSurroundingMine)
                        displaySurroundingMines();
                    else
                        //revealExpanding();

                    this.setEnabled(false);
            }
        });
    }

    //Getters
    public boolean getFlagged(){return mFlagged;}
    public Integer getMineCount(){ return mSurroundingMines;}
    public boolean getIsMine(){ return mIsMine;}
    public boolean getHasSurroundingMine(){return mHasSurroundingMine;}

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

        if(show == true)
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

    //sets the text on the tile showing how many mines are near
    public void displaySurroundingMines(){
        //we will not display 0 for number of mines
        if(mSurroundingMines == 0)
            return;

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
