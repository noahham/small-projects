import java.util.ArrayList;

public class Player {

  private static int weight = 0;
  private static int oxygen = 100;
  private static ArrayList<Item> tanks = new ArrayList<Item>();

 /**
    * @return  Returns current player oxygen level.
    */
  public static int getOxygen() {
    return oxygen;
  }


  
 /**
    * Sets the player's oxygen level to a given value
    * @param newOxygen (int)  The new oxygen level
    */
  public static void setOxygen(int newOxygen) {
    oxygen = newOxygen;
  }


  
 /**
    * @return  Returns player's tank ArrayList
    */
  public static ArrayList<Item> getTanks() {
    return tanks;
  }


  
 /**
    * Adds a given item to the tank list
    * @param tank (Item)  The tank to be added
    */
  public static void addTanks(Item tank) {
    tanks.add(tank);
  }

  

 /**
    * Removes a given index from the tank list
    * @param index (int) The index to be removed
    */
  public static Item removeTanks(int index) {
    return tanks.remove(index);
  }

  

 /**
    * Calculates and updates the weight variable based on the items in the tanks list
    * @return  Returns the player's current weight
    */
  public static int calculateWeight() {
    weight = 0;
    for (Item tank : tanks) {
      weight += tank.getWeight();
    }
    return weight;
  }


  
 /**
    * Adds the weight of the tank as oxygen, then removes the tank from the list
    * @param index (int)  The index of the tank that will be used
    */
  public static void useTank(int index) {
    oxygen += tanks.get(index).getWeight();
    if (oxygen > 100)
      oxygen = 100;
    System.out.println(tanks.get(index).getWeight() + "% oxygen replenished!");
    removeTanks(index);
    calculateWeight();
  }
}