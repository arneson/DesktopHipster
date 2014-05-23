package model;

/**
 * Exception for errors while reading/writing the backup file.
 * @author Edvard Hübinette
 *
 */
@SuppressWarnings("serial")
public class BackupCorruptException extends Exception {

	public BackupCorruptException() {
		super();
	}

	public BackupCorruptException(String message) {
		super(message);
	}


}
