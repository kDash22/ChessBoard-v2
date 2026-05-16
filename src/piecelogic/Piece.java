package piecelogic;

import chessboard.ChessboardLogic;

import java.util.ArrayList;
import java.util.List;

public abstract class Piece {
    List<int[]> moveSet = new ArrayList<>();
    protected int[][] validMoveSet;

    protected final char originalFile;
    protected final int originalChessRow;

    private char file;
    private int chessRow;

    private boolean isWhite;
    private PieceType pieceType;

    public static final List<Character> COLUMN_LETTERS = List.of('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h');

    protected Piece(boolean isWhite, PieceType pieceType, char originalFile, int originalChessRow) {

        Character fileObj = originalFile;
        this.isWhite = isWhite;
        this.pieceType = pieceType;
        if (COLUMN_LETTERS.contains(fileObj)) {
            this.originalFile = originalFile;
        } else {
            throw new IllegalArgumentException("Chess column letter not valid ! : "+ originalFile);
        }

        if (originalChessRow > 0 && originalChessRow <= 8) {
            this.originalChessRow = originalChessRow;
        } else {
            throw new IllegalArgumentException("Chess row not valid ! : "+originalChessRow);
        }
        ;

    }

    //setters
    public void setFile(char file){
        Character fileObj = file;
        if (COLUMN_LETTERS.contains(fileObj)) {
            this.file = file;
        } else {
            throw new IllegalArgumentException("Chess column letter not valid ! : "+file);
        }
            
    }

    public void setChessRow(int chessRow){
        if (chessRow > 0 && chessRow <= 8) {
            this.chessRow = chessRow;
        } else {
            throw new IllegalArgumentException("Chess row not valid ! : "+chessRow);
        }
    }

    //getters
    public char getFile(){
        return file;
    }

    public int getChessRow(){
        return chessRow;
    }

    public char getOriginalFile() {
        return originalFile;
    }

    public int getOriginalChessRow() {
        return originalChessRow;
    }

    public List<int[]> getPseudoLegalMoves() {
        return moveSet;
    }

    public int[][] getValidMoveSet() {
        return validMoveSet;
    }

    public PieceType getPieceType(){
        return pieceType;
    }

    // a method used to convert column letter into int to be used in arrays
    public static int fileToCol(Character file) {
        if (!COLUMN_LETTERS.contains(file)) {
            throw new IllegalArgumentException(" COLUMN LETTER NOT VALID ! : " + file);
        }
        return (file - 'a');
    }

    // a method used to convert array col number to chess column number
    public static char colToFile(int col) {
        if (col > 7 || col < 0)
            throw new IllegalArgumentException(" Array Column number must be between 0 and 7 ! :" + col);
        return (char) ('a' + col);
    }

    // a method used to convert chess rows into int to be used in arrays
    public static int chessRowToRow(int chessRow) {
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
        return isWhite;
    }

    public boolean isKing(){
        return getPieceType() == PieceType.KING;
    }

    public void updateCoords(int row, int col){
        setChessRow(rowToChessRow(row));
        setFile(colToFile(col));

    }

    public void filterIllegalMoves(ChessboardLogic chessboardLogic, List<int[]> moveSet){

        int row = chessRowToRow(getChessRow());
        int col = fileToCol(getFile());

        Piece[][] refBoard = chessboardLogic.getChessboard().clone();

        for (int i = moveSet.size()-1 ; i >= 0; i--){

            int[] square = moveSet.get(i);
            Piece captured = refBoard[ square[0] ][ square[1] ];

            refBoard[ square[0] ][ square[1] ] = refBoard[row][col];
            updateCoords(square[0],square[1]);
            refBoard[row][col] = null;

            chessboardLogic.setChessboard(refBoard);

            if ( chessboardLogic.isKingInCheck(isWhite()) ){
                moveSet.remove(i);
            }

            //undo move
            // restore moving piece
            refBoard[row][col] = refBoard[ square[0] ][ square[1] ];
            updateCoords(row,col);

            //restore captured
            refBoard[ square[0] ][ square[1] ] = captured;

        }

    }

    public abstract boolean attacksSquare(ChessboardLogic chessboardLogic,char targetFile, int targetChessRow);

    public abstract void moveCheck(ChessboardLogic chessboardLogic);
}
