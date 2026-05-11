package chessboard;

import piecelogic.*;

public class ChessboardLogic {

    protected ChessboardGui chessboardGui;

    protected boolean whiteToMove = false;

    protected Piece[][] chessboard = new Piece[8][8];//logical representation of the 8 x 8 board

    public ChessboardLogic(){
        System.out.println("chessboardLogic obj created ! ");
        chessboardGui = new ChessboardGui();
        whiteToMove = true; //check this out later
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
        int col = Piece.chessColToIndex(piece.getChessCol());
        int row = Piece.chessRowToIndex(piece.getChessRow());

        this.chessboard[row][col] = piece;
        //System.out.println("piece inserted into the board ! ");
    }

    public void newGame(){

        chessboardGui.setChessboardLogic(this);

        Piece[][] emptyBoard = new Piece[8][8];
        setChessboard(emptyBoard);

        //white pieces
        Rook wR1 = new Rook('a',1,true,this);
        Knight wN1 = new Knight('b',1,true,this);
        Bishop wB1 = new Bishop('c',1,true,this);
        Queen wQ  = new Queen('d',1,true,this);
        King wK   = new King('e',1,true,this);
        Bishop wB2 = new Bishop('f',1,true,this);
        Knight wN2 = new Knight('g',1,true,this);
        Rook wR2 = new Rook('h',1,true,this);

        Pawn wP1 = new Pawn('a',2,true,this);
        Pawn wP2 = new Pawn('b',2,true,this);
        Pawn wP3 = new Pawn('c',2,true,this);
        Pawn wP4 = new Pawn('d',2,true,this);
        Pawn wP5 = new Pawn('e',2,true,this);
        Pawn wP6 = new Pawn('f',2,true,this);
        Pawn wP7 = new Pawn('g',2,true,this);
        Pawn wP8 = new Pawn('h',2,true,this);

        //black pieces
        Rook bR1 = new Rook('a',8,false,this);
        Knight bN1 = new Knight('b',8,false,this);
        Bishop bB1 = new Bishop('c',8,false,this);
        Queen bQ  = new Queen('d',8,false,this);
        King bK   = new King('e',8,false,this);
        Bishop bB2 = new Bishop('f',8,false,this);
        Knight bN2 = new Knight('g',8,false,this);
        Rook bR2 = new Rook('h',8,false,this);

        Pawn bP1 = new Pawn('a',7,false,this);
        Pawn bP2 = new Pawn('b',7,false,this);
        Pawn bP3 = new Pawn('c',7,false,this);
        Pawn bP4 = new Pawn('d',7,false,this);
        Pawn bP5 = new Pawn('e',7,false,this);
        Pawn bP6 = new Pawn('f',7,false,this);
        Pawn bP7 = new Pawn('g',7,false,this);
        Pawn bP8 = new Pawn('h',7,false,this);

        System.out.println("Chessboard.newGame() was called ! ");
    }

    public void customBoard(){
        chessboardGui.setChessboardLogic(this);

        Piece[][] emptyBoard = new Piece[8][8];
        setChessboard(emptyBoard);

        King wK   = new King('e',4,true,this);

    }

    public static boolean isSquareWithinBounds(int row, int col){
        return row < 8 && col < 8 && row >= 0 && col >= 0;
    }
}
