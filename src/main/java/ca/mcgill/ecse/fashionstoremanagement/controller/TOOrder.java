/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.36.0.8498.ce78327bf modeling language!*/

package ca.mcgill.ecse.fashionstoremanagement.controller;
import java.sql.Date;

// line 51 "../../../../../FSMSTransferObjects.ump"
public class TOOrder
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOOrder Attributes
  private int orderNumber;
  private Date datePlaced;
  private String deadline;
  private int totalCost;
  private int finalCost;
  private int pointsUsedInPayment;
  private int pointsAwarded;
  private String customerUsername;
  private String assigneeUsername;
  private String status;

  //TOOrder Associations
  private TOCustomer orderPlacer;
  private TOEmployee orderAssignee;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOOrder(int aOrderNumber, Date aDatePlaced, String aDeadline, int aTotalCost, int aFinalCost, int aPointsUsedInPayment, int aPointsAwarded, String aCustomerUsername, String aAssigneeUsername, String aStatus, TOCustomer aOrderPlacer)
  {
    orderNumber = aOrderNumber;
    datePlaced = aDatePlaced;
    deadline = aDeadline;
    totalCost = aTotalCost;
    finalCost = aFinalCost;
    pointsUsedInPayment = aPointsUsedInPayment;
    pointsAwarded = aPointsAwarded;
    customerUsername = aCustomerUsername;
    assigneeUsername = aAssigneeUsername;
    status = aStatus;
    boolean didAddOrderPlacer = setOrderPlacer(aOrderPlacer);
    if (!didAddOrderPlacer)
    {
      throw new RuntimeException("Unable to create ordersPlaced due to orderPlacer. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setOrderNumber(int aOrderNumber)
  {
    boolean wasSet = false;
    orderNumber = aOrderNumber;
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

  public boolean setDeadline(String aDeadline)
  {
    boolean wasSet = false;
    deadline = aDeadline;
    wasSet = true;
    return wasSet;
  }

  public boolean setTotalCost(int aTotalCost)
  {
    boolean wasSet = false;
    totalCost = aTotalCost;
    wasSet = true;
    return wasSet;
  }

  public boolean setFinalCost(int aFinalCost)
  {
    boolean wasSet = false;
    finalCost = aFinalCost;
    wasSet = true;
    return wasSet;
  }

  public boolean setPointsUsedInPayment(int aPointsUsedInPayment)
  {
    boolean wasSet = false;
    pointsUsedInPayment = aPointsUsedInPayment;
    wasSet = true;
    return wasSet;
  }

  public boolean setPointsAwarded(int aPointsAwarded)
  {
    boolean wasSet = false;
    pointsAwarded = aPointsAwarded;
    wasSet = true;
    return wasSet;
  }

  public boolean setCustomerUsername(String aCustomerUsername)
  {
    boolean wasSet = false;
    customerUsername = aCustomerUsername;
    wasSet = true;
    return wasSet;
  }

  public boolean setAssigneeUsername(String aAssigneeUsername)
  {
    boolean wasSet = false;
    assigneeUsername = aAssigneeUsername;
    wasSet = true;
    return wasSet;
  }

  public boolean setStatus(String aStatus)
  {
    boolean wasSet = false;
    status = aStatus;
    wasSet = true;
    return wasSet;
  }

  public int getOrderNumber()
  {
    return orderNumber;
  }

  public Date getDatePlaced()
  {
    return datePlaced;
  }

  public String getDeadline()
  {
    return deadline;
  }

  public int getTotalCost()
  {
    return totalCost;
  }

  public int getFinalCost()
  {
    return finalCost;
  }

  public int getPointsUsedInPayment()
  {
    return pointsUsedInPayment;
  }

  public int getPointsAwarded()
  {
    return pointsAwarded;
  }

  public String getCustomerUsername()
  {
    return customerUsername;
  }

  public String getAssigneeUsername()
  {
    return assigneeUsername;
  }

  public String getStatus()
  {
    return status;
  }
  /* Code from template association_GetOne */
  public TOCustomer getOrderPlacer()
  {
    return orderPlacer;
  }
  /* Code from template association_GetOne */
  public TOEmployee getOrderAssignee()
  {
    return orderAssignee;
  }

  public boolean hasOrderAssignee()
  {
    boolean has = orderAssignee != null;
    return has;
  }
  /* Code from template association_SetOneToMany */
  public boolean setOrderPlacer(TOCustomer aOrderPlacer)
  {
    boolean wasSet = false;
    if (aOrderPlacer == null)
    {
      return wasSet;
    }

    TOCustomer existingOrderPlacer = orderPlacer;
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
  public boolean setOrderAssignee(TOEmployee aOrderAssignee)
  {
    boolean wasSet = false;
    TOEmployee existingOrderAssignee = orderAssignee;
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
    TOCustomer placeholderOrderPlacer = orderPlacer;
    this.orderPlacer = null;
    if(placeholderOrderPlacer != null)
    {
      placeholderOrderPlacer.removeOrdersPlaced(this);
    }
    if (orderAssignee != null)
    {
      TOEmployee placeholderOrderAssignee = orderAssignee;
      this.orderAssignee = null;
      placeholderOrderAssignee.removeOrdersAssigned(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "orderNumber" + ":" + getOrderNumber()+ "," +
            "deadline" + ":" + getDeadline()+ "," +
            "totalCost" + ":" + getTotalCost()+ "," +
            "finalCost" + ":" + getFinalCost()+ "," +
            "pointsUsedInPayment" + ":" + getPointsUsedInPayment()+ "," +
            "pointsAwarded" + ":" + getPointsAwarded()+ "," +
            "customerUsername" + ":" + getCustomerUsername()+ "," +
            "assigneeUsername" + ":" + getAssigneeUsername()+ "," +
            "status" + ":" + getStatus()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "datePlaced" + "=" + (getDatePlaced() != null ? !getDatePlaced().equals(this)  ? getDatePlaced().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "orderPlacer = "+(getOrderPlacer()!=null?Integer.toHexString(System.identityHashCode(getOrderPlacer())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "orderAssignee = "+(getOrderAssignee()!=null?Integer.toHexString(System.identityHashCode(getOrderAssignee())):"null");
  }
}