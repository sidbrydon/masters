/**
Comment: Nim Project C
Class: Nimsys
Andrew Brydon, May 2015

The game begins with a number of objects (e.g., stones placed on a table).
Each player takes turns removing stones from the table.

This has been further expanded to in include automated AI characteristics

The Nimsys Class is used to manage interaction for adding players, removing
players, showing and updating statistics, showing rankings and interactions for
the game are passed to NimGame.

This version has the addition of an AI player and advanced games
*/

/**
 * Import the relevant Java Utilities for:
 * Keyboard input 
 * Array List interaction 
 * File creation and interaction
 * Exception Handling
 **/
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Comparator;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.util.Collections;


/* Declaration of the Nimsys Class */
public class Nimsys 
{
    /** Declare static scanner object that will be globally referenced **/
    public static final Scanner KEYBOARD = new Scanner(System.in);
    /** Static declaration of the maximum number of players **/
    private static final int MAX_NO_OF_PLAYERS = 100;        
    /** Declare the array for the players, there can be up to 100 total **/
//    private static NimPlayer[] players = new NimPlayer[MAX_NO_OF_PLAYERS];
    public static final ArrayList<NimPlayer> playersAL = 
            new ArrayList<>(MAX_NO_OF_PLAYERS);
    /** Declare the array for the commands list, there can be up to 10 total **/
    private static String[] commandsList;
    /** Sets the number of players to 0 or the number in the file later **/
    private static int playercount = 0;
    /** Declare a static delimiter for splitting Strings */
    private static String delimiter = ",";
    /** Declare the String to manage the entered commands */
    private static String commandEnter;
    /** Ensure the command line stays open until exit is entered */
    private static boolean keepPlaying = true;
    /** Test if file exists */
    private static boolean testFile = false;
    /** For testing if a command is valid */
    private static boolean isCommand = false;
    /** The name of the file store the player data */
    private static String fileName = "players.dat";
    /** The file object that will be used for the player data later */
    private static File playerFile;
    
    /** Initiate the main method to run the program 
     * @param args for the main method 
     **/
    public static void main(final String[] args) {
        playerFile = new File(fileName);
        fileTest(playerFile);
        fileImport(playerFile);
        /* The beginning of the Nim came and user interaction */
        System.out.println("Welcome to Nim");

        /* While loop to ensure the game keeps running until exit is typed */
        while (runStatus() == true) {
            System.out.println();
            System.out.printf(">");
            Nimsys.commandEnter = KEYBOARD.nextLine();
            Nimsys.commandEnter = commandEnter.trim();

            /* Run the command entered, keep asking for input until exit */
            runningCommands(Nimsys.commandEnter);
        }
    }

