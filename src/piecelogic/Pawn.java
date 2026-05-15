package piecelogic;

import chessboard.ChessboardLogic;

import java.util.List;

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

        //en passant logic start
        Piece pieceToTheLeft = null,pieceToTheRight = null;

        if (ChessboardLogic.isSquareWithinBounds(row,col-1))
            pieceToTheLeft = refBoard[row][col-1];

        if (ChessboardLogic.isSquareWithinBounds(row,col+1))
            pieceToTheRight = refBoard[row][col+1];

        if (pieceToTheLeft instanceof Pawn pawnToTheLeft && pawnToTheLeft.getEnPassantVulnerable()){
            int dir = isWhite() ? -1 : 1;
            int c = fileToCol(pawnToTheLeft.getFile());
            moveSet.add(new int[]{row+dir,c});
        }

        if (pieceToTheRight instanceof Pawn pawnToTheRight && pawnToTheRight.getEnPassantVulnerable()){
            int dir = isWhite() ? -1 : 1;
            int c = fileToCol(pawnToTheRight.getFile());
            moveSet.add(new int[]{row+dir,c});
        }
        //en passant logic over

        filterIllegalMoves(chessboardLogic,moveSet);

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

    public static void clearAllEnPassantFlags(ChessboardLogic chessboardLogic){

        Piece[][] refBoard = chessboardLogic.getChessboard();

        for (int r = 0; r < 8; r++){
            for (int c = 0; c < 8; c++){

                Piece p = refBoard[r][c];

                if (p instanceof Pawn pawn){
                    pawn.setEnPassantVulnerable(false);
                }
            }
        }
    }

    @Override
    public void filterIllegalMoves(ChessboardLogic chessboardLogic, List<int[]> moveSet){

        int row = chessRowToRow(getChessRow());
        int col = fileToCol(getFile());

        Piece[][] refBoard = chessboardLogic.getChessboard();

        for (int i = moveSet.size()-1 ; i >= 0; i--){

            int[] square = moveSet.get(i);

            Piece captured;
            boolean enPassantHappened = false;

            if (Math.abs(col - square[1]) == 1 && refBoard[ square[0] ][ square[1] ] == null ){
                int dir = isWhite() ? 1 : -1;
                captured = refBoard[square[0]+dir][square[1]] ;

                if (!(captured instanceof Pawn)){
                    throw new IllegalArgumentException("The captured Piece using EnPassant is not a Pawn at filterIllegalMoves in Pawn ! ");
                }

                refBoard[square[0]+dir][square[1]] = null;
                enPassantHappened = true;

            } else {
                captured = refBoard[ square[0] ][ square[1] ];
            }


            refBoard[ square[0] ][ square[1] ] = refBoard[row][col];
            updateCoords(square[0],square[1]);
            refBoard[row][col] = null;

            chessboardLogic.setChessboard(refBoard);

            if ( chessboardLogic.isKingInCheck(isWhite()) ){
                moveSet.remove(i);
            }

            //undo move
            // restore moving piece
            refBoard[row][col] = refBoard[ square[0] ][ square[1] ];
            updateCoords(row,col);

            //restore captured
            if (enPassantHappened){
                int dir = isWhite() ? 1 : -1;
                refBoard[square[0]+dir][square[1]] = captured ;
                refBoard[ square[0] ][ square[1] ] = null;
            } else {
                refBoard[ square[0] ][ square[1] ] = captured;
            }

            chessboardLogic.setChessboard(refBoard);

        }

    }

    public boolean hasPawnMoved(){
        return originalChessRow != getChessRow() || originalFile != getFile();
    }

    public String toString(){
        String tag = isWhite() ? "White Pawn at " : "Black Pawn at ";
        tag += getFile()+""+getChessRow();
        return tag;
    }

    public void setEnPassantVulnerable(boolean enPassantVulnerable) {
        this.enPassantVulnerable = enPassantVulnerable;
    }

    public boolean getEnPassantVulnerable(){return enPassantVulnerable;}
}
