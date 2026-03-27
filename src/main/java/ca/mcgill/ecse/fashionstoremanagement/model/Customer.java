/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.36.0.8291.fe15f81dc modeling language!*/

package ca.mcgill.ecse.fashionstoremanagement.model;
import java.util.*;
import java.sql.Date;

// line 28 "../../../../../FashionStoreManagement.ump"
public class Customer extends UserRole
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Customer Attributes
  private String address;
  private int loyaltyPoints;

  //Customer Associations
  private FashionStoreManagement fashionStoreManagement;
  private List<Order> ordersPlaced;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Customer(User aUser, String aAddress, int aLoyaltyPoints, FashionStoreManagement aFashionStoreManagement)
  {
    super(aUser);
    address = aAddress;
    loyaltyPoints = aLoyaltyPoints;
    boolean didAddFashionStoreManagement = setFashionStoreManagement(aFashionStoreManagement);
    if (!didAddFashionStoreManagement)
    {
      throw new RuntimeException("Unable to create customer due to fashionStoreManagement. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    ordersPlaced = new ArrayList<Order>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setAddress(String aAddress)
  {
    boolean wasSet = false;
    address = aAddress;
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

  public String getAddress()
  {
    return address;
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
  public Order getOrdersPlaced(int index)
  {
    Order aOrdersPlaced = ordersPlaced.get(index);
    return aOrdersPlaced;
  }

  public List<Order> getOrdersPlaced()
  {
    List<Order> newOrdersPlaced = Collections.unmodifiableList(ordersPlaced);
    return newOrdersPlaced;
  }

  public int numberOfOrdersPlaced()
  {
    int number = ordersPlaced.size();
    return number;
  }

  public boolean hasOrdersPlaced()
  {
    boolean has = ordersPlaced.size() > 0;
    return has;
  }

  public int indexOfOrdersPlaced(Order aOrdersPlaced)
  {
    int index = ordersPlaced.indexOf(aOrdersPlaced);
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
      existingFashionStoreManagement.removeCustomer(this);
    }
    fashionStoreManagement.addCustomer(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfOrdersPlaced()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Order addOrdersPlaced(String aPendingAssigneeUsername, Date aDatePlaced, Order.DeliveryDeadline aDeadline, FashionStoreManagement aFashionStoreManagement)
  {
    return new Order(aPendingAssigneeUsername, aDatePlaced, aDeadline, aFashionStoreManagement, this);
  }

  public boolean addOrdersPlaced(Order aOrdersPlaced)
  {
    boolean wasAdded = false;
    if (ordersPlaced.contains(aOrdersPlaced)) { return false; }
    Customer existingOrderPlacer = aOrdersPlaced.getOrderPlacer();
    boolean isNewOrderPlacer = existingOrderPlacer != null && !this.equals(existingOrderPlacer);
    if (isNewOrderPlacer)
    {
      aOrdersPlaced.setOrderPlacer(this);
    }
    else
    {
      ordersPlaced.add(aOrdersPlaced);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeOrdersPlaced(Order aOrdersPlaced)
  {
    boolean wasRemoved = false;
    //Unable to remove aOrdersPlaced, as it must always have a orderPlacer
    if (!this.equals(aOrdersPlaced.getOrderPlacer()))
    {
      ordersPlaced.remove(aOrdersPlaced);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addOrdersPlacedAt(Order aOrdersPlaced, int index)
  {  
    boolean wasAdded = false;
    if(addOrdersPlaced(aOrdersPlaced))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrdersPlaced()) { index = numberOfOrdersPlaced() - 1; }
      ordersPlaced.remove(aOrdersPlaced);
      ordersPlaced.add(index, aOrdersPlaced);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveOrdersPlacedAt(Order aOrdersPlaced, int index)
  {
    boolean wasAdded = false;
    if(ordersPlaced.contains(aOrdersPlaced))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrdersPlaced()) { index = numberOfOrdersPlaced() - 1; }
      ordersPlaced.remove(aOrdersPlaced);
      ordersPlaced.add(index, aOrdersPlaced);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addOrdersPlacedAt(aOrdersPlaced, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    FashionStoreManagement placeholderFashionStoreManagement = fashionStoreManagement;
    this.fashionStoreManagement = null;
    if(placeholderFashionStoreManagement != null)
    {
      placeholderFashionStoreManagement.removeCustomer(this);
    }
    for(int i=ordersPlaced.size(); i > 0; i--)
    {
      Order aOrdersPlaced = ordersPlaced.get(i - 1);
      aOrdersPlaced.delete();
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "address" + ":" + getAddress()+ "," +
            "loyaltyPoints" + ":" + getLoyaltyPoints()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "fashionStoreManagement = "+(getFashionStoreManagement()!=null?Integer.toHexString(System.identityHashCode(getFashionStoreManagement())):"null");
  }
}