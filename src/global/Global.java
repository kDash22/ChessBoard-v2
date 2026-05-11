package global;

import piecelogic.Piece;

public class Global {

    public static void print2D(Object[][] arr) {
        for (int r = 0; r < arr.length; r++) {
            for (int c = 0; c < arr[r].length; c++) {

                if (arr[r][c] == null) {
                    System.out.print("null ");
                } else {
                    System.out.print(arr[r][c] + " ");
                }

            }
            System.out.println();
        }
    }

    public static void printValidMoveSet(int[][] arr) {
        if (arr == null ){
            System.out.println("move array null ! \n");
            return;
        }
        if (arr.length == 0){
            System.out.println("no moves ! \n");
            return;
        }
        for (int[] move : arr){
            int chessRow = Piece.rowToChessRow(move[0]);
            char chessCol = Piece.colToChessCol(move[1]);
            System.out.println("move : "+chessCol+chessRow);
        }
        System.out.println();
    }

    public static void print1D(Object[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == null) {
                System.out.print("null ");
            } else {
                System.out.print(arr[i] + " ");
            }
        }
        System.out.println();
    }

    public static void print2D(boolean[][] arr) {
        for (int r = 0; r < arr.length; r++) {
            for (int c = 0; c < arr[r].length; c++) {
                System.out.print(arr[r][c] + " ");
            }
            System.out.println();
        }
    }

    public static void print1D(boolean[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] ? "1 " : "0 ");
        }
        System.out.println();
    }


}