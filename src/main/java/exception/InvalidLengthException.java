package exception;

public class InvalidLengthException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage() {
		return "The lenght must be a multiple of 16";
	}

}
