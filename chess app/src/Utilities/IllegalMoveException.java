/**
 * @author West Laos
 * 
 * This class extends exception and is used to indicate that 
 * the users move they attempted to make is not valid.
 * 
 */

package Utilities;

public class IllegalMoveException extends Exception {
	public IllegalMoveException(String message) {
		super(message);
	}
}