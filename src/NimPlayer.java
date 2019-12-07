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

    public void setNumberRemove(int numberRemove) {
        this.numberRemove = numberRemove;
    }

    public NimPlayer(String username, String surname, String forename) {
        setUsername(username);
        setSurname(surname);
        setForename(forename);
        this.numberPlayed = 0;
        this.numberWon = 0;
    }

    /**
     * Mutator Methods
     */

    private void setUsername(String newuser) {
        this.usernamePlayer = newuser;
    }

    public void setForename(String newforename) {
        this.forenamePlayer = newforename;
    }

    public void setSurname(String newsurname) {
        this.surnamePlayer = newsurname;
    }

    public void setPlayed(int updatePlayed) {
        this.numberPlayed = updatePlayed;
    }

    public void setAIStatus(boolean status) {
        this.isAIPlayer = status;
    }

    public void gamesPlayed() {
        this.numberPlayed++;
        setPercentwins();
    }

    public void setWon(int updateWon) {
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

    private void setStone(int number) {
        this.setNumberRemove(number);
    }   
                
    private void setPercentwins()
    {
        double den = getWon();
        double nom = getPlayed();
        double perwins = (den/nom)*100.0;
        perwins = Math.round(perwins);
        int intperwins = (int) perwins;
        this.percentageWins = intperwins;
    }
    /* End of Mutators */
    
    /**
     * Accessor Methods 
     * @return 
     **/
    
    public String getUsername()
    {
        return this.usernamePlayer;
    }
              
    public String getForename ()
    {
        return this.forenamePlayer;
    }
                
    public String getSurname ()
    {
        return this.surnamePlayer;
    }
               
    public int getPlayed ()
    {
        return this.numberPlayed;
    }
                
    public int getWon ()
    {
        return this.numberWon;
    }
    
    public boolean getAIStatus ()
    {
        return this.isAIPlayer;
    }
    
    public int getPercentwins ()
    {
        return this.percentageWins;
    }

    /* Empty classes which are defined in child classes */
    
    /** 
     * Base Class for basic remove stone, overridden in sub-classes
     * @param i
     * @param u
     * @param turnNumber
     * @return 
     */
    public int RemoveStone(int i, int u, int turnNumber) 
    {
        return 1;
    }
       
    /**
     * Base Class for advanced remove stone, overridden in sub-classes
     * @param i
     * @param u
     * @param turnNumber
     * @param lastmove
     * @return
     */
    public String RemoveadvStone(int i,int u,int turnNumber, String lastmove) 
    {
        return "";
    }

/*Final End of Class, no more code after here*/
}
