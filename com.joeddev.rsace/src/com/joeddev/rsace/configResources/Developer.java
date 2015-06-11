package com.joeddev.rsace.configResources;




public class Developer
{
    
    private String id;
    private String name;
    private String email;
    private boolean active;
    private boolean isTheSender;
    private String fileTarget;
    public Developer () 
    {
        // Empty Constructor
    }
    public Developer (String id, String name, String email, boolean isActive, boolean isTheSender) 
    {
        
        this.id = id;
        this.name = name;
        this.email = email;
        this.active = isActive;
        this.isTheSender = isTheSender;
    }
    
    public void setId (String id)
    {
       this.id = id;
    }
    
    public void setName (String name)
    {
       this.name = name;
    }
    
    public void setEmail (String email)
    {
       this.email = email;
    }
    
    public void setAsSender (boolean isSender)
    {
        this.isTheSender = isSender;
    }
    
    public void setActive (boolean isActive)
    {
        this.active = isActive;
    }
    
    public String getId ()
    {
        return id;
    }
    public String getName ()
    {
        return name;
    }
    
    public String getEmail ()
    {
        return email;
    }
    
    public boolean isSender ()
    {
        return isTheSender;
    }
    
    public boolean isActive ()
    {
        return active;
    }
    
    public String getFileTarget ()
    {
        return fileTarget;
    }
    
   
}
