/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.8072.d3fbfafbc modeling language!*/

package ca.mcgill.ecse.fashionstoremanagement.model;
import java.util.*;

// line 42 "../../../../../../model.ump"
// line 137 "../../../../../../model.ump"
public class Item
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, Item> itemsByName = new HashMap<String, Item>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Item Attributes
  private String name;
  private double price;
  private int loyaltyPoints;

  //Item Associations
  private FashionStoreManagement fashionStoreManagement;
  private List<SizedItem> sizedItems;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Item(String aName, double aPrice, int aLoyaltyPoints, FashionStoreManagement aFashionStoreManagement)
  {
    price = aPrice;
    loyaltyPoints = aLoyaltyPoints;
    if (!setName(aName))
    {
      throw new RuntimeException("Cannot create due to duplicate name. See https://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    boolean didAddFashionStoreManagement = setFashionStoreManagement(aFashionStoreManagement);
    if (!didAddFashionStoreManagement)
    {
      throw new RuntimeException("Unable to create item due to fashionStoreManagement. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    sizedItems = new ArrayList<SizedItem>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    String anOldName = getName();
    if (anOldName != null && anOldName.equals(aName)) {
      return true;
    }
    if (hasWithName(aName)) {
      return wasSet;
    }
    name = aName;
    wasSet = true;
    if (anOldName != null) {
      itemsByName.remove(anOldName);
    }
    itemsByName.put(aName, this);
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
  /* Code from template attribute_GetUnique */
  public static Item getWithName(String aName)
  {
    return itemsByName.get(aName);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithName(String aName)
  {
    return getWithName(aName) != null;
  }

  public double getPrice()
  {
    return price;
  }

  public int getLoyaltyPoints()
  {
    return loyaltyPoints;
  }
  /* Code from template association_GetOne */
  public FashionStoreManagement getFashionStoreManagement()
  {
    return fashionStoreManagement;
  }
  /* Code from template association_GetMany */
  public SizedItem getSizedItem(int index)
  {
    SizedItem aSizedItem = sizedItems.get(index);
    return aSizedItem;
  }

  public List<SizedItem> getSizedItems()
  {
    List<SizedItem> newSizedItems = Collections.unmodifiableList(sizedItems);
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

  public int indexOfSizedItem(SizedItem aSizedItem)
  {
    int index = sizedItems.indexOf(aSizedItem);
    return index;
  }
  /* Code from template association_SetOneToMany */
  public boolean setFashionStoreManagement(FashionStoreManagement aFashionStoreManagement)
  {
    boolean wasSet = false;
    if (aFashionStoreManagement == null)
    {
      return wasSet;
    }

    FashionStoreManagement existingFashionStoreManagement = fashionStoreManagement;
    fashionStoreManagement = aFashionStoreManagement;
    if (existingFashionStoreManagement != null && !existingFashionStoreManagement.equals(aFashionStoreManagement))
    {
      existingFashionStoreManagement.removeItem(this);
    }
    fashionStoreManagement.addItem(this);
    wasSet = true;
    return wasSet;
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
  public SizedItem addSizedItem(SizedItem.Size aSize, int aQuantityInInventory, FashionStoreManagement aFashionStoreManagement)
  {
    if (numberOfSizedItems() >= maximumNumberOfSizedItems())
    {
      return null;
    }
    else
    {
      return new SizedItem(aSize, aQuantityInInventory, aFashionStoreManagement, this);
    }
  }

  public boolean addSizedItem(SizedItem aSizedItem)
  {
    boolean wasAdded = false;
    if (sizedItems.contains(aSizedItem)) { return false; }
    if (numberOfSizedItems() >= maximumNumberOfSizedItems())
    {
      return wasAdded;
    }

    Item existingItem = aSizedItem.getItem();
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

  public boolean removeSizedItem(SizedItem aSizedItem)
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
  public boolean addSizedItemAt(SizedItem aSizedItem, int index)
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

  public boolean addOrMoveSizedItemAt(SizedItem aSizedItem, int index)
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
    itemsByName.remove(getName());
    FashionStoreManagement placeholderFashionStoreManagement = fashionStoreManagement;
    this.fashionStoreManagement = null;
    if(placeholderFashionStoreManagement != null)
    {
      placeholderFashionStoreManagement.removeItem(this);
    }
    for(int i=sizedItems.size(); i > 0; i--)
    {
      SizedItem aSizedItem = sizedItems.get(i - 1);
      aSizedItem.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "price" + ":" + getPrice()+ "," +
            "loyaltyPoints" + ":" + getLoyaltyPoints()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "fashionStoreManagement = "+(getFashionStoreManagement()!=null?Integer.toHexString(System.identityHashCode(getFashionStoreManagement())):"null");
  }
}