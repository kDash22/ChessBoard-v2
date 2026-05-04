import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class chessboardGui extends JPanel {

    private final int TILE_SIZE = 80;

    @Override
    protected void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        //background color around the chessboard
        setBackground(Color.GRAY);
        
        
        int boardSize = 8*TILE_SIZE;

        int offsetX = (getWidth()-boardSize) / 2;
        int offsetY = (getHeight()-boardSize) / 2;

        //dynamically shifts the origin point of the board
        g2d.translate(offsetX, offsetY);

        for(int row = 0; row < 8; row++){
            for(int col = 0; col <8; col++){

                if ((row+col) % 2 == 0) {
                    g2d.setColor(Color.LIGHT_GRAY);
                } else {
                    g2d.setColor(Color.DARK_GRAY);
                }

                if (row + col == 0) {
                    g2d.setColor(Color.PINK);
                }

                g2d.fillRect(col* TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);

            }
                
        }
        
        

    }

    void main(){
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
