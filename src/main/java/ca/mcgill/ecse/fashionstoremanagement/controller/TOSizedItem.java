/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.36.0.8498.ce78327bf modeling language!*/

package ca.mcgill.ecse.fashionstoremanagement.controller;

// line 87 "../../../../../FSMSTransferObjects.ump"
public class TOSizedItem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOSizedItem Attributes
  private int productId;
  private String itemName;
  private String size;
  private int quantityInInventory;

  //TOSizedItem Associations
  private TOItem item;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOSizedItem(int aProductId, String aItemName, String aSize, int aQuantityInInventory, TOItem aItem)
  {
    productId = aProductId;
    itemName = aItemName;
    size = aSize;
    quantityInInventory = aQuantityInInventory;
    boolean didAddItem = setItem(aItem);
    if (!didAddItem)
    {
      throw new RuntimeException("Unable to create sizedItem due to item. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setProductId(int aProductId)
  {
    boolean wasSet = false;
    productId = aProductId;
    wasSet = true;
    return wasSet;
  }

  public boolean setItemName(String aItemName)
  {
    boolean wasSet = false;
    itemName = aItemName;
    wasSet = true;
    return wasSet;
  }

  public boolean setSize(String aSize)
  {
    boolean wasSet = false;
    size = aSize;
    wasSet = true;
    return wasSet;
  }

  public boolean setQuantityInInventory(int aQuantityInInventory)
  {
    boolean wasSet = false;
    quantityInInventory = aQuantityInInventory;
    wasSet = true;
    return wasSet;
  }

  public int getProductId()
  {
    return productId;
  }

  public String getItemName()
  {
    return itemName;
  }

  public String getSize()
  {
    return size;
  }

  public int getQuantityInInventory()
  {
    return quantityInInventory;
  }
  /* Code from template association_GetOne */
  public TOItem getItem()
  {
    return item;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setItem(TOItem aItem)
  {
    boolean wasSet = false;
    //Must provide item to sizedItem
    if (aItem == null)
    {
      return wasSet;
    }

    //item already at maximum (5)
    if (aItem.numberOfSizedItems() >= TOItem.maximumNumberOfSizedItems())
    {
      return wasSet;
    }
    
    TOItem existingItem = item;
    item = aItem;
    if (existingItem != null && !existingItem.equals(aItem))
    {
      boolean didRemove = existingItem.removeSizedItem(this);
      if (!didRemove)
      {
        item = existingItem;
        return wasSet;
      }
    }
    item.addSizedItem(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    TOItem placeholderItem = item;
    this.item = null;
    if(placeholderItem != null)
    {
      placeholderItem.removeSizedItem(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "productId" + ":" + getProductId()+ "," +
            "itemName" + ":" + getItemName()+ "," +
            "size" + ":" + getSize()+ "," +
            "quantityInInventory" + ":" + getQuantityInInventory()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "item = "+(getItem()!=null?Integer.toHexString(System.identityHashCode(getItem())):"null");
  }
}