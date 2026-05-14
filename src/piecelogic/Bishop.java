package piecelogic;

import chessboard.ChessboardLogic;

public class Bishop extends Piece{

    public static final int PIECE_VALUE = 3;
    private boolean check = false;

    public Bishop(char file, int chessRow, boolean isWhite){
        super(isWhite,PieceType.BISHOP,file,chessRow);

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

        //a bishop can move in 4 directions
        int[][] directions = {{1,1},{-1,1},{-1,-1},{1,-1}};

        int col = fileToCol(getFile());
        int row = chessRowToRow(getChessRow());

        for (int[] direction : directions){
            int toRow = row + direction[0];
            int toCol = col + direction[1];

            while(ChessboardLogic.isSquareWithinBounds(toRow,toCol)){

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

        int targetCol = fileToCol(targetFile);
        int targetRow = chessRowToRow(targetChessRow);

        int row = chessRowToRow(getChessRow());
        int col = fileToCol(getFile());

        if (Math.abs(targetRow - row) != Math.abs(targetCol - col))
            return false;

        int rowDir = (targetRow > row) ? 1 : -1;
        int colDir = (targetCol > col) ? 1 : -1;

        int r = row+rowDir;
        int c = col+colDir;

        while (r != targetRow && c != targetCol){

            if (!ChessboardLogic.isSquareWithinBounds(r, c)) return false;

            if (chessboardLogic.getChessboard()[r][c] != null) return false;

            r += rowDir;
            c += colDir;
        }

        return true;
    }

    public String toString(){
        String tag = isWhite() ? "White Bishop at " : "Black Bishop at ";
        tag += getFile()+""+getChessRow();
        return tag;
    }
}
