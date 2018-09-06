import java.awt.EventQueue;
import java.awt.GridLayout;
import javax.swing.*;

public class Board {

    public Board( int row, int height, int mines ) {
        /* EventQueue.invokeLater() is necessary to avoid window hanging */
        EventQueue.invokeLater(() -> {
            initGame( row, height, mines );
        });
    }

    private void initGame( int tileRow, int tileHeight, int mines ) {

        /* Constants for board size based on 15 x 15 pixel tiles */
        final int rowSize = ( tileRow * 20 );
        final int rowHeight = ( tileHeight * 20 );

        /* JFrame game is the board window */
        JFrame game = new JFrame();

        /*
        Below are the JFrame values being set, documented by the related
        function name used to set the JFrame characteristic
        */
        game.setTitle( "Definitely not Minesweeper" );
        game.setSize( rowSize, rowHeight );
        game.setLayout( new GridLayout( tileRow, tileHeight ) );
        game.setLocationRelativeTo( null );
        game.setResizable( false );
        game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE );
        game.setVisible( true );

        /*
        This adds the tiles to the grid, in this case the number
        of tiles will be the tileRow * tileHeight.
        */
        for(int i = 0; i < tileRow * tileHeight; i++){
            game.add( new JButton( "x" ) );
        }

        /*
        This WindowListener has an Overridden windowClosing event that allows
        the function Menu.open() to get called on the Board window closing.
        */
        game.addWindowListener( new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing( java.awt.event.WindowEvent e ){
                Menu.open();
            }
        });
    }

    public static void main(String[] args) {
        /* Empty main() */
    }
}