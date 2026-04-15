/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.36.0.8498.ce78327bf modeling language!*/

package ca.mcgill.ecse.fashionstoremanagement.controller;
import java.util.*;

// line 39 "../../../../../FSMSTransferObjects.ump"
public class TOEmployee
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOEmployee Attributes
  private String username;
  private String name;
  private String phoneNumber;

  //TOEmployee Associations
  private List<TOOrder> ordersAssigned;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOEmployee(String aUsername, String aName, String aPhoneNumber)
  {
    username = aUsername;
    name = aName;
    phoneNumber = aPhoneNumber;
    ordersAssigned = new ArrayList<TOOrder>();
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
  /* Code from template association_GetMany */
  public TOOrder getOrdersAssigned(int index)
  {
    TOOrder aOrdersAssigned = ordersAssigned.get(index);
    return aOrdersAssigned;
  }

  public List<TOOrder> getOrdersAssigned()
  {
    List<TOOrder> newOrdersAssigned = Collections.unmodifiableList(ordersAssigned);
    return newOrdersAssigned;
  }

  public int numberOfOrdersAssigned()
  {
    int number = ordersAssigned.size();
    return number;
  }

  public boolean hasOrdersAssigned()
  {
    boolean has = ordersAssigned.size() > 0;
    return has;
  }

  public int indexOfOrdersAssigned(TOOrder aOrdersAssigned)
  {
    int index = ordersAssigned.indexOf(aOrdersAssigned);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfOrdersAssigned()
  {
    return 0;
  }
  /* Code from template association_AddManyToOptionalOne */
  public boolean addOrdersAssigned(TOOrder aOrdersAssigned)
  {
    boolean wasAdded = false;
    if (ordersAssigned.contains(aOrdersAssigned)) { return false; }
    TOEmployee existingOrderAssignee = aOrdersAssigned.getOrderAssignee();
    if (existingOrderAssignee == null)
    {
      aOrdersAssigned.setOrderAssignee(this);
    }
    else if (!this.equals(existingOrderAssignee))
    {
      existingOrderAssignee.removeOrdersAssigned(aOrdersAssigned);
      addOrdersAssigned(aOrdersAssigned);
    }
    else
    {
      ordersAssigned.add(aOrdersAssigned);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeOrdersAssigned(TOOrder aOrdersAssigned)
  {
    boolean wasRemoved = false;
    if (ordersAssigned.contains(aOrdersAssigned))
    {
      ordersAssigned.remove(aOrdersAssigned);
      aOrdersAssigned.setOrderAssignee(null);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addOrdersAssignedAt(TOOrder aOrdersAssigned, int index)
  {  
    boolean wasAdded = false;
    if(addOrdersAssigned(aOrdersAssigned))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrdersAssigned()) { index = numberOfOrdersAssigned() - 1; }
      ordersAssigned.remove(aOrdersAssigned);
      ordersAssigned.add(index, aOrdersAssigned);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveOrdersAssignedAt(TOOrder aOrdersAssigned, int index)
  {
    boolean wasAdded = false;
    if(ordersAssigned.contains(aOrdersAssigned))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrdersAssigned()) { index = numberOfOrdersAssigned() - 1; }
      ordersAssigned.remove(aOrdersAssigned);
      ordersAssigned.add(index, aOrdersAssigned);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addOrdersAssignedAt(aOrdersAssigned, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    while( !ordersAssigned.isEmpty() )
    {
      ordersAssigned.get(0).setOrderAssignee(null);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "username" + ":" + getUsername()+ "," +
            "name" + ":" + getName()+ "," +
            "phoneNumber" + ":" + getPhoneNumber()+ "]";
  }
}