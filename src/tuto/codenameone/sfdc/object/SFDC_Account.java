/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuto.codenameone.sfdc.object;

import java.util.Hashtable;
import java.util.Set;

/**
 *
 * @author BeBack
 */
public class SFDC_Account implements SFDC_IObject {

    public String Id;
    public String Name;
    
    @Override
    public String toString() {
        return "SFDC_Account{" + "Id=" + Id + ", Name=" + Name + '}';
    }

    public void injectDatas(Hashtable datas) {
        Set keys = datas.keySet();
        for (Object key : keys) {
            String keyAsString = (String) key;
            if (keyAsString.equals("Id")) {
                this.Id = (String) datas.get(keyAsString);
            } else if (keyAsString.equals("Name")) {
                this.Name = (String) datas.get(keyAsString);
            }
        }
    }
}
