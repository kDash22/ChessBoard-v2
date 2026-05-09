package piecelogic;

import chessboard.ChessboardLogic;

public class Rook extends Piece{

    public static final int PIECE_VALUE = 5;
    private boolean check = false;

    public Rook(char chessCol, int chessRow, boolean isWhite, ChessboardLogic chessboardLogic){
        super(chessCol,chessRow,chessboardLogic);

        setChessCol(chessCol);
        setChessRow(chessRow);

        if (isWhite){
            setID(PieceId.W_ROOK);
        } else {
            setID(PieceId.B_ROOK);
        }
        chessboardLogic.insertPieceToBoard(this);
    }

    @Override
    public void moveCheck() {

        if (isWhite() != chessboardLogic.isWhiteToMove()){
            return;
        }

        Piece[][] refBoard = chessboardLogic.getChessboard();

        moveSet.clear();//clear the list to remove earlier move
        //a Rook can move in 4 directions
        int[][] directions = {{1,0},{0,1},{-1,0},{0,-1}};

        int col = chessColToIndex(getChessCol());
        int row = chessRowToIndex(getChessRow());

        for (int[] direction : directions){
            int toRow = row + direction[0];
            int toCol = col + direction[1];

            while( ChessboardLogic.isSquareWithinBounds(toRow,toCol) ){

                if (refBoard[toRow][toCol] == null){
                    moveSet.add(new int[]{toRow, toCol});

                } else if (refBoard[toRow][toCol].isWhite() != isWhite() && refBoard[toRow][toCol].isKing()) {
                    check = true;//find the best way to implement this feature
                    break;
                } else if(refBoard[toRow][toCol].isWhite() != isWhite()) {
                    moveSet.add(new int[]{toRow, toCol});
                    break;
                } else {
                    break;
                }

                toRow += direction[0];
                toCol += direction[1];
            }
        }
        int validMoveCount = moveSet.size();
        validMoveSet = new int[validMoveCount][2];

        for (int i = 0; i < validMoveCount; i++){
            validMoveSet[i] = moveSet.get(i);
        }
    }

    public String toString(){
        String tag = isWhite() ? "White Rook at " : "Black Rook at ";
        tag += getChessCol()+""+getChessRow();
        return tag;
    }
}
