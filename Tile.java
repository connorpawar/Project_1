import javax.swing.*;

public class Tile extends JButton{

    static final int mWidth = 20;
    static final int mHieght = 20;

    int mSurroundingMines;
    boolean mFlagged;
    boolean mIsMine;

    ImageIcon mFlaggedIcon;
    ImageIcon mMineIcon;

    //constructor
    public Tile(){
        super();

        mFlaggedIcon = new ImageIcon("americanFlagIcon.png");
        mMineIcon = new ImageIcon("MineIcon.png");
        mSurroundingMines = 0;
        mFlagged = false;
        mIsMine= false;
    }

    public Tile(boolean isMine){
        super();

        mFlaggedIcon = new ImageIcon("americanFlagIcon.png");
        mMineIcon = new ImageIcon("MineIcon.png");
        mSurroundingMines = 0;
        mFlagged = false;
        mIsMine= false;
    }

    public void finishConstucting(){
        this.setVisible(true);
        this.setSize(mWidth, mHieght);
        //this.addActionListener(this);
    }

    //Getters
    public boolean getFlagged(){return mFlagged;}
    public int getMineCount(){ return mSurroundingMines;}
    public boolean getIsMine(){ return mIsMine;}

    //Setters
    public void setFlagged(boolean flagged){
        if(flagged){
            this.setIcon(mFlaggedIcon);
        }else{
            this.setIcon(null);
            //this.setText("");
        }

        mFlagged = flagged;
    }

    public void setMineImage(boolean show){
        if(!this.getIsMine())
            return ;
        //TODO find an icon for a mine

        if(show)
            this.setIcon(mMineIcon);
        else
            this.setIcon(null);
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
