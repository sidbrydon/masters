/**
 * Comment: Nim Project C Class: NimPlayer Andrew Brydon, May 2015
 * 
 * This file is associated with Nimsys.java, NimGame.java, NimPlayer.java
 * NimAIPlayer.java, inputExcepton.java This file creates all the
 * characteristics associated with the player for the NimGame.
 */

/* Create the public Class that defines the players and player interaction */
public class NimPlayer {
    /**
     * Declare the variables that are to be consistent across the program
     */
    private String usernamePlayer;
    private String forenamePlayer;
    private String surnamePlayer;
    private int numberPlayed;
    private int numberWon;
    private int numberRemove;
    private int percentageWins;
    private boolean isAIPlayer;

    /* Declare the constructor method to get the player details */
    public NimPlayer() {
        /* Default empty method */
    }

    public int getNumberRemove() {
        return numberRemove;
    }

    public void setNumberRemove(final int numberRemove) {
        this.numberRemove = numberRemove;
    }

    public NimPlayer(final String username, final String surname, final String forename) {
        setUsername(username);
        setSurname(surname);
        setForename(forename);
        this.numberPlayed = 0;
        this.numberWon = 0;
    }

    /**
     * Mutator Methods
     */

    private void setUsername(final String newuser) {
        this.usernamePlayer = newuser;
    }

    public void setForename(final String newforename) {
        this.forenamePlayer = newforename;
    }

    public void setSurname(final String newsurname) {
        this.surnamePlayer = newsurname;
    }

    public void setPlayed(final int updatePlayed) {
        this.numberPlayed = updatePlayed;
    }

    public void setAIStatus(final boolean status) {
        this.isAIPlayer = status;
    }

    public void gamesPlayed() {
        this.numberPlayed++;
        setPercentwins();
    }

    public void setWon(final int updateWon) {
        this.numberWon = updateWon;
        setPercentwins();
    }

    public void gamesWon() {
        this.numberWon++;
        setPercentwins();
    }

    public void setStats() {
        this.numberPlayed = 0;
        this.numberWon = 0;
        this.percentageWins = 0;
    }

    private void setPercentwins() {
        final double den = getWon();
        final double nom = getPlayed();
        double perwins = (den / nom) * 100.0;
        perwins = Math.round(perwins);
        final int intperwins = (int) perwins;
        this.percentageWins = intperwins;
    }
    /* End of Mutators */

    /**
     * Accessor Methods
     * 
     * @return
     **/

    public String getUsername() {
        return this.usernamePlayer;
    }

    public String getForename() {
        return this.forenamePlayer;
    }

    public String getSurname() {
        return this.surnamePlayer;
    }

    public int getPlayed() {
        return this.numberPlayed;
    }

    public int getWon() {
        return this.numberWon;
    }

    public boolean getAIStatus() {
        return this.isAIPlayer;
    }

    public int getPercentwins() {
        return this.percentageWins;
    }

    /* Empty classes which are defined in child classes */

    /**
     * Base Class for basic remove stone, overridden in sub-classes
     * 
     * @param i
     * @param u
     * @param turnNumber
     * @return
     */
    public int RemoveStone(final int i, final int u, final int turnNumber) {
        return 1;
    }

    /**
     * Base Class for advanced remove stone, overridden in sub-classes
     * 
     * @param i
     * @param u
     * @param turnNumber
     * @param lastmove
     * @return
     */
    public String RemoveadvStone(final int i, final int u, final int turnNumber, final String lastmove) {
        return "";
    }

