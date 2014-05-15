package model;

/**
 * Exception for when trying to access an edited version of an image which does
 * not exist
 * 
 * @author Edvard Hübinette
 * 
 */
public class NoSuchVersionException extends Exception {
	public NoSuchVersionException() {
		super();
	}

	public NoSuchVersionException(String message) {
		super(message);
	}
}
