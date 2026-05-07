package chessboard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import piecelogic.*;

public class ChessboardGui extends JPanel {

    private static final int TILE_SIZE = 80;

    private ChessboardLogic chessboardLogic;

    private Image wPawn, wKnight, wBishop, wRook, wQueen, wKing,
            bPawn, bKnight, bBishop, bRook, bQueen, bKing;

    public ChessboardGui(){
        //background color around the chessboard
        setBackground(Color.GRAY);
        loadPieces();
        System.out.println("ChessboardGui was created ! ");
    }

    public void setChessboardLogic(ChessboardLogic chessboardLogic) {
        this.chessboardLogic = chessboardLogic;
    }

    public ChessboardLogic getChessboardLogic() {
        return chessboardLogic;
    }

    public void loadPieces() {
        try {
            //white
            wPawn = new ImageIcon(getClass().getResource("/pieceimages/white/white-pawn.png")).getImage();
            wKnight = new ImageIcon(getClass().getResource("/pieceimages/white/white-knight.png")).getImage();
            wBishop = new ImageIcon(getClass().getResource("/pieceimages/white/white-bishop.png")).getImage();
            wRook = new ImageIcon(getClass().getResource("/pieceimages/white/white-rook.png")).getImage();
            wQueen = new ImageIcon(getClass().getResource("/pieceimages/white/white-queen.png")).getImage();
            wKing = new ImageIcon(getClass().getResource("/pieceimages/white/white-king.png")).getImage();

            //black
            bPawn = new ImageIcon(getClass().getResource("/pieceimages/black/black-pawn.png")).getImage();
            bKnight = new ImageIcon(getClass().getResource("/pieceimages/black/black-knight.png")).getImage();
            bBishop = new ImageIcon(getClass().getResource("/pieceimages/black/black-bishop.png")).getImage();
            bRook = new ImageIcon(getClass().getResource("/pieceimages/black/black-rook.png")).getImage();
            bQueen = new ImageIcon(getClass().getResource("/pieceimages/black/black-queen.png")).getImage();
            bKing = new ImageIcon(getClass().getResource("/pieceimages/black/black-king.png")).getImage();
        } catch (Exception e) {
            throw new RuntimeException("Image loading failed", e);
        }
        System.out.println("ChessboardGui.loadPieces method was called ! ");
    }


    @Override
    protected void paintComponent(Graphics g) {

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
                    int y = row * TILE_SIZE + (TILE_SIZE * 9 / 10 );

                    char file = (char) ('a' + col);
                    String label = ""+file;
                    g2d.drawString(label, x, y);
                }

                if (col == 0) {
                    //top left corner in the square
                    int x = col * TILE_SIZE + 5;
                    int y = row * TILE_SIZE + fm.getAscent()+5;
                    
                    int rank = 8 - row;
                    String label = ""+rank;
                    g2d.drawString(label, x, y);
                }
            
            }
                        
        }

        //draw the pieces
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = chessboardLogic.chessboard[row][col];
                Image pieceIcon = getPieceImage(piece);

                if (pieceIcon != null) {

                    g2d.drawImage(
                            pieceIcon,
                            col * TILE_SIZE,
                            row * TILE_SIZE,
                            TILE_SIZE-5,
                            TILE_SIZE-5,
                            null
                    );
                }
            }

        }
        g2d.translate(-offsetX, -offsetY);
        System.out.println("painting the chessboardGui");
    }

    public Image getPieceImage(Piece piece){

        System.out.println("chessboardGui.getPieceImage was called ! ");
        return switch(piece){
            case Pawn pawn -> piece.isWhite()  ? wPawn : bPawn;
            case Knight knight -> piece.isWhite() ? wKnight : bKnight;
            case Bishop bishop -> piece.isWhite() ? wBishop : bBishop;
            case Rook rook -> piece.isWhite() ? wRook : bRook;
            case Queen queen -> piece.isWhite() ? wQueen : bQueen;
            case King king -> piece.isWhite() ? wKing : bKing;
            case null, default -> null;

        };

    }

    public void showGame(){
        JFrame frame = new JFrame("Chessboard");
        frame.setLayout(new BorderLayout());

        this.setPreferredSize(new Dimension(8*TILE_SIZE, 8*TILE_SIZE));

        frame.add(this, BorderLayout.CENTER);
        frame.setResizable(true);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        System.out.println("chessboardGui.showGame() was called ! ");
    }

    public static void main(String[] args){

        ChessboardLogic chessboardLogic = new ChessboardLogic();
        chessboardLogic.newGame();
        chessboardLogic.getChessboardGui().showGame();
        
    }




}
