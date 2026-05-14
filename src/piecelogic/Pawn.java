package piecelogic;

import chessboard.ChessboardLogic;

public class Pawn extends Piece{

    public static final int PIECE_VALUE = 1;
    private boolean check = false;
    private boolean enPassantVulnerable = false;

    public Pawn(char file, int chessRow, boolean isWhite ){
        super(isWhite,PieceType.PAWN,file,chessRow);

        setFile(file);
        setChessRow(chessRow);

    }


    @Override
    public void moveCheck(ChessboardLogic chessboardLogic) {
        moveSet.clear();//clear the list to remove earlier move

        if (isWhite() != chessboardLogic.isWhiteToMove()){
            validMoveSet = new int[0][2];
            return;
        }

        Piece[][] refBoard = chessboardLogic.getChessboard();

        int col = fileToCol(getFile());
        int row = chessRowToRow(getChessRow());

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

        filterIllegalMoves(chessboardLogic,moveSet);


        //implement en passant

        int validMoveCount = moveSet.size();
        validMoveSet = new int[validMoveCount][2];

        for (int i = 0; i < validMoveCount; i++){
            validMoveSet[i] = moveSet.get(i);
        }
    }

    @Override
    public boolean attacksSquare(ChessboardLogic chessboardLogic,char targetFile, int targetChessRow) {

        int targetCol = fileToCol(targetFile);
        int targetRow = chessRowToRow(targetChessRow);

        int rowDir = isWhite() ? -1 : 1;

        return (targetRow == chessRowToRow(getChessRow())+rowDir
                && (targetCol == fileToCol(getFile()) - 1 || targetCol == fileToCol(getFile()) + 1)) ;

    }

    public boolean hasPawnMoved(){
        return originalChessRow != getChessRow() || originalFile != getFile();
    }

    public String toString(){
        String tag = isWhite() ? "White Pawn at " : "Black Pawn at ";
        tag += getFile()+""+getChessRow();
        return tag;
    }

}
