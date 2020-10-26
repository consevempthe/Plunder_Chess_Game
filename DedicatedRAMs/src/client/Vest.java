package client;

public class Vest {

	public enum VestColor {
		YELLOW, BLUE, ORANGE, RED, GREEN
	}

	private VestColor color;
	private ChessPiece type;

	public Vest(ChessPiece type) {
		this.type = type;
		this.setColor();
	}

	public ChessPiece getType() {
		return this.type;
	}

	public void setType(ChessPiece type) throws IllegalArgumentException {
		if (type != null) {
			this.type = type;
			this.setColor();
		} else {
			throw new IllegalArgumentException();
		}
	}

	public VestColor getVestColor() {
		return this.color;
	}
	
	/**
	 * Checks if a move is legal based on the vest.
	 * @param positon : the position of the move
	 * @return a value indicating whether to not a move is legal
	 */
	public boolean moveIsLegal(String position) {
		return this.type.legalMoves(true, false).contains(position);
	}
	
	/**
	 * Gets the vest color to be used on the UI.
	 * @return a java swing useable color, the default is magenta (if a the vest doesn't have a color)
	 */
	public java.awt.Color getUiColor()
	{
		if (this.color == VestColor.YELLOW)
		{
			return java.awt.Color.yellow;
		}
		else if (this.color == VestColor.BLUE)
		{
			return java.awt.Color.blue;
		}
		else if (this.color == VestColor.ORANGE)
		{
			return java.awt.Color.orange;
		}
		else if (this.color == VestColor.RED)
		{
			return java.awt.Color.red;
		}
		else if (this.color == VestColor.GREEN)
		{
			return java.awt.Color.green;
		}
		
	 return java.awt.Color.magenta;
	}

	public void setVestPosition(String position) throws IllegalPositionException {
		this.type.setPosition(position);
	}

	//The user can't set the color, auto set the color when the type changes
	private void setColor() {
		if (this.type.getClass() == Queen.class)
		{
			this.color = VestColor.YELLOW;
		}
		else if (this.type.getClass() == Bishop.class)
		{
			this.color = VestColor.BLUE;
		}
		else if (this.type.getClass() == Knight.class)
		{
			this.color = VestColor.ORANGE;
		}
		else if (this.type.getClass() == Rook.class)
		{
			this.color = VestColor.RED;
		}
		else if (this.type.getClass() == Pawn.class)
		{
			this.color = VestColor.GREEN;
		}
	}
}
