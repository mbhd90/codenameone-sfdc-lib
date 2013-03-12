/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuto.codenameone.sfdc.internal;

import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.util.EventDispatcher;

/**
 *
 * @author bcirot
 */
public class __IOHandler {
    
    protected EventDispatcher actionListeners;
    
    protected void fireResponseListener(ActionEvent ev) {
        if(actionListeners != null) {
            actionListeners.fireActionEvent(ev);
        }
    }
    
    protected boolean hasResponseListeners() {
        return actionListeners != null;
    }
}
