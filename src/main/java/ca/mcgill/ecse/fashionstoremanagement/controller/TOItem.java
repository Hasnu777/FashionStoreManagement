/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.36.0.8498.ce78327bf modeling language!*/

package ca.mcgill.ecse.fashionstoremanagement.controller;
import java.util.*;

// line 72 "../../../../../FSMSTransferObjects.ump"
public class TOItem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOItem Attributes
  private String name;
  private double price;
  private int loyaltyPoints;

  //TOItem Associations
  private List<TOSizedItem> sizedItems;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOItem(String aName, double aPrice, int aLoyaltyPoints)
  {
    name = aName;
    price = aPrice;
    loyaltyPoints = aLoyaltyPoints;
    sizedItems = new ArrayList<TOSizedItem>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setPrice(double aPrice)
  {
    boolean wasSet = false;
    price = aPrice;
    wasSet = true;
    return wasSet;
  }

  public boolean setLoyaltyPoints(int aLoyaltyPoints)
  {
    boolean wasSet = false;
    loyaltyPoints = aLoyaltyPoints;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public double getPrice()
  {
    return price;
  }

  public int getLoyaltyPoints()
  {
    return loyaltyPoints;
  }
  /* Code from template association_GetMany */
  public TOSizedItem getSizedItem(int index)
  {
    TOSizedItem aSizedItem = sizedItems.get(index);
    return aSizedItem;
  }

  public List<TOSizedItem> getSizedItems()
  {
    List<TOSizedItem> newSizedItems = Collections.unmodifiableList(sizedItems);
    return newSizedItems;
  }

  public int numberOfSizedItems()
  {
    int number = sizedItems.size();
    return number;
  }

  public boolean hasSizedItems()
  {
    boolean has = sizedItems.size() > 0;
    return has;
  }

  public int indexOfSizedItem(TOSizedItem aSizedItem)
  {
    int index = sizedItems.indexOf(aSizedItem);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfSizedItems()
  {
    return 0;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfSizedItems()
  {
    return 5;
  }
  /* Code from template association_AddOptionalNToOne */
  public TOSizedItem addSizedItem(int aProductId, String aItemName, String aSize, int aQuantityInInventory)
  {
    if (numberOfSizedItems() >= maximumNumberOfSizedItems())
    {
      return null;
    }
    else
    {
      return new TOSizedItem(aProductId, aItemName, aSize, aQuantityInInventory, this);
    }
  }

  public boolean addSizedItem(TOSizedItem aSizedItem)
  {
    boolean wasAdded = false;
    if (sizedItems.contains(aSizedItem)) { return false; }
    if (numberOfSizedItems() >= maximumNumberOfSizedItems())
    {
      return wasAdded;
    }

    TOItem existingItem = aSizedItem.getItem();
    boolean isNewItem = existingItem != null && !this.equals(existingItem);
    if (isNewItem)
    {
      aSizedItem.setItem(this);
    }
    else
    {
      sizedItems.add(aSizedItem);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeSizedItem(TOSizedItem aSizedItem)
  {
    boolean wasRemoved = false;
    //Unable to remove aSizedItem, as it must always have a item
    if (!this.equals(aSizedItem.getItem()))
    {
      sizedItems.remove(aSizedItem);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addSizedItemAt(TOSizedItem aSizedItem, int index)
  {  
    boolean wasAdded = false;
    if(addSizedItem(aSizedItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSizedItems()) { index = numberOfSizedItems() - 1; }
      sizedItems.remove(aSizedItem);
      sizedItems.add(index, aSizedItem);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveSizedItemAt(TOSizedItem aSizedItem, int index)
  {
    boolean wasAdded = false;
    if(sizedItems.contains(aSizedItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSizedItems()) { index = numberOfSizedItems() - 1; }
      sizedItems.remove(aSizedItem);
      sizedItems.add(index, aSizedItem);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addSizedItemAt(aSizedItem, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=sizedItems.size(); i > 0; i--)
    {
      TOSizedItem aSizedItem = sizedItems.get(i - 1);
      aSizedItem.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "price" + ":" + getPrice()+ "," +
            "loyaltyPoints" + ":" + getLoyaltyPoints()+ "]";
  }
}