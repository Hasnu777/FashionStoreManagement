/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.8072.d3fbfafbc modeling language!*/

package ca.mcgill.ecse.fashionstoremanagement.model;
import java.sql.Date;
import java.util.*;

// line 70 "../../../../../../model.ump"
// line 152 "../../../../../../model.ump"
public class Shipment
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextShipmentNumber = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Shipment Attributes
  private Date dateOrdered;
  private Date dateArrived;

  //Autounique Attributes
  private int shipmentNumber;

  //Shipment Associations
  private FashionStoreManagement fashionStoreManagement;
  private List<ShipmentItem> shipmentItems;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Shipment(Date aDateOrdered, Date aDateArrived, FashionStoreManagement aFashionStoreManagement)
  {
    dateOrdered = aDateOrdered;
    dateArrived = aDateArrived;
    shipmentNumber = nextShipmentNumber++;
    boolean didAddFashionStoreManagement = setFashionStoreManagement(aFashionStoreManagement);
    if (!didAddFashionStoreManagement)
    {
      throw new RuntimeException("Unable to create shipment due to fashionStoreManagement. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    shipmentItems = new ArrayList<ShipmentItem>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDateOrdered(Date aDateOrdered)
  {
    boolean wasSet = false;
    dateOrdered = aDateOrdered;
    wasSet = true;
    return wasSet;
  }

  public boolean setDateArrived(Date aDateArrived)
  {
    boolean wasSet = false;
    dateArrived = aDateArrived;
    wasSet = true;
    return wasSet;
  }

  public Date getDateOrdered()
  {
    return dateOrdered;
  }

  public Date getDateArrived()
  {
    return dateArrived;
  }

  public int getShipmentNumber()
  {
    return shipmentNumber;
  }
  /* Code from template association_GetOne */
  public FashionStoreManagement getFashionStoreManagement()
  {
    return fashionStoreManagement;
  }
  /* Code from template association_GetMany */
  public ShipmentItem getShipmentItem(int index)
  {
    ShipmentItem aShipmentItem = shipmentItems.get(index);
    return aShipmentItem;
  }

  public List<ShipmentItem> getShipmentItems()
  {
    List<ShipmentItem> newShipmentItems = Collections.unmodifiableList(shipmentItems);
    return newShipmentItems;
  }

  public int numberOfShipmentItems()
  {
    int number = shipmentItems.size();
    return number;
  }

  public boolean hasShipmentItems()
  {
    boolean has = shipmentItems.size() > 0;
    return has;
  }

  public int indexOfShipmentItem(ShipmentItem aShipmentItem)
  {
    int index = shipmentItems.indexOf(aShipmentItem);
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
      existingFashionStoreManagement.removeShipment(this);
    }
    fashionStoreManagement.addShipment(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfShipmentItems()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public ShipmentItem addShipmentItem(int aQuantity, FashionStoreManagement aFashionStoreManagement, SizedItem aItem)
  {
    return new ShipmentItem(aQuantity, aFashionStoreManagement, this, aItem);
  }

  public boolean addShipmentItem(ShipmentItem aShipmentItem)
  {
    boolean wasAdded = false;
    if (shipmentItems.contains(aShipmentItem)) { return false; }
    Shipment existingShipment = aShipmentItem.getShipment();
    boolean isNewShipment = existingShipment != null && !this.equals(existingShipment);
    if (isNewShipment)
    {
      aShipmentItem.setShipment(this);
    }
    else
    {
      shipmentItems.add(aShipmentItem);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeShipmentItem(ShipmentItem aShipmentItem)
  {
    boolean wasRemoved = false;
    //Unable to remove aShipmentItem, as it must always have a shipment
    if (!this.equals(aShipmentItem.getShipment()))
    {
      shipmentItems.remove(aShipmentItem);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addShipmentItemAt(ShipmentItem aShipmentItem, int index)
  {  
    boolean wasAdded = false;
    if(addShipmentItem(aShipmentItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfShipmentItems()) { index = numberOfShipmentItems() - 1; }
      shipmentItems.remove(aShipmentItem);
      shipmentItems.add(index, aShipmentItem);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveShipmentItemAt(ShipmentItem aShipmentItem, int index)
  {
    boolean wasAdded = false;
    if(shipmentItems.contains(aShipmentItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfShipmentItems()) { index = numberOfShipmentItems() - 1; }
      shipmentItems.remove(aShipmentItem);
      shipmentItems.add(index, aShipmentItem);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addShipmentItemAt(aShipmentItem, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    FashionStoreManagement placeholderFashionStoreManagement = fashionStoreManagement;
    this.fashionStoreManagement = null;
    if(placeholderFashionStoreManagement != null)
    {
      placeholderFashionStoreManagement.removeShipment(this);
    }
    for(int i=shipmentItems.size(); i > 0; i--)
    {
      ShipmentItem aShipmentItem = shipmentItems.get(i - 1);
      aShipmentItem.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "shipmentNumber" + ":" + getShipmentNumber()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "dateOrdered" + "=" + (getDateOrdered() != null ? !getDateOrdered().equals(this)  ? getDateOrdered().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "dateArrived" + "=" + (getDateArrived() != null ? !getDateArrived().equals(this)  ? getDateArrived().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "fashionStoreManagement = "+(getFashionStoreManagement()!=null?Integer.toHexString(System.identityHashCode(getFashionStoreManagement())):"null");
  }
}