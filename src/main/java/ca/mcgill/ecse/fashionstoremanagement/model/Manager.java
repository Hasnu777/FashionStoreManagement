/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.36.0.8291.fe15f81dc modeling language!*/

package ca.mcgill.ecse.fashionstoremanagement.model;

// line 38 "../../../../../FashionStoreManagement.ump"
public class Manager extends UserRole
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Manager Associations
  private FashionStoreManagement fashionStoreManagement;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Manager(User aUser, FashionStoreManagement aFashionStoreManagement)
  {
    super(aUser);
    boolean didAddFashionStoreManagement = setFashionStoreManagement(aFashionStoreManagement);
    if (!didAddFashionStoreManagement)
    {
      throw new RuntimeException("Unable to create manager due to fashionStoreManagement. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public FashionStoreManagement getFashionStoreManagement()
  {
    return fashionStoreManagement;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setFashionStoreManagement(FashionStoreManagement aNewFashionStoreManagement)
  {
    boolean wasSet = false;
    if (aNewFashionStoreManagement == null)
    {
      //Unable to setFashionStoreManagement to null, as manager must always be associated to a fashionStoreManagement
      return wasSet;
    }
    
    Manager existingManager = aNewFashionStoreManagement.getManager();
    if (existingManager != null && !equals(existingManager))
    {
      //Unable to setFashionStoreManagement, the current fashionStoreManagement already has a manager, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    FashionStoreManagement anOldFashionStoreManagement = fashionStoreManagement;
    fashionStoreManagement = aNewFashionStoreManagement;
    fashionStoreManagement.setManager(this);

    if (anOldFashionStoreManagement != null)
    {
      anOldFashionStoreManagement.setManager(null);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    FashionStoreManagement existingFashionStoreManagement = fashionStoreManagement;
    fashionStoreManagement = null;
    if (existingFashionStoreManagement != null)
    {
      existingFashionStoreManagement.setManager(null);
    }
    super.delete();
  }

}