import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.*;


public class Menu {

    /*Menu() Constructor
     * Constructor just calls initMenu() which
     * initializes the menu
     * */
    public Menu() {

        initMenu();
    }

    /*open()
     * @Return Void
     * Sort of like a get/set function but in this
     * case open() provides a public method to avoid
     * creating Menu Objects in any other class
     * */
    public static void open() {
        Menu menu = new Menu();
    }

    /*initMenu()
     * @Return Void
     * Creates the menu, called only by the constructor
     * */
    private void initMenu() {

    }


    /*Menu.main(String[] args)
     * Main function of Menu, just used for board
     * creation testing at the moment, will probably be
     * in future implementations.
     * */
    public static void main(String[] args) {
        /*Currently using this commented block to test the
         * Board and Menu class while an entrance function/driver class
         * has not been created/determined*/


        /* Current implementation due to the nature of gridlayout partitioning the window
         * size to the number of cells, we will make only square windows to allow the tiles
         * to be of appropriate sizing. Below is getting the screensize information and
         * using it in a pseudo error handling manner, this will eventually be moved to a
         * max input value the user can use to create a board.*/
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        int windowDimensionMax = (width <= height) ? width : height;
        System.out.println("Window is limited to: " + windowDimensionMax);
        System.out.println("Width: " + width);
        System.out.println("Height: " + height);

        JFrame menuFrame = new JFrame();
        menuFrame.setTitle("Minesweeper Setup");
        JButton menuButton = new JButton("Start");
        menuButton.setBounds(100, 250, 80, 40);


        JComboBox field_BoardSize = new JComboBox();
        field_BoardSize.setBounds(140, 108, 80, 30);
        for (int i = 2; i <= windowDimensionMax / Board.tileSize; i++) {
            field_BoardSize.addItem(i);
        }
        field_BoardSize.setVisible(true);

        JLabel field_BoardSize_label = new JLabel();
        field_BoardSize_label.setText("Board Size:");
        field_BoardSize_label.setBounds(40, 70, 100, 100);




        JLabel mineLabel = new JLabel();
        mineLabel.setText("Enter Mines :");
        mineLabel.setBounds(40, 140, 100, 100);
        JTextField mineField = new JTextField();
        mineField.setBounds(140, 178, 80, 30);
        JLabel mineErr = new JLabel();
        mineErr.setBounds(140, 200, 140, 30);

       /* menuFrame.add(rowLabel);
        menuFrame.add(rowField);
        menuFrame.add(rowErr);
        rowErr.setVisible(false);*/
        menuFrame.add(field_BoardSize_label);

        menuFrame.add(mineLabel);
        menuFrame.add(mineField);
        menuFrame.add(mineErr);
        field_BoardSize.setSelectedIndex((windowDimensionMax / Board.tileSize) - 2);
        menuFrame.add(field_BoardSize);
        mineErr.setVisible(false);
        menuFrame.add(menuButton);
        menuFrame.setSize(300, 400);
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setLayout(null);
        menuFrame.setVisible(true);
        menuFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        menuFrame.setResizable(false);

        menuButton.addActionListener(e -> {
            boolean error_exists = false;
            int userInput_BoardSize = Integer.parseInt(field_BoardSize.getEditor().getItem().toString());
            mineErr.setVisible(false);
            try {
                int mineNum = Integer.parseInt(mineField.getText());
                if(mineNum > ((userInput_BoardSize * userInput_BoardSize) - 1)){
                    error_exists = true;
                    mineErr.setText("Current Mine Max: " + ((userInput_BoardSize * userInput_BoardSize) - 1));
                    mineErr.setVisible(true);
                }
            } catch (NumberFormatException e1) {
                error_exists = true;
                mineErr.setText("Please only use numbers.");
                mineErr.setVisible(true);
            }


            if (!error_exists) {
                Board game = new Board(userInput_BoardSize, Integer.parseInt(mineField.getText()));
                menuFrame.dispose();
            }
        });

        /* board will be displayed as tile x tile board where boardsize is the tiles
         * along one side of the board, this will be changed to user input */
        int boardSize = 28;

        /* Check if board fits screen, if not print corresponding limiting factor and terminate
         * This will hopefully be used to implement a max value the user can input into the game */
        if (((boardSize * Board.tileSize) >= windowDimensionMax)) {
            if (boardSize * Board.tileSize >= height) {
                System.out.println("Window is too tall for this screen.");
            }
            if (boardSize * Board.tileSize >= width) {
                System.out.println("The window is too wide for this screen.");
            }
            //System.exit(0);
        }
    }
}
