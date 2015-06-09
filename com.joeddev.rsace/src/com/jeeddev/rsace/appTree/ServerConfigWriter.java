package com.jeeddev.rsace.appTree;



import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IFolder;
import org.w3c.dom.Element;

public class ServerConfigWriter extends AbstractTreeWriter
{
    public static final String ROOT_SERVER_MEMBERS = "server_members";
    private Element root;
    private Element subType;
    public ServerConfigWriter(String rootType) throws ParserConfigurationException 
    {
        super();
        root = super.createRoot(rootType);
        
    }

    private void setId (String context, String key, String id)
    {
        Element subType = super.createElement(this.root, context);
        super.setAtrribute(subType, key, id);
        this.subType = subType;
    }
    
    private void setMemberName (String name)
    {
        setChildNode(this.subType, "name", name);
    }
     
    private void setMemberEmail (String email)
    {
        setChildNode(this.subType, "email", email);
    }
    private void setSessionStatus (boolean isActive)
    {
        setChildNode(this.subType, "session_active", String.valueOf(isActive));
    }
    
    public void createServerOwner (String keyId, String valueId, String ownerName, String ownerEmail, boolean isSessionActive)
    {
        setId("owner", keyId, valueId);
        setMemberName(ownerName);
        setMemberEmail(ownerEmail);
        setSessionStatus(isSessionActive);
        saveInConfigFile(TreeBuilder.CONFIG_DIR, ConfigBuilder.SERVER_FILE_CONFIG);
    }
    
    public void updateServerOwner (String keyId, String valueId, String ownerName, String ownerEmail, boolean isSessionActive)
    {
        setId("owner", keyId, valueId);
        setMemberName(ownerName);
        setMemberEmail(ownerEmail);
        setSessionStatus(isSessionActive);
        updateInConfigFile(TreeBuilder.CONFIG_DIR, ConfigBuilder.SERVER_FILE_CONFIG);
    }
    
}
