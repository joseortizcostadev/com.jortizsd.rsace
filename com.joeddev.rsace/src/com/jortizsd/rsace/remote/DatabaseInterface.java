package com.jortizsd.rsace.remote;

public interface DatabaseInterface 
{
	  default boolean connectToDatabase ()
	  {
		  
		  return false;
		  
	  }
      public void addDeveloperToDatabase();
      public void deleteDeveloperInDatabase();
      public void updateDeveloperInDatabase(Developer developer);
      
      
      
}
