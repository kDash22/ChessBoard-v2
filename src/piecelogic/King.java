package piecelogic;

import chessboard.ChessboardLogic;

public class King extends Piece{

    private boolean check = false;

    public King(char chessCol, int chessRow, boolean isWhite, ChessboardLogic chessboardLogic){
        setChessCol(chessCol);
        setChessRow(chessRow);

        setOriginalChessCol(chessCol);
        setOriginalChessRow(chessRow);

        if (isWhite){
            setID(PieceId.W_KING);
        } else {
            setID(PieceId.B_KING);
        }
        setChessboard(chessboardLogic.getChessboard());
        chessboardLogic.insertPieceToBoard(this);    }

    @Override
    public void moveCheck() {
        //implement castling
    }
}
