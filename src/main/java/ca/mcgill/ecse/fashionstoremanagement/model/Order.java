/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.36.0.8291.fe15f81dc modeling language!*/

package ca.mcgill.ecse.fashionstoremanagement.model;
import java.sql.Date;
import java.util.*;

// line 3 "../../../../../OrderStateMachine.ump"
// line 63 "../../../../../FashionStoreManagement.ump"
public class Order
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum DeliveryDeadline { SameDay, InOneDay, InTwoDays, InThreeDays }

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextOrderNumber = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Order Attributes
  private String pendingAssigneeUsername;
  private boolean pendingUsePoints;
  private Date datePlaced;
  private DeliveryDeadline deadline;
  private int totalCost;
  private int finalCost;
  private int pointsUsedInPayment;
  private int pointsAwarded;

  //Autounique Attributes
  private int orderNumber;

  //Order State Machines
  public enum State { UnderConstruction, Pending, Placed, InPreparation, ReadyForDelivery, Delivered, Cancelled }
  private State state;

  //Order Associations
  private FashionStoreManagement fashionStoreManagement;
  private List<OrderItem> orderItems;
  private Customer orderPlacer;
  private Employee orderAssignee;

  //Helper Variables
  private boolean canSetTotalCost;
  private boolean canSetFinalCost;
  private boolean canSetPointsUsedInPayment;
  private boolean canSetPointsAwarded;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Order(String aPendingAssigneeUsername, Date aDatePlaced, DeliveryDeadline aDeadline, FashionStoreManagement aFashionStoreManagement, Customer aOrderPlacer)
  {
    pendingAssigneeUsername = aPendingAssigneeUsername;
    pendingUsePoints = false;
    datePlaced = aDatePlaced;
    deadline = aDeadline;
    canSetTotalCost = true;
    canSetFinalCost = true;
    canSetPointsUsedInPayment = true;
    canSetPointsAwarded = true;
    orderNumber = nextOrderNumber++;
    boolean didAddFashionStoreManagement = setFashionStoreManagement(aFashionStoreManagement);
    if (!didAddFashionStoreManagement)
    {
      throw new RuntimeException("Unable to create order due to fashionStoreManagement. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    orderItems = new ArrayList<OrderItem>();
    boolean didAddOrderPlacer = setOrderPlacer(aOrderPlacer);
    if (!didAddOrderPlacer)
    {
      throw new RuntimeException("Unable to create ordersPlaced due to orderPlacer. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    setState(State.UnderConstruction);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPendingAssigneeUsername(String aPendingAssigneeUsername)
  {
    boolean wasSet = false;
    pendingAssigneeUsername = aPendingAssigneeUsername;
    wasSet = true;
    return wasSet;
  }

  public boolean setPendingUsePoints(boolean aPendingUsePoints)
  {
    boolean wasSet = false;
    pendingUsePoints = aPendingUsePoints;
    wasSet = true;
    return wasSet;
  }

  public boolean setDatePlaced(Date aDatePlaced)
  {
    boolean wasSet = false;
    datePlaced = aDatePlaced;
    wasSet = true;
    return wasSet;
  }

  public boolean setDeadline(DeliveryDeadline aDeadline)
  {
    boolean wasSet = false;
    deadline = aDeadline;
    wasSet = true;
    return wasSet;
  }
  /* Code from template attribute_SetImmutable */
  public boolean setTotalCost(int aTotalCost)
  {
    boolean wasSet = false;
    if (!canSetTotalCost) { return false; }
    canSetTotalCost = false;
    totalCost = aTotalCost;
    wasSet = true;
    return wasSet;
  }
  /* Code from template attribute_SetImmutable */
  public boolean setFinalCost(int aFinalCost)
  {
    boolean wasSet = false;
    if (!canSetFinalCost) { return false; }
    canSetFinalCost = false;
    finalCost = aFinalCost;
    wasSet = true;
    return wasSet;
  }
  /* Code from template attribute_SetImmutable */
  public boolean setPointsUsedInPayment(int aPointsUsedInPayment)
  {
    boolean wasSet = false;
    if (!canSetPointsUsedInPayment) { return false; }
    canSetPointsUsedInPayment = false;
    pointsUsedInPayment = aPointsUsedInPayment;
    wasSet = true;
    return wasSet;
  }
  /* Code from template attribute_SetImmutable */
  public boolean setPointsAwarded(int aPointsAwarded)
  {
    boolean wasSet = false;
    if (!canSetPointsAwarded) { return false; }
    canSetPointsAwarded = false;
    pointsAwarded = aPointsAwarded;
    wasSet = true;
    return wasSet;
  }

  public String getPendingAssigneeUsername()
  {
    return pendingAssigneeUsername;
  }

  public boolean getPendingUsePoints()
  {
    return pendingUsePoints;
  }

  public Date getDatePlaced()
  {
    return datePlaced;
  }

  public DeliveryDeadline getDeadline()
  {
    return deadline;
  }

  /**
   * Total cost of the order, without considering points.
   */
  public int getTotalCost()
  {
    return totalCost;
  }

  /**
   * Amount that the customer actually had to pay for the order.
   * This depends on both the total cost and whether or not the customer decided to use their points.
   */
  public int getFinalCost()
  {
    return finalCost;
  }

  /**
   * Points used by customer in payment
   */
  public int getPointsUsedInPayment()
  {
    return pointsUsedInPayment;
  }

  /**
   * Points awarded to customer
   */
  public int getPointsAwarded()
  {
    return pointsAwarded;
  }

  public int getOrderNumber()
  {
    return orderNumber;
  }

  public String getStateFullName()
  {
    String answer = state.toString();
    return answer;
  }

  public State getState()
  {
    return state;
  }

  public boolean checkout(int totalCost)
  {
    boolean wasEventProcessed = false;
    
    State aState = state;
    switch (aState)
    {
      case UnderConstruction:
        if (this.numberOfOrderItems()>this.minimumNumberOfOrderItems())
        {
        // line 12 "../../../../../OrderStateMachine.ump"
          setTotalCost(totalCost);
          setState(State.Pending);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean cancelOrder()
  {
    boolean wasEventProcessed = false;
    
    State aState = state;
    switch (aState)
    {
      case UnderConstruction:
        setState(State.Cancelled);
        wasEventProcessed = true;
        break;
      case Pending:
        setState(State.Cancelled);
        wasEventProcessed = true;
        break;
      case Placed:
        setState(State.Cancelled);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean pay(int finalCost,int pointsUsed,int pointsAwarded,Date datePlaced)
  {
    boolean wasEventProcessed = false;
    
    State aState = state;
    switch (aState)
    {
      case Pending:
        // line 20 "../../../../../OrderStateMachine.ump"
        setFinalCost(finalCost);
                setPointsUsedInPayment(pointsUsed);
                setPointsAwarded(pointsAwarded);
                setDatePlaced(datePlaced);
        setState(State.Placed);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean assignEmployee(Employee employeeToAssign)
  {
    boolean wasEventProcessed = false;
    
    State aState = state;
    switch (aState)
    {
      case Placed:
        if (!(hasOrderAssignee()))
        {
        // line 34 "../../../../../OrderStateMachine.ump"
          setOrderAssignee(employeeToAssign);
          setState(State.InPreparation);
          wasEventProcessed = true;
          break;
        }
        break;
      case InPreparation:
        if (hasOrderAssignee())
        {
        // line 43 "../../../../../OrderStateMachine.ump"
          setOrderAssignee(employeeToAssign);
          setState(State.InPreparation);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean finishAssembly()
  {
    boolean wasEventProcessed = false;
    
    State aState = state;
    switch (aState)
    {
      case InPreparation:
        setState(State.ReadyForDelivery);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean deliver(Date orderDeliveryDeadline)
  {
    boolean wasEventProcessed = false;
    
    State aState = state;
    switch (aState)
    {
      case ReadyForDelivery:
        if (isDeliveryDay(orderDeliveryDeadline))
        {
          setState(State.Delivered);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void setState(State aState)
  {
    state = aState;
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
  /* Code from template association_GetOne */
  public Customer getOrderPlacer()
  {
    return orderPlacer;
  }
  /* Code from template association_GetOne */
  public Employee getOrderAssignee()
  {
    return orderAssignee;
  }

  public boolean hasOrderAssignee()
  {
    boolean has = orderAssignee != null;
    return has;
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
      existingFashionStoreManagement.removeOrder(this);
    }
    fashionStoreManagement.addOrder(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfOrderItems()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public OrderItem addOrderItem(int aQuantity, FashionStoreManagement aFashionStoreManagement, SizedItem aItem)
  {
    return new OrderItem(aQuantity, aFashionStoreManagement, this, aItem);
  }

  public boolean addOrderItem(OrderItem aOrderItem)
  {
    boolean wasAdded = false;
    if (orderItems.contains(aOrderItem)) { return false; }
    Order existingOrder = aOrderItem.getOrder();
    boolean isNewOrder = existingOrder != null && !this.equals(existingOrder);
    if (isNewOrder)
    {
      aOrderItem.setOrder(this);
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
    //Unable to remove aOrderItem, as it must always have a order
    if (!this.equals(aOrderItem.getOrder()))
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
  /* Code from template association_SetOneToMany */
  public boolean setOrderPlacer(Customer aOrderPlacer)
  {
    boolean wasSet = false;
    if (aOrderPlacer == null)
    {
      return wasSet;
    }

    Customer existingOrderPlacer = orderPlacer;
    orderPlacer = aOrderPlacer;
    if (existingOrderPlacer != null && !existingOrderPlacer.equals(aOrderPlacer))
    {
      existingOrderPlacer.removeOrdersPlaced(this);
    }
    orderPlacer.addOrdersPlaced(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToMany */
  public boolean setOrderAssignee(Employee aOrderAssignee)
  {
    boolean wasSet = false;
    Employee existingOrderAssignee = orderAssignee;
    orderAssignee = aOrderAssignee;
    if (existingOrderAssignee != null && !existingOrderAssignee.equals(aOrderAssignee))
    {
      existingOrderAssignee.removeOrdersAssigned(this);
    }
    if (aOrderAssignee != null)
    {
      aOrderAssignee.addOrdersAssigned(this);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    FashionStoreManagement placeholderFashionStoreManagement = fashionStoreManagement;
    this.fashionStoreManagement = null;
    if(placeholderFashionStoreManagement != null)
    {
      placeholderFashionStoreManagement.removeOrder(this);
    }
    for(int i=orderItems.size(); i > 0; i--)
    {
      OrderItem aOrderItem = orderItems.get(i - 1);
      aOrderItem.delete();
    }
    Customer placeholderOrderPlacer = orderPlacer;
    this.orderPlacer = null;
    if(placeholderOrderPlacer != null)
    {
      placeholderOrderPlacer.removeOrdersPlaced(this);
    }
    if (orderAssignee != null)
    {
      Employee placeholderOrderAssignee = orderAssignee;
      this.orderAssignee = null;
      placeholderOrderAssignee.removeOrdersAssigned(this);
    }
  }

  // line 62 "../../../../../OrderStateMachine.ump"
  public boolean isDeliveryDay(Date orderDeliveryDeadline){
    Date todaysDate = new Date(System.currentTimeMillis());
        return !orderDeliveryDeadline.after(todaysDate);
  }

  // line 79 "../../../../../FashionStoreManagement.ump"
   public void setOrderState(State aState){
    state = aState;
  }


  public String toString()
  {
    return super.toString() + "["+
            "orderNumber" + ":" + getOrderNumber()+ "," +
            "pendingAssigneeUsername" + ":" + getPendingAssigneeUsername()+ "," +
            "pendingUsePoints" + ":" + getPendingUsePoints()+ "," +
            "totalCost" + ":" + getTotalCost()+ "," +
            "finalCost" + ":" + getFinalCost()+ "," +
            "pointsUsedInPayment" + ":" + getPointsUsedInPayment()+ "," +
            "pointsAwarded" + ":" + getPointsAwarded()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "datePlaced" + "=" + (getDatePlaced() != null ? !getDatePlaced().equals(this)  ? getDatePlaced().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "deadline" + "=" + (getDeadline() != null ? !getDeadline().equals(this)  ? getDeadline().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "fashionStoreManagement = "+(getFashionStoreManagement()!=null?Integer.toHexString(System.identityHashCode(getFashionStoreManagement())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "orderPlacer = "+(getOrderPlacer()!=null?Integer.toHexString(System.identityHashCode(getOrderPlacer())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "orderAssignee = "+(getOrderAssignee()!=null?Integer.toHexString(System.identityHashCode(getOrderAssignee())):"null");
  }
}