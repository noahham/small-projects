import java.util.Set;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Room 
{
    private String name;
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private ArrayList<Item> items;
    private String lookDescription;
    private String interactDescription;
    private boolean complete;


  
 /**
     * Creates a room with customized variables except for itemsList, which will be empty
     * @param name (String)  The room's name
     * @param description (String)  The room's description
     * @param lD (String)  The room's look description
     * @param iD (String)  The room's interact description
     */
    public Room(String name, String description, String lD, String iD) {
        this.name = name;
        this.description = description;
        lookDescription = lD;
        interactDescription = iD;
        complete = false;
        items = new ArrayList<Item>();
        exits = new HashMap<>();
    }


  
 /**
     * Creates a room with completely customized variables
     * @param name (String)  The room's name
     * @param description (String)  The room's description
     * @param lD (String)  The room's look description
     * @param iD (String)  The room's interact description
     * @param itemsList (ArrayList<Item>)  The list of items in the room
     */
    public Room(String name, String description, String lD, String iD, ArrayList<Item> itemsList) 
    {
        this.name = name;
        this.description = description;
        lookDescription = lD;
        interactDescription = iD;
        items = itemsList;
        complete = false;
        exits = new HashMap<>();
    }


  
  /**
     * @return  The Room's name
     */
    public String getName() {
      return name;
    }



 /**
     * @return  Returns the Room's look description
     */
    public String getLookDescription() {
      return lookDescription;
    }



  /**
     * @return  Returns the Room's interact description
     */
    public String getInteractDescription() {
      return interactDescription;
    }



  /**
     * Changes the interactDescription variable to a given String
     * @param iD (String)  The new interact description
     */
    public void setInteractDescription(String iD) {
      interactDescription = iD;
    }


  
  /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }


  
  /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription() {
        return description;
    }


  
  /**
     * Return a description of the room in the form:
     *     You are in the cafeteria.
     *     Exits: west south up
     * @return A long description of this room
     */
    public String getLongDescription() {
        return "You are " + description + ".\n" + getExitString();
    }


  
  /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    public String getExitString() {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }


  
  /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) {
        return exits.get(direction);
    }


  
  /**
     * @return  Room's list of items
     */
    public ArrayList<Item> getItems() {
      return items;
    }


  
  /**
     * Removes the item of the given index
     * @param index (int)  The index of which will be removed
     */
    public void removeItem(int index) {
      items.remove(index);
    }



  /**
     * Adds a given item to the Room's item list
     * @param item (Item)  The item that will be added
     */
    public void addItem(Item item) {
      items.add(item);
    }


  
  /**
     * @return  Returns the boolean value indicating if the room has already been interacted with
     */
    public boolean getComplete() {
      return complete;
    }



  /**
     * Sets the complete variable to a given boolean
     * @param complete (boolean)  The new value of complete
     */
    public void setComplete(boolean complete) {
      this.complete = complete;
    }
}

