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
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Vector;
import tuto.codenameone.sfdc.event.SFDC_ConnectionEvent;
import tuto.codenameone.sfdc.event.SFDC_QueryEvent;
import tuto.codenameone.sfdc.object.SFDC_Account;
import tuto.codenameone.sfdc.utils.SFDC_ConnectionStatus;
import tuto.codenameone.sfdc.utils.SFDC_QueryStatus;

/**
 * SFDC Connector for CodenameOne
 *
 * @author BCirot (Tuto-CodenameOne)
 */
public class SFDC_Connector extends __IOHandler {
    
    // Access Token Request Parameters
    private static String prodLoginURL = "https://login.salesforce.com/services/oauth2/token";
    private static String sandboxLoginURL = "https://test.salesforce.com/services/oauth2/token";
    
    // Access Token Response parameters
    public String access_token;
    public String instance_url;
    public String id;
    public String issued_at;
    public String signature;
    
    // Services
    private String listApiVersionsURL = "/services/data/";
    private String listResourcesURL = "/services/data/v26.0/";
    private String queryURL = "query/";
    
    private ConnectionRequest request;
    private String currentEnvironment;
    
    private Class queryObjectType;
    
    /**
     * Contructor
     */
    public SFDC_Connector(String environement) {
        this.currentEnvironment = environement;
    }
    
    public void connect(String login, String password, String consumerKey, String consumerSecret) {
        initConnectionRequest(login, password, consumerKey, consumerSecret, null);
    }
    
    public void connect(String login, String password, String consumerKey, String consumerSecret, String securityToken) {
        initConnectionRequest(login, password, consumerKey, consumerSecret, securityToken);
    }
    
    private void initConnectionRequest(String login, String password, String clientId, String clientSecret, String securityToken) {
        request = new ConnectionRequest();
        request.setPost(true);
        
        request.setUrl(getSFDCConnectionURL());
        
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
    
    private String getSFDCConnectionURL() {
        String loginURL;
        if (currentEnvironment.equals(SFDC_Environment.SANDBOX)) {
            loginURL = sandboxLoginURL;
        } else {
            loginURL = prodLoginURL;
        }
        return loginURL;
        
    }
    
    private ActionListener connectionListener = new ActionListener() {
            
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
                SFDC_ConnectionEvent event = new SFDC_ConnectionEvent(this, SFDC_ConnectionStatus.SUCCESS, "");
                // Send response
                if (hasConnectionListeners()) {
                    fireConnectionListener(event);
                }
                
                // connectionListeners = new EventDispatcher();
                        
            } catch (IOException ex) {
                Log.e(ex);
            }
        }
    };
    
    public void executeQuery(String query) {
        queryObjectType = null;
        launchQuery(query);
    }
    
    public void executeQuery(String query, Class objectType) {
        queryObjectType = objectType;
        launchQuery(query);
    }
    
    private void launchQuery(String query) {
        request = new ConnectionRequest();
        request.setPost(false);
        
        request.setUrl(instance_url + listResourcesURL + queryURL);
        
        request.addRequestHeader("Authorization", "OAuth " + access_token);
        request.addArgument("q", query);
        
        request.addResponseListener(queryListener);
        
        NetworkManager.getInstance().addToQueue(request);
    }
    
    
    private ActionListener queryListener = new ActionListener() {
            
        public void actionPerformed(ActionEvent evt) {
            try {
                NetworkEvent networkEvt = (NetworkEvent)evt;
                Log.p("Server response : " + networkEvt.getResponseCode());
                byte[] dataArray = (byte[]) networkEvt.getMetaData();
                Log.p("Response datas : " + new String(dataArray));
                InputStream input = new ByteArrayInputStream(dataArray);
                JSONParser p = new JSONParser();
                Hashtable hashTable = p.parse(new InputStreamReader(input));
                
                // Create Result Event
                Vector result = (Vector) hashTable.get("records");
                SFDC_QueryEvent event = new SFDC_QueryEvent(this, SFDC_QueryStatus.SUCCESS, new String(dataArray), result);
                // Send response
                if (hasQueryListeners()) {
                    fireQueryListener(event);
                }
                
                // queryListeners = new EventDispatcher();
                
            } catch (IOException ex) {
                Log.e(ex);
            }
        }
    };
    
    
    
    // Newwwwwwwwwwwwwwwwwwwwwwwwwwww
    
    private void getApiVersions() {
        // Get API Versions
        request = new ConnectionRequest();
        request.setPost(false);
        request.setUrl(instance_url + listApiVersionsURL);
        request.addResponseListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent evt) {
                try {
                    NetworkEvent networkEvt = (NetworkEvent)evt;
                    Log.p("Server response : " + networkEvt.getResponseCode());
                    byte[] dataArray = (byte[]) networkEvt.getMetaData();
                    Log.p("Response datas : " + new String(dataArray));
                    InputStream input = new ByteArrayInputStream(dataArray);
                    JSONParser p = new JSONParser();
                    Hashtable hashTable = p.parse(new InputStreamReader(input));
                    Log.p("API Versions : " + hashTable);
                } catch (IOException ex) {
                    Log.p("IOException : " + ex.getMessage() != null ? ex.getMessage():ex.toString());
                    ex.printStackTrace();
                }
            }
            
        });
        
        NetworkManager.getInstance().addToQueue(request);
    }
    
    private void getAvailableResources() {
        // Get API Versions
        request = new ConnectionRequest();
        request.setPost(false);
        request.setUrl(instance_url + listResourcesURL);
        request.addRequestHeader("Authorization", "OAuth " + access_token);
        request.addResponseListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent evt) {
                try {
                    NetworkEvent networkEvt = (NetworkEvent)evt;
                    Log.p("Server response : " + networkEvt.getResponseCode());
                    byte[] dataArray = (byte[]) networkEvt.getMetaData();
                    Log.p("Response datas : " + new String(dataArray));
                    InputStream input = new ByteArrayInputStream(dataArray);
                    JSONParser p = new JSONParser();
                    Hashtable hashTable = p.parse(new InputStreamReader(input));
                    Log.p("API Versions : " + hashTable);
                } catch (IOException ex) {
                    Log.p("IOException : " + ex.getMessage() != null ? ex.getMessage():ex.toString());
                    ex.printStackTrace();
                }
            }
            
        });
        
        NetworkManager.getInstance().addToQueue(request);
    }
}
