package piecelogic;

import chessboard.ChessboardLogic;

public class Rook extends Piece{

    public static final int PIECE_VALUE = 5;
    private boolean check = false;

    private boolean hasMoved = false;

    public Rook(char file, int chessRow, boolean isWhite){
        super(isWhite,PieceType.ROOK,file,chessRow);

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

        //a Rook can move in 4 directions
        int[][] directions = {{1,0},{0,1},{-1,0},{0,-1}};

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

        int targetCol = fileToCol(targetFile);
        int targetRow = chessRowToRow(targetChessRow);

        int row = chessRowToRow(getChessRow());
        int col = fileToCol(getFile());

        if (targetRow != row && targetCol != col)
            return false;

        int rowDir = Integer.compare(targetRow,row);
        int colDir = Integer.compare(targetCol,col);

        int r = row+rowDir;
        int c = col+colDir;

        while (targetCol != c || targetRow != r){

            if (chessboardLogic.getChessboard()[r][c] != null){
                return false;

            }

            r += rowDir;
            c += colDir;
        }

        return true;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public boolean getHasMoved(){
        return hasMoved;
    }

    public String toString(){
        String tag = isWhite() ? "White Rook at " : "Black Rook at ";
        tag += getFile()+""+getChessRow();
        return tag;
    }
}
