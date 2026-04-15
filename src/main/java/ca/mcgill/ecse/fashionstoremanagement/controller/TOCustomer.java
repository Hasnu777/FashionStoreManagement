/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.36.0.8498.ce78327bf modeling language!*/

package ca.mcgill.ecse.fashionstoremanagement.controller;
import java.util.*;
import java.sql.Date;

// line 30 "../../../../../FSMSTransferObjects.ump"
public class TOCustomer
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOCustomer Attributes
  private String username;
  private String name;
  private String phoneNumber;
  private String address;
  private int loyaltyPoints;

  //TOCustomer Associations
  private List<TOOrder> ordersPlaced;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOCustomer(String aUsername, String aName, String aPhoneNumber, String aAddress, int aLoyaltyPoints)
  {
    username = aUsername;
    name = aName;
    phoneNumber = aPhoneNumber;
    address = aAddress;
    loyaltyPoints = aLoyaltyPoints;
    ordersPlaced = new ArrayList<TOOrder>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setUsername(String aUsername)
  {
    boolean wasSet = false;
    username = aUsername;
    wasSet = true;
    return wasSet;
  }

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setPhoneNumber(String aPhoneNumber)
  {
    boolean wasSet = false;
    phoneNumber = aPhoneNumber;
    wasSet = true;
    return wasSet;
  }

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

  public String getUsername()
  {
    return username;
  }

  public String getName()
  {
    return name;
  }

  public String getPhoneNumber()
  {
    return phoneNumber;
  }

  public String getAddress()
  {
    return address;
  }

  public int getLoyaltyPoints()
  {
    return loyaltyPoints;
  }
  /* Code from template association_GetMany */
  public TOOrder getOrdersPlaced(int index)
  {
    TOOrder aOrdersPlaced = ordersPlaced.get(index);
    return aOrdersPlaced;
  }

  public List<TOOrder> getOrdersPlaced()
  {
    List<TOOrder> newOrdersPlaced = Collections.unmodifiableList(ordersPlaced);
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

  public int indexOfOrdersPlaced(TOOrder aOrdersPlaced)
  {
    int index = ordersPlaced.indexOf(aOrdersPlaced);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfOrdersPlaced()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public TOOrder addOrdersPlaced(int aOrderNumber, Date aDatePlaced, String aDeadline, int aTotalCost, int aFinalCost, int aPointsUsedInPayment, int aPointsAwarded, String aCustomerUsername, String aAssigneeUsername, String aStatus)
  {
    return new TOOrder(aOrderNumber, aDatePlaced, aDeadline, aTotalCost, aFinalCost, aPointsUsedInPayment, aPointsAwarded, aCustomerUsername, aAssigneeUsername, aStatus, this);
  }

  public boolean addOrdersPlaced(TOOrder aOrdersPlaced)
  {
    boolean wasAdded = false;
    if (ordersPlaced.contains(aOrdersPlaced)) { return false; }
    TOCustomer existingOrderPlacer = aOrdersPlaced.getOrderPlacer();
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

  public boolean removeOrdersPlaced(TOOrder aOrdersPlaced)
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
  public boolean addOrdersPlacedAt(TOOrder aOrdersPlaced, int index)
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

  public boolean addOrMoveOrdersPlacedAt(TOOrder aOrdersPlaced, int index)
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
    for(int i=ordersPlaced.size(); i > 0; i--)
    {
      TOOrder aOrdersPlaced = ordersPlaced.get(i - 1);
      aOrdersPlaced.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "username" + ":" + getUsername()+ "," +
            "name" + ":" + getName()+ "," +
            "phoneNumber" + ":" + getPhoneNumber()+ "," +
            "address" + ":" + getAddress()+ "," +
            "loyaltyPoints" + ":" + getLoyaltyPoints()+ "]";
  }
}