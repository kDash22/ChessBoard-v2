package piecelogic;

import chessboard.ChessboardLogic;

public class Queen extends Piece{

    public static final int PIECE_VALUE = 9;
    private boolean check = false;

    public Queen(char file, int chessRow, boolean isWhite){
        super(isWhite,PieceType.QUEEN,file,chessRow);

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

        //a Queen can move in 8 directions
        int[][] directions = {{1,0},{1,1},{0,1},{-1,1},{-1,0},{-1,-1},{0,-1},{1,-1}};

        int col = fileToCol(getFile());
        int row = chessRowToRow(getChessRow());

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

    @Override
    public boolean attacksSquare(ChessboardLogic chessboardLogic,char targetFile, int targetChessRow) {

        Bishop bishopLogic = new Bishop(getFile(),getChessRow(),isWhite());

        Rook rookLogic = new Rook(getFile(),getChessRow(),isWhite());

        return bishopLogic.attacksSquare(chessboardLogic, targetFile,targetChessRow)
                || rookLogic.attacksSquare(chessboardLogic,targetFile,targetChessRow);
    }

    public String toString(){
        String tag = isWhite() ? "White Queen at " : "Black Queen at ";
        tag += getFile()+""+getChessRow();
        return tag;
    }
}
