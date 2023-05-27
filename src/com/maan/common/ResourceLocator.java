package com.maan.common;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public final class ResourceLocator {
	
  private static ResourceLocator instance=new ResourceLocator();
	
  private ResourceLocator(){
	  
  }
  public static ResourceLocator getInstance(){
	  
	  return instance;
  }
  public  ResourceBundle getDBBundle() {
	
	  ResourceBundle dbBundle=null;
	  try{
		  dbBundle=ResourceBundle.getBundle(DBConstants.DB_QUERY_PROPERTY_FILE);
	  }
	  catch(MissingResourceException e){
		  
	  }
	  
	  
	  return dbBundle;
  }
	
	

}
