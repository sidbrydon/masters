/**
Comment: Nim Project C
Class: NimAIPlayer

This file is associated with Nimsys.java, NimGame.java, NimPlayer.java
NimAIPlayer.java, inputExcepton.java.
This file creates all the characteristics associated with the AI player
for the NimGame as a subclass of the NimPlayer utilising Polymorphism.

This class is provided as a skeleton code for the tasks of 
Sections 2.3, 2.4 and 2.5 in Project C. Add code (do NOT delete any) to it
to finish the tasks. 
	
Coded by: Jin Huang
Modified by: Jianzhong Qi, 29/04/2015
Modified by: Andrew Brydon 25/05/2015
**/

public class NimAIPlayer extends NimPlayer implements Testable
{
    private boolean firstPlayer;
    /**
     * Constructors
     */
    
    /* Create the default NimAIPlayer method*/
    public NimAIPlayer ()
    {
        super();
        /* Default empty method */
    }
    
    /* Create the full NimAIPlayer */
    public NimAIPlayer(String aiuser, String aisur, String aifore) 
    {
        super(aiuser, aisur, aifore);
        super.setAIStatus(true);
    }
    
    /**
     * End of Constructors
     */
    
    /** 
     * Method to perform the AI move taking the:
     * Current Stone Count
     * Upper Bound of Stones
     * Boolean of whether the AI player goes first
     * @param curi
     * @param curu
     * @param turnNo
     * @return 
     **/
    @Override
    public int RemoveStone (int curi, int curu, int turnNo)
    {
        int remai = curi; int M = curu;      
        int moveai = ((remai - 1) % (M + 1));
        
	if (moveai == 0) 
        {
            return (int) Math.round(Math.random() * (M - 1) + 1);
        }
        else 
        {
            return moveai;
        }
    }
    
        /**
     * Method to take input from the game and utilize the advanced move
     * @param i
     * @param u
     * @param turnNumber
     * @param lastmove
     * @return 
     */
    @Override
    public String RemoveadvStone(int i,int u,int turnNumber, String lastmove)
    {            
        String stoneTurn;
        stoneTurn = advancedMove(NimGame.gameStatus,lastmove);
        return stoneTurn;
    }
    
    /**
     * Method to take an advanced move via the AI
     * @param available
     * @param lastMove
     * @return 
     */
    @Override
    public String advancedMove(boolean[] available, String lastMove) 
    {
        // the implementation of the victory
        // guaranteed strategy designed by you
	String move = "";
        int move1 = 0, move2 = 0;
        boolean found = false, found2 = false;
        
        this.firstPlayer = "".equals(lastMove);
        
        while (!found && !found2)
        {
            for(int tonumb=0; tonumb<available.length; tonumb++)            
                if(available[tonumb]==true) 
                { 
                    move1 = tonumb-1;
                    found = true;
                }
            for(int tonumb2=0; tonumb2<available.length; tonumb2++) 
                if(available[tonumb2]==true && ((tonumb2 == move1+1)||(tonumb2 == move1-1)))
                {
                    move2 = 2;
                    found2 = true;
                }
                else 
                {
                    move2 = 1;
                    found2 = true;
                }
        }
        
        
        move = move1 + " " + move2;
        return move;
    }  
/*Final End of Class, no more code after here*/
}