    /** Method to take the commands and run them */
    private static void runningCommands(String commandtoRun) {
        commandtoRun = commandtoRun.trim();
        final String[] arrayCommand = commandtoRun.split(" ", 2);
        arrayCommand[0] = arrayCommand[0].trim();
        try {
            switch (arrayCommand[0]) {
            /* This command will finally exit the program */
            case "exit":
                /* This takes 0 commands */
                if (arrayCommand.length != 1)
                    throw new inputException(2, 2);
                exitFinal();
                System.out.println();
                System.exit(0);
                keepPlaying = false;
                break;

            /* This command will add a player */
            case "addplayer":
                if (arrayCommand.length == 1)
                    throw new inputException(2, 2);
                final String[] addpArray = arrayCommand[1].split(",", 3);
                if (addpArray.length < 3)
                    throw new inputException(2, 2);
                else
                    addplayer(addpArray[0], addpArray[1], addpArray[2], false);
                break;

            /* This command will add an automnomous player */
            case "addaiplayer":
                if (arrayCommand.length == 1)
                    throw new inputException(2, 2);
                final String[] addaipArray = arrayCommand[1].split(",", 3);
                if (addaipArray.length < 3)
                    throw new inputException(2, 2);
                else
                    addaiplayer(addaipArray[0], addaipArray[1], addaipArray[2]);
                break;

            /* This case will remove a player or all players */
            case "removeplayer":
                if (arrayCommand.length == 1) {
                    System.out.println("Are you sure you want to " + "remove all players? (y/n)");
                    String confdeny = KEYBOARD.nextLine();
                    confdeny = confdeny.trim();
                    if ("y".equalsIgnoreCase(confdeny))
                        removePlayer();
                    else
                        break;
                } else
                    removePlayer(arrayCommand[1]);
                break;

            /* This case will edit a player */
            case "editplayer":
                final String[] editpArray = arrayCommand[1].split(",", 3);
                editplayer(editpArray[0], editpArray[1], editpArray[2]);
                break;

            /* This case will reset a player or all players statistics */
            case "resetstats":
                if (arrayCommand.length == 1) {
                    System.out.println("Are you sure you want to " + "reset all player statistics? (y/n)");
                    String confdeny = KEYBOARD.nextLine();
                    confdeny = confdeny.trim();
                    if ("y".equalsIgnoreCase(confdeny))
                        resetStats();
                    else
                        break;
                } else
                    resetStats(arrayCommand[1]);
                break;

            /* This case will display a player or all players ordered user */
            case "displayplayer":
                if (arrayCommand.length == 1)
                    displayPlayer();
                else
                    displayPlayer(arrayCommand[1]);
                break;

            /* This case will rank all players ordered by won and user */
            case "rankings":
                if (arrayCommand.length != 1)
                    throw new inputException(2, 2);
                rankings();
                break;

            /* Play the NimGame */
            case "startgame":
                if (arrayCommand.length == 1)
                    throw new inputException(2, 2);
                arrayCommand[1] = arrayCommand[1].trim();
                final String[] startgArray = arrayCommand[1].split(",", 4);
                if (startgArray.length < 4)
                    throw new inputException(2, 2);
                final int tempinit = Integer.parseInt(startgArray[0]);
                final int tempupbd = Integer.parseInt(startgArray[1]);
                startgArray[3] = startgArray[3].trim();
                startGame(tempinit, tempupbd, startgArray[2], startgArray[3]);
                break;

            /* Play the Advanced Nim Game */
            case "startadvancedgame":
                /* startadvancedgame initialstones,username1,username2 */
                if (arrayCommand.length == 1)
                    throw new inputException(2, 2);
                arrayCommand[1] = arrayCommand[1].trim();
                final String[] startadvArray = arrayCommand[1].split(",", 3);
                if (startadvArray.length < 3)
                    throw new inputException(2, 2);
                final int tempadvi = Integer.parseInt(startadvArray[0]);
                startadvArray[1] = startadvArray[1].trim();
                startadvArray[2] = startadvArray[2].trim();
                startadvancedgame(tempadvi, startadvArray[1], startadvArray[2]);
                break;

            default:
                final boolean helpCommand = checkCommand(arrayCommand[0]);
                if (helpCommand == false) {
                    throw new inputException(arrayCommand[0]);
                }
                break;
            }
        } catch (final inputException e) {
            System.out.println(e.getCommand());
        }
    }

    /* Checks to see if the command exists */
    private static boolean checkCommand(final String inputCommand) {
        /* Declares all the commands that are to be available */
        final String[] listofcommands = { "addplayer", "addaiplayer", "removeplayer", "editplayer", "resetstats",
                "displayplayer", "rankings", "startgame", "startadvancedgame", "countofplayers", "exit" };

        boolean rightCommand = false;
        for (final String realcommand : listofcommands) {
            if (realcommand.equals(inputCommand))
                rightCommand = true;
        }
        return rightCommand;
    }

    /* Tests to see if the file already exists */
    private static boolean fileTest(final File forTest) {
        testFile = forTest.exists();
        return testFile;
    }

    /* Creates the file for writing to */
    private static File fileCreation() {
        Nimsys.playerFile = new File(fileName);
        return Nimsys.playerFile;
    }