    void playGame(final NimGame nimGame, int initial, final int upper) {
	    String plUser, plFore, plSurn;
	    String opUser = null, opFore, opSurn, winner;
	    boolean plAIuser, opAIuser;
	
	    /* Set a variable up that can be used to switch between players */
	    final int pTurn = 1;
	
	    /* Get required variables for Player 1 & 2 */
	    final String p1Fore = getForename(), p2Fore = nimGame.plTwo.getForename();
	    final String p1User = getUsername(), p2User = nimGame.plTwo.getUsername();
	    final String p1Sur = getSurname(), p2Sur = nimGame.plTwo.getSurname();
	    final boolean p1AI = getAIStatus(), p2AI = nimGame.plTwo.getAIStatus();
	
	    /* Start the game with the game configuration and continue */
	    System.out.println();
	    System.out.println("Initial stone count: " + initial);
	    System.out.println("Maximum stone removal: " + upper);
	    System.out.println("Player 1: " + p1Fore + " " + p1Sur);
	    System.out.println("Player 2: " + p2Fore + " " + p2Sur);
	
	    /* Keep looping until exiting the game */
	    while (initial > 0) {
	        if (pTurn % 2 != 0) {
	            plUser = p1User;
	            plFore = p1Fore;
	            plSurn = p1Sur;
	            plAIuser = p1AI;
	            opUser = p2User;
	            opFore = p2Fore;
	            opSurn = p2Sur;
	            opAIuser = p2AI;
	            if (getAIStatus()) {
	                final NimAIPlayer player = (NimAIPlayer) this;
	                initial = nimGame.plTurn(initial, upper, plFore, pTurn, player);
	            } else {
	                final NimHumanPlayer player = (NimHumanPlayer) this;
	                initial = nimGame.plTurn(initial, upper, plFore, pTurn, player);
	            }
	        } else {
	            plUser = p2User;
	            plFore = p2Fore;
	            plSurn = p2Sur;
	            plAIuser = p2AI;
	            opUser = p1User;
	            opFore = p1Fore;
	            opSurn = p1Sur;
	            opAIuser = p1AI;
	            if (nimGame.plTwo.getAIStatus()) {
	                final NimAIPlayer player = (NimAIPlayer) nimGame.plTwo;
	                initial = nimGame.plTurn(initial, upper, plFore, pTurn, player);
	            } else {
	                final NimHumanPlayer player = (NimHumanPlayer) nimGame.plTwo;
	                initial = nimGame.plTurn(initial, upper, plFore, pTurn, player);
	            }
	
	        }
	
	        /* Check to see if this was the last possible move */
	        if (initial == NimGame.INT2222) {
	            System.out.println();
	            winner = opFore + " " + opSurn + " wins!";
	            winner = winner.trim();
	            System.out.println("Game Over");
	            System.out.println(winner);
	        }
	        /*
	         * Increment the current turn which will swap the player and opponent.
	         */
	        pTurn += 1;
	    }
	    nimGame.winnerName = opUser;
	}

	void playGame(final NimGame nimGame, int initial, final int upper) {
	    String plUser, plFore, plSurn;
	    String opUser = null, opFore, opSurn, winner;
	    boolean plAIuser, opAIuser;
	
	    /* Set a variable up that can be used to switch between players */
	    final int pTurn = 1;
	
	    /* Get required variables for Player 1 & 2 */
	    final String p1Fore = getForename(), p2Fore = nimGame.plTwo.getForename();
	    final String p1User = getUsername(), p2User = nimGame.plTwo.getUsername();
	    final String p1Sur = getSurname(), p2Sur = nimGame.plTwo.getSurname();
	    final boolean p1AI = getAIStatus(), p2AI = nimGame.plTwo.getAIStatus();
	
	    /* Start the game with the game configuration and continue */
	    System.out.println();
	    System.out.println("Initial stone count: " + initial);
	    System.out.println("Maximum stone removal: " + upper);
	    System.out.println("Player 1: " + p1Fore + " " + p1Sur);
	    System.out.println("Player 2: " + p2Fore + " " + p2Sur);
	
	    /* Keep looping until exiting the game */
	    while (initial > 0) {
	        if (pTurn % 2 != 0) {
	            plUser = p1User;
	            plFore = p1Fore;
	            plSurn = p1Sur;
	            plAIuser = p1AI;
	            opUser = p2User;
	            opFore = p2Fore;
	            opSurn = p2Sur;
	            opAIuser = p2AI;
	            if (getAIStatus()) {
	                final NimAIPlayer player = (NimAIPlayer) this;
	                initial = nimGame.plTurn(initial, upper, plFore, pTurn, player);
	            } else {
	                final NimHumanPlayer player = (NimHumanPlayer) this;
	                initial = nimGame.plTurn(initial, upper, plFore, pTurn, player);
	            }
	        } else {
	            plUser = p2User;
	            plFore = p2Fore;
	            plSurn = p2Sur;
	            plAIuser = p2AI;
	            opUser = p1User;
	            opFore = p1Fore;
	            opSurn = p1Sur;
	            opAIuser = p1AI;
	            if (nimGame.plTwo.getAIStatus()) {
	                final NimAIPlayer player = (NimAIPlayer) nimGame.plTwo;
	                initial = nimGame.plTurn(initial, upper, plFore, pTurn, player);
	            } else {
	                final NimHumanPlayer player = (NimHumanPlayer) nimGame.plTwo;
	                initial = nimGame.plTurn(initial, upper, plFore, pTurn, player);
	            }
	
	        }
	
	        /* Check to see if this was the last possible move */
	        if (initial == NimGame.INT2222) {
	            System.out.println();
	            winner = opFore + " " + opSurn + " wins!";
	            winner = winner.trim();
	            System.out.println("Game Over");
	            System.out.println(winner);
	        }
	        /*
	         * Increment the current turn which will swap the player and opponent.
	         */
	        pTurn += 1;
	    }
	    nimGame.winnerName = opUser;
	}

/*Final End of Class, no more code after here*/
}
