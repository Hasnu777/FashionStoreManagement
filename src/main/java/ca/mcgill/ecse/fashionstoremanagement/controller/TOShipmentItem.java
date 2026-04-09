/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.36.0.8498.ce78327bf modeling language!*/

package ca.mcgill.ecse.fashionstoremanagement.controller;

// line 16 "../../../../../FSMSTransferObjects.ump"
public class TOShipmentItem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOShipmentItem Attributes
  private int shipmentNumber;
  private int productId;
  private String itemName;
  private String size;
  private int quantity;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOShipmentItem(int aShipmentNumber, int aProductId, String aItemName, String aSize, int aQuantity)
  {
    shipmentNumber = aShipmentNumber;
    productId = aProductId;
    itemName = aItemName;
    size = aSize;
    quantity = aQuantity;
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

  public boolean setProductId(int aProductId)
  {
    boolean wasSet = false;
    productId = aProductId;
    wasSet = true;
    return wasSet;
  }

  public boolean setItemName(String aItemName)
  {
    boolean wasSet = false;
    itemName = aItemName;
    wasSet = true;
    return wasSet;
  }

  public boolean setSize(String aSize)
  {
    boolean wasSet = false;
    size = aSize;
    wasSet = true;
    return wasSet;
  }

  public boolean setQuantity(int aQuantity)
  {
    boolean wasSet = false;
    quantity = aQuantity;
    wasSet = true;
    return wasSet;
  }

  public int getShipmentNumber()
  {
    return shipmentNumber;
  }

  public int getProductId()
  {
    return productId;
  }

  public String getItemName()
  {
    return itemName;
  }

  public String getSize()
  {
    return size;
  }

  public int getQuantity()
  {
    return quantity;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "shipmentNumber" + ":" + getShipmentNumber()+ "," +
            "productId" + ":" + getProductId()+ "," +
            "itemName" + ":" + getItemName()+ "," +
            "size" + ":" + getSize()+ "," +
            "quantity" + ":" + getQuantity()+ "]";
  }
}