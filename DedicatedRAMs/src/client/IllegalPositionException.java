package client;

public class IllegalPositionException extends Exception {

	private static final long serialVersionUID = 1L;
	public IllegalPositionException(String error) {
		super(error);
	}
	public IllegalPositionException() {
	}

}
