
package com.martinanalytics.legaltime.client.model.bean;

import java.util.Map;
import java.util.HashMap;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * VwCustomerHourlyBillRateBean is a mapping of vw_customer_hourly_bill_rate Table.
 * @author sql2java
*/
public class VwCustomerHourlyBillRateBean implements IsSerializable {
    private static final long serialVersionUID = 1596685049191219492L;

    private java.util.Date lastUpdate;

    private boolean lastUpdateIsModified = false;
    private boolean lastUpdateIsInitialized = false;

    private Integer customerId;

    private boolean customerIdIsModified = false;
    private boolean customerIdIsInitialized = false;

    private Double billRate;

    private boolean billRateIsModified = false;
    private boolean billRateIsInitialized = false;

    private String displayName;

    private boolean displayNameIsModified = false;
    private boolean displayNameIsInitialized = false;

    private String userId;

    private boolean userIdIsModified = false;
    private boolean userIdIsInitialized = false;

    private Integer customerBillRateId;

    private boolean customerBillRateIdIsModified = false;
    private boolean customerBillRateIdIsInitialized = false;

    private Integer clientId;

    private boolean clientIdIsModified = false;
    private boolean clientIdIsInitialized = false;

    private boolean _isNew = true;

    /**
     * Prefered method to create a VwCustomerHourlyBillRate is via the createVwCustomerHourlyBillRate method in VwCustomerHourlyBillRateFetcher.
     */
    public VwCustomerHourlyBillRateBean() {
    }

    /**
     * Getter method for lastUpdate.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: vw_customer_hourly_bill_rate.last_update</li>
     * <li>column size: 29</li>
     * <li>jdbc type returned by the driver: Types.TIMESTAMP</li>
     * </ul>
     *
     * @return the value of lastUpdate
     */
    public java.util.Date getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Setter method for lastUpdate.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to lastUpdate
     */
    public void setLastUpdate(java.util.Date newVal) {
        if ((newVal != null && lastUpdate != null && (newVal.compareTo(lastUpdate) == 0)) ||
            (newVal == null && lastUpdate == null && lastUpdateIsInitialized)) {
            return;
        }
        lastUpdate = newVal;
        lastUpdateIsModified = true;
        lastUpdateIsInitialized = true;
    }

    /**
     * Setter method for lastUpdate.
     * <br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to lastUpdate
     */
    public void setLastUpdate(long newVal) {
        setLastUpdate(new java.util.Date(newVal));
    }

    /**
     * Determines if the lastUpdate has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isLastUpdateModified() {
        return lastUpdateIsModified;
    }

    /**
     * Determines if the lastUpdate has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isLastUpdateInitialized() {
        return lastUpdateIsInitialized;
    }

    /**
     * Getter method for customerId.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: vw_customer_hourly_bill_rate.customer_id</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of customerId
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     * Setter method for customerId.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to customerId
     */
    public void setCustomerId(Integer newVal) {
        if ((newVal != null && customerId != null && (newVal.compareTo(customerId) == 0)) ||
            (newVal == null && customerId == null && customerIdIsInitialized)) {
            return;
        }
        customerId = newVal;
        customerIdIsModified = true;
        customerIdIsInitialized = true;
    }

    /**
     * Setter method for customerId.
     * <br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to customerId
     */
    public void setCustomerId(int newVal) {
        setCustomerId(new Integer(newVal));
    }

    /**
     * Determines if the customerId has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isCustomerIdModified() {
        return customerIdIsModified;
    }

    /**
     * Determines if the customerId has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isCustomerIdInitialized() {
        return customerIdIsInitialized;
    }

    /**
     * Getter method for billRate.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: vw_customer_hourly_bill_rate.bill_rate</li>
     * <li>column size: 17</li>
     * <li>jdbc type returned by the driver: Types.DOUBLE</li>
     * </ul>
     *
     * @return the value of billRate
     */
    public Double getBillRate() {
        return billRate;
    }

    /**
     * Setter method for billRate.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to billRate
     */
    public void setBillRate(Double newVal) {
        if ((newVal != null && billRate != null && (newVal.compareTo(billRate) == 0)) ||
            (newVal == null && billRate == null && billRateIsInitialized)) {
            return;
        }
        billRate = newVal;
        billRateIsModified = true;
        billRateIsInitialized = true;
    }

