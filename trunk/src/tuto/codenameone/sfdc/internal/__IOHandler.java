/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuto.codenameone.sfdc.internal;

import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.EventDispatcher;

/**
 *
 * @author bcirot
 */
public class __IOHandler {
    
    // Connection listening methods
    protected EventDispatcher connectionListeners;
    
    protected void fireConnectionListener(ActionEvent ev) {
        if(connectionListeners != null) {
            connectionListeners.fireActionEvent(ev);
        }
    }
    
    protected boolean hasConnectionListeners() {
        return connectionListeners != null;
    }
    
    public void addConnectionListener(ActionListener a) {
        if(connectionListeners == null) {
            connectionListeners = new EventDispatcher();
            connectionListeners.setBlocking(false);
        }
        connectionListeners.addListener(a);
    }
    
    public void removeConnectionListener(ActionListener a) {
        if(connectionListeners == null) {
            return;
        }
        connectionListeners.removeListener(a);
        if(connectionListeners.getListenerVector() == null || connectionListeners.getListenerVector().size() == 0) {
            connectionListeners = null;
        }
    }
    
    
    
    
    // Query listening methods
    protected EventDispatcher queryListeners;
    
    protected void fireQueryListener(ActionEvent ev) {
        if(queryListeners != null) {
            queryListeners.fireActionEvent(ev);
        }
    }
    
    protected boolean hasQueryListeners() {
        return queryListeners != null;
    }
    
    public void addQueryListener(ActionListener a) {
        if(queryListeners == null) {
            queryListeners = new EventDispatcher();
            queryListeners.setBlocking(false);
        }
        queryListeners.addListener(a);
    }
    
    public void removeQueryListener(ActionListener a) {
        if(queryListeners == null) {
            return;
        }
        queryListeners.removeListener(a);
        if(queryListeners.getListenerVector() == null || queryListeners.getListenerVector().size() == 0) {
            queryListeners = null;
        }
    }
}
