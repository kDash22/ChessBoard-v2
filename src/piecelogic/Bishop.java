package piecelogic;

import chessboard.ChessboardGui;
import chessboard.ChessboardLogic;

public class Bishop extends Piece{

    public static final int PIECE_VALUE = 3;
    private boolean check = false;

    public Bishop(char chessCol, int chessRow, boolean isWhite, ChessboardLogic chessboardLogic){
        setChessCol(chessCol);
        setChessRow(chessRow);

        setOriginalChessCol(chessCol);
        setOriginalChessRow(chessRow);

        if (isWhite){
            setID(PieceId.W_BISHOP);
        } else {
            setID(PieceId.B_BISHOP);
        }

        setChessboard(chessboardLogic.getChessboard());
        chessboardLogic.insertPieceToBoard(this);
    }

    @Override
    public void moveCheck() {

        moveSet.clear();//clear the list to remove earlier move
        //a bishop can move in 4 directions
        int[][] directions = {{1,1},{-1,1},{-1,-1},{1,-1}};

        int col = chessColToIndex(getChessCol());
        int row = chessRowToIndex(getChessRow());

        for (int[] direction : directions){
            int toRow = row + direction[0];
            int toCol = col + direction[1];

            while(toRow < 8 && toCol < 8 && toRow >=0 && toCol >= 0){

                if (chessboard[toRow][toCol] == null){
                    moveSet.add(new int[]{toRow, toCol});

                } else if (chessboard[toRow][toCol].isWhite() != isWhite() && chessboard[toRow][toCol].isKing()) {
                    check = true;//find the best way to implement this feature
                    break;
                } else if(chessboard[toRow][toCol].isWhite() != isWhite()) {
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
}
