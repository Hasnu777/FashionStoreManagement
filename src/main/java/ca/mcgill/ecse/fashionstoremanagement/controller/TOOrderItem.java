/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.36.0.8498.ce78327bf modeling language!*/

package ca.mcgill.ecse.fashionstoremanagement.controller;

// line 64 "../../../../../FSMSTransferObjects.ump"
public class TOOrderItem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOOrderItem Attributes
  private int orderNumber;
  private int productId;
  private String itemName;
  private String size;
  private int quantity;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOOrderItem(int aOrderNumber, int aProductId, String aItemName, String aSize, int aQuantity)
  {
    orderNumber = aOrderNumber;
    productId = aProductId;
    itemName = aItemName;
    size = aSize;
    quantity = aQuantity;
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

  public int getOrderNumber()
  {
    return orderNumber;
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
            "orderNumber" + ":" + getOrderNumber()+ "," +
            "productId" + ":" + getProductId()+ "," +
            "itemName" + ":" + getItemName()+ "," +
            "size" + ":" + getSize()+ "," +
            "quantity" + ":" + getQuantity()+ "]";
  }
}