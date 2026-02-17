/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.8072.d3fbfafbc modeling language!*/

package ca.mcgill.ecse.fashionstoremanagement.model;
import java.sql.Date;
import java.util.*;

// line 48 "../../../../../../model.ump"
// line 167 "../../../../../../model.ump"
public class SpecialItem extends Item
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum ItemAvailabilityType { Seasonal, LimitedEdition }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //SpecialItem Attributes
  private ItemAvailabilityType availabilityType;
  private Date startDate;
  private Date endDate;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SpecialItem(String aName, double aPrice, int aLoyaltyPoints, FashionStoreManagement aFashionStoreManagement, ItemAvailabilityType aAvailabilityType, Date aStartDate, Date aEndDate)
  {
    super(aName, aPrice, aLoyaltyPoints, aFashionStoreManagement);
    availabilityType = aAvailabilityType;
    startDate = aStartDate;
    endDate = aEndDate;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setAvailabilityType(ItemAvailabilityType aAvailabilityType)
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

  public ItemAvailabilityType getAvailabilityType()
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
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "availabilityType" + "=" + (getAvailabilityType() != null ? !getAvailabilityType().equals(this)  ? getAvailabilityType().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "startDate" + "=" + (getStartDate() != null ? !getStartDate().equals(this)  ? getStartDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endDate" + "=" + (getEndDate() != null ? !getEndDate().equals(this)  ? getEndDate().toString().replaceAll("  ","    ") : "this" : "null");
  }
}