/**
Comment: Nim Project C
Class: NimGame
Andrew Brydon, May 2015

The game begins with a number of objects (e.g., stones placed on a table).
Each player takes turns removing stones from the table.

This file manages the game and outputs the winner
**/

/* Declaration of the Nimsys Class */
public class NimGame 
{
    private int stoneCount;
    private int upperBound;
    private NimPlayer plOne;
    private NimPlayer plTwo;
    private String showStars, winnerName, advStars;
    public static boolean [] gameStatus;
    private boolean available;
        
    /**
     * Constructor Methods 
     **/
    
    /* Default empty method */     
    public NimGame ()
    { 
    }
    
    /* Constructor to play the basic NimGame */
    public NimGame (int count, int bound, String unamep1, String unamep2)
    {
        this.stoneCount = count; /* Current stone count*/
        this.upperBound = bound; /* An upper bound on stone removal */
        setPlayerOne (unamep1);
        setPlayerTwo (unamep2);
        playGame(this.stoneCount,this.upperBound);        
    }

    /* Constructor to play the advanced NimGame */
    public NimGame (int ct, int bd,String unp1, String unp2, boolean adgame)
    {
        this.stoneCount = ct; /* Current stone count*/
        this.upperBound = bd; /* An upper bound on stone removal */
        setPlayerOne (unp1);
        setPlayerTwo (unp2);
        NimGame.gameStatus = new boolean[ct];
        playadvGame(this.stoneCount,this.upperBound);        
    }
    
    /* Constructor containing information to play the basic NimGame */
    private void playGame(int initial, int upper)
    {        
        String plUser, plFore, plSurn;
        String opUser = null, opFore, opSurn, winner;
        boolean plAIuser, opAIuser;
        
        /* Set a variable up that can be used to switch between players */        
        int pTurn = 1;
        
        /* Get required variables for Player 1 & 2*/
        String p1Fore = plOne.getForename(), p2Fore = plTwo.getForename();
        String p1User = plOne.getUsername(), p2User = plTwo.getUsername();
        String p1Sur = plOne.getSurname(), p2Sur = plTwo.getSurname();
        boolean p1AI = plOne.getAIStatus(), p2AI = plTwo.getAIStatus();
        
        /* Start the game with the game configuration and continue */
        System.out.println();
        System.out.println("Initial stone count: " + initial);
        System.out.println("Maximum stone removal: " + upper);
        System.out.println("Player 1: " + p1Fore + " " + p1Sur);
        System.out.println("Player 2: " + p2Fore + " " + p2Sur);
        

        /* Keep looping until exiting the game*/
        while (initial > 0)
        {
            if (pTurn % 2 != 0)
            {
                plUser = p1User; plFore = p1Fore; 
                plSurn = p1Sur; plAIuser = p1AI;
                opUser = p2User; opFore = p2Fore; 
                opSurn = p2Sur; opAIuser = p2AI;
                if (this.plOne.getAIStatus())
                {
                    NimAIPlayer player = (NimAIPlayer)this.plOne;
                    initial = plTurn(initial,upper,plFore,pTurn, player);
                }
                else 
                {
                    NimHumanPlayer player = (NimHumanPlayer)this.plOne;
                    initial = plTurn(initial,upper,plFore,pTurn, player);
                }                     
            }
            else
            {
                plUser = p2User; plFore = p2Fore; 
                plSurn = p2Sur; plAIuser = p2AI;
                opUser = p1User; opFore = p1Fore; 
                opSurn = p1Sur; opAIuser = p1AI;
                if (this.plTwo.getAIStatus())
                {
                    NimAIPlayer player = (NimAIPlayer)this.plTwo;
                    initial = plTurn(initial,upper,plFore,pTurn, player);
                }
                else 
                {
                    NimHumanPlayer player = (NimHumanPlayer)this.plTwo;
                    initial = plTurn(initial,upper,plFore,pTurn, player);
                } 
                
            }
            
            /* Check to see if this was the last possible move */
            if (initial == 0) 
            {
                System.out.println();
                winner = opFore + " " + opSurn + " wins!";
                winner = winner.trim();
                System.out.println("Game Over");
                System.out.println(winner);
            }
            /* Increment the current turn which will swap the player
            and opponent.*/
            pTurn += 1;
        }
        this.winnerName = opUser;
    }    
    
