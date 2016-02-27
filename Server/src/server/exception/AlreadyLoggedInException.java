package server.exception;

public class AlreadyLoggedInException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AlreadyLoggedInException(String string) {
		super(string);
	}

}
