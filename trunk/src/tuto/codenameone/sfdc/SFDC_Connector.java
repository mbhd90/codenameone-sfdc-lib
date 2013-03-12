/*
 * Copyright comment here
 */
package tuto.codenameone.sfdc;

import tuto.codenameone.sfdc.internal.__IOHandler;
import tuto.codenameone.sfdc.utils.SFDC_Environment;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.EventDispatcher;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;
import tuto.codenameone.sfdc.event.SFDC_ConnectionEvent;
import tuto.codenameone.sfdc.internal.__ActionListener;
import tuto.codenameone.sfdc.utils.SFDC_ConnectionStatus;

/**
 * This is a demo class to help you get started building a library
 *
 * @author Your name here
 */
public class SFDC_Connector extends __IOHandler {
    
    // Access Token Request Parameters
    private static String prodLoginURL = "https://login.salesforce.com/services/oauth2/token";
    private static String sandboxLoginURL = "https://test.salesforce.com/services/oauth2/token";
    
    // Access Token Response parameters
    private String access_token;
    private String instance_url;
    private String id;
    private String issued_at;
    private String signature;
    
    // Services
    private String listApiVersionsURL = "/services/data/";
    private String listResourcesURL = "/services/data/v26.0/";
    
    private ConnectionRequest request;
    private SFDC_Environment currentEnvironment;
    
    /**
     * Contructor
     */
    public SFDC_Connector(SFDC_Environment environement) {
        this.currentEnvironment = environement;
    }
    
    public void connect(String login, String password, String clientId, String clientSecret) {
        initConnectionRequest(login, password, clientId, clientSecret, null);
    }
    
    public void connect(String login, String password, String clientId, String clientSecret, String securityToken) {
        initConnectionRequest(login, password, clientId, clientSecret, securityToken);
    }
    
    private void initConnectionRequest(String login, String password, String clientId, String clientSecret, String securityToken) {
        request = new ConnectionRequest();
        request.setPost(true);
        
        String loginURL;
        if (currentEnvironment.equals(SFDC_Environment.PRODUCTION)) {
            loginURL = prodLoginURL;
        } else {
            loginURL = sandboxLoginURL;
        }
        request.setUrl(loginURL);
        
        request.addArgument("grant_type", "password");
        request.addArgument("client_id", clientId);
        request.addArgument("client_secret", clientSecret);
        request.addArgument("username", login);
        String connectionPassword = password;
        if (securityToken != null) {
            connectionPassword += securityToken;
        }
        request.addArgument("password", connectionPassword);
        
        request.addResponseListener(connectionListener);
        
        NetworkManager.getInstance().addToQueue(request);
    }
    
    public void addConnectionListener(ActionListener a) {
        if(actionListeners == null) {
            actionListeners = new EventDispatcher();
            actionListeners.setBlocking(false);
        }
        actionListeners.addListener(a);
    }
    
    public void removeConnectionListener(ActionListener a) {
        if(actionListeners == null) {
            return;
        }
        actionListeners.removeListener(a);
        if(actionListeners.getListenerVector() == null || actionListeners.getListenerVector().size() == 0) {
            actionListeners = null;
        }
    }
    
    private __ActionListener connectionListener = new __ActionListener(this) {
            
        public void actionPerformed(ActionEvent evt) {
            try {
                NetworkEvent networkEvt = (NetworkEvent)evt;
                Log.p("Server response : " + networkEvt.getResponseCode());
                byte[] dataArray = (byte[]) networkEvt.getMetaData();
                Log.p("Response datas : " + new String(dataArray));
                InputStream input = new ByteArrayInputStream(dataArray);
                JSONParser p = new JSONParser();
                Hashtable hashTable = p.parse(new InputStreamReader(input));
                access_token = (String) hashTable.get("access_token");
                Log.p("Access token : " + access_token);
                instance_url = (String) hashTable.get("instance_url");
                Log.p("Instance URL : " + instance_url);
                id = (String) hashTable.get("id");
                Log.p("ID : " + id);
                issued_at = (String) hashTable.get("issued_at");
                Log.p("Issued at : " + issued_at);
                signature = (String) hashTable.get("signature");
                Log.p("Signature : " + signature);

                // Create Result Event
                SFDC_ConnectionEvent event = new SFDC_ConnectionEvent((SFDC_Connector)source, SFDC_ConnectionStatus.SUCCESS, "");
                // Send response
                if (hasResponseListeners()) {
                    fireResponseListener(event);
                }
                
            } catch (IOException ex) {
                Log.e(ex);
            }
        }
    };
    
}
