package piecelogic;

import chessboard.ChessboardLogic;

public class Knight extends Piece {

    public static final int PIECE_VALUE = 3;
    private boolean check = false;

    public Knight(char chessCol, int chessRow, boolean isWhite, ChessboardLogic chessboardLogic){
        super(chessCol,chessRow,chessboardLogic);

        setChessCol(chessCol);
        setChessRow(chessRow);

        if (isWhite){
            setID(PieceId.W_KNIGHT);
        } else {
            setID(PieceId.B_KNIGHT);
        }
        chessboardLogic.insertPieceToBoard(this);

    }

    @Override
    public void moveCheck() {

        if (isWhite() != chessboardLogic.isWhiteToMove()){
            return;
        }

        Piece[][] refboard = chessboardLogic.getChessboard();

        moveSet.clear();//clear the list to remove earlier moves
        //a knight has 8 possible moves or directions
        int[][] directions = {{2,1},{1,2},{-1,2},{-2,1},{-2,-1},{-1,-2},{1,-2},{2,-1}};

        int col = chessColToIndex(getChessCol());
        int row = chessRowToIndex(getChessRow());

        for (int i = 0; i < 8; i++){
                int toRow = row + directions[i][0];
                int toCol = col + directions[i][1];

                if( ChessboardLogic.isSquareWithinBounds(toRow,toCol) ){

                    if (refboard[toRow][toCol] == null){
                        moveSet.add(new int[]{toRow, toCol});

                    } else if (refboard[toRow][toCol].isWhite() != isWhite() && refboard[toRow][toCol].isKing()) {
                        check = true;//find the best way to implement this feature

                    } else if(refboard[toRow][toCol].isWhite() != isWhite()) {
                        moveSet.add(new int[]{toRow, toCol});

                    }

                }

        }
        int validMoveCount = moveSet.size();
        validMoveSet = new int[validMoveCount][2];

        for (int i = 0; i < validMoveCount; i++){
            validMoveSet[i] = moveSet.get(i);
        }

    }

    public String toString(){
        String tag = isWhite() ? "White Knight at " : "Black Knight at ";
        tag += getChessCol()+""+getChessRow();
        return tag;
    }
}
