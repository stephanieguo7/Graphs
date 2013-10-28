/*
 * This exception is thrown when there is something wrong with the file. It also provides a 
 * brief explanation of what the format should look like.
 */
public class WrongFormatException extends Exception {
	public WrongFormatException() { //constructor
		super("This file is not formatted correctly: each Vertex and number should be separated by a comma to denote edge." + "\n" + 
					"Each edge should be separated by one space only.");
	}
}//end of WrongFormatException
