/**
 * @author        Jose Ortiz Costa
 * @application   com.joeddev.rsace
 * @File          DVTPreferencesGetter.java
 * @Date          04/06/2015
 * @Description   This class extends DVTPreferencesPage, and 
 *                gets all the developer data from the developer
 *                preferences page
 */
package com.jocdev.rsace.preferences;
public class DVTPreferencesGetter extends DVTPreferencesPage 
{
	 /**
	  * @category Constructor
	  */
     public DVTPreferencesGetter ()
     {
         super();
     }
     
     /**
      * @category     Public Class Method
      * @description  Gets the developer's name or user name from its preferences page
      * @return       Object representing the developer's name or user name
      * @see          com.jocdev.rsace.team.Developer class
     */
     public Object getUsername() 
     {
        return super.getPreferenceStore().getString(PreferenceConstants.P_STRING_AUTHOR_SERVER);
     }
     
     /**
      * @category     Public Class Method
      * @description  Gets the developer's email from its preferences page
      * @return       Object representing the developer's email
      * @see          com.jocdev.rsace.team.Developer class
     */
     public Object getEmail()
     {
         return super.getPreferenceStore().getString(PreferenceConstants.P_STRING_EMAIL_SERVER);
     }
     
     /**
      * @category     Public Class Method
      * @description  Gets the developer's id from its preferences page
      * @return       Object representing the developer's id
      * @see          com.jocdev.rsace.team.Developer class
     */
     public Object getId()
     {
         return super.getPreferenceStore().getString(PreferenceConstants.P_STRING_ID);
     }
     
     /**
      * @category     Public Class Method
      * @description  Gets the state of the remote session permissions
      * @return       Object representing the state of the remote session permissions
      * @see          com.jocdev.rsace.team.Developer class
     */
     public Object getRemotePermissionsState ()
     {
         return super.getPreferenceStore().getString(PreferenceConstants.P_REMOTE_SHARING_PERMISSIONS);
     }
     
}
