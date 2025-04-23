import java.util.ArrayList;
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game {
    private Parser parser;
    private Room currentRoom;

    private static boolean step1 = false;
    private static boolean step2 = false;
    private static boolean step3 = false;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() {
        createRooms();
        parser = new Parser();
    }


  
    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms() {
        Room cafeteria, sleepingQuarters, bathroom, security, communications, lounge, storage, airlock;

        ArrayList<Item> tempList = new ArrayList<Item>();
        tempList.add(new Item(false));

        ArrayList<Item> tempList2 = new ArrayList<Item>();
        tempList2.add(new Item(true));
        tempList2.add(new Item(true));

        
        // create the rooms
        cafeteria = new Room("cafeteria", 
                             "inside the cafeteria", 
                             "There is a plate with exactly 3.123 saltine crackers on it.", 
                             "You eat the crackers. You weren't really hungry in the first place, but good job regardless.");
        sleepingQuarters = new Room("sleeping quarters", 
                                    "in the sleeping quarters", 
                                    "There is a nice comfy bed that draws you closer to it every time you look at it.", 
                                    "You spend the next hour taking the best nap of you life. Was it worth the wasted air?");
        bathroom = new Room("bathroom", 
                            "in a restroom", 
                            "The toilet calls your name...", 
                            "You commit unspeakable actions to the toilet for the next 20 minutes.");
        security = new Room("security", 
                            "in the security room", 
                            "There is just a bunch of computers, sensors, and monitors. You are afraid to touch anything in fear of breaking it.", 
                            "Nothing to do here right now, maybe come back later...");
        communications = new Room("communications", 
                                  "in the communications room", 
                                  "Along with the typical computers, there is also a single, 80's style telephone, seeming plugged into thin air.", 
                                 "You pick up the telephone and dial the number given to you during training: 912. Command down at Earth tells you that help is on the way.\n\nNext, go to the SECURITY ROOM to open the airlock.");
        lounge = new Room("lounge", 
                          "in the game room", 
                          "There is an iPad laying on the couch, an iPad you know to have Among Us installed on. There is also a small air tank in the corner.", 
                          "You power on the iPad and hop on a quick game of Among Us. Luckily, you are the imposter, but some 7 year old rats you out to his friends and you are voted out.", 
                          tempList);
        storage = new Room("storage", 
                           "in a random storage room", 
                           "As you enter the storage room, you’re surrounded by a labyrinth of boxes and stacked supplies. The musty smell of aged materials fills the air as you step over boxes of various sizes in your path. Cardboard boxes create an obstacle course down the center of the room. It’s clear that most of the contents of these boxes are just random junk: broken tools, antiques, worthless gadgets, and so much more that they blur together. At the far end of the room two large oxygen tanks sitting against the wall. Thinking back to it, there was something that Command said to do with the supplies sitting next to the boxes, but you might have forgetten about it in the chaotic events that have happened today.", 
                           "You push through the boxes to reveal a bright yellow sticky note. It reads 'Please sort.'. Seeing as you have avoided doing it thus far, you decide to worry about it later.", 
                           tempList2);
        airlock = new Room("airlock", 
                           "next to the airlock", 
                           "You see the door that leads out into space. The rest of the room is empty.", 
                           "The door is locked. Maybe you need to somehow unlock it first...");
        
        // initialise room exits
        cafeteria.setExit("west", storage);
        cafeteria.setExit("south", lounge);
        cafeteria.setExit("up", airlock);

        storage.setExit("east", cafeteria);
        storage.setExit("south", sleepingQuarters); 

        sleepingQuarters.setExit("north", storage);
        sleepingQuarters.setExit("south", bathroom);
        sleepingQuarters.setExit("east", lounge);

        bathroom.setExit("north", sleepingQuarters);
        bathroom.setExit("east", security);

        security.setExit("north", lounge);
        security.setExit("west", bathroom);

        lounge.setExit("north", cafeteria);
        lounge.setExit("west", sleepingQuarters);
        lounge.setExit("south", security);
        lounge.setExit("east", communications);

        communications.setExit("west", lounge);

        airlock.setExit("down", cafeteria);
        currentRoom = cafeteria;  // start game in the cafeteria
    }


  
   /**
        *  Main play routine. Loops until end of play.
        */
    public void play() {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        if (checkLose())
          System.out.println("\n\n\nYou ran out of oxygen, and died.");
        else if (checkWin())
          System.out.println("\n\n\nYou won! Thanks for playing!");
        else 
          System.out.println("\n\n\nGoodbye!");
    }


  
   /**
        * Print out the opening message for the player.
        */
    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to 'Escape the Space Station'");
        System.out.println("Your objective is to open the airlock door");
        System.out.println("and get out, but you must first find the");
        System.out.println("Communications room to contact your team.");
        System.out.println("\nType 'help' if you need help.");
        System.out.println("\n" + currentRoom.getLongDescription());
    }


  
    /**
          * Given a command, process (that is: execute) the command.
          * @param command The command to be processed.
          * @return true If the command ends the game, false otherwise.
          */
    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("\n\nI don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
      
        if (commandWord.equals("help"))
          printHelp();
          
        else if (commandWord.equals("go"))
          goRoom(command);
          
        else if (commandWord.equals("quit"))
          wantToQuit = quit(command);
          
        else if (commandWord.equals("look"))
          look(command);
          
        else if (commandWord.equals("use"))
          use(command);
          
        else if (commandWord.equals("grab"))
          grab(command);
          
        else if (commandWord.equals("refill"))
          refill(command);

        else if (commandWord.equals("drop"))
          drop(command);

        else if (commandWord.equals("inventory"))
          inventory();

        if (checkLose() || checkWin())
          wantToQuit = true;
      
        // else command not recognised.
        return wantToQuit;
    }

  
    // implementations of user commands:

   /**
        * Print out some help information.
        * Here we print some stupid, cryptic message and a list of the 
        * command words.
        */
    private void printHelp() {
        System.out.println("\n\nYou are inside a failing space station. The oxygen has been sucked out of the room and your only source of air is from your space suit.");
        if (!step1)
          System.out.println("\nRight now, you need to head to the COMMUNICATIONS room to contact Command to get you out.");
        else if (step1 && !step2)
          System.out.println("\nNow, you need to go to the SECURITY room to open the airlock and leave.");
        else
          System.out.println("\nYou're almost done! All you need to do is get to the AIRLOCK and get out.");
        System.out.println("\nYour command words are:");
        parser.showCommands();
    }


  
   /** 
        * Try to in to one direction. If there is an exit, enter the new
        * room, otherwise print an error message.
        */
    private void goRoom(Command command) {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("\n\nGo where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("\n\nThere is no door!");
        }

        else {
            currentRoom = nextRoom;
            
            Player.setOxygen(Player.getOxygen() - 10);
          
            if (!checkLose()) {
              System.out.println("\n\n" + currentRoom.getLongDescription());
              System.out.println("\nYou now have " + Player.getOxygen() + "% oxygen left.");
            }
        }
    }

  

   /**
        * Prints room's look description.
        */
    private void look(Command command) { 
        System.out.println("\n\n" + currentRoom.getLookDescription());
        if (currentRoom.getItems().size() > 0)
          System.out.println("\nItems: " + currentRoom.getItems());
        System.out.println("\n" + currentRoom.getExitString());
    }

  

   /** 
        * "Quit" was entered. Check the rest of the command to see.
        * whether we really quit the game.
        * @return true, if this command quits the game, false otherwise.
        */
    private boolean quit(Command command) {
        if(command.hasSecondWord()) {
            System.out.println("\n\nQuit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

  

   /**
        * Prints the room's interact description and updates the step variables if an objective is completed. Also removes 5% oxygen.
        */
    private void use(Command command) {
      if (command.hasSecondWord()) {
            System.out.println("\n\nPlease use only the key word.");
        }
      else {
          if (!currentRoom.getComplete()) {
              
              currentRoom.setComplete(true);
              Player.setOxygen(Player.getOxygen() - 5);
              
              System.out.println("\n\n" + currentRoom.getInteractDescription());
              
              
              if (currentRoom.getName().equals("communications") && !step1) {
                step1 = true;
                Room tempRoom = (currentRoom.getExit("west").getExit("south"));
                tempRoom.setInteractDescription("Amongst the foreign technologies in the room, you notice a big red button that you somehow didn't before. You press it. Now, all you need to do is get to the AIRLOCK and escape the station.");
                return;
              }
                
              else if (currentRoom.getName().equals("security")) { // If it's the security room
                if (step1) {
                  step2 = true;
                  Room tempRoom = currentRoom.getExit("north").getExit("north").getExit("up");
                  tempRoom.setInteractDescription("You pull the lever next to the door and it finally opens. You drift out into space ever so slightly before your team comes and picks you up. You lived this time, but you might not be so lucky next time...");
                }
                else {
                  currentRoom.setComplete(false);
                  Player.setOxygen(Player.getOxygen() + 5);
                }
              }

              else if (currentRoom.getName().equals("airlock")) { // if it's the airlock room
                if (step2)
                  step3 = true;
                else {
                  currentRoom.setComplete(false);
                  Player.setOxygen(Player.getOxygen() + 5);
                }
              }
            
              System.out.println("\n\nYou now have " + Player.getOxygen() + "% oxygen left.");
            }
          else {
            System.out.println("You've already interacted with this room.");
          }
        }
    }


  
 /**
    * Grabs the item corresponding with the number given, considering the list starts at 1.
    * If no number is given, grabs the first item in the list.
    *
    * If the player is carrying too much weight, limits command words to only "drop" and essentials.
    *
    * @param command (Command)  The command object that has the first and second word of the command.
    */
  private void grab(Command command) {

    if (currentRoom.getItems().size() < 1) {
      System.out.println("\nThere is nothing to grab in this room.");
    }
    else if (!command.hasSecondWord()) {
      Player.addTanks(currentRoom.getItems().get(0));
      System.out.println("\nGrabbed " + currentRoom.getItems().get(0) + "!");
      currentRoom.removeItem(0);
    }
    else {
      int choice = Integer.parseInt(command.getSecondWord()) - 1;
      if ((currentRoom.getItems().size() - 1) < choice || choice < 0)
        System.out.println("\nPlease enter a valid number.");
      else {
        Player.addTanks(currentRoom.getItems().get(choice));
        System.out.println("\nGrabbed " + currentRoom.getItems().get(choice) + "!");
        currentRoom.removeItem(choice);
      }
      System.out.println(Player.calculateWeight());
    }
    if (Player.calculateWeight() > 100) {
        System.out.println("\nYou are carrying too much weight.");
        System.out.println("Please enter the 'drop' keyword and the number of the item you want to drop.");
        System.out.println(Player.getTanks());
        
        ArrayList<String> temp = new ArrayList<String>();
        temp.add("quit");
        temp.add("help");
        temp.add("inventory");
        temp.add("drop");
        CommandWords.setCommandWords(temp);
      }
  }



 /**
    * Drops the item corresponding with the number given, considering the list starts at 1.
    * If no number is given, drops the first item in the list.
    *
    * Resets the command words after dropping.
    *
    * @param command (Command)  The command object that has the first and second word of the command.
    */
  private void drop(Command command) {
    
    if (!command.hasSecondWord()) {
        System.out.println("\nDropped " + Player.getTanks().get(0) + "!");
        currentRoom.addItem(Player.removeTanks(0));
    }
    else {
        int choice = Integer.parseInt(command.getSecondWord()) - 1;
      
        if (Player.getTanks().size() < 1)
          System.out.println("\nYou have nothing to drop.");
      
        else if (choice < 0 || choice >= Player.getTanks().size())
          System.out.println("Please enter a valid number.");
      
        else {
          System.out.println("\nDropped " + Player.getTanks().get(choice) + "!");
          currentRoom.addItem(Player.removeTanks(choice));
          System.out.println("\nCurrent items: " + Player.getTanks());
        }
      }
    
    ArrayList<String> temp = new ArrayList<String>();
    temp.add("go");
    temp.add("quit");
    temp.add("help");
    temp.add("look");
    temp.add("inventory");
    temp.add("use");
    temp.add("grab");
    temp.add("refill");
      
    CommandWords.setCommandWords(temp);
  }


  
 /**
    * Refills with the item corresponding with the number given, considering the list starts at 1.
    * If no number is given, refills with the first item in the list.
    *
    * A refill adds oxygen to the player, but removes the selected item from the items list.
    *
    * @param command (Command)  The command object that has the first and second word of the command.
    */
  private void refill(Command command) {
    
    if (Player.getTanks().size() < 1) {
      System.out.println("You don't have any tanks...");
    }
    else if (!command.hasSecondWord()) {
      Player.useTank(0);
    }
    else {
      int choice = Integer.parseInt(command.getSecondWord()) - 1;
      if (choice < 0 || choice > Player.getTanks().size() - 1)
        System.out.println("\nPlease enter a valid number.");
      else
        Player.useTank(choice);
    }
  }


  
 /**
    * Displays the items that the player has as well as the remaining oxygen.
    */
  private void inventory() {
    System.out.println("\n\nItems: " + Player.getTanks());
    System.out.println("Oxygen: " + Player.getOxygen() + "%");
  }

  

 /**
    * @return  Returns true if the player ran out of oxygen, false if they are still alive.
    */
  private boolean checkLose() {
    if (Player.getOxygen() <= 0)
      return true;
    return false;
  }

  

 /**
    * @return  Returns true if the player completed all objectives, false if not.
    */
  private boolean checkWin() {
    if (step1 && step2 && step3)
      return true;
    return false;
  }
}