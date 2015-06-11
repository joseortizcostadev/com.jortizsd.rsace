package com.joeddev.rsace.preferences;

public class DVTPreferencesGetter extends DVTPreferencesPage 
{
     public DVTPreferencesGetter ()
     {
         super();
     }
     public DVTPreferencesGetter (String author, String email)
     {
         super();
         
     }
     
     

     public Object getAuthor() 
     {
        return super.getPreferenceStore().getString(PreferenceConstants.P_STRING_AUTHOR_SERVER);
     }
     
     public Object getEmail()
     {
         return super.getPreferenceStore().getString(PreferenceConstants.P_STRING_EMAIL_SERVER);
     }
     
     public Object getId()
     {
         return super.getPreferenceStore().getString(PreferenceConstants.P_STRING_ID);
     }
     
     public Object getRemotePermissionsState ()
     {
         return super.getPreferenceStore().getString(PreferenceConstants.P_REMOTE_SHARING_PERMISSIONS);
     }
     
}
