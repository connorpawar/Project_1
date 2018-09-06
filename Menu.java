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
        /*Board game = new Board( 20, 20, 15 );*/
    }
}
