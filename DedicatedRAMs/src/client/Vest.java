package client;

public class Vest {

	public enum VestColor {
		PURPLE, YELLOW, BLUE, ORANGE, RED, GREEN
	};

	private VestColor color;
	private ChessPiece type;

	public Vest(ChessPiece type) {
		this.type = type;
		this.setColor();
	}

	public ChessPiece getPiece() {
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
	
	public void setVestPosition(String position) throws IllegalPositionException
	{
		this.type.setPosition(position);
	}
	
	//THe user can't set the color, auto set the color when the type changes
	private void setColor()
	{
		if(this.type == null)
		{
			return;
		}
		else if (this.type.getClass() == King.class)
		{
			this.color = VestColor.PURPLE;
		}
		else if (this.type.getClass() == Queen.class)
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
