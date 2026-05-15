package chessboard;

import piecelogic.*;

public class ChessboardLogic {

    protected ChessboardGui chessboardGui;

    private boolean whiteToMove ;

    protected Piece[][] chessboard = new Piece[8][8];//logical representation of the 8 x 8 board

    private King wKing,bKing;

    private boolean immediateAction = false;

    public ChessboardLogic(){
        System.out.println("chessboardLogic obj created ! ");
        chessboardGui = new ChessboardGui();
        whiteToMove = true;
    }
    //setters
    public void setChessboard(Piece[][] board){
        if (board.length != 8 || board[0].length != 8){
            throw new IllegalArgumentException("Board size is not 8 x 8 ! : "+board.length+" x "+board[0].length);
        }
        this.chessboard = board;
    }

    public void setWhiteToMove(boolean whiteToMove) {
        this.whiteToMove = whiteToMove;
    }

    //getters
    public ChessboardGui getChessboardGui(){
        return chessboardGui;
    }

    public Piece[][] getChessboard() {
        return chessboard;
    }

    public boolean isWhiteToMove(){
        return whiteToMove;
    }

    public void insertPieceToBoard(Piece piece){
        int col = Piece.fileToCol(piece.getFile());
        int row = Piece.chessRowToRow(piece.getChessRow());

        this.chessboard[row][col] = piece;

        if (piece instanceof King){

            if (piece.isWhite()){
                wKing = (King) piece;
            } else {
                bKing = (King) piece;
            }
        }
        //System.out.println("piece inserted into the board ! ");
    }

    public void newGame(){

        chessboardGui.setChessboardLogic(this);
        setWhiteToMove(true);

        setChessboard(new Piece[8][8]);

        // white pieces
        insertPieceToBoard(PieceFactory.createPiece(PieceType.ROOK,   'a', 1, true, this));
        insertPieceToBoard(PieceFactory.createPiece(PieceType.KNIGHT, 'b', 1, true, this));
        insertPieceToBoard(PieceFactory.createPiece(PieceType.BISHOP, 'c', 1, true, this));
        insertPieceToBoard(PieceFactory.createPiece(PieceType.QUEEN,  'd', 1, true, this));
        insertPieceToBoard(PieceFactory.createPiece(PieceType.KING,   'e', 1, true, this));
        insertPieceToBoard(PieceFactory.createPiece(PieceType.BISHOP, 'f', 1, true, this));
        insertPieceToBoard(PieceFactory.createPiece(PieceType.KNIGHT, 'g', 1, true, this));
        insertPieceToBoard(PieceFactory.createPiece(PieceType.ROOK,   'h', 1, true, this));

        for (char f = 'a'; f <= 'h'; f++) {
            insertPieceToBoard(PieceFactory.createPiece(PieceType.PAWN, f, 2, true, this));
        }

        // black pieces
        insertPieceToBoard(PieceFactory.createPiece(PieceType.ROOK,   'a', 8, false, this));
        insertPieceToBoard(PieceFactory.createPiece(PieceType.KNIGHT, 'b', 8, false, this));
        insertPieceToBoard(PieceFactory.createPiece(PieceType.BISHOP, 'c', 8, false, this));
        insertPieceToBoard(PieceFactory.createPiece(PieceType.QUEEN,  'd', 8, false, this));
        insertPieceToBoard(PieceFactory.createPiece(PieceType.KING,   'e', 8, false, this));
        insertPieceToBoard(PieceFactory.createPiece(PieceType.BISHOP, 'f', 8, false, this));
        insertPieceToBoard(PieceFactory.createPiece(PieceType.KNIGHT, 'g', 8, false, this));
        insertPieceToBoard(PieceFactory.createPiece(PieceType.ROOK,   'h', 8, false, this));

        for (char f = 'a'; f <= 'h'; f++) {
            insertPieceToBoard(PieceFactory.createPiece(PieceType.PAWN, f, 7, false, this));
        }

        System.out.println("Chessboard.newGame() was called!");
    }

    //for testing purposes
    public void customBoard(){
        chessboardGui.setChessboardLogic(this);
        setWhiteToMove(true);

        Piece[][] emptyBoard = new Piece[8][8];
        setChessboard(emptyBoard);

        insertPieceToBoard(PieceFactory.createPiece(PieceType.KING,'e',1,false,this));
        insertPieceToBoard(PieceFactory.createPiece(PieceType.KING,'e',3,true,this));
        insertPieceToBoard(PieceFactory.createPiece(PieceType.KNIGHT,'e',4,false,this));
        insertPieceToBoard(PieceFactory.createPiece(PieceType.BISHOP,'e',5,false,this));
        insertPieceToBoard(PieceFactory.createPiece(PieceType.QUEEN,'d',7,true,this));


    }

