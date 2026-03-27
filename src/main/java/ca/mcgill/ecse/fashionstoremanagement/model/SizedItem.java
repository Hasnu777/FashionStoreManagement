/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.36.0.8291.fe15f81dc modeling language!*/

package ca.mcgill.ecse.fashionstoremanagement.model;
import java.util.*;

// line 56 "../../../../../FashionStoreManagement.ump"
public class SizedItem
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Size { XS, S, M, L, XL }

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextProductId = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //SizedItem Attributes
  private Size size;
  private int quantityInInventory;

  //Autounique Attributes
  private int productId;

  //SizedItem Associations
  private FashionStoreManagement fashionStoreManagement;
  private List<OrderItem> orderItems;
  private List<ShipmentItem> shipmentItems;
  private Item item;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SizedItem(Size aSize, int aQuantityInInventory, FashionStoreManagement aFashionStoreManagement, Item aItem)
  {
    size = aSize;
    quantityInInventory = aQuantityInInventory;
    productId = nextProductId++;
    boolean didAddFashionStoreManagement = setFashionStoreManagement(aFashionStoreManagement);
    if (!didAddFashionStoreManagement)
    {
      throw new RuntimeException("Unable to create sizedItem due to fashionStoreManagement. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    orderItems = new ArrayList<OrderItem>();
    shipmentItems = new ArrayList<ShipmentItem>();
    boolean didAddItem = setItem(aItem);
    if (!didAddItem)
    {
      throw new RuntimeException("Unable to create sizedItem due to item. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setSize(Size aSize)
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

  public Size getSize()
  {
    return size;
  }

  public int getQuantityInInventory()
  {
    return quantityInInventory;
  }

  public int getProductId()
  {
    return productId;
  }
  /* Code from template association_GetOne */
  public FashionStoreManagement getFashionStoreManagement()
  {
    return fashionStoreManagement;
  }
  /* Code from template association_GetMany */
  public OrderItem getOrderItem(int index)
  {
    OrderItem aOrderItem = orderItems.get(index);
    return aOrderItem;
  }

  public List<OrderItem> getOrderItems()
  {
    List<OrderItem> newOrderItems = Collections.unmodifiableList(orderItems);
    return newOrderItems;
  }

  public int numberOfOrderItems()
  {
    int number = orderItems.size();
    return number;
  }

  public boolean hasOrderItems()
  {
    boolean has = orderItems.size() > 0;
    return has;
  }

  public int indexOfOrderItem(OrderItem aOrderItem)
  {
    int index = orderItems.indexOf(aOrderItem);
    return index;
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
  /* Code from template association_GetOne */
  public Item getItem()
  {
    return item;
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
      existingFashionStoreManagement.removeSizedItem(this);
    }
    fashionStoreManagement.addSizedItem(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfOrderItems()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public OrderItem addOrderItem(int aQuantity, FashionStoreManagement aFashionStoreManagement, Order aOrder)
  {
    return new OrderItem(aQuantity, aFashionStoreManagement, aOrder, this);
  }

  public boolean addOrderItem(OrderItem aOrderItem)
  {
    boolean wasAdded = false;
    if (orderItems.contains(aOrderItem)) { return false; }
    SizedItem existingItem = aOrderItem.getItem();
    boolean isNewItem = existingItem != null && !this.equals(existingItem);
    if (isNewItem)
    {
      aOrderItem.setItem(this);
    }
    else
    {
      orderItems.add(aOrderItem);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeOrderItem(OrderItem aOrderItem)
  {
    boolean wasRemoved = false;
    //Unable to remove aOrderItem, as it must always have a item
    if (!this.equals(aOrderItem.getItem()))
    {
      orderItems.remove(aOrderItem);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addOrderItemAt(OrderItem aOrderItem, int index)
  {  
    boolean wasAdded = false;
    if(addOrderItem(aOrderItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrderItems()) { index = numberOfOrderItems() - 1; }
      orderItems.remove(aOrderItem);
      orderItems.add(index, aOrderItem);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveOrderItemAt(OrderItem aOrderItem, int index)
  {
    boolean wasAdded = false;
    if(orderItems.contains(aOrderItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrderItems()) { index = numberOfOrderItems() - 1; }
      orderItems.remove(aOrderItem);
      orderItems.add(index, aOrderItem);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addOrderItemAt(aOrderItem, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfShipmentItems()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public ShipmentItem addShipmentItem(int aQuantity, FashionStoreManagement aFashionStoreManagement, Shipment aShipment)
  {
    return new ShipmentItem(aQuantity, aFashionStoreManagement, aShipment, this);
  }

  public boolean addShipmentItem(ShipmentItem aShipmentItem)
  {
    boolean wasAdded = false;
    if (shipmentItems.contains(aShipmentItem)) { return false; }
    SizedItem existingItem = aShipmentItem.getItem();
    boolean isNewItem = existingItem != null && !this.equals(existingItem);
    if (isNewItem)
    {
      aShipmentItem.setItem(this);
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
    //Unable to remove aShipmentItem, as it must always have a item
    if (!this.equals(aShipmentItem.getItem()))
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
  /* Code from template association_SetOneToAtMostN */
  public boolean setItem(Item aItem)
  {
    boolean wasSet = false;
    //Must provide item to sizedItem
    if (aItem == null)
    {
      return wasSet;
    }

    //item already at maximum (5)
    if (aItem.numberOfSizedItems() >= Item.maximumNumberOfSizedItems())
    {
      return wasSet;
    }
    
    Item existingItem = item;
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
    FashionStoreManagement placeholderFashionStoreManagement = fashionStoreManagement;
    this.fashionStoreManagement = null;
    if(placeholderFashionStoreManagement != null)
    {
      placeholderFashionStoreManagement.removeSizedItem(this);
    }
    for(int i=orderItems.size(); i > 0; i--)
    {
      OrderItem aOrderItem = orderItems.get(i - 1);
      aOrderItem.delete();
    }
    for(int i=shipmentItems.size(); i > 0; i--)
    {
      ShipmentItem aShipmentItem = shipmentItems.get(i - 1);
      aShipmentItem.delete();
    }
    Item placeholderItem = item;
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
            "quantityInInventory" + ":" + getQuantityInInventory()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "size" + "=" + (getSize() != null ? !getSize().equals(this)  ? getSize().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "fashionStoreManagement = "+(getFashionStoreManagement()!=null?Integer.toHexString(System.identityHashCode(getFashionStoreManagement())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "item = "+(getItem()!=null?Integer.toHexString(System.identityHashCode(getItem())):"null");
  }
}