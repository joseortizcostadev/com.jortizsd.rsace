package com.joeddev.rsace.developers;

public class Developer 
{
    private String name;
    private String email;
    private boolean active;
    
    public Developer (String name, String email, boolean isActive)
    {
        this.name = name;
        this.email = email;
        this.active = isActive;
    }
    
    public void setName (String name)
    {
        this.name = name;
    }
    
    public void setEmail (String email)
    {
        this.email = email;
    }
    
    public void setActive (boolean isActive)
    {
        this.active = isActive;
    }
    
    public String getName ()
    {
        return name;
    }
    
    public String getEmail ()
    {
        return email;
    }
    
    public boolean isActive ()
    {
        return active;
    }
}
