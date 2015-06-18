package com.jortizsd.rsace;

import java.util.concurrent.TimeUnit;

public class Task extends AbstractTaskBuilder
{
    private String name;
    private long totalTime;
    private long startTime;
    private long stopTime;
    public static final String MODE_SECONDS = "seconds";
    public static final String MODE_MILISECONDS = "miliSeconds";
    public static final String MODE_NANOSECONDS = "nanoSeconds";
    private String mode;

    public Task (String mode)
    {
    	 super();
    	 this.mode = mode;
    	 
    }
     
    public Task (String taskName, String mode)
    {
    	 super();
    	 this.mode = mode;
    	 setTaskName(taskName);
    }
    
    public void setTaskName (String taskName)
    {
    	this.name = taskName;
    }
    
    
    
    public String getTaskName ()
    {
    	return this.name;
    }
    
    public void startTime ()
    {
    	if (mode.equalsIgnoreCase(MODE_SECONDS))
    	    this.startTime = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
    	else if (mode.equalsIgnoreCase(MODE_MILISECONDS))
    		this.startTime = System.currentTimeMillis();
    	else if (mode.equalsIgnoreCase(MODE_NANOSECONDS))
    		this.startTime = TimeUnit.MILLISECONDS.toNanos(System.currentTimeMillis());
    	System.out.println(this.startTime);
    }
    
    public void stopTime ()
    {
    	if (mode.equalsIgnoreCase(MODE_SECONDS))
    	    this.stopTime = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
    	else if (mode.equalsIgnoreCase(MODE_MILISECONDS))
    		this.stopTime = System.currentTimeMillis();
    	else if (mode.equalsIgnoreCase(MODE_NANOSECONDS))
    		this.stopTime = TimeUnit.MILLISECONDS.toNanos(System.currentTimeMillis());
    	calculateExecutionTime();
    	addTaskToProcesses();
    	
    }
    
    private void calculateExecutionTime ()
    {
    	this.totalTime = this.stopTime - this.startTime;
    	
    	
    }
    
    public long getExecutionTime ()
    {
    	return this.totalTime;
    }
    
    private void addTaskToProcesses()
    {
    	super.addTask(this);
    }
    
    
    
    
    
    
}
