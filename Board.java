/*Board.java -- Meant to initialize the board and pass related values to Game_Driver
 *
 * initGame(int, int, int) -- Creates the board, info window, and passes to Game_Driver
 * incrementFlagCount() -- increments the flagCount of the JLabel
 * decrementFlagCount() -- decrements the flagCount of the JLabel
 *
 * getInfoFrame() -- returns the JFrame that stores info about the flags
 * getFlagCount() -- returns mNumFlags
 * main() -- Empty
 * */

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

public class Board {
    /* Centralized location for Board variables */
    static int tileSize = 30;
    private static int mNumFlags;
    private static JLabel flags;
    private static JFrame info;
    private static JFrame game;

    /////////////////////////////////////////////////////////
    //Constructor
    /////////////////////////////////////////////////////////
    Board(int rowLength, int colLength, int mines) {
        /* EventQueue.invokeLater() is necessary to avoid window hanging */
        EventQueue.invokeLater(() -> initGame(rowLength, colLength, mines));
    }

    /////////////////////////////////////////////////////////
    //Methods
    /////////////////////////////////////////////////////////

    /*initGame(int numCols, int numRows, int mines)
     * @Return Void
     * @numCols Number of columns which is related to the length of a row
     * @numRows Number of rows which is related to the length of a column
     * @mines Number of mines in the board
     *
     * Creates the board and the information box based on the given
     * variables. Passes relevant information to a Game_Driver object
     * to allow the player to play the game.
     * */
    private void initGame(int numCols, int numRows, int mines) {
        /*
         *  JFrame game is the board window
         *  JFrame info is the information window
         */
        game = new JFrame();
        info = new JFrame();
        mNumFlags = mines;

        /*
        Below are the JFrame values being set, documented by the related
        function name used to set the JFrame characteristic
        */
        game.setTitle("Definitely not Minesweeper");

        /*
         * Because a bigger board placed in the center of the screen would go
         * off screen, this setup below limits the placement of the game window
         * which is oriented by the top left of its frame to the upper left quadrant
         * of the users monitor. This will then move further up and further left
         * as the board increases in respective size.
         * */
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        int xOffset = width / 2 - (numRows * 15);
        int yOffset = height / 2 - (numCols * 15);
        game.setLocation(xOffset, yOffset);

        game.setResizable(false);
        game.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        info.setTitle("Information");
        info.setAlwaysOnTop(true);
        info.setSize(200, 100);
        info.setLocationRelativeTo(game);
        info.setLayout(new GridLayout(2, 2));

        /*
         * Below is the JLabel being created to show the current number of flags
         * available to the player.
         */
        flags = new JLabel();
        flags.setText("Flags Available: " + Integer.toString(mines));
        try {
            Image img = ImageIO.read(getClass().getResource("Resources/flag.png"));
            flags.setIcon(new ImageIcon(img));
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
         * Below is a button being created to test the decrement of the flags JLabel
         * by clicking the Tile updateFlags, this will be implemented as an extension
         * of the Tile class.
         */
        JButton updateFlags = new JButton();
        updateFlags.setText("RESTART");
        updateFlags.addActionListener((ActionEvent event) -> {
            game.dispose();
            info.dispose();
            Board newgame = new Board(numCols, numRows, mines);
        });

        info.add(updateFlags);
        info.add(flags);
        info.setResizable(false);
        info.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

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
            game.add(masterPanel);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        game.validate();
        game.pack();
        game.setVisible(true);
        info.validate();
        info.setVisible(true);

        /*
        This WindowListener has an Overridden windowClosing event that allows
        the function Menu.open() to get called on the Board window closing.
        */
        game.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                info.dispose();
                if (game.getDefaultCloseOperation() == WindowConstants.DISPOSE_ON_CLOSE) {
                    Menu.open();
                }
            }
        });

        Game_Driver gameStart = new Game_Driver(game, tileGrid, numRows, numCols, mines);
    }

    /*incrementFlagCount()
     * @Return Void
     *
     * Takes the information stored in the flags JLabel
     * and increments it and updates the Label
     * */
    static void incrementFlagCount() {
        mNumFlags += 1;

        try {
            if (Integer.parseInt(flags.getText().replaceAll("[^\\d]", "")) == mNumFlags) {
                throw new NumberFormatException();
            }
            flags.setText("Flags Available: " + Integer.toString(mNumFlags));
        } catch (NumberFormatException e) {
            System.out.println(flags.getText());
        }
    }

    /*decrementFlagCount()
     * @Return Void
     *
     * Takes the information stored in the flags JLabel
     * and decrements it and updates the Label
     * */
    static void decrementFlagCount() {
        mNumFlags -= 1;
        try {
            if (Integer.parseInt(flags.getText().replaceAll("[^\\d]", "")) == 0) {
                throw new NumberFormatException();
            }
            flags.setText("Flags Available: " + Integer.toString(mNumFlags));
        } catch (NumberFormatException e) {
            System.out.println(flags.getText());
        }
    }


    /////////////////////////////////////////////////////////
    //Getters
    /////////////////////////////////////////////////////////
    static JFrame getInfoFrame() {
        return info;
    }

    static int getFlagCount() {
        return mNumFlags;
    }

    /////////////////////////////////////////////////////////
    //Main
    /////////////////////////////////////////////////////////
    public static void main(String[] args) {
        /* Empty main() */
    }
}