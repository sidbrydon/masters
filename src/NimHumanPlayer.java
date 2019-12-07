/**
Comment: Nim Project C
Class: NimHumanPlayer
Andrew Brydon, May 2015

This file is associated with Nimsys.java, NimGame.java, NimPlayer.java
NimAIPlayer.java, inputExcepton.java
This file creates all the characteristics associated with the AI player
for the NimGame as a subclass of the NimPlayer utilising Polymorphism.
**/

public class NimHumanPlayer extends NimPlayer
{
    // you may further extend a class or implement an interface
    // to accomplish the task in Section 2.3	
    private NimPlayer newAIPlayer;
    private int numRemove;
        
    /**
     * Creates the empty default object of the Human player
     */
    public NimHumanPlayer ()
    {
        super();
        /* Default empty method */
    }
    
    /** Creates the Human Player as a subclass of the NimPlayer
     * @param aiuser
     * @param aisur
     * @param aifore 
     */
    public NimHumanPlayer(final String aiuser, final String aisur, final String aifore) {
        super(aiuser, aisur, aifore);
        super.setAIStatus(false);
    }

    /**
     * Performs the basic stone removal by interacting with the user
     * 
     * @param i
     * @param u
     * @param turnNumber
     * @return
     */
    @Override
    public int RemoveStone(final int i, final int u, final int turnNumber) {
        int stoneTurn;
        final String winner;
        String strstoneTurn = "";

        strstoneTurn = Nimsys.KEYBOARD.nextLine();
        strstoneTurn = strstoneTurn.trim();
        stoneTurn = Integer.parseInt(strstoneTurn);

        this.numRemove = stoneTurn;

        return this.numRemove;
    }

    /**
     * Performs the advanced move removal using interaction with the user
     * 
     * @param i
     * @param u
     * @param turnNumber
     * @param lastmove
     * @return
     */
    @Override
    public String RemoveadvStone(final int i, final int u, final int turnNumber, final String lastmove)
    {            
        String inputs, stoneTurn = null;        
        
        inputs = Nimsys.KEYBOARD.nextLine();
        inputs = inputs.trim();
        stoneTurn = inputs;
        return stoneTurn;
    }        

/*Final End of Class, no more code after here*/   
}