    /* Constrcutor containing information to play the basic NimGame move */
    private int plTurn(int i,int u,String pf,int turnNumber,NimPlayer player)
    {
        int stoneTurn;    
        setStars(i); 
                
     
        for (int n =0; n<=1; n++)
        {    
            System.out.println();
            System.out.println (i + " stones left: " + (getStars()));
            System.out.println(pf + "'s turn - remove how many?");   

            /* Take either the AI move or the human player move and
            update the game, play until the end. */
            stoneTurn = player.RemoveStone(i, u, turnNumber);
            

            try 
            {
                if (stoneTurn > u || stoneTurn == 0) 
                {
                    n=0;
                    throw new inputException (u);
                }
                else 
                {    
                    i = i - stoneTurn;
                    n++;
                }
            }
            catch (inputException e)
            {
                System.out.println();
                System.out.println(e.getCommand());   
            }    
        }
        return i;  
    }   
    
    /* Constructor containing information to play the advanced NimGame */
    private void playadvGame(int init, int up)
    {
         /** 
         * Take upper bound and initial number then 
         * ask for number of stone to be removed, then
         * keep repeating until the winner is confirmed
         **/
      
        /* Get required variables for Player 1 & 2*/
        String p1Fore = plOne.getForename(), p2Fore = plTwo.getForename();
        String p1User = plOne.getUsername(), p2User = plTwo.getUsername();
        String p1Sur = plOne.getSurname(), p2Sur = plTwo.getSurname();
        boolean p1AI = plOne.getAIStatus(), p2AI = plTwo.getAIStatus();
        
        String plUser ="", plFore, plSurn, winner;
        boolean aiF, plAI;
        int pileStones = init, pTurn = 1;
        
        /* Setup the array with the pile configuration */
        for(int i=0; i<init;i++) NimGame.gameStatus[i] = true;
        
        setAdvStars(NimGame.gameStatus,pileStones);
        
        System.out.println();
        System.out.println("Initial stone count: " + init);
        System.out.println("Stones display: " + getAdvStars());
        System.out.println("Player 1: " + p1Fore + " " + p1Sur);
        System.out.println("Player 2: " + p2Fore + " " + p2Sur);
        
        /**
         * Start the While loop to mange the actual game play 
         **/
        aiF = this.plOne.getAIStatus() == true;
        
        /* Keep looping until exiting the game*/
        while (init > 0)
        {
            if (pTurn % 2 != 0)
            {

                plUser = p1User; plFore = p1Fore; plSurn = p1Sur;
                plAI = p1AI;
                if (this.plOne.getAIStatus())
                {
                    NimAIPlayer player = (NimAIPlayer)plOne;
                    init = pladvTurn(pileStones,init,up, plFore, player);
                }
                else 
                {
                    NimHumanPlayer player = (NimHumanPlayer)plOne;
                    init = pladvTurn(pileStones,init,up, plFore, player);
                }


            }
            else
            {
                plUser = p2User; plFore = p2Fore; plSurn = p2Sur;
                plAI = p2AI;
                if (this.plTwo.getAIStatus())
                {
                    NimAIPlayer player = (NimAIPlayer)plTwo;
                    init = pladvTurn(pileStones,init,up, plFore, player);
                }
                else 
                {
                    NimHumanPlayer player = (NimHumanPlayer)plTwo;
                    init = pladvTurn(pileStones,init,up, plFore, player);
                }
                
            }
                      
            /* Check to see if this was the last possible move */
            if (init == 0) 
            {
                System.out.println();
                winner = plFore + " " + plSurn + " wins!";
                winner = winner.trim();
                System.out.println("Game Over");
                System.out.println(winner);
            }
            /* Increment the current turn which will swap the player
            and opponent.*/
            pTurn += 1;
        }
        this.winnerName = plUser;
    } 
    
