package piecelogic;

import chessboard.ChessboardLogic;

public class King extends Piece{

    private boolean check = false;

    public King(char chessCol, int chessRow, boolean isWhite, ChessboardLogic chessboardLogic){
        super(chessCol,chessRow,chessboardLogic);

        setChessCol(chessCol);
        setChessRow(chessRow);

        if (isWhite){
            setID(PieceId.W_KING);
        } else {
            setID(PieceId.B_KING);
        }
        chessboardLogic.insertPieceToBoard(this);    }

    @Override
    public void moveCheck() {

        if (isWhite() != chessboardLogic.isWhiteToMove()){
            return;
        }


        moveSet.clear();//clear the list to remove earlier move

        //implement castling

        int validMoveCount = moveSet.size();
        validMoveSet = new int[validMoveCount][2];

        for (int i = 0; i < validMoveCount; i++){
            validMoveSet[i] = moveSet.get(i);
        }
    }

    public String toString(){
        String tag = isWhite() ? "White King at " : "Black King at ";
        tag += getChessCol()+""+getChessRow();
        return tag;
    }
}
