public class Item {

  private boolean isBig;
  private int weight;


  
  /**
   * Creates a Small Tank item
   */
  public Item() {
    isBig = false;
    weight = 30;
  }


  
  /**
   * Creates a tank that is either big or small depending on the parameter
   * isBig (boolean)  If true, big tank, if false, small tank
   */
  public Item(boolean isBig) {
    this.isBig = isBig;
    if (isBig)
      weight = 60;
    else
      weight = 30;
  }


  
  /**
   * @return  Returns the isBig variable
   */
  public boolean getIsBig() {
    return isBig;
  }


  
  /**
   * @return  Returns the weight of the item
   */
  public int getWeight() {
    return weight;
  }


  
  /**
   * @return Returns 'Big Air Tank' if its big, return 'Small Air Tank' if its small
   */
  public String toString() {
    if (isBig)
      return "Big Air Tank";
    return "Small Air Tank";
  }
}