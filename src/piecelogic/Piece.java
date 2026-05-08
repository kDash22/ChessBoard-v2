package piecelogic;

import java.util.ArrayList;
import java.util.List;

public abstract class Piece {
    List<int[]> moveSet = new ArrayList<>();
    protected int[][] validMoveSet;

    private PieceId id;

    protected final char originalChessCol;
    protected final int originalChessRow;

    private char chessCol;
    private int chessRow;

    protected Piece[][] chessboard;

    public static final List<Character> COLUMN_LETTERS = List.of('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h');

    protected Piece(char originalChessCol, int originalChessRow) {
        this.originalChessCol = originalChessCol;
        this.originalChessRow = originalChessRow;
    }

    //setters
    public void setChessCol(char chessCol){
        Character chessColObj = chessCol;
        if (COLUMN_LETTERS.contains(chessColObj)) {
            this.chessCol = chessCol;
        } else {
            throw new IllegalArgumentException("Chess column letter not valid ! : "+chessCol);
        }
            
    }

    public void setChessRow(int chessRow){
        if (chessRow > 0 && chessRow <= 8) {
            this.chessRow = chessRow;
        } else {
            throw new IllegalArgumentException("Chess row not valid ! : "+chessRow);
        }
    }

    public void setID(PieceId id){
        this.id = id;
    }

    public void setChessboard(Piece[][] chessboard) {
        this.chessboard = chessboard;
    }

    //getters
    public char getChessCol(){
        return chessCol;
    }

    public int getChessRow(){
        return chessRow;
    }

    public char getOriginalChessCol() {
        return originalChessCol;
    }

    public int getOriginalChessRow() {
        return originalChessRow;
    }

    public List<int[]> getMoveSet() {
        return moveSet;
    }

    public int[][] getValidMoveSet() {
        return validMoveSet;
    }

    public PieceId getId(){
        return id;
    }

    public Piece[][] getChessboard() {
        return chessboard;
    }

    // a method used to convert column letter into int to be used in arrays
    public static int chessColToIndex(Character chessCol) {
        if (!COLUMN_LETTERS.contains(chessCol)) {
            throw new IllegalArgumentException(" COLUMN LETTER NOT VALID ! : " + chessCol);
        }
        return switch (chessCol) {
            case 'a' -> 0;
            case 'b' -> 1;
            case 'c' -> 2;
            case 'd' -> 3;
            case 'e' -> 4;
            case 'f' -> 5;
            case 'g' -> 6;
            case 'h' -> 7;
            default -> -1;
        };
    }

    // a method used to convert array col number to chess column number
    public static char colToChessCol(int col) {
        if (col > 7 || col < 0)
            throw new IllegalArgumentException(" Array Column number must be between 0 and 7 ! :" + col);
        return switch (col) {
            case 0 -> 'a';
            case 1 -> 'b';
            case 2 -> 'c';
            case 3 -> 'd';
            case 4 -> 'e';
            case 5 -> 'f';
            case 6 -> 'g';
            case 7 -> 'h';
            default -> 'x';
        };
    }

    // a method used to convert chess rows into int to be used in arrays
    public static int chessRowToIndex(int chessRow) {
        if (chessRow < 1 || chessRow > 8) {
            throw new IllegalArgumentException("chessRow must be between 1 and 8: " + chessRow);
        }
        return 8 - chessRow;
    }

    // a method used to convert chess rows into int to be used in arrays
    public static int rowToChessRow(int row) {
        if (row < 0 || row > 7) {
            throw new IllegalArgumentException("Array row number must be between 0 and 7: " + row);
        }
        return 8 - row;
    }

    public boolean isWhite(){
        return getId().isWhite();
    }

    public boolean isKing(){
        return getId() == PieceId.W_KING ||
                getId() == PieceId.B_KING;
    }

    public void updateCoords(char newChessCol, int newChessRow){
        setChessRow(newChessRow);
        setChessCol(newChessCol);

    }

    public boolean hasMoved(){
        return originalChessRow != chessRow || originalChessCol != chessCol;
    }

    public abstract void moveCheck();
}
