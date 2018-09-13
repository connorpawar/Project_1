import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.lang.Exception;

/*
The below imports need to be adjusted to only import the
required classes/methods for this file, I got lazy and just
imported every class/method in awt/swing/imagio
*/
import java.awt.*;
import javax.swing.*;
import javax.imageio.*;

public class Board {
    /* Centralized location for Board constants */
    static int tileSize = 30;
    static int mNumFlags;
    static JLabel flags;

    Board(int rowLength, int colLength, int mines) {
        /* EventQueue.invokeLater() is necessary to avoid window hanging */
        EventQueue.invokeLater(() -> initGame(rowLength, colLength, mines));
    }

    private void initGame(int numCols, int numRows, int mines) {
        /*
         *  JFrame game is the board window
         *  JFrame info is the information window
         *
         */
        JFrame game = new JFrame();
        JFrame info = new JFrame();
        mNumFlags = mines;
        /*
        Below are the JFrame values being set, documented by the related
        function name used to set the JFrame characteristic
        */
        game.setTitle("Definitely not Minesweeper");
        game.setLocationRelativeTo(null);
        game.setResizable(false);
        game.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        info.setTitle("Information");
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
                Menu.open();
            }
        });

        Game_Driver gameStart = new Game_Driver(tileGrid, numRows, numCols, mines);
    }

    static public void incrementDownFlagCount(){
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

    static public void incrementUpFlagCount(){
        mNumFlags += 1;

        try {
            if (Integer.parseInt(flags.getText().replaceAll("[^\\d]", "")) == 0) {
                throw new NumberFormatException();
            }
            flags.setText("Flags Available: " + Integer.toString(mNumFlags));
        } catch (NumberFormatException e) {
            System.out.println(flags.getText());
        }
    }

    public static void main(String[] args) {
        /* Empty main() */
    }

}