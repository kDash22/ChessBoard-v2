package piecelogic;

import chessboard.ChessboardLogic;

public class Pawn extends Piece{

    public static final int PIECE_VALUE = 1;
    private boolean check = false;

    public Pawn(char chessCol, int chessRow, boolean isWhite, ChessboardLogic chessboardLogic ){
        setChessCol(chessCol);
        setChessRow(chessRow);

        setOriginalChessCol(chessCol);
        setOriginalChessRow(chessRow);

        if (isWhite){
            setID(PieceId.W_PAWN);
        } else {
            setID(PieceId.B_PAWN);
        }
        setChessboard(chessboardLogic.getChessboard());
        chessboardLogic.insertPieceToBoard(this);    }

    @Override
    public void moveCheck() {

    }
}
