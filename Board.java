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

    Board(int numTiles, int mines) {
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
       JButton buttonGrid[][] = new JButton[numTiles][numTiles];

        try {
//          Image img = ImageIO.read(getClass().getResource("Resources/Number1.png"));
            for (int i = 0; i < numTiles; i++) {
                for (int j = 0; j < numTiles; j++) {
                    buttonGrid[i][j] = new JButton();
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
    }

    public static void main(String[] args) {
        /* Empty main() */
    }
}
