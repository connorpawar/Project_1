/* Menu.java -- Creates the menu that takes user input, entry point of minesweeper game.
 *
 * open() -- creates a new menu object
 * initMenu() -- called by the constructor of menu, creates all relevant input fields for user
 * */

//Swing imports
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
//AWT imports
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Toolkit;

public class Menu {

    /*Menu() Constructor
     * Constructor just calls initMenu() which
     * initializes the menu
     * */
    private Menu() {

        initMenu();
    }

    /*open()
     * @Return Void
     *
     * Sort of like a get/set function but in this
     * case open() provides a public method to avoid
     * creating Menu Objects in any other class
     * */
    static void open() {
        Menu menu = new Menu();
    }

    /*initMenu()
     * @Return Void
     *
     * Creates the menu, called only by the constructor
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
        field_colLength.setBounds(140, 78, 80, 30);
        for (int i = 2; i <= max_numCols - 1; i++) {
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
        field_rowLength.setBounds(140, 108, 80, 30);
        for (int i = 2; i <= max_numRows - 2; i++) {
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
        mineField.setBounds(140, 178, 80, 30);
        JLabel mineErr = new JLabel();
        mineErr.setBounds(40, 200, 200, 30);
        mineErr.setVisible(false);
        mineErr.setForeground(Color.red);

        /*
         * Add components to menu frame
         */
        menuFrame.add(field_rowLength_label);
        menuFrame.add(field_colLength_label);
        menuFrame.add(mineLabel);
        menuFrame.add(mineField);
        menuFrame.add(mineErr);
        field_rowLength.setSelectedIndex(max_numRows - 4);
        field_colLength.setSelectedIndex(max_numCols - 3);
        menuFrame.add(field_rowLength);
        menuFrame.add(field_colLength);
        menuFrame.add(menuButton);

        /*
         * Set various menuFrame properties
         */
        menuFrame.setSize(300, 400);
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

            mineErr.setVisible(false);
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


            if (!error_exists) {
                Board game = new Board(numRows, numCols, Integer.parseInt(mineField.getText()));
                menuFrame.dispose();
            }
        });
    }

    /////////////////////////////////////////////////////////
    //Main
    /////////////////////////////////////////////////////////
    public static void main(String[] args) {
        new Tile().setIcons();
        Menu startGame = new Menu();
    }

}
