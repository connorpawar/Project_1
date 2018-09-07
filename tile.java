import javax.swing.*;

public class tile extends JButton{

    static final int mWidth = 20;
    static final int mHieght = 20;

    int mSurroundingMines;
    boolean mFlagged;
    boolean mIsMine;

    ImageIcon mFlaggedIcon;

    //constructor
    public tile(){
        super();

        mFlaggedIcon = new ImageIcon();//TODO new to find flagged image
        mSurroundingMines = 0;
        mFlagged = false;
        mIsMine= false;
    }

    public tile(boolean isMine){
        super();

        mFlaggedIcon = new ImageIcon();//TODO new to find flagged image
        mSurroundingMines = 0;
        mFlagged = false;
        mIsMine= false;
    }

    public void finishConstucting(){
        this.setVisible(true);
        //tile.setSize(mWidth, mHieght);
        //this.addActionListener(this);
    }

    //Getters
    public boolean getFlagged(){return mFlagged;}
    public int getMineCount(){ return mSurroundingMines;}
    public boolean getIsMine(){ return mIsMine;}

    //Setters
    public void setFlagged(boolean flagged){
        if(flagged){
            //this.setIcon();
        }else{
            this.setText("");
        }

        mFlagged = flagged;
    }
    public void setMineCount(int mineCount){ mSurroundingMines = mineCount;}
    public void setIsMine(boolean isMine){ mIsMine = isMine;}


    public void increaseMineCount(){mSurroundingMines += 1;}

    public void displaySurroundingMines(){
        String SurroundingMinesString = Integer.toString(mSurroundingMines);
        this.setText(SurroundingMinesString);
    }

    /*public void actionPerformed(ActionEvent e) {

        if(SwingUtilities.isLeftMouseButton(MouseEvent)){//TODO get MouseEvent
            if(mIsMine){
                //TODO game over
            }else{
                displaySurroundingMines();
                this.setEnabled(false);
            }
        }else if(SwingUtilities.isRightMouseButton(MouseEvent)){
            setFlagged(!mFlagged);
        }
    }*/
}
