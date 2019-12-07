/**
Comment: Nim Project C
Class: inputException
Andrew Brydon, May 2015

The game begins with a number of objects (e.g., stones placed on a table).
Each player takes turns removing stones from the table.

This file manages the exceptions seen in the game
*/

/**
 * Class created that will be referenced for the custom exceptions in the code
 */
public class inputException extends Exception
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String inputCheck;
    
    /**
     * Constructors, referenced from the base Class
     */
    public inputException ()
    {
        inputCheck = "Invalid move.";
    }
             
    public inputException (final int inpEntered) {
        inputCheck = "Invalid move. You must remove between 1 and " + inpEntered + " stones.";
        inputCheck = inputCheck.trim();
    }

    public inputException(final int comEntered, final int otherEntered) {
        inputCheck = "Incorrect number of arguments supplied to command.";
        inputCheck = inputCheck.trim();
    }

    public inputException(final String badCommand)
    {   
        inputCheck = "'" + badCommand + "' is not a valid command.";
    }
    
    /**
     * Return the String error command to the Catch statement
     * @return 
     */
    public String getCommand ()
    {
        return inputCheck;
    }
/*Final End of Class, no more code after here*/
}
