package client;

public class Vest {

	public enum VestColor {
		KING_PURPLE, QUEEN_YELLOW, BISHOP_BLUE, KNIGHT_ORANGE, ROOK_RED, PAWN_GREEN
	};

	private VestColor color;
	private ChessPiece type;

	public Vest(ChessPiece type, VestColor color) {
		this.type = type;
		this.color = color;
	}

	public ChessPiece getPiece() {
		return this.type;
	}

	public void setType(ChessPiece type) throws IllegalArgumentException {
		if (type != null) {
			this.type = type;
		} else {
			throw new IllegalArgumentException();
		}
	}

	public VestColor getVestColor() {
		return this.color;
	}
}
