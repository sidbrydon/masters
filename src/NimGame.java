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
    /**
     *
     */
    private static final int _0 = 0;
    /**
     *
     */
    private static final int INT = _0;
    /**
     *
     */
    private static final int INT2 = INT;
    /**
     *
     */
    private static final int INT22 = INT2;
    /**
     *
     */
    private static final int INT222 = INT22;
    /**
     *
     */
    static final int INT2222 = INT222;
    private int stoneCount;
    private int upperBound;
    private NimPlayer plOne;
    NimPlayer plTwo;
    private String showStars;
	String winnerName;
	private String advStars;
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
    public NimGame (final int count, final int bound, final String unamep1, final String unamep2) {
        this.stoneCount = count; /* Current stone count */
        this.upperBound = bound; /* An upper bound on stone removal */
        setPlayerOne(unamep1);
        setPlayerTwo(unamep2);
        plOne.playGame(this, this.stoneCount, this.upperBound);
    }

    /* Constructor to play the advanced NimGame */
    public NimGame(final int ct, final int bd, final String unp1, final String unp2, final boolean adgame) {
        this.stoneCount = ct; /* Current stone count */
        this.upperBound = bd; /* An upper bound on stone removal */
        setPlayerOne(unp1);
        setPlayerTwo(unp2);
        NimGame.gameStatus = new boolean[ct];
        playadvGame(this.stoneCount, this.upperBound);
    }

    /* Constrcutor containing information to play the basic NimGame move */
    int plTurn(int i, final int u, final String pf, final int turnNumber, final NimPlayer player) {
        int stoneTurn;
        setStars(i);

        for (int n = 0; n <= 1; n++) {
            System.out.println();
            System.out.println(i + " stones left: " + (getStars()));
            System.out.println(pf + "'s turn - remove how many?");

            /*
             * Take either the AI move or the human player move and update the game, play
             * until the end.
             */
            stoneTurn = player.RemoveStone(i, u, turnNumber);

            try {
                if (stoneTurn > u || stoneTurn == 0) {
                    n = 0;
                    throw new inputException(u);
                } else {
                    i = i - stoneTurn;
                    n++;
                }
            } catch (final inputException e) {
                System.out.println();
                System.out.println(e.getCommand());
            }
        }
        return i;
    }

    /* Constructor containing information to play the advanced NimGame */
    private void playadvGame(int init, final int up) {
        /**
         * Take upper bound and initial number then ask for number of stone to be
         * removed, then keep repeating until the winner is confirmed
         **/

        /* Get required variables for Player 1 & 2 */
        final String p1Fore = plOne.getForename(), p2Fore = plTwo.getForename();
        final String p1User = plOne.getUsername(), p2User = plTwo.getUsername();
        final String p1Sur = plOne.getSurname(), p2Sur = plTwo.getSurname();
        final boolean p1AI = plOne.getAIStatus(), p2AI = plTwo.getAIStatus();

        String plUser = "", plFore, plSurn, winner;
        boolean aiF, plAI;
        final int pileStones = init;
        int pTurn = 1;

        /* Setup the array with the pile configuration */
        for (int i = 0; i < init; i++)
            NimGame.gameStatus[i] = true;

        setAdvStars(NimGame.gameStatus, pileStones);

        System.out.println();
        System.out.println("Initial stone count: " + init);
        System.out.println("Stones display: " + getAdvStars());
        System.out.println("Player 1: " + p1Fore + " " + p1Sur);
        System.out.println("Player 2: " + p2Fore + " " + p2Sur);

        /**
         * Start the While loop to mange the actual game play
         **/
        aiF = this.plOne.getAIStatus() == true;

        /* Keep looping until exiting the game */
        while (init > 0) {
            if (pTurn % 2 != 0) {

                plUser = p1User;
                plFore = p1Fore;
                plSurn = p1Sur;
                plAI = p1AI;
                if (this.plOne.getAIStatus()) {
                    final NimAIPlayer player = (NimAIPlayer) plOne;
                    init = pladvTurn(pileStones, init, up, plFore, player);
                } else {
                    final NimHumanPlayer player = (NimHumanPlayer) plOne;
                    init = pladvTurn(pileStones, init, up, plFore, player);
                }

            } else {
                plUser = p2User;
                plFore = p2Fore;
                plSurn = p2Sur;
                plAI = p2AI;
                if (this.plTwo.getAIStatus()) {
                    final NimAIPlayer player = (NimAIPlayer) plTwo;
                    init = pladvTurn(pileStones, init, up, plFore, player);
                } else {
                    final NimHumanPlayer player = (NimHumanPlayer) plTwo;
                    init = pladvTurn(pileStones, init, up, plFore, player);
                }

            }

            /* Check to see if this was the last possible move */
            if (init == 0) {
                System.out.println();
                winner = plFore + " " + plSurn + " wins!";
                winner = winner.trim();
                System.out.println("Game Over");
                System.out.println(winner);
            }
            /*
             * Increment the current turn which will swap the player and opponent.
             */
            pTurn += 1;
        }
        this.winnerName = plUser;
    }

    /* Constructor containing information make moves in the advanced NimGame */
    private int pladvTurn(final int p, int i, final int u, final String pf, final NimPlayer player) {

        int tempturn, strstoneTurn1 = 0, strstoneTurn2 = 0;
        String stoneTurn, lastmove;
        final String strstoneTurn = "", inputs;
        boolean avail01, avail02;

        /* Create arrays to capture the pile status and commands */
        final int[] pileturn;
        String[] inputcmd;

        setAdvStars(NimGame.gameStatus, p);

        for (int n = 0; n <= 1; n++) {
            try {
                System.out.println();
                System.out.println(i + " stones left: " + (getAdvStars()));
                System.out.println(pf + "'s turn - which to remove?");

                lastmove = strstoneTurn;

                stoneTurn = player.RemoveadvStone(i, u, n, lastmove);

                /*
                 * Take either the AI move or the human player move and update the game, play
                 * until the end.
                 */
                inputcmd = stoneTurn.split(" ", 2);
                inputcmd[0] = inputcmd[0].trim();
                inputcmd[1] = inputcmd[1].trim();
                strstoneTurn1 = Integer.parseInt(inputcmd[0]);
                strstoneTurn1 = strstoneTurn1 - 1;
                // System.out.println(strstoneTurn1);
                strstoneTurn2 = Integer.parseInt(inputcmd[1]);
                // System.out.println(strstoneTurn2);
                // strstoneTurn = strstoneTurn1+" "+strstoneTurn2;

                if (strstoneTurn2 == 1)
                    tempturn = strstoneTurn1;
                else if (strstoneTurn2 == 2)
                    tempturn = strstoneTurn1 + 1;
                else
                    throw new inputException();

                avail01 = getAvailability(NimGame.gameStatus, strstoneTurn1, p);
                avail02 = getAvailability(NimGame.gameStatus, tempturn, p);

                if (!avail01 || !avail02) {
                    n = 0;
                    throw new inputException();
                } else if (strstoneTurn1 > p) {
                    n = 0;
                    throw new inputException();
                } else {
                    if (strstoneTurn2 == 1) {
                        for (int t = 0; t <= p; t++)
                            if (t == strstoneTurn1)
                                NimGame.gameStatus[t] = false;
                        i--;
                        n++;
                    } else {
                        for (int t = 0; t < p; t++)
                            if (t == strstoneTurn1)
                                NimGame.gameStatus[t] = false;
                        for (int v = 0; v < p; v++)
                            if (v == tempturn)
                                NimGame.gameStatus[v] = false;
                        i = (i - 2);
                        n++;
                    }
                }
            } catch (final inputException e) {
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
    private void setStars(final int stoneNumber) {
        this.showStars = "*";
        for (int maxStars = 2; maxStars <= stoneNumber; maxStars++) {
            this.showStars += " *";
        }
        this.showStars = this.showStars.trim();
    }

    /* Method to set strings of advanced stars */
    private void setAdvStars(final boolean[] advgame, final int pileStones) {
        this.advStars = "";
        String starcr = "";
        boolean taken = true;
        final int advlen = advgame.length;
        int tempnumb;

        for (int maxStars = 0; maxStars < pileStones; maxStars++) {
            taken = advgame[maxStars];
            if (taken == true)
                starcr = "*";
            else
                starcr = "x";

            tempnumb = maxStars + 1;
            this.advStars += "<" + tempnumb + "," + starcr + "> ";
        }
        this.advStars = this.advStars.trim();
    }

    /* Import Player One an confirm as AI or Human */
    private void setPlayerOne(final String unamep) {
        for (int pone = 0; pone < Nimsys.playersAL.size(); pone++) {
            if ((unamep.equals(Nimsys.playersAL.get(pone).getUsername()))) {
                if ((Nimsys.playersAL.get(pone).getAIStatus()))
                    this.plOne = (NimAIPlayer) Nimsys.playersAL.get(pone);
                else
                    this.plOne = (NimHumanPlayer) Nimsys.playersAL.get(pone);
            }
        }
    }

    /* Import Player Two an confirm as AI or Human */
    private void setPlayerTwo(final String unamep) {
        for (int ptwo = 0; ptwo < Nimsys.playersAL.size(); ptwo++) {
            if ((unamep.equals(Nimsys.playersAL.get(ptwo).getUsername()))) {
                if ((Nimsys.playersAL.get(ptwo).getAIStatus()))
                    this.plTwo = (NimAIPlayer) Nimsys.playersAL.get(ptwo);
                else
                    this.plTwo = (NimHumanPlayer) Nimsys.playersAL.get(ptwo);
            }
        }
    }

    /** End of Mutators **/

    /**
     * Accessors Methods
     **/

    /* Method to return the string of stars */
    private boolean getAvailability(final boolean[] list, final int numb, final int pile) {
        try {
            if (numb > list.length)
                throw new inputException();
            else
                this.available = list[numb];
        } catch (final inputException e)
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
    
/*Final End of Class, no more code after here */    
}