    /**
     * Setter method for billRate.
     * <br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to billRate
     */
    public void setBillRate(double newVal) {
        setBillRate(new Double(newVal));
    }

    /**
     * Determines if the billRate has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isBillRateModified() {
        return billRateIsModified;
    }

    /**
     * Determines if the billRate has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isBillRateInitialized() {
        return billRateIsInitialized;
    }

    /**
     * Getter method for displayName.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: vw_customer_hourly_bill_rate.display_name</li>
     * <li>column size: 25</li>
     * <li>jdbc type returned by the driver: Types.VARCHAR</li>
     * </ul>
     *
     * @return the value of displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Setter method for displayName.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to displayName
     */
    public void setDisplayName(String newVal) {
        if ((newVal != null && displayName != null && (newVal.compareTo(displayName) == 0)) ||
            (newVal == null && displayName == null && displayNameIsInitialized)) {
            return;
        }
        displayName = newVal;
        displayNameIsModified = true;
        displayNameIsInitialized = true;
    }

    /**
     * Determines if the displayName has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isDisplayNameModified() {
        return displayNameIsModified;
    }

    /**
     * Determines if the displayName has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isDisplayNameInitialized() {
        return displayNameIsInitialized;
    }

    /**
     * Getter method for userId.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: vw_customer_hourly_bill_rate.user_id</li>
     * <li>column size: 2147483647</li>
     * <li>jdbc type returned by the driver: Types.VARCHAR</li>
     * </ul>
     *
     * @return the value of userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Setter method for userId.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to userId
     */
    public void setUserId(String newVal) {
        if ((newVal != null && userId != null && (newVal.compareTo(userId) == 0)) ||
            (newVal == null && userId == null && userIdIsInitialized)) {
            return;
        }
        userId = newVal;
        userIdIsModified = true;
        userIdIsInitialized = true;
    }

    /**
     * Determines if the userId has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isUserIdModified() {
        return userIdIsModified;
    }

    /**
     * Determines if the userId has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isUserIdInitialized() {
        return userIdIsInitialized;
    }

    /**
     * Getter method for customerBillRateId.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: vw_customer_hourly_bill_rate.customer_bill_rate_id</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of customerBillRateId
     */
    public Integer getCustomerBillRateId() {
        return customerBillRateId;
    }

    /**
     * Setter method for customerBillRateId.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to customerBillRateId
     */
    public void setCustomerBillRateId(Integer newVal) {
        if ((newVal != null && customerBillRateId != null && (newVal.compareTo(customerBillRateId) == 0)) ||
            (newVal == null && customerBillRateId == null && customerBillRateIdIsInitialized)) {
            return;
        }
        customerBillRateId = newVal;
        customerBillRateIdIsModified = true;
        customerBillRateIdIsInitialized = true;
    }

    /**
     * Setter method for customerBillRateId.
     * <br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to customerBillRateId
     */
    public void setCustomerBillRateId(int newVal) {
        setCustomerBillRateId(new Integer(newVal));
    }

    /**
     * Determines if the customerBillRateId has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isCustomerBillRateIdModified() {
        return customerBillRateIdIsModified;
    }

    /**
     * Determines if the customerBillRateId has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isCustomerBillRateIdInitialized() {
        return customerBillRateIdIsInitialized;
    }

    /**
     * Getter method for clientId.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: vw_customer_hourly_bill_rate.client_id</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of clientId
     */
    public Integer getClientId() {
        return clientId;
    }

    /**
     * Setter method for clientId.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to clientId
     */
    public void setClientId(Integer newVal) {
        if ((newVal != null && clientId != null && (newVal.compareTo(clientId) == 0)) ||
            (newVal == null && clientId == null && clientIdIsInitialized)) {
            return;
        }
        clientId = newVal;
        clientIdIsModified = true;
        clientIdIsInitialized = true;
    }

    /**
     * Setter method for clientId.
     * <br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to clientId
     */
    public void setClientId(int newVal) {
        setClientId(new Integer(newVal));
    }