    /* Imports the file with all the player details in it */
    private static void fileImport(final File forImport) {
        Scanner inputStream = null;
        String iuname, isname, ifname;
        int igetply, igetwon, iperwon;
        String lineImport = null;
        String[] playerImport;
        boolean isaiornot;

        if (fileTest(Nimsys.playerFile)) {
            try {
                inputStream = new Scanner(new FileInputStream(forImport));
            } catch (final FileNotFoundException e) {
                System.out.println("No data to import");
            }
            playercount = inputStream.nextInt();
            inputStream.nextLine();

            while (inputStream.hasNextLine()) {
                for (int readin = 0; readin < playercount; readin++) {
                    lineImport = inputStream.nextLine();
                    playerImport = lineImport.split(",", 7);

                    iuname = playerImport[0];
                    isname = playerImport[1];
                    ifname = playerImport[2];

                    igetply = Integer.parseInt(playerImport[3]);
                    igetwon = Integer.parseInt(playerImport[4]);
                    iperwon = Integer.parseInt(playerImport[5]);

                    isaiornot = Boolean.parseBoolean(playerImport[6]);

                    if (!isaiornot)
                        Nimsys.playersAL.add(readin, new NimHumanPlayer(iuname, isname, ifname) {
                        });
                    else
                        Nimsys.playersAL.add(readin, new NimAIPlayer(iuname, isname, ifname));

                    Nimsys.playersAL.get(readin).setPlayed(igetply);
                    Nimsys.playersAL.get(readin).setWon(igetwon);
                }
            }
            inputStream.close();
        }

    }

    /* Exports all the player data to the file */
    private static void fileExport() {
        PrintWriter outputStream = null;
        try {
            outputStream = new PrintWriter(new FileOutputStream(fileCreation()));
        } catch (final FileNotFoundException e) {
            System.out.println("Error opening the file players.dat");
        }

        outputStream.println(Nimsys.playercount);
        for (int ops = 0; ops < Nimsys.playercount; ops++) {
            outputStream.println(Nimsys.playersAL.get(ops).getUsername() + "," + Nimsys.playersAL.get(ops).getSurname()
                    + "," + Nimsys.playersAL.get(ops).getForename() + "," + Nimsys.playersAL.get(ops).getPlayed() + ","
                    + Nimsys.playersAL.get(ops).getWon() + "," + Nimsys.playersAL.get(ops).getPercentwins() + ","
                    + Nimsys.playersAL.get(ops).getAIStatus());
        }
        outputStream.close();
        // System.out.println("End of program.");
    }

    /* Returns true for as long as exit is not typed */
    private static boolean runStatus() {
        return keepPlaying;
    }

    /* Runs the final exit and runs the file Export method */
    private static void exitFinal() {
        if (!(playersAL == null))
            fileExport();
        else
            System.exit(0);
    }

    /* Method to add the player to the NimPlayer array */
    private static void addplayer(String uname, String sname, String fname, final boolean isAI) {
        /**
         * This will add and create a NimPlayer to the array of players Checks to see if
         * the player exists and errors if it does
         */
        final int plexists = 0;
        boolean playerex = false;

        /* Ensure no whitespaces in string */
        uname = uname.trim();
        sname = sname.trim();
        fname = fname.trim();

        if (playercount == 0) {
            if (isAI) {
                playersAL.add(new NimAIPlayer(uname, sname, fname));
                playercount = playersAL.size();
            } else {
                playersAL.add(new NimHumanPlayer(uname, sname, fname));
                playercount = playersAL.size();
            }
        } else {
            for (int addid = 0; addid < playercount; addid++) {
                final String tempname = playersAL.get(addid).getUsername();
                if (tempname.equals(uname)) {
                    playerex = true;
                    break;
                }
            }
            if (!playerex && !isAI) {
                playersAL.add(new NimHumanPlayer(uname, sname, fname));
                playercount = playersAL.size();
            } else if (!playerex && isAI) {
                playersAL.add(new NimAIPlayer(uname, sname, fname));
                playercount = playersAL.size();
            } else
                System.out.println("The player already exists.");
        }
        /* End of addPlayer */
    }

    /* Method to add and AI player to the NimPlayer array */
    private static void addaiplayer(final String uname, final String sname, final String fname) {
        addplayer(uname, sname, fname, true);
    }

    /* Removes all players and sets all objects in array to null again */
    private static void removePlayer() {
        playersAL.clear();
        playercount = 0;
    }

    /* Find the user with user name, then remove them from the array */
    private static void removePlayer(final String userName) {
        boolean removepl = false;
        for (int removeid = 0; removeid < playercount; removeid++) {
            if (userName.equals(playersAL.get(removeid).getUsername())) {
                playersAL.remove(removeid);
                playercount--;
                removepl = true;
            }
        }
        if (removepl == false)
            System.out.println("The player does not exist.");
        /* End of removePlayer */
    }

    /* Find the user with user name, edit there details, but not username */
    private static void editplayer(final String editUname, final String editSname, final String editFname) {
        boolean editfind = false;
        for (final NimPlayer editPly : playersAL) {
            if (editUname.equalsIgnoreCase(editPly.getUsername())) {
                editPly.setSurname(editSname);
                editPly.setForename(editFname);
                editfind = true;
            }
        }
        if (editfind == false)
            System.out.println("The player does not exist.");
        /* End of editPlayer */
    }

    /* Resets all players stats to 0 */
    private static void resetStats() {
        for (final NimPlayer resetPly : playersAL)
            resetPly.setStats();
    }

    /* Resets a specific stats to 0, found by user name */
    private static void resetStats(final String resetUname) {
        boolean checkreset = false;
        for (final NimPlayer resetPly : playersAL) {
            if (resetUname.equalsIgnoreCase(resetPly.getUsername())) {
                resetPly.setStats();
                checkreset = true;
            }
        }
        if (checkreset == false)
            System.out.println("The player does not exist.");
        /* End of resetStats */
    }

    /**
     * Method displayPlayer, Display a specific player, found by user name with:
     * Username, Forename, Surname, Games Played and Games Won
     */
    private static void displayPlayer(final String dispUser) {
        boolean founduser = false;
        for (final NimPlayer playersAL1 : playersAL) {
            if (dispUser.equalsIgnoreCase(playersAL1.getUsername())) {
                System.out.println(
                        playersAL1.getUsername() + "," + playersAL1.getForename() + "," + playersAL1.getSurname() + ","
                                + playersAL1.getPlayed() + " games," + playersAL1.getWon() + " wins");
                founduser = true;
            }
        }
        if (founduser == false)
            System.out.println("The player does not exist.");
    }

    /**
     * Comparator method to check if user exists
     */
    public static Comparator<NimPlayer> PlrNmComp = new Comparator<NimPlayer>() {

        @Override
        public int compare(final NimPlayer plyr1, final NimPlayer plyr2) {
            final String PlayerName1 = plyr1.getUsername().toLowerCase();
            final String PlayerName2 = plyr2.getUsername().toLowerCase();

            // ascending order
            return PlayerName1.compareTo(PlayerName2);

            // descending order
            // return PlayerName2.compareTo(PlayerName1);
        }
    };

    /**
     * Comparator method used sort by player percentage wins
     */
    public static Comparator<NimPlayer> PlrRankComp = new Comparator<NimPlayer>() {

        @Override
        public int compare(final NimPlayer plyr1, final NimPlayer plyr2) {
            final int PlayerWin1 = plyr1.getPercentwins();
            final int PlayerWin2 = plyr2.getPercentwins();

            // ascending order
            return PlayerWin2 - PlayerWin1;

            // descending order
            // return PlayerName2.compareTo(PlayerName1);
        }
    };

    /* Method to sort players based on user name */
    private static void sortPlayersbyUser(final ArrayList<NimPlayer> copyPlayers) {

        Collections.sort(copyPlayers, PlrNmComp);

        for (final NimPlayer copyPlayer : copyPlayers) {
            if (!copyPlayers.isEmpty()) {
                System.out.println(
                        copyPlayer.getUsername() + "," + copyPlayer.getForename() + "," + copyPlayer.getSurname() + ","
                                + copyPlayer.getPlayed() + " games," + copyPlayer.getWon() + " wins");
            } else {
                System.out.printf("");
            }
        }
        /* End of sortPlayersbyUser */
    }

    /* Method to sort for the rankings table by ratio then user name */
    private static void sortForRankings(final ArrayList<NimPlayer> rankPlayers) {
        /* Create a NimPlayer Object to user for sorting */
        final NimPlayer rank;
        int index = 0;

        Collections.sort(rankPlayers, PlrNmComp);
        Collections.sort(rankPlayers, PlrRankComp);

        /* Create the rankings table and output to the screen */
        final String perStr = "%";
        String strgetPer, strfullPer, strFullname;

        for (final NimPlayer element : rankPlayers) {
            if (index <= 10) {
                strgetPer = Integer.toString(element.getPercentwins());
                strfullPer = strgetPer + perStr + " ";
                strFullname = element.getForename() + " " + element.getSurname();
                System.out.printf("%-5s| %02d games | %s%n", strfullPer, element.getPlayed(), strFullname);
                index++;
            }
        }
        /* End of sortforRankings */
    }

    /* Method to display all the sorted players using sorting method above */
    private static void displayPlayer() {
        final ArrayList<NimPlayer> dispPlayers = new ArrayList<>(MAX_NO_OF_PLAYERS);
        dispPlayers.addAll(playersAL);
        sortPlayersbyUser(dispPlayers);
    }

    /* Method for ranking all the players based on winnning ratio */
    private static void rankings() {
        final ArrayList<NimPlayer> statsPlayers = new ArrayList<>(MAX_NO_OF_PLAYERS);
        statsPlayers.addAll(playersAL);
        sortForRankings(statsPlayers);
    }

    /* Method called to start playing the game */
    private static void startGame(final int is, final int ub, final String uname1, final String uname2) {
        /* Play out the game until the end */
        /* Create each player */
        final String playerOne = uname1, playerTwo = uname2;
        final int upBo = ub, iniNo = is;
        int firstplpos = 0, secondplpos = 0;
        boolean onecheck = false, twocheck = false;

        /* Find the first user and confirm they exist */
        for (int pone = 0; pone < playersAL.size(); pone++) {
            if (uname1.equalsIgnoreCase(playersAL.get(pone).getUsername())) {
                firstplpos = pone;
                playersAL.get(firstplpos).gamesPlayed();
                onecheck = true;
            }
        }

        /* Find the second user and confirm they exist */
        for (int ptwo = 0; ptwo < playersAL.size(); ptwo++) {
            if (uname2.equalsIgnoreCase(playersAL.get(ptwo).getUsername())) {
                secondplpos = ptwo;
                playersAL.get(secondplpos).gamesPlayed();
                twocheck = true;
            }
        }

        if (onecheck == false || twocheck == false) {
            System.out.println("One of the players does not exist.");
        } else {
            /* Create the game object then start the game */
            final NimGame game1 = new NimGame(iniNo, upBo, uname1, uname2);

            /* Return the winner of the game */
            final String winner = game1.getWinner();

            /* Add one to the winners stats */
            for (final NimPlayer win : playersAL) {
                if (winner.equalsIgnoreCase(win.getUsername()))
                    win.gamesWon();
            }
        }
        /* End of startGame method */
    }

    /* Method called to start playing the advanced game */
    private static void startadvancedgame(final int isadv, final String uname1, final String uname2) {
        /* Play out the game until the end */
        /* Create each player */
        final String playerOne = uname1, playerTwo = uname2;
        final int upperBound = 2, initialNumber = isadv;
        int firstplpos = 0, index1 = 0, secondplpos = 0, index2 = 0;
        boolean onecheck = false, twocheck = false;
        final boolean isadvgame = true;

        /* Find the first user and confirm they exist */
        for (final NimPlayer one : playersAL) {

            if (uname1.equalsIgnoreCase(one.getUsername())) {
                firstplpos = index1;
                one.gamesPlayed();
                onecheck = true;
            }
            index1++;
        }

        /* Find the second user and confirm they exist */
        for (final NimPlayer two : playersAL) {

            if (uname2.equalsIgnoreCase(two.getUsername())) {
                secondplpos = index2;
                two.gamesPlayed();
                twocheck = true;
            }
            index2++;
        }

        if (onecheck == false || twocheck == false) {
            System.out.println("One of the players does not exist.");
        } else {
            /* Create the game object then start the game */
            final NimGame advGame1 = new NimGame(initialNumber, upperBound, playersAL.get(firstplpos).getUsername(),
                    playersAL.get(secondplpos).getUsername(), isadvgame);

            final String winner = advGame1.getWinner();

            /* Add one to the winners stats */
            for (final NimPlayer win : playersAL)
            {
                if (winner.equalsIgnoreCase(win.getUsername())) win.gamesWon();
            }
        }
    }
/*Final End of Class, no more code after here*/
}