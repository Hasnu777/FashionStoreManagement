/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.36.0.8291.fe15f81dc modeling language!*/

package ca.mcgill.ecse.fashionstoremanagement.model;

// line 95 "../../../../../FashionStoreManagement.ump"
public class ShipmentItem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ShipmentItem Attributes
  private int quantity;

  //ShipmentItem Associations
  private FashionStoreManagement fashionStoreManagement;
  private Shipment shipment;
  private SizedItem item;

  //Helper Variables
  private int cachedHashCode;
  private boolean canSetShipment;
  private boolean canSetItem;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ShipmentItem(int aQuantity, FashionStoreManagement aFashionStoreManagement, Shipment aShipment, SizedItem aItem)
  {
    cachedHashCode = -1;
    canSetShipment = true;
    canSetItem = true;
    quantity = aQuantity;
    boolean didAddFashionStoreManagement = setFashionStoreManagement(aFashionStoreManagement);
    if (!didAddFashionStoreManagement)
    {
      throw new RuntimeException("Unable to create shipmentItem due to fashionStoreManagement. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddShipment = setShipment(aShipment);
    if (!didAddShipment)
    {
      throw new RuntimeException("Unable to create shipmentItem due to shipment. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddItem = setItem(aItem);
    if (!didAddItem)
    {
      throw new RuntimeException("Unable to create shipmentItem due to item. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setQuantity(int aQuantity)
  {
    boolean wasSet = false;
    quantity = aQuantity;
    wasSet = true;
    return wasSet;
  }

  public int getQuantity()
  {
    return quantity;
  }
  /* Code from template association_GetOne */
  public FashionStoreManagement getFashionStoreManagement()
  {
    return fashionStoreManagement;
  }
  /* Code from template association_GetOne */
  public Shipment getShipment()
  {
    return shipment;
  }
  /* Code from template association_GetOne */
  public SizedItem getItem()
  {
    return item;
  }
  /* Code from template association_SetOneToManyAssociationClass */
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
      existingFashionStoreManagement.removeShipmentItem(this);
    }
    if (!fashionStoreManagement.addShipmentItem(this))
    {
      fashionStoreManagement = existingFashionStoreManagement;
      wasSet = false;
    }
    else
    {
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetOneToManyAssociationClass */
  public boolean setShipment(Shipment aShipment)
  {
    boolean wasSet = false;
    if (!canSetShipment) { return false; }
    if (aShipment == null)
    {
      return wasSet;
    }

    Shipment existingShipment = shipment;
    shipment = aShipment;
    if (existingShipment != null && !existingShipment.equals(aShipment))
    {
      existingShipment.removeShipmentItem(this);
    }
    if (!shipment.addShipmentItem(this))
    {
      shipment = existingShipment;
      wasSet = false;
    }
    else
    {
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetOneToManyAssociationClass */
  public boolean setItem(SizedItem aItem)
  {
    boolean wasSet = false;
    if (!canSetItem) { return false; }
    if (aItem == null)
    {
      return wasSet;
    }

    SizedItem existingItem = item;
    item = aItem;
    if (existingItem != null && !existingItem.equals(aItem))
    {
      existingItem.removeShipmentItem(this);
    }
    if (!item.addShipmentItem(this))
    {
      item = existingItem;
      wasSet = false;
    }
    else
    {
      wasSet = true;
    }
    return wasSet;
  }

  public boolean equals(Object obj)
  {
    if (obj == null) { return false; }
    if (!getClass().equals(obj.getClass())) { return false; }

    ShipmentItem compareTo = (ShipmentItem)obj;
  
    if (getShipment() == null && compareTo.getShipment() != null)
    {
      return false;
    }
    else if (getShipment() != null && !getShipment().equals(compareTo.getShipment()))
    {
      return false;
    }

    if (getItem() == null && compareTo.getItem() != null)
    {
      return false;
    }
    else if (getItem() != null && !getItem().equals(compareTo.getItem()))
    {
      return false;
    }

    return true;
  }

  public int hashCode()
  {
    if (cachedHashCode != -1)
    {
      return cachedHashCode;
    }
    cachedHashCode = 17;
    if (getShipment() != null)
    {
      cachedHashCode = cachedHashCode * 23 + getShipment().hashCode();
    }
    else
    {
      cachedHashCode = cachedHashCode * 23;
    }
    if (getItem() != null)
    {
      cachedHashCode = cachedHashCode * 23 + getItem().hashCode();
    }
    else
    {
      cachedHashCode = cachedHashCode * 23;
    }

    canSetShipment = false;
    canSetItem = false;
    return cachedHashCode;
  }

  public void delete()
  {
    FashionStoreManagement placeholderFashionStoreManagement = fashionStoreManagement;
    this.fashionStoreManagement = null;
    if(placeholderFashionStoreManagement != null)
    {
      placeholderFashionStoreManagement.removeShipmentItem(this);
    }
    Shipment placeholderShipment = shipment;
    this.shipment = null;
    if(placeholderShipment != null)
    {
      placeholderShipment.removeShipmentItem(this);
    }
    SizedItem placeholderItem = item;
    this.item = null;
    if(placeholderItem != null)
    {
      placeholderItem.removeShipmentItem(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "quantity" + ":" + getQuantity()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "fashionStoreManagement = "+(getFashionStoreManagement()!=null?Integer.toHexString(System.identityHashCode(getFashionStoreManagement())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "shipment = "+(getShipment()!=null?Integer.toHexString(System.identityHashCode(getShipment())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "item = "+(getItem()!=null?Integer.toHexString(System.identityHashCode(getItem())):"null");
  }
}