/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuto.codenameone.sfdc.event;

import com.codename1.ui.events.ActionEvent;
import java.util.Hashtable;
import tuto.codenameone.sfdc.utils.SFDC_ConnectionStatus;
import tuto.codenameone.sfdc.utils.SFDC_QueryStatus;

/**
 *
 * @author bcirot
 */
public class SFDC_QueryEvent extends ActionEvent {
    
    private String status;

    private String message;
    
    private Hashtable datas;
    
    public SFDC_QueryEvent(Object source, String status, String message, Hashtable dats) {
        super(source);
        this.status = status;
        this.message = message;
        this.datas = datas;
    }
    
    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Hashtable getDatas() {
        return datas;
    }
}
