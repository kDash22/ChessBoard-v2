import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class chessboardGui extends JPanel {

    private static final int TILE_SIZE = 80;

    public chessboardGui(){
        //background color around the chessboard
        setBackground(Color.GRAY);
    }

    @Override
    protected void paintComponent(Graphics g) {

        for(int i = -1; ++i < 20;){

        }
        
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        //set the font
        g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        FontMetrics fm = g2d.getFontMetrics();

        
        int boardSize = 8*TILE_SIZE;

        int offsetX = (getWidth()-boardSize) / 2;
        int offsetY = (getHeight()-boardSize) / 2;

        //dynamically shifts the origin point of the board
        g2d.translate(offsetX, offsetY);

        for(int row = 0; row < 8; row++){
            for(int col = 0; col <8; col++){

                if ((row + col) % 2 == 0) g2d.setColor(Color.LIGHT_GRAY);
                else g2d.setColor(Color.DARK_GRAY);

                g2d.fillRect(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);

                // force visibility
                if ((row + col) % 2 == 0) g2d.setColor(Color.BLACK);
                else g2d.setColor(Color.WHITE);

                //print first chessRow and first chessCol only
                if (row == 7 ) {
                    //bottom right corner in the square
                    int x =  col * TILE_SIZE + (TILE_SIZE * 8 / 10) ;
                    //int y = row * TILE_SIZE + fm.getAscent();
                    int y = row * TILE_SIZE + (TILE_SIZE * 9 / 10 );

                    char file = (char) ('a' + col);
                    String labe1 = ""+file;
                    g2d.drawString(labe1, x, y);
                }

                if (col == 0) {
                    //top left corner in the square
                    int x = col * TILE_SIZE + 5;
                    int y = row * TILE_SIZE + fm.getAscent()+5;
                    
                    int rank = 8 - row;
                    String labe1 = ""+rank;
                    g2d.drawString(labe1, x, y);
                }
                

                

                

                

            }
                        
        }
        g2d.translate(-offsetX, -offsetY);
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("Chessboard");
        frame.setLayout(new BorderLayout());

        chessboardGui chessboardGui = new chessboardGui();
        chessboardGui.setPreferredSize(new Dimension(8*TILE_SIZE, 8*TILE_SIZE));
        
        frame.add(chessboardGui, BorderLayout.CENTER);
        frame.setResizable(true);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
    }
        


}
