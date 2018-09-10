
import java.awt.event.ActionEvent;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.lang.Exception;

import java.util.Random;



/*
The below imports need to be adjusted to only import the
required classes/methods for this file, I got lazy and just
imported every class/method in awt/swing/imagio
*/
import java.awt.*;
import javax.swing.*;
import javax.imageio.*;

public class Board {

    /* Centralized location for constants */
    public static int tileSize = 30;
    private Tile[][] buttonGrid;
    private int mNumMines;
    private int numTiles;
    private Random random;


    Board(int numTiles, int mines) {
        super();
        /* EventQueue.invokeLater() is necessary to avoid window hanging */
        EventQueue.invokeLater(() -> initGame(numTiles, mines));
    }

    private void initGame(int numTiles, int mines) {

        /* Constants for board size based on 15 x 15 pixel tiles */
        final int windowSize = (numTiles * tileSize);

        /*
         *  JFrame game is the board window
         *  JFrame info is the information window
         *
         */
        JFrame game = new JFrame();
        JFrame info = new JFrame();

        /*
        Below are the JFrame values being set, documented by the related
        function name used to set the JFrame characteristic
        */
        game.setTitle("Definitely not Minesweeper");
        game.setSize(windowSize, windowSize);
        game.setLayout(new GridLayout(numTiles, numTiles));
        game.setLocationRelativeTo(null);
        game.setResizable(false);
        game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        info.setTitle("Information");
        info.setSize(200, 100);
        info.setLocationRelativeTo(game);
        info.setLayout(new GridLayout(2, 2));

        /*
         * Below is the JLabel being created to show the current number of flags
         * available to the player.
         */
        JLabel flags = new JLabel();
        try {
            Image img = ImageIO.read(getClass().getResource("Resources/flag.png"));
            flags.setIcon(new ImageIcon(img));
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
         * Below is a button being created to test the decrement of the flags JLabel
         * by clicking the JButton updateFlags, this will be implemented as an extension
         * of the Tile class.
         */
        JButton updateFlags = new JButton();
        updateFlags.addActionListener((ActionEvent event) -> {

            try {
                if (Integer.parseInt(flags.getText().replaceAll("[^\\d]", "")) == 0) {
                    throw new NumberFormatException();
                }
                flags.setText("Flags Available: " + Integer.toString(Integer.parseInt(flags.getText().replaceAll("[^\\d]", "")) - 1));
            } catch (NumberFormatException e) {
                System.out.println(flags.getText());
            }
        });

        flags.setText("Flags Available: " + Integer.toString(mines));

        info.add(updateFlags);
        info.add(flags);
        info.setResizable(false);
        info.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        /*
        This adds the tiles to the grid, in this case the number
        of tiles will be the tileRow * tileHeight. The tile icon will be
        changed throughout the coding process, for now Number1.png is just
        an example icon I made in microsoft paint to display in the grid.
        */
        buttonGrid = new Tile[numTiles][numTiles];

        try {
//          Image img = ImageIO.read(getClass().getResource("Resources/Number1.png"));
            for (int i = 0; i < numTiles; i++) {
                for (int j = 0; j < numTiles; j++) {
                    buttonGrid[i][j] = new Tile();
                    buttonGrid[i][j].setMargin(new Insets(0, 0, 0, 0));
//                  newButton.setIcon( new ImageIcon(img) );
                    buttonGrid[i][j].setText(Integer.toString(i + j));
                    buttonGrid[i][j].setPreferredSize(new Dimension(tileSize, tileSize));
                    game.add(buttonGrid[i][j]);
                }
            }
        } catch (Exception ex) {
            System.out.println("Problem with loading Resources/Number1.png");
        }

        game.validate();
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
                Menu.open();
            }
        });
        initBoard();
    }

    public static void main(String[] args) {
        /* Empty main() */

    }

    /*
    * Initializes the board for buttonGrid by cleaning all of the tiles, then calls
    * placeMines which randomly sets in the desired amount of mines,
    * it then will set the risk of nearby mines
    */
    public void initBoard() {
        for (int i = 0; i < numTiles; i++) {
            for (int j = 0; j < numTiles; j++) {
                buttonGrid[i][j].cleanTile();
            }
        }
        placeMines();
        setRiskNum();
    }

    /*
    * Places all of the mines to mNumMines
    * it calls setMine to be placed randomly
    */
    private void placeMines() {
        for (int i = 0; i < mNumMines; i++) {
            setMine();
        }
    }

    /*
    * setMine() is a helper function of placeMines()
    * randomly places a mine inside the board
     */
    private void setMine() {
        int x = random.nextInt(numTiles);
        int y = random.nextInt(numTiles);

        if (!buttonGrid[x][y].getIsMine()) {
            buttonGrid[x][y].setIsMine(true);
        } else {
            setMine();
        }
    }

    /*
    * setRiskNum() accesses the number of mines around a tile
     */
    private void setRiskNum() {
        for (int i = 0; i < numTiles; i++) {
            for (int j = 0; j < numTiles; j++) {
                int leftOne = i - 1;
                int rightOne = i + 1;
                int downOne = j - 1;
                int upOne = j + 1;

                int mineRisk = 0;

                if (leftOne >= 0 && downOne >= 0 && buttonGrid[leftOne][downOne].getIsMine())
                    mineRisk++;
                if (leftOne >= 0 && buttonGrid[leftOne][j].getIsMine())
                    mineRisk++;
                if (leftOne >= 0 && upOne < numTiles && buttonGrid[leftOne][upOne].getIsMine())
                    mineRisk++;
                if (downOne >= 0 && buttonGrid[i][downOne].getIsMine())
                    mineRisk++;
                if (upOne < numTiles && buttonGrid[i][upOne].getIsMine())
                    mineRisk++;
                if (rightOne < numTiles && buttonGrid[rightOne][downOne].getIsMine())
                    mineRisk++;
                if (rightOne < numTiles && buttonGrid[rightOne][j].getIsMine())
                    mineRisk++;
                if (rightOne < numTiles && upOne < numTiles && buttonGrid[rightOne][upOne].getIsMine())
                    mineRisk++;

                buttonGrid[i][j].setMineCount(mineRisk);
                System.out.print(mineRisk);
            }
        }
    }

}