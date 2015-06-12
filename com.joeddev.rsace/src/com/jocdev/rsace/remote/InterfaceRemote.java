/**
 * @author        Jose Ortiz Costa
 * @application   com.joeddev.rsace
 * @File          InterfaceRemote.java
 * @Date          04/06/2015
 * @Description   This interface provides signature methods 
 *                for remote communication between developers
 */
package com.jocdev.rsace.remote;
import java.rmi.Remote;
import org.eclipse.core.resources.IFile;
import com.jocdev.rsace.team.Developer;

public interface InterfaceRemote extends Remote
{
    public Developer getRemoteDeveloper();
    public void sendRemoteInvitation(String url, Developer dev);
    public void sendRemoteFile (IFile file);
    public IFile getRemoteFile();
    public boolean isServerWorking(String url);
    public IFile getUpdatedFile ();
    public void sendUpdatesInFile (IFile file);
    
}
