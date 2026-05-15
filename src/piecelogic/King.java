package piecelogic;

import chessboard.ChessboardLogic;

public class King extends Piece{

    private boolean check = false;

    private boolean hasMoved = false;

    public King(char file, int chessRow, boolean isWhite){
        super(isWhite,PieceType.KING,file,chessRow);

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

        int row = chessRowToRow(getChessRow());
        int col = fileToCol(getFile());

        Piece[][] refBoard = chessboardLogic.getChessboard();

        //a king has 8 general moves
        int[][] directions = {{1,0},{1,1},{0,1},{-1,1},{-1,0},{-1,-1},{0,-1},{1,-1}};

        for (int[] direction : directions){

            int toRow = row + direction[0];
            int toCol = col + direction[1];

            if (ChessboardLogic.isSquareWithinBounds(toRow,toCol)){
                if (refBoard[toRow][toCol] == null) {
                    // Empty square
                    moveSet.add(new int[]{toRow,toCol});
                } else if (refBoard[toRow][toCol].isWhite() != isWhite()) {
                    // Enemy piece
                    moveSet.add(new int[]{toRow,toCol});
                }
            }

        }


        // King Proximity Rule
        // Check if adjacent squares contain an enemy King
        for (int i = moveSet.size() - 1; i >= 0; i--){

            int[] move = moveSet.get(i);
            boolean remove = false;

            for (int dr = -1; dr <= 1 && !remove; dr++) {
                for (int dc = -1; dc <= 1; dc++) {

                    //skip the destination square
                    if (dr == 0 && dc == 0) {
                        continue;
                    }

                    int adjRow = move[0] + dr;
                    int adjCol = move[1] + dc;

                    if (ChessboardLogic.isSquareWithinBounds(adjRow, adjCol)) {
                        Piece adjPiece = refBoard[adjRow][adjCol];
                        if (adjPiece instanceof King && adjPiece.isWhite() != isWhite()) {
                            remove = true;
                            break;
                        }

                    }
                }

            }
            if (remove) moveSet.remove(i);
        }
        // --- Castling Logic ---
        if (!getHasMoved()  && !chessboardLogic.isKingInCheck(isWhite())  ) {
            // King Side Castling
            if (refBoard[row][7] instanceof Rook rook && !rook.getHasMoved()) {
                int chessRow = rowToChessRow(row);
                char fileF = colToFile(5);
                char fileG = colToFile(6);

                if ( (refBoard[row][5] == null && refBoard[row][6] == null)
                        && !chessboardLogic.isSquareAttacked(!isWhite(),fileF,chessRow)
                        && !chessboardLogic.isSquareAttacked(!isWhite(),fileG,chessRow))  {
                    moveSet.add(new int[] {row, 6});
                }
            }
            // Queen Side Castling
            if (refBoard[row][0] instanceof Rook rook && !rook.getHasMoved()) {
                int chessRow = rowToChessRow(row);
                char fileB = colToFile(1);
                char fileC = colToFile(2);
                char fileD = colToFile(3);
                if (refBoard[row][1] == null && refBoard[row][2] == null && refBoard[row][3] == null
                        && !chessboardLogic.isSquareAttacked(!isWhite(),fileB,chessRow)
                        && !chessboardLogic.isSquareAttacked(!isWhite(),fileC,chessRow)
                        && !chessboardLogic.isSquareAttacked(!isWhite(),fileD,chessRow)) {
                    moveSet.add(new int[] {row, 2});
                }
            }
        }

        filterIllegalMoves(chessboardLogic,moveSet);

        //implement checks

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

        int row = Piece.chessRowToRow(getChessRow());
        int col = Piece.fileToCol(getFile());

        return (Math.abs(targetRow - row) <= 1 && Math.abs(targetCol-col) <= 1) && !(Math.abs(targetRow - row) == 0 && Math.abs(targetCol-col) == 0);
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public boolean getHasMoved(){
        return hasMoved;
    }

    public String toString(){
        String tag = isWhite() ? "White King at " : "Black King at ";
        tag += getFile()+""+getChessRow();
        return tag;
    }
}
