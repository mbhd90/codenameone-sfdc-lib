/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuto.codenameone.sfdc.event;

import com.codename1.ui.events.ActionEvent;
import tuto.codenameone.sfdc.SFDC_Connector;
import tuto.codenameone.sfdc.utils.SFDC_ConnectionStatus;

/**
 *
 * @author bcirot
 */
public class SFDC_ConnectionEvent extends ActionEvent {
    
    private SFDC_ConnectionStatus status;

    private String message;
    
    public SFDC_ConnectionEvent(Object source, SFDC_ConnectionStatus status, String message) {
        super(source);
        this.status = status;
        this.message = message;
    }
    
    public SFDC_ConnectionStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
