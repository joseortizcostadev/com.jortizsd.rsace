package com.jortizsd.rsace;
import java.util.*;

public class AbstractTaskBuilder 
{
    public static  List <Task> tasks;
    
    
    public AbstractTaskBuilder ()
    {
    	tasks = new ArrayList <> ();
    }
    
    protected void addTask (Task task)
    {
    	tasks.add(task);
    }
    
    protected boolean deleteTask (String taskName)
    {
    	for (Task task : tasks)
    	{
    		if (task.getTaskName().equalsIgnoreCase(taskName))
    		{
    			tasks.remove(task);
    			return true;
    		}
    	}
    	return false;
    }
    
    public static void readTasks ()
    {
    	for (Task t : tasks)
    	{
    		System.out.println("TaskName: " + t.getTaskName() + " Execution Time: " + t.getExecutionTime());
    	}
    }
    
    
    
    
}
