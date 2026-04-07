/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.36.0.8498.ce78327bf modeling language!*/

package ca.mcgill.ecse.fashionstoremanagement.controller;
import java.sql.Date;

/**
 * Transfer objects are used to transfer information
 * to the UI (which we will have in delievrable 4)
 * without giving it access to model objects
 */
// line 10 "../../../../../FSMSTransferObjects.ump"
public class TOShipment
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOShipment Attributes
  private int shipmentNumber;
  private Date dateOrdered;
  private Date dateArrived;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOShipment(int aShipmentNumber, Date aDateOrdered, Date aDateArrived)
  {
    shipmentNumber = aShipmentNumber;
    dateOrdered = aDateOrdered;
    dateArrived = aDateArrived;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setShipmentNumber(int aShipmentNumber)
  {
    boolean wasSet = false;
    shipmentNumber = aShipmentNumber;
    wasSet = true;
    return wasSet;
  }

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

  public int getShipmentNumber()
  {
    return shipmentNumber;
  }

  public Date getDateOrdered()
  {
    return dateOrdered;
  }

  public Date getDateArrived()
  {
    return dateArrived;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "shipmentNumber" + ":" + getShipmentNumber()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "dateOrdered" + "=" + (getDateOrdered() != null ? !getDateOrdered().equals(this)  ? getDateOrdered().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "dateArrived" + "=" + (getDateArrived() != null ? !getDateArrived().equals(this)  ? getDateArrived().toString().replaceAll("  ","    ") : "this" : "null");
  }
}