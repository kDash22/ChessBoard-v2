package piecelogic;

import chessboard.ChessboardLogic;

public class Knight extends Piece {

    public static final int PIECE_VALUE = 3;
    private boolean check = false;

    public Knight(char chessCol, int chessRow, boolean isWhite, ChessboardLogic chessboardLogic){
        setChessCol(chessCol);
        setChessRow(chessRow);

        setOriginalChessCol(chessCol);
        setOriginalChessRow(chessRow);

        if (isWhite){
            setID(PieceId.W_KNIGHT);
        } else {
            setID(PieceId.B_KNIGHT);
        }

        setChessboard(chessboardLogic.getChessboard());
        chessboardLogic.insertPieceToBoard(this);

    }

    @Override
    public void moveCheck() {

        moveSet.clear();//clear the list to remove earlier moves
        //a knight has 8 possible moves or directions
        int[][] directions = {{2,1},{1,2},{-1,2},{-2,1},{-2,-1},{-1,-2},{1,-2},{2,-1}};

        int col = chessColToIndex(getChessCol());
        int row = chessRowToIndex(getChessRow());

        for (int i = 0; i < 8; i++){
                int toRow = row + directions[i][0];
                int toCol = col + directions[i][1];

                if(toRow < 8 && toCol < 8 && toRow >= 0 && toCol >= 0 ){

                    if (chessboard[toRow][toCol] == null){
                        moveSet.add(new int[]{toRow, toCol});

                    } else if (chessboard[toRow][toCol].isWhite() != isWhite() && isKing()) {
                        check = true;//find the best way to implement this feature

                    } else if(chessboard[toRow][toCol].isWhite() != isWhite()) {
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
}
