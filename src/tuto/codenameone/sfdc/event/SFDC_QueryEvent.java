/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuto.codenameone.sfdc.event;

import com.codename1.ui.events.ActionEvent;
import java.util.Vector;

/**
 *
 * @author bcirot
 */
public class SFDC_QueryEvent extends ActionEvent {
    
    private String status;

    private String message;
    
    private Vector datas;
    
    public SFDC_QueryEvent(Object source, String status, String message, Vector rawDatas) {
        super(source);
        this.status = status;
        this.message = message;
        this.datas = rawDatas;
    }
    
    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
    
    public Vector getDatas() {
        return datas;
    }
}
