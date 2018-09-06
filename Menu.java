import java.awt.*;

public class Menu {

    /*Menu() Constructor
    * Constructor just calls initMenu() which
    * initializes the menu
    * */
    public Menu(){
        initMenu();
    }

    /*open()
    * @Return Void
    * Sort of like a get/set function but in this
    * case open() provides a public method to avoid
    * creating Menu Objects in any other class
    * */
    public static void open(){
        Menu menu = new Menu();
    }

    /*initMenu()
    * @Return Void
    * Creates the menu, called only by the constructor
    * */
    private void initMenu(){
        System.out.println("initMenu()");
    }

    public static void main(String[] args){
        /*Currently using this commented block to test the
        * Board and Menu class while an entrance function/driver class
        * has not been created/determined*/


        /*Row and Col represent the board width x height the user will
        * enter, the if statements immediately below them are an
        * example of using the user screenSize to respond to their
        * input. If we have 20x20 pixel tiles and they want a 30 x 20
        * board, check their height and width to see if their screenSize width
        * and height is greater than the input board size.*/
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)screenSize.getWidth();
        int height = (int)screenSize.getHeight();
        System.out.println( "Width: " + width );
        System.out.println( "Height: " + height );

        int row = 100;
        int col = 50;

        if( row * 20 >= height ){ System.exit(0); }
        if( col * 20 >= width ){ System.exit(0); }
        Board game = new Board( row, col, 15 );
    }
}
