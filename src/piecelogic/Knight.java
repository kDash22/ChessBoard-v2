package piecelogic;

import chessboard.ChessboardLogic;

public class Knight extends Piece {

    public static final int PIECE_VALUE = 3;
    private boolean check = false;

    public Knight(char file, int chessRow, boolean isWhite){
        super(isWhite,PieceType.KNIGHT,file,chessRow);
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

        //a knight has 8 possible moves or directions
        int[][] directions = {{2,1},{1,2},{-1,2},{-2,1},{-2,-1},{-1,-2},{1,-2},{2,-1}};

        int col = fileToCol(getFile());
        int row = chessRowToRow(getChessRow());

        for (int i = 0; i < 8; i++){
                int toRow = row + directions[i][0];
                int toCol = col + directions[i][1];

                if( ChessboardLogic.isSquareWithinBounds(toRow,toCol) ){

                    if (refBoard[toRow][toCol] == null){
                        moveSet.add(new int[]{toRow, toCol});

                    } else if (refBoard[toRow][toCol].isWhite() != isWhite() && refBoard[toRow][toCol].isKing()) {
                        check = true;//find the best way to implement this feature

                    } else if(refBoard[toRow][toCol].isWhite() != isWhite()) {
                        moveSet.add(new int[]{toRow, toCol});

                    }

                }

        }

        filterIllegalMoves(chessboardLogic,moveSet);

        int validMoveCount = moveSet.size();
        validMoveSet = new int[validMoveCount][2];

        for (int i = 0; i < validMoveCount; i++){
            validMoveSet[i] = moveSet.get(i);
        }

    }

    public String toString(){
        String tag = isWhite() ? "White Knight at " : "Black Knight at ";
        tag += getFile()+""+getChessRow();
        return tag;
    }

    @Override
    public boolean attacksSquare(ChessboardLogic chessboardLogic,char targetFile, int targetChessRow) {

        int targetCol = fileToCol(targetFile);
        int targetRow = chessRowToRow(targetChessRow);

        int rowDiff = Math.abs(targetRow - chessRowToRow(getChessRow()));
        int colDiff = Math.abs(targetCol - fileToCol(getFile()));

        return ((rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2));
    }
}
