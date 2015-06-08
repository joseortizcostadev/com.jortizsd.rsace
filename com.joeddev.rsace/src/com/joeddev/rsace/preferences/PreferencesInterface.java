package com.joeddev.rsace.preferences;

public interface PreferencesInterface 
{
    public void saveInPreferences(String key, Object value);
    public void updatePreferences(String key, Object value);
    public void deleteObjectFromPreferences(String key);
    public void getObjectFromPreferences (String key);

}
