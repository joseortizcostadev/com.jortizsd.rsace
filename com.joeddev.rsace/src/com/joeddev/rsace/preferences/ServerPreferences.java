package com.joeddev.rsace.preferences;

public class ServerPreferences extends RsacePreferences 
{
     public ServerPreferences ()
     {
         super();
     }
     public ServerPreferences (String author, String email)
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
     
}
