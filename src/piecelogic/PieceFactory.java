package piecelogic;

import chessboard.ChessboardLogic;

public class PieceFactory {

    public static Piece createPiece(PieceType pieceType,char chessCol, int chessRow, boolean isWhite, ChessboardLogic chessboardLogic){

        return switch (pieceType){
            case KING -> new King(chessCol,chessRow,isWhite);
            case PAWN -> new Pawn(chessCol,chessRow,isWhite);
            case KNIGHT -> new Knight(chessCol,chessRow,isWhite);
            case BISHOP -> new Bishop(chessCol,chessRow,isWhite);
            case ROOK -> new Rook(chessCol,chessRow,isWhite);
            case QUEEN -> new Queen(chessCol,chessRow,isWhite);

        };
    }
}
