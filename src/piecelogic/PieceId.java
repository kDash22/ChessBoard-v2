package piecelogic;

public enum PieceId {

    B_PAWN(false), W_PAWN(true),
    B_KNIGHT(false), W_KNIGHT(true),
    B_BISHOP(false), W_BISHOP(true),
    B_ROOK(false), W_ROOK(true),
    B_QUEEN(false), W_QUEEN(true),
    B_KING(false), W_KING(true);

    private final boolean isWhite;

    PieceId(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public boolean isBlack() {
        return !isWhite;
    }

    public static boolean isPawn(Piece piece){
        if (piece == null){
            return false;
        }
        return piece.getId() == W_PAWN || piece.getId() == B_PAWN;
    }
}
