package minesweeper;

//Swing imports
import javax.swing.*;
//AWT imports
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Toolkit;

/**
 * Creates the menu that the user inputs information into and then verifies input
 * and passes the input over to {@link Board} as well as calls {@link Tile#setIcons()} to set the
 * static icon variables in the {@link Tile} class, the entry point of Minesweeper.
 * */
public class Menu {

    /**
     * Constructor just calls initMenu() which
     * initializes the menu
     *
     * @ms.Pre-condition No guarantees are made before this function is called
     * @ms.Post-condition Calls the function that creates the menu
     * */
    private Menu() {

        initMenu();
    }

    /**
     * Provides a public method to avoid
     * creating Menu Objects in any other class
     *
     * @ms.Pre-condition The board, winFrame, or loseFrame are disposed
     * @ms.Post-condition Calls the constructor to create a new menu for the user to create a new board
     * */
    static void open() {
        Menu menu = new Menu();
    }

    /**
     * Creates the menu, which takes in the user's input, verifies it,
     * and passes it to {@link Board}
     *
     * @ms.Pre-condition No guarantees are made before this function is called
     * @ms.Post-condition Creates a JFrame where users can input their preferred rows, columns and mines
     *                    while confirming the inputs are valid and passing them to Board class
     * */
    private void initMenu() {

        /*
         * Used to get screen boundaries from users screen settings and limits
         * board size to values pulled.
         * */
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        int max_numRows = height / Board.tileSize;
        int max_numCols = width / Board.tileSize;

        JFrame menuFrame = new JFrame();
        menuFrame.setTitle("Minesweeper Setup");
        JButton menuButton = new JButton("Start");
        menuButton.setBounds(100, 250, 80, 40);

        /*
         * Column combobox generation along with associated label
         */
        JComboBox<Integer> field_colLength = new JComboBox<>();
        field_colLength.setBounds(240, 78, 80, 30);
        for (int i = 2; i <= max_numCols - 4; i++) {
            field_colLength.addItem(i);
        }
        field_colLength.setVisible(true);

        JLabel field_colLength_label = new JLabel();
        field_colLength_label.setText("Columns:");
        field_colLength_label.setBounds(40, 40, 100, 100);

        /*
         * Row combobox generation along with associated label
         */
        JComboBox<Integer> field_rowLength = new JComboBox<>();
        field_rowLength.setBounds(240, 108, 80, 30);
        for (int i = 2; i <= max_numRows - 4; i++) {
            field_rowLength.addItem(i);
        }
        field_rowLength.setVisible(true);

        JLabel field_rowLength_label = new JLabel();
        field_rowLength_label.setText("Rows:");
        field_rowLength_label.setBounds(40, 70, 120, 100);



        /*
         * Mine text field generation along with associated labels
         */
        JLabel mineLabel = new JLabel();
        mineLabel.setText("Enter Mines :");
        mineLabel.setBounds(40, 140, 100, 100);
        JTextField mineField = new JTextField();
        mineField.setText("0");
        mineField.setBounds(240, 178, 80, 30);
        JLabel mineErr = new JLabel();
        mineErr.setBounds(40, 200, 200, 30);
        mineErr.setVisible(false);
        mineErr.setForeground(Color.red);

        /*
         * Creates turns until mines change combobox
         */
        JComboBox<Integer> field_changemine = new JComboBox<>();
        field_changemine.setBounds(240, 138, 80, 30);
        for (int i = 0; i <= 5; i++) {
            field_changemine.addItem(i);
        }
        field_changemine.setVisible(true);

        JLabel field_changemine_label = new JLabel();
        field_changemine_label.setText("<html>Turns Until Mines Change:<br>(0 for normal minesweeper)</html>");
        field_changemine_label.setBounds(40, 100, 250, 100);

        /*
         * Add components to menu frame
         */
        menuFrame.add(field_rowLength_label);
        menuFrame.add(field_colLength_label);
        menuFrame.add(field_changemine_label);
        menuFrame.add(mineLabel);
        menuFrame.add(mineField);
        menuFrame.add(mineErr);
        field_rowLength.setSelectedIndex(max_numRows-6);
        field_colLength.setSelectedIndex(max_numCols-6);
        menuFrame.add(field_rowLength);
        menuFrame.add(field_colLength);
        menuFrame.add(field_changemine);
        menuFrame.add(menuButton);

        /*
         * Set various menuFrame properties
         */
        menuFrame.setSize(370, 400);
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setLayout(null);
        menuFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        menuFrame.setResizable(false);
        menuFrame.setVisible(true);

        /*
         * Action listener that controls the Start button functionality
         */
        menuButton.addActionListener(e -> {
            boolean error_exists = false;
            int numRows = (Integer) field_rowLength.getSelectedItem();
            int numCols = (Integer) field_colLength.getSelectedItem();
            int numTurns = (Integer) field_changemine.getSelectedItem();

            mineErr.setVisible(false);
            /*
             * Try and catch to prevent the function from passing anything that
             * is not a number
             */
            try {
                int mineNum = Integer.parseInt(mineField.getText());
                if (mineNum > ((numRows * numCols) - 1)) {
                    error_exists = true;
                    mineErr.setText("Current Mine Max: " + ((numRows * numCols) - 1));
                    mineErr.setVisible(true);
                }
                if (mineNum < 1) {
                    error_exists = true;
                    mineErr.setText("Current Mine Min: 1");
                    mineErr.setVisible(true);
                }

            } catch (NumberFormatException e1) {
                error_exists = true;
                mineErr.setText("Numbers only.");
                mineErr.setVisible(true);
            }

            /*
             * If no errors have been found in the inputs, the Board constructor is called
             * and the valid parameters are passed through
             */
            if (!error_exists) {
                Board game = new Board(numRows, numCols, numTurns, Integer.parseInt(mineField.getText()));
                menuFrame.dispose();
            }
        });
    }

    /////////////////////////////////////////////////////////
    //Main
    /////////////////////////////////////////////////////////
    /**
     * Main calls the constructor for {@link #Menu()}
     *
     * @param args Never used due to this class being the entry point of the function.
     *
     * @ms.Pre-condition No guarantees are made before this function is called
     * @ms.Post-condition Constructor is called
     */
    public static void main(String[] args) {
        new Tile().setIcons();
        Menu startGame = new Menu();
    }

}