    /**
     * Determines if the clientId has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isClientIdModified() {
        return clientIdIsModified;
    }

    /**
     * Determines if the clientId has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isClientIdInitialized() {
        return clientIdIsInitialized;
    }


    /**
     * Determines if the current object is new.
     *
     * @return true if the current object is new, false if the object is not new
     */
    public boolean isNew() {
        return _isNew;
    }

    /**
     * Specifies to the object if it has been set as new.
     *
     * @param isNew the boolean value to be assigned to the isNew field
     */
    public void isNew(boolean isNew) {
        this._isNew = isNew;
    }

    /**
     * Determines if the object has been modified since the last time this method was called.
     * <br>
     * We can also determine if this object has ever been modified since its creation.
     *
     * @return true if the object has been modified, false if the object has not been modified
     */
    public boolean isModified() {
        return lastUpdateIsModified         || customerIdIsModified          || billRateIsModified          || displayNameIsModified          || userIdIsModified          || customerBillRateIdIsModified          || clientIdIsModified  ;
    }

    /**
     * Resets the object modification status to 'not modified'.
     */
    public void resetIsModified() {
        lastUpdateIsModified = false;
        customerIdIsModified = false;
        billRateIsModified = false;
        displayNameIsModified = false;
        userIdIsModified = false;
        customerBillRateIdIsModified = false;
        clientIdIsModified = false;
    }

    /**
     * Copies the passed bean into the current bean.
     *
     * @param bean the bean to copy into the current bean
     */
    public void copy(VwCustomerHourlyBillRateBean bean) {
        setLastUpdate(bean.getLastUpdate());
        setCustomerId(bean.getCustomerId());
        setBillRate(bean.getBillRate());
        setDisplayName(bean.getDisplayName());
        setUserId(bean.getUserId());
        setCustomerBillRateId(bean.getCustomerBillRateId());
        setClientId(bean.getClientId());
    }

    /**
     * Return a dictionnary of the object.
     *
     * @return a map of column name keys to column value string representations
     */
    public  java.util.Map<java.lang.String, java.lang.String> getDictionnary() {
        Map<java.lang.String, java.lang.String> dictionnary = new HashMap<java.lang.String, java.lang.String>();
        dictionnary.put("last_update", getLastUpdate() == null ? "" : String.valueOf(getLastUpdate()));
        dictionnary.put("customer_id", getCustomerId() == null ? "" : String.valueOf(getCustomerId()));
        dictionnary.put("bill_rate", getBillRate() == null ? "" : String.valueOf(getBillRate()));
        dictionnary.put("display_name", getDisplayName() == null ? "" : String.valueOf(getDisplayName()));
        dictionnary.put("user_id", getUserId() == null ? "" : String.valueOf(getUserId()));
        dictionnary.put("customer_bill_rate_id", getCustomerBillRateId() == null ? "" : String.valueOf(getCustomerBillRateId()));
        dictionnary.put("client_id", getClientId() == null ? "" : String.valueOf(getClientId()));
        return dictionnary;
    }

    /**
     * Return a dictionnary of the primary key columns.
     * There is no primary key, the regular dictionnary is returned.
     * 
     * @return a map of column name keys to column value string representations
     */
    public java.util.Map<java.lang.String, java.lang.String> getPkDictionnary() {
        return getDictionnary();
    }

