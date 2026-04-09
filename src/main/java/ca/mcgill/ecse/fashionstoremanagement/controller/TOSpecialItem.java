/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.36.0.8498.ce78327bf modeling language!*/

package ca.mcgill.ecse.fashionstoremanagement.controller;
import java.sql.Date;

// line 78 "../../../../../FSMSTransferObjects.ump"
public class TOSpecialItem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOSpecialItem Attributes
  private String name;
  private double price;
  private int loyaltyPoints;
  private String availabilityType;
  private Date startDate;
  private Date endDate;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOSpecialItem(String aName, double aPrice, int aLoyaltyPoints, String aAvailabilityType, Date aStartDate, Date aEndDate)
  {
    name = aName;
    price = aPrice;
    loyaltyPoints = aLoyaltyPoints;
    availabilityType = aAvailabilityType;
    startDate = aStartDate;
    endDate = aEndDate;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setPrice(double aPrice)
  {
    boolean wasSet = false;
    price = aPrice;
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

  public boolean setAvailabilityType(String aAvailabilityType)
  {
    boolean wasSet = false;
    availabilityType = aAvailabilityType;
    wasSet = true;
    return wasSet;
  }

  public boolean setStartDate(Date aStartDate)
  {
    boolean wasSet = false;
    startDate = aStartDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndDate(Date aEndDate)
  {
    boolean wasSet = false;
    endDate = aEndDate;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public double getPrice()
  {
    return price;
  }

  public int getLoyaltyPoints()
  {
    return loyaltyPoints;
  }

  public String getAvailabilityType()
  {
    return availabilityType;
  }

  public Date getStartDate()
  {
    return startDate;
  }

  public Date getEndDate()
  {
    return endDate;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "price" + ":" + getPrice()+ "," +
            "loyaltyPoints" + ":" + getLoyaltyPoints()+ "," +
            "availabilityType" + ":" + getAvailabilityType()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startDate" + "=" + (getStartDate() != null ? !getStartDate().equals(this)  ? getStartDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endDate" + "=" + (getEndDate() != null ? !getEndDate().equals(this)  ? getEndDate().toString().replaceAll("  ","    ") : "this" : "null");
  }
}