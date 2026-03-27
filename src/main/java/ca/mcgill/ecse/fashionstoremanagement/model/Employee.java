/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.36.0.8291.fe15f81dc modeling language!*/

package ca.mcgill.ecse.fashionstoremanagement.model;
import java.util.*;

// line 34 "../../../../../FashionStoreManagement.ump"
public class Employee extends UserRole
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Employee Associations
  private FashionStoreManagement fashionStoreManagement;
  private List<Order> ordersAssigned;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Employee(User aUser, FashionStoreManagement aFashionStoreManagement)
  {
    super(aUser);
    boolean didAddFashionStoreManagement = setFashionStoreManagement(aFashionStoreManagement);
    if (!didAddFashionStoreManagement)
    {
      throw new RuntimeException("Unable to create employee due to fashionStoreManagement. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    ordersAssigned = new ArrayList<Order>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public FashionStoreManagement getFashionStoreManagement()
  {
    return fashionStoreManagement;
  }
  /* Code from template association_GetMany */
  public Order getOrdersAssigned(int index)
  {
    Order aOrdersAssigned = ordersAssigned.get(index);
    return aOrdersAssigned;
  }

  public List<Order> getOrdersAssigned()
  {
    List<Order> newOrdersAssigned = Collections.unmodifiableList(ordersAssigned);
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

  public int indexOfOrdersAssigned(Order aOrdersAssigned)
  {
    int index = ordersAssigned.indexOf(aOrdersAssigned);
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
      existingFashionStoreManagement.removeEmployee(this);
    }
    fashionStoreManagement.addEmployee(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfOrdersAssigned()
  {
    return 0;
  }
  /* Code from template association_AddManyToOptionalOne */
  public boolean addOrdersAssigned(Order aOrdersAssigned)
  {
    boolean wasAdded = false;
    if (ordersAssigned.contains(aOrdersAssigned)) { return false; }
    Employee existingOrderAssignee = aOrdersAssigned.getOrderAssignee();
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

  public boolean removeOrdersAssigned(Order aOrdersAssigned)
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
  public boolean addOrdersAssignedAt(Order aOrdersAssigned, int index)
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

  public boolean addOrMoveOrdersAssignedAt(Order aOrdersAssigned, int index)
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
    FashionStoreManagement placeholderFashionStoreManagement = fashionStoreManagement;
    this.fashionStoreManagement = null;
    if(placeholderFashionStoreManagement != null)
    {
      placeholderFashionStoreManagement.removeEmployee(this);
    }
    while( !ordersAssigned.isEmpty() )
    {
      ordersAssigned.get(0).setOrderAssignee(null);
    }
    super.delete();
  }

}