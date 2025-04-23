import java.util.ArrayList;

/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.
 * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class CommandWords {
    // a constant array that holds all valid command words
    private static  ArrayList<String> validCommands = new ArrayList<String>();


  
    /**
     * Constructor - initialise the command words.
     */
    public CommandWords() {
      validCommands.add("go");
      validCommands.add("quit");
      validCommands.add("help");
      validCommands.add("look");
      validCommands.add("inventory");
      validCommands.add("use");
      validCommands.add("grab");
      validCommands.add("refill");
    }


  
    /**
     * Check whether a given String is a valid command word. 
     * @return true if it is, false if it isn't.
     */
    public boolean isCommand(String aString) {
        for(int i = 0; i < validCommands.size(); i++) {
            if(validCommands.get(i).equals(aString))
                return true;
        }
        // if we get here, the string was not found in the commands
        return false;
    }

  

    /**
     * Print all valid commands to System.out.
     */
    public void showAll() {
        for(String command: validCommands) {
            System.out.print(command + "  ");
        }
        System.out.println();
    }


  
    /**
     * Changes the commandWords ArrayList to a new ArrayList
     * @param newWords (ArrayList<String>)  The new commandWords list
     */
    public static void setCommandWords(ArrayList<String> newWords) {
      validCommands = new ArrayList<String>();
      for (String word : newWords)
        validCommands.add(word);
    }
}
