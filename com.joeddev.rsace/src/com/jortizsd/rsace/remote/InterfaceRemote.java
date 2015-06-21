/**
 * @author        Jose Ortiz Costa
 * @application   com.joeddev.rsace
 * @File          InterfaceRemote.java
 * @Date          04/06/2015
 * @Description   This interface provides signature methods 
 *                for remote communication between developers
 */
package com.jortizsd.rsace.remote;
import java.rmi.Remote;
import java.rmi.RemoteException;

import org.eclipse.core.resources.IFile;

import com.jortizsd.rsace.team.Developer;

public interface InterfaceRemote extends Remote
{
    // public Developer getRemoteDeveloper(String developerId) throws RemoteException;
   // public void registerRemoteDeveloper (String teamId, String developerId, String developerName, String developerEmail) throws RemoteException;
   // public boolean doesRemoteDeveloperExist (String developerId) throws RemoteException;
   // public void updateRemoteDeveloperInfo (String developerId, String tag, String value);
   // public void deleteRemoteDeveloper (String developerId) throws RemoteException;
   // public boolean isServerUp (String server) throws RemoteException;
   // public void addDeveloperToRemoteTeam (String developerId, String teamId) throws RemoteException;
   // public void createRemoteConfigurationFile () throws RemoteException;
	public void hello (String name) throws RemoteException;
}
