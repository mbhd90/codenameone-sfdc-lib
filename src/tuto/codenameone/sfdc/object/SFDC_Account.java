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
public class SFDC_Account extends SFDC_Object implements SFDC_IObject {

    private String Name;
    
    private String AccountNumber;
    
    private String OwnerId;
    
    private String Site;
    
    private String AccountSource;
    
    private String AnnualRevenue;
    
    private String BillingAddress;
    
    private String CreatedBy;
    
    private String Jigsaw;
    
    private String Description;
    
    private String NumberOfEmployees;
    
    private String Fax;
    
    private String Industry;
    
    private String LastModifiedBy;
    
    private String Ownership;
    
    private String ParentId;
    
    private String Phone;
    
    private String Rating;
    
    private String ShippingAddress;
    
    private String Sic;
    
    private String SicDesc;
    
    private String TickerSymbol;
    
    private String Type;
    
    private String Website;
    
    
    @Override
    public String toString() {
        return "SFDC_Account{" + "Id=" + getId() + ", Name=" + getName() + '}';
    }

    public void injectDatas(Hashtable datas) {
        Set keys = datas.keySet();
        for (Object key : keys) {
            String keyAsString = (String) key;
            if (keyAsString.equals("Id")) {
                this.setId((String) datas.get(keyAsString));
            } else if (keyAsString.equals("Name")) {
                this.setName((String) datas.get(keyAsString));
            }
        }
    }
    

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }
    
    public String getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(String AccountNumber) {
        this.AccountNumber = AccountNumber;
    }

    public String getOwnerId() {
        return OwnerId;
    }

    public void setOwnerId(String OwnerId) {
        this.OwnerId = OwnerId;
    }

    public String getSite() {
        return Site;
    }

    public void setSite(String Site) {
        this.Site = Site;
    }

    public String getAccountSource() {
        return AccountSource;
    }

    public void setAccountSource(String AccountSource) {
        this.AccountSource = AccountSource;
    }

    public String getAnnualRevenue() {
        return AnnualRevenue;
    }

    public void setAnnualRevenue(String AnnualRevenue) {
        this.AnnualRevenue = AnnualRevenue;
    }

    public String getBillingAddress() {
        return BillingAddress;
    }

    public void setBillingAddress(String BillingAddress) {
        this.BillingAddress = BillingAddress;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String CreatedBy) {
        this.CreatedBy = CreatedBy;
    }

    public String getJigsaw() {
        return Jigsaw;
    }

    public void setJigsaw(String Jigsaw) {
        this.Jigsaw = Jigsaw;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getNumberOfEmployees() {
        return NumberOfEmployees;
    }

    public void setNumberOfEmployees(String NumberOfEmployees) {
        this.NumberOfEmployees = NumberOfEmployees;
    }

    public String getFax() {
        return Fax;
    }

    public void setFax(String Fax) {
        this.Fax = Fax;
    }

    public String getIndustry() {
        return Industry;
    }

    public void setIndustry(String Industry) {
        this.Industry = Industry;
    }

    public String getLastModifiedBy() {
        return LastModifiedBy;
    }

    public void setLastModifiedBy(String LastModifiedBy) {
        this.LastModifiedBy = LastModifiedBy;
    }

    public String getOwnership() {
        return Ownership;
    }

    public void setOwnership(String Ownership) {
        this.Ownership = Ownership;
    }

    public String getParentId() {
        return ParentId;
    }

    public void setParentId(String ParentId) {
        this.ParentId = ParentId;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String Rating) {
        this.Rating = Rating;
    }

    public String getShippingAddress() {
        return ShippingAddress;
    }

    public void setShippingAddress(String ShippingAddress) {
        this.ShippingAddress = ShippingAddress;
    }

    public String getSic() {
        return Sic;
    }

    public void setSic(String Sic) {
        this.Sic = Sic;
    }

    public String getSicDesc() {
        return SicDesc;
    }

    public void setSicDesc(String SicDesc) {
        this.SicDesc = SicDesc;
    }

    public String getTickerSymbol() {
        return TickerSymbol;
    }

    public void setTickerSymbol(String TickerSymbol) {
        this.TickerSymbol = TickerSymbol;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String Website) {
        this.Website = Website;
    }
}
