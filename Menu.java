import java.awt.*;

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
        System.out.println("initMenu()");
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
        System.out.println("Width: " + width);
        System.out.println("Height: " + height);

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
            System.exit(0);
        }

        Board game = new Board(boardSize, 15);
    }
}