    /**
     * return a the value string representation of the given field
     */
    public String getValue(String column) {
        if (null == column || "".equals(column)) {
            return "";
        } else if ("last_update".equalsIgnoreCase(column) || "lastUpdate".equalsIgnoreCase(column)) {
            return getLastUpdate() == null ? "" : String.valueOf(getLastUpdate());
        } else if ("customer_id".equalsIgnoreCase(column) || "customerId".equalsIgnoreCase(column)) {
            return getCustomerId() == null ? "" : String.valueOf(getCustomerId());
        } else if ("bill_rate".equalsIgnoreCase(column) || "billRate".equalsIgnoreCase(column)) {
            return getBillRate() == null ? "" : String.valueOf(getBillRate());
        } else if ("display_name".equalsIgnoreCase(column) || "displayName".equalsIgnoreCase(column)) {
            return getDisplayName() == null ? "" : String.valueOf(getDisplayName());
        } else if ("user_id".equalsIgnoreCase(column) || "userId".equalsIgnoreCase(column)) {
            return getUserId() == null ? "" : String.valueOf(getUserId());
        } else if ("customer_bill_rate_id".equalsIgnoreCase(column) || "customerBillRateId".equalsIgnoreCase(column)) {
            return getCustomerBillRateId() == null ? "" : String.valueOf(getCustomerBillRateId());
        } else if ("client_id".equalsIgnoreCase(column) || "clientId".equalsIgnoreCase(column)) {
            return getClientId() == null ? "" : String.valueOf(getClientId());
        }
        return "";
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(Object object) {
        if (!(object instanceof VwCustomerHourlyBillRateBean)) {
            return false;
        }

        VwCustomerHourlyBillRateBean obj = (VwCustomerHourlyBillRateBean) object;
        if (this.lastUpdate != obj.lastUpdate) {
                if (this.lastUpdate != null && obj.lastUpdate != null && (obj.lastUpdate.compareTo(lastUpdate) != 0)) {
                    return false;
                }
        }
        if (this.customerId != obj.customerId) {
                if (this.customerId != null && obj.customerId != null && (obj.customerId.compareTo(customerId) != 0)) {
                    return false;
                }
        }
        if (this.billRate != obj.billRate) {
                if (this.billRate != null && obj.billRate != null && (obj.billRate.compareTo(billRate) != 0)) {
                    return false;
                }
        }
        if (this.displayName != obj.displayName) {
                if (this.displayName != null && obj.displayName != null && (obj.displayName.compareTo(displayName) != 0)) {
                    return false;
                }
        }
        if (this.userId != obj.userId) {
                if (this.userId != null && obj.userId != null && (obj.userId.compareTo(userId) != 0)) {
                    return false;
                }
        }
        if (this.customerBillRateId != obj.customerBillRateId) {
                if (this.customerBillRateId != null && obj.customerBillRateId != null && (obj.customerBillRateId.compareTo(customerBillRateId) != 0)) {
                    return false;
                }
        }
        if (this.clientId != obj.clientId) {
                if (this.clientId != null && obj.clientId != null && (obj.clientId.compareTo(clientId) != 0)) {
                    return false;
                }
        }
        return true;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        int total = -82280557;
        final int constant = -700257973;
        total = total * constant + this.lastUpdate.hashCode();
        total = total * constant + this.customerId.hashCode();
        total = total * constant + this.billRate.hashCode();
        total = total * constant + this.displayName.hashCode();
        total = total * constant + this.userId.hashCode();
        total = total * constant + this.customerBillRateId.hashCode();
        total = total * constant + this.clientId.hashCode();
        return total;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuffer sb = new StringBuffer(60);
            sb.append("last_update = ");
            sb.append(this.getLastUpdate());
            sb.append("\n");
            sb.append("customer_id = ");
            sb.append(this.getCustomerId());
            sb.append("\n");
            sb.append("bill_rate = ");
            sb.append(this.getBillRate());
            sb.append("\n");
            sb.append("display_name = ");
            sb.append(this.getDisplayName());
            sb.append("\n");
            sb.append("user_id = ");
            sb.append(this.getUserId());
            sb.append("\n");
            sb.append("customer_bill_rate_id = ");
            sb.append(this.getCustomerBillRateId());
            sb.append("\n");
            sb.append("client_id = ");
            sb.append(this.getClientId());
            sb.append("\n");
       return sb.toString();
    }


    public int compareTo(Object object) {
        VwCustomerHourlyBillRateBean obj = (VwCustomerHourlyBillRateBean) object;
        int comparison = 0;
        if (0 == comparison) {
            comparison = this.lastUpdate.compareTo(obj.lastUpdate);
        }
        if (0 == comparison) {
            comparison = this.customerId.compareTo(obj.customerId);
        }
        if (0 == comparison) {
            comparison = this.billRate.compareTo(obj.billRate);
        }
        if (0 == comparison) {
            comparison = this.displayName.compareTo(obj.displayName);
        }
        if (0 == comparison) {
            comparison = this.userId.compareTo(obj.userId);
        }
        if (0 == comparison) {
            comparison = this.customerBillRateId.compareTo(obj.customerBillRateId);
        }
        if (0 == comparison) {
            comparison = this.clientId.compareTo(obj.clientId);
        }
        return comparison;
   }
}