    /* Constructor containing information make moves in the advanced NimGame */
    private int pladvTurn(int p,int i,int u,String pf, NimPlayer player)
    {

        int tempturn, strstoneTurn1 = 0, strstoneTurn2 = 0;     
        String stoneTurn, lastmove, strstoneTurn ="", inputs;        
        boolean avail01, avail02;
        
        /* Create arrays to capture the pile status and commands */
        int[] pileturn;
        String[] inputcmd;
        
        
        setAdvStars(NimGame.gameStatus,p); 
        
        for (int n=0; n<=1; n++)
        {    
          try 
            {
            System.out.println();
            System.out.println (i + " stones left: " + (getAdvStars()));
            System.out.println(pf + "'s turn - which to remove?");   
            
            lastmove = strstoneTurn;
            
            stoneTurn = player.RemoveadvStone(i,u,n,lastmove);

            /* Take either the AI move or the human player move and
            update the game, play until the end. */
            inputcmd = stoneTurn.split(" ", 2);
            inputcmd[0]=inputcmd[0].trim();
            inputcmd[1]=inputcmd[1].trim();
            strstoneTurn1 = Integer.parseInt(inputcmd[0]);
            strstoneTurn1 = strstoneTurn1-1;
//            System.out.println(strstoneTurn1);
            strstoneTurn2 = Integer.parseInt(inputcmd[1]);
//            System.out.println(strstoneTurn2);
//            strstoneTurn = strstoneTurn1+" "+strstoneTurn2;
            

                if (strstoneTurn2==1) tempturn = strstoneTurn1;
                else if (strstoneTurn2==2) tempturn = strstoneTurn1+1;
                else throw new inputException();
            
                avail01 = getAvailability (NimGame.gameStatus,strstoneTurn1,p);
                avail02 = getAvailability (NimGame.gameStatus,tempturn,p);

                if (!avail01 || !avail02)
                {
                    n=0;
                    throw new inputException();
                }
                else if (strstoneTurn1 > p)
                {
                    n=0;
                    throw new inputException();
                }                
                else
                {
                    if (strstoneTurn2==1)
                    {
                        for (int t=0;t<=p;t++) 
                            if(t==strstoneTurn1)NimGame.gameStatus[t]=false;
                        i--;
                        n++;
                    }    
                    else
                    {   
                        for (int t=0;t<p;t++) 
                            if(t==strstoneTurn1)NimGame.gameStatus[t]=false;
                        for (int v=0;v<p;v++) 
                            if(v==tempturn)NimGame.gameStatus[v]=false;
                        i=(i-2);
                        n++;                              
                    }    
                }
            }
            catch (inputException e)
            {
                System.out.println();
                System.out.println(e.getCommand());   
            }    
        }
        return i;  
    }
        
    /* End of Constructors */
    
    /** 
     * Mutator Methods 
     **/
    
    /* Method to set strings of stars */
    private void setStars(int stoneNumber)
    {
        this.showStars ="*";     
        for (int maxStars = 2; maxStars <= stoneNumber; maxStars++)
        {
            this.showStars += " *";
        }
            this.showStars = this.showStars.trim();                  
    }
    
    /* Method to set strings of advanced stars */
    private void setAdvStars(boolean[] advgame, int pileStones)
    {
        this.advStars =""; String starcr ="";
        boolean taken = true;
        int advlen = advgame.length, tempnumb;
        
        for (int maxStars = 0; maxStars < pileStones; maxStars++)
        {
            taken = advgame[maxStars];
            if (taken == true)starcr ="*";
            else starcr="x";
            
            tempnumb = maxStars+1;
            this.advStars += "<" + tempnumb + "," + starcr + "> ";
        }
        this.advStars = this.advStars.trim();
    }
    
    /* Import Player One an confirm as AI or Human */
    private void setPlayerOne (String unamep)
    {
        for (int pone = 0; pone < Nimsys.playersAL.size(); pone ++)
        {
            if ((unamep.equals(Nimsys.playersAL.get(pone).getUsername())))
            {
                if ((Nimsys.playersAL.get(pone).getAIStatus()))
                    this.plOne = (NimAIPlayer)Nimsys.playersAL.get(pone);
                else this.plOne = (NimHumanPlayer)Nimsys.playersAL.get(pone);
            }
        }
    }
    
    /* Import Player Two an confirm as AI or Human */
    private void setPlayerTwo (String unamep)
    {
        for (int ptwo = 0; ptwo < Nimsys.playersAL.size(); ptwo ++)
        {
            if ((unamep.equals(Nimsys.playersAL.get(ptwo).getUsername())))
            {
                if ((Nimsys.playersAL.get(ptwo).getAIStatus()))
                    this.plTwo = (NimAIPlayer)Nimsys.playersAL.get(ptwo);
                else this.plTwo = (NimHumanPlayer)Nimsys.playersAL.get(ptwo);
            }
        }
    }
    
    /** End of Mutators **/
   
    /**
     * Accessors Methods 
     **/
    
    /* Method to return the string of stars */
    private boolean getAvailability (boolean[] list, int numb, int pile)
    {
        try
        {    
            if (numb > list.length) throw new inputException();
            else this.available = list[numb];
        }
        catch (inputException e)
        {
            System.out.println();            
            System.out.println(e.getCommand());
        }
        return this.available;
    }
    
    /**
     *
     * @return
     */
    public boolean[] getAdvGameState ()
    {
        return NimGame.gameStatus;
    }
    
    public String getStars()
    {
        return this.showStars;                    
    }
    
    public String getAdvStars()
    {
        return this.advStars;
    }
    
    /* Method to retun the winner of the game to Nimsys */
    public String getWinner()
    {
        return this.winnerName;
    }
    /* End of Accessors */
    
/*Final End of Class, no more code after here*/    
}