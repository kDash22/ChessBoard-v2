package piecelogic;

import chessboard.ChessboardLogic;

public class Pawn extends Piece{

    public static final int PIECE_VALUE = 1;
    private boolean check = false;
    private boolean enPassantVulnerable = false;

    public Pawn(char chessCol, int chessRow, boolean isWhite, ChessboardLogic chessboardLogic ){
        super(chessCol,chessRow,chessboardLogic);

        setChessCol(chessCol);
        setChessRow(chessRow);

        if (isWhite){
            setID(PieceId.W_PAWN);
        } else {
            setID(PieceId.B_PAWN);
        }
        chessboardLogic.insertPieceToBoard(this);    }

    @Override
    public void moveCheck() {
        moveSet.clear();//clear the list to remove earlier move

        if (isWhite() != chessboardLogic.isWhiteToMove()){
            validMoveSet = new int[0][2];
            return;
        }

        Piece[][] refBoard = chessboardLogic.getChessboard();

        int col = chessColToIndex(getChessCol());
        int row = chessRowToIndex(getChessRow());

        //there are 4 general moves for a pawn
        // move 1 square forward, move 2 squares forward (only as the first move), take diagonally to the left and right
        //Pawns can only move forward, and it's different for the 2 teams
        int[][] tempMoves;
        if (isWhite()){
            tempMoves = new int[][]{{row-1, col}, {row-2, col}, {row-1, col+1}, {row-1, col-1}};
        } else {
            tempMoves = new int[][]{{row+ 1, col}, {row+2, col}, {row+1, col+1}, {row+1,col -1}};
        }

        //moving logic
        if ( ChessboardLogic.isSquareWithinBounds( tempMoves[0][0],tempMoves[0][1] ) ){

            if (refBoard[tempMoves[0][0]][tempMoves[0][1]] == null) {//1 square check
                moveSet.add(tempMoves[0]);

                if (ChessboardLogic.isSquareWithinBounds( tempMoves[1][0],tempMoves[1][1] )){

                    if (refBoard[tempMoves[1][0]][tempMoves[1][1]] == null && !hasPawnMoved()) //2 square check
                        moveSet.add(tempMoves[1]);
                }
            }
        }

        //capturing logic to the right (observer's  right)
        if ( ChessboardLogic.isSquareWithinBounds( tempMoves[2][0],tempMoves[2][1] ) ){
            int toRow = tempMoves[2][0];
            int toCol = tempMoves[2][1];
            if (       refBoard[toRow][toCol] != null
                    && refBoard[toRow][toCol].isWhite() != isWhite()
                    && !refBoard[toRow][toCol].isKing())
            {
                moveSet.add(tempMoves[2]);
            }
        }

        //capturing logic to the left (observer's left)
        if ( ChessboardLogic.isSquareWithinBounds( tempMoves[3][0],tempMoves[3][1] ) ){
            int toRow = tempMoves[3][0];
            int toCol = tempMoves[3][1];
            if (       refBoard[toRow][toCol] != null
                    && refBoard[toRow][toCol].isWhite() != isWhite()
                    && !refBoard[toRow][toCol].isKing())
            {
                moveSet.add(tempMoves[3]);
            }
        }

        //implement en passant

        int validMoveCount = moveSet.size();
        validMoveSet = new int[validMoveCount][2];

        for (int i = 0; i < validMoveCount; i++){
            validMoveSet[i] = moveSet.get(i);
        }
    }

    public boolean hasPawnMoved(){
        return originalChessRow != getChessRow() || originalChessCol != getChessCol();
    }

    public String toString(){
        String tag = isWhite() ? "White Pawn at " : "Black Pawn at ";
        tag += getChessCol()+""+getChessRow();
        return tag;
    }
}
