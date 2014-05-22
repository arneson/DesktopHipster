package model;

/**
 * Exception for failed uploads from hosts.
 * @author Edvard Hübinette
 *
 */
public class UploadFailedException extends Exception {

	public UploadFailedException(){
		super();
	}
	
	public UploadFailedException(String message){
		super(message);
	}
}