    public static boolean isSquareWithinBounds(int row, int col){
        return row < 8 && col < 8 && row >= 0 && col >= 0;
    }

    public void movePiece(int selectedRow, int selectedCol, int selectedToRow, int selectedToCol){

        Piece movingPiece = chessboard[selectedRow][selectedCol];

        int[][] validMoveSet = movingPiece.getValidMoveSet();

        for(int i = 0; i < validMoveSet.length; i++){

            int r = validMoveSet[i][0];
            int c = validMoveSet[i][1];

            if (r == selectedToRow && c== selectedToCol){

                // Castling Execution Logic
                if (movingPiece instanceof King && Math.abs(selectedCol - selectedToCol) == 2) {
                    int rookOriginalCol = (selectedToCol == 6) ? 7 : 0;
                    int rookTargetCol = (selectedToCol == 6) ? 5 : 3;

                    Piece rook = chessboard[selectedRow][rookOriginalCol];

                    if (rook instanceof Rook) {

                        rook.setFile(Piece.colToFile(rookTargetCol));
                        ((Rook) rook).setHasMoved(true);
                        chessboard[selectedRow][rookTargetCol] = rook;
                        chessboard[selectedRow][rookOriginalCol] = null;
                        rook.updateCoords(selectedRow,rookTargetCol);
                    }
                }

                //EnPassant Execution Logic
                if (immediateAction && movingPiece instanceof Pawn pawn){

                    if (Math.abs(selectedCol - selectedToCol) == 1 && chessboard[selectedToRow][selectedToCol] == null ){
                        int dir = pawn.isWhite() ? 1 : -1;
                        chessboard[selectedToRow+dir][selectedToCol] = null;
                    }
                    immediateAction = false;

                }

                Pawn.clearAllEnPassantFlags(this);//resetting

                //en passant available setting logic, must be after en passant execution logic
                if (movingPiece instanceof Pawn pawn && Math.abs(selectedRow-selectedToRow) == 2){
                    Piece pieceToTheLeft = null,pieceToTheRight = null;

                    if (isSquareWithinBounds(selectedToRow,selectedToCol-1))
                        pieceToTheLeft = chessboard[selectedToRow][selectedToCol-1];

                    if (isSquareWithinBounds(selectedToRow,selectedToCol+1))
                        pieceToTheRight = chessboard[selectedToRow][selectedToCol+1];


                    if (pieceToTheLeft instanceof Pawn || pieceToTheRight instanceof Pawn){
                        pawn.setEnPassantVulnerable(true);
                        immediateAction = true;
                    }

                }

                chessboard[selectedToRow][selectedToCol] = movingPiece;
                chessboard[selectedRow][selectedCol] = null;
                movingPiece.updateCoords(selectedToRow,selectedToCol);
                setWhiteToMove(!whiteToMove);
            }

        }

        if(movingPiece instanceof Rook && !((Rook) movingPiece).getHasMoved()){
                ((Rook) movingPiece).setHasMoved(true);
        }

        if(movingPiece instanceof King && !((King) movingPiece).getHasMoved()){
                ((King) movingPiece).setHasMoved(true);
        }
    }

    //a method to check if a square is attacked by a specified color
    public boolean isSquareAttacked(boolean attackerIsWhite, char file, int chessRow){

        for (int r = 0; r < 8; r++){
            for (int c = 0; c < 8; c++ ){

                Piece piece = chessboard[r][c];
                if (piece != null &&
                        piece.isWhite() == attackerIsWhite &&
                        piece.attacksSquare(this, file, chessRow)){

                    return true;
                }

            }
        }

        return false;
    }

    public int[] getKingPos(boolean isWhite){

        King king = isWhite ? wKing : bKing;

        int row = Piece.chessRowToRow(king.getChessRow());
        int col = Piece.fileToCol(king.getFile());

        return new int[]{row,col};


    }

    public boolean isKingInCheck(boolean isWhite){

        int[] kingPos = getKingPos(isWhite);

        int row = Piece.rowToChessRow(kingPos[0]);
        char col = Piece.colToFile(kingPos[1]);

        return isSquareAttacked(!isWhite,col,row);
    }
}
