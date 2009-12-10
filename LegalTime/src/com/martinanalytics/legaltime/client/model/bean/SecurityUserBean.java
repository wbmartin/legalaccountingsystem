

package com.martinanalytics.legaltime.client.model.bean;

import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * SecurityUserBean is a mapping of security_user Table.
 * @author sql2java
*/
public class SecurityUserBean implements IsSerializable {
    private static final long serialVersionUID = -1919045226787250814L;

    private Integer securityProfileId;

    private boolean securityProfileIdIsModified = false;
    private boolean securityProfileIdIsInitialized = false;

    private java.util.Date lastUpdate;

    private boolean lastUpdateIsModified = false;
    private boolean lastUpdateIsInitialized = false;

    private String activeYn;

    private boolean activeYnIsModified = false;
    private boolean activeYnIsInitialized = false;

    private java.util.Date sessionExpireDt;

    private boolean sessionExpireDtIsModified = false;
    private boolean sessionExpireDtIsInitialized = false;

    private String sessionId;

    private boolean sessionIdIsModified = false;
    private boolean sessionIdIsInitialized = false;

    private String passwordEnc;

    private boolean passwordEncIsModified = false;
    private boolean passwordEncIsInitialized = false;

    private String userId;

    private boolean userIdIsModified = false;
    private boolean userIdIsInitialized = false;

    private Integer clientId;

    private boolean clientIdIsModified = false;
    private boolean clientIdIsInitialized = false;

    private boolean _isNew = true;

    /**
     * Prefered method to create a SecurityUser is via the createSecurityUser method in SecurityUserFetcher.
     */
    public SecurityUserBean() {
    }

    /**
     * Getter method for securityProfileId.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: security_user.security_profile_id</li>
     * <li> foreign key: security_profile.security_profile_id</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of securityProfileId
     */
    public Integer getSecurityProfileId() {
        return securityProfileId;
    }

    /**
     * Setter method for securityProfileId.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to securityProfileId
     */
    public void setSecurityProfileId(Integer newVal) {
        if ((newVal != null && securityProfileId != null && (newVal.compareTo(securityProfileId) == 0)) ||
            (newVal == null && securityProfileId == null && securityProfileIdIsInitialized)) {
            return;
        }
        securityProfileId = newVal;
        securityProfileIdIsModified = true;
        securityProfileIdIsInitialized = true;
    }

    /**
     * Setter method for securityProfileId.
     * <br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to securityProfileId
     */
    public void setSecurityProfileId(int newVal) {
        setSecurityProfileId(new Integer(newVal));
    }

    /**
     * Determines if the securityProfileId has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isSecurityProfileIdModified() {
        return securityProfileIdIsModified;
    }

    /**
     * Determines if the securityProfileId has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isSecurityProfileIdInitialized() {
        return securityProfileIdIsInitialized;
    }

    /**
     * Getter method for lastUpdate.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: security_user.last_update</li>
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
     * Getter method for activeYn.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: security_user.active_yn</li>
     * <li>column size: 1</li>
     * <li>jdbc type returned by the driver: Types.CHAR</li>
     * </ul>
     *
     * @return the value of activeYn
     */
    public String getActiveYn() {
        return activeYn;
    }

    /**
     * Setter method for activeYn.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to activeYn
     */
    public void setActiveYn(String newVal) {
        if ((newVal != null && activeYn != null && (newVal.compareTo(activeYn) == 0)) ||
            (newVal == null && activeYn == null && activeYnIsInitialized)) {
            return;
        }
        activeYn = newVal;
        activeYnIsModified = true;
        activeYnIsInitialized = true;
    }

    /**
     * Determines if the activeYn has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isActiveYnModified() {
        return activeYnIsModified;
    }

    /**
     * Determines if the activeYn has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isActiveYnInitialized() {
        return activeYnIsInitialized;
    }

    /**
     * Getter method for sessionExpireDt.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: security_user.session_expire_dt</li>
     * <li>column size: 29</li>
     * <li>jdbc type returned by the driver: Types.TIMESTAMP</li>
     * </ul>
     *
     * @return the value of sessionExpireDt
     */
    public java.util.Date getSessionExpireDt() {
        return sessionExpireDt;
    }

    /**
     * Setter method for sessionExpireDt.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to sessionExpireDt
     */
    public void setSessionExpireDt(java.util.Date newVal) {
        if ((newVal != null && sessionExpireDt != null && (newVal.compareTo(sessionExpireDt) == 0)) ||
            (newVal == null && sessionExpireDt == null && sessionExpireDtIsInitialized)) {
            return;
        }
        sessionExpireDt = newVal;
        sessionExpireDtIsModified = true;
        sessionExpireDtIsInitialized = true;
    }

    /**
     * Setter method for sessionExpireDt.
     * <br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to sessionExpireDt
     */
    public void setSessionExpireDt(long newVal) {
        setSessionExpireDt(new java.util.Date(newVal));
    }

    /**
     * Determines if the sessionExpireDt has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isSessionExpireDtModified() {
        return sessionExpireDtIsModified;
    }

    /**
     * Determines if the sessionExpireDt has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isSessionExpireDtInitialized() {
        return sessionExpireDtIsInitialized;
    }

    /**
     * Getter method for sessionId.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: security_user.session_id</li>
     * <li>column size: 2147483647</li>
     * <li>jdbc type returned by the driver: Types.VARCHAR</li>
     * </ul>
     *
     * @return the value of sessionId
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * Setter method for sessionId.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to sessionId
     */
    public void setSessionId(String newVal) {
        if ((newVal != null && sessionId != null && (newVal.compareTo(sessionId) == 0)) ||
            (newVal == null && sessionId == null && sessionIdIsInitialized)) {
            return;
        }
        sessionId = newVal;
        sessionIdIsModified = true;
        sessionIdIsInitialized = true;
    }

    /**
     * Determines if the sessionId has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isSessionIdModified() {
        return sessionIdIsModified;
    }

    /**
     * Determines if the sessionId has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isSessionIdInitialized() {
        return sessionIdIsInitialized;
    }

    /**
     * Getter method for passwordEnc.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: security_user.password_enc</li>
     * <li>column size: 2147483647</li>
     * <li>jdbc type returned by the driver: Types.VARCHAR</li>
     * </ul>
     *
     * @return the value of passwordEnc
     */
    public String getPasswordEnc() {
        return passwordEnc;
    }

    /**
     * Setter method for passwordEnc.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to passwordEnc
     */
    public void setPasswordEnc(String newVal) {
        if ((newVal != null && passwordEnc != null && (newVal.compareTo(passwordEnc) == 0)) ||
            (newVal == null && passwordEnc == null && passwordEncIsInitialized)) {
            return;
        }
        passwordEnc = newVal;
        passwordEncIsModified = true;
        passwordEncIsInitialized = true;
    }

    /**
     * Determines if the passwordEnc has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isPasswordEncModified() {
        return passwordEncIsModified;
    }

    /**
     * Determines if the passwordEnc has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isPasswordEncInitialized() {
        return passwordEncIsInitialized;
    }

    /**
     * Getter method for userId.
     * <br>
     * PRIMARY KEY.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: security_user.user_id</li>
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
     * Getter method for clientId.
     * <br>
     * PRIMARY KEY.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: security_user.client_id</li>
     * <li> foreign key: client.client_id</li>
     * <li> foreign key: security_profile.client_id</li>
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
        return securityProfileIdIsModified         || lastUpdateIsModified          || activeYnIsModified          || sessionExpireDtIsModified          || sessionIdIsModified          || passwordEncIsModified          || userIdIsModified          || clientIdIsModified  ;
    }

    /**
     * Resets the object modification status to 'not modified'.
     */
    public void resetIsModified() {
        securityProfileIdIsModified = false;
        lastUpdateIsModified = false;
        activeYnIsModified = false;
        sessionExpireDtIsModified = false;
        sessionIdIsModified = false;
        passwordEncIsModified = false;
        userIdIsModified = false;
        clientIdIsModified = false;
    }

    /**
     * Copies the passed bean into the current bean.
     *
     * @param bean the bean to copy into the current bean
     */
    public void copy(SecurityUserBean bean) {
        setSecurityProfileId(bean.getSecurityProfileId());
        setLastUpdate(bean.getLastUpdate());
        setActiveYn(bean.getActiveYn());
        setSessionExpireDt(bean.getSessionExpireDt());
        setSessionId(bean.getSessionId());
        setPasswordEnc(bean.getPasswordEnc());
        setUserId(bean.getUserId());
        setClientId(bean.getClientId());
    }

    /**
     * Return a dictionnary of the object.
     *
     * @return a map of column name keys to column value string representations
     */
    public  java.util.Map<java.lang.String, java.lang.String> getDictionnary() {
        Map<java.lang.String, java.lang.String> dictionnary = new HashMap<java.lang.String, java.lang.String>();
        dictionnary.put("security_profile_id", getSecurityProfileId() == null ? "" : String.valueOf(getSecurityProfileId()));
        dictionnary.put("last_update", getLastUpdate() == null ? "" : String.valueOf(getLastUpdate()));
        dictionnary.put("active_yn", getActiveYn() == null ? "" : String.valueOf(getActiveYn()));
        dictionnary.put("session_expire_dt", getSessionExpireDt() == null ? "" : String.valueOf(getSessionExpireDt()));
        dictionnary.put("session_id", getSessionId() == null ? "" : String.valueOf(getSessionId()));
        dictionnary.put("password_enc", getPasswordEnc() == null ? "" : String.valueOf(getPasswordEnc()));
        dictionnary.put("user_id", getUserId() == null ? "" : String.valueOf(getUserId()));
        dictionnary.put("client_id", getClientId() == null ? "" : String.valueOf(getClientId()));
        return dictionnary;
    }

    /**
     * Return a dictionnary of the primary key columns.
     * 
     * @return a map of column name keys to column value string representations
     */
    public java.util.Map<java.lang.String, java.lang.String> getPkDictionnary() {
        Map<java.lang.String, java.lang.String> dictionnary = new HashMap<java.lang.String, java.lang.String>();
        dictionnary.put("client_id", getClientId() == null ? "" : String.valueOf(getClientId()));
        dictionnary.put("user_id", getUserId() == null ? "" : String.valueOf(getUserId()));
        return dictionnary;
    }

    /**
     * return a the value string representation of the given field
     */
    public String getValue(String column) {
        if (null == column || "".equals(column)) {
            return "";
        } else if ("security_profile_id".equalsIgnoreCase(column) || "securityProfileId".equalsIgnoreCase(column)) {
            return getSecurityProfileId() == null ? "" : String.valueOf(getSecurityProfileId());
        } else if ("last_update".equalsIgnoreCase(column) || "lastUpdate".equalsIgnoreCase(column)) {
            return getLastUpdate() == null ? "" : String.valueOf(getLastUpdate());
        } else if ("active_yn".equalsIgnoreCase(column) || "activeYn".equalsIgnoreCase(column)) {
            return getActiveYn() == null ? "" : String.valueOf(getActiveYn());
        } else if ("session_expire_dt".equalsIgnoreCase(column) || "sessionExpireDt".equalsIgnoreCase(column)) {
            return getSessionExpireDt() == null ? "" : String.valueOf(getSessionExpireDt());
        } else if ("session_id".equalsIgnoreCase(column) || "sessionId".equalsIgnoreCase(column)) {
            return getSessionId() == null ? "" : String.valueOf(getSessionId());
        } else if ("password_enc".equalsIgnoreCase(column) || "passwordEnc".equalsIgnoreCase(column)) {
            return getPasswordEnc() == null ? "" : String.valueOf(getPasswordEnc());
        } else if ("user_id".equalsIgnoreCase(column) || "userId".equalsIgnoreCase(column)) {
            return getUserId() == null ? "" : String.valueOf(getUserId());
        } else if ("client_id".equalsIgnoreCase(column) || "clientId".equalsIgnoreCase(column)) {
            return getClientId() == null ? "" : String.valueOf(getClientId());
        }
        return "";
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(Object object) {
        if (!(object instanceof SecurityUserBean)) {
            return false;
        }

        SecurityUserBean obj = (SecurityUserBean) object;
        if (this.securityProfileId != obj.securityProfileId) {
                if (this.securityProfileId != null && obj.securityProfileId != null && (obj.securityProfileId.compareTo(securityProfileId) != 0)) {
                    return false;
                }
        }
        if (this.lastUpdate != obj.lastUpdate) {
                if (this.lastUpdate != null && obj.lastUpdate != null && (obj.lastUpdate.compareTo(lastUpdate) != 0)) {
                    return false;
                }
        }
        if (this.activeYn != obj.activeYn) {
                if (this.activeYn != null && obj.activeYn != null && (obj.activeYn.compareTo(activeYn) != 0)) {
                    return false;
                }
        }
        if (this.sessionExpireDt != obj.sessionExpireDt) {
                if (this.sessionExpireDt != null && obj.sessionExpireDt != null && (obj.sessionExpireDt.compareTo(sessionExpireDt) != 0)) {
                    return false;
                }
        }
        if (this.sessionId != obj.sessionId) {
                if (this.sessionId != null && obj.sessionId != null && (obj.sessionId.compareTo(sessionId) != 0)) {
                    return false;
                }
        }
        if (this.passwordEnc != obj.passwordEnc) {
                if (this.passwordEnc != null && obj.passwordEnc != null && (obj.passwordEnc.compareTo(passwordEnc) != 0)) {
                    return false;
                }
        }
        if (this.userId != obj.userId) {
                if (this.userId != null && obj.userId != null && (obj.userId.compareTo(userId) != 0)) {
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
        total = total * constant + this.securityProfileId.hashCode();
        total = total * constant + this.lastUpdate.hashCode();
        total = total * constant + this.activeYn.hashCode();
        total = total * constant + this.sessionExpireDt.hashCode();
        total = total * constant + this.sessionId.hashCode();
        total = total * constant + this.passwordEnc.hashCode();
        total = total * constant + this.userId.hashCode();
        total = total * constant + this.clientId.hashCode();
        return total;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuffer sb = new StringBuffer(60);
            sb.append("security_profile_id = ");
            sb.append(this.getSecurityProfileId());
            sb.append("\n");
            sb.append("last_update = ");
            sb.append(this.getLastUpdate());
            sb.append("\n");
            sb.append("active_yn = ");
            sb.append(this.getActiveYn());
            sb.append("\n");
            sb.append("session_expire_dt = ");
            sb.append(this.getSessionExpireDt());
            sb.append("\n");
            sb.append("session_id = ");
            sb.append(this.getSessionId());
            sb.append("\n");
            sb.append("password_enc = ");
            sb.append(this.getPasswordEnc());
            sb.append("\n");
            sb.append("user_id = ");
            sb.append(this.getUserId());
            sb.append("\n");
            sb.append("client_id = ");
            sb.append(this.getClientId());
            sb.append("\n");
       return sb.toString();
    }


    public int compareTo(Object object) {
        SecurityUserBean obj = (SecurityUserBean) object;
        int comparison = 0;
        if (0 == comparison) {
            comparison = this.securityProfileId.compareTo(obj.securityProfileId);
        }
        if (0 == comparison) {
            comparison = this.lastUpdate.compareTo(obj.lastUpdate);
        }
        if (0 == comparison) {
            comparison = this.activeYn.compareTo(obj.activeYn);
        }
        if (0 == comparison) {
            comparison = this.sessionExpireDt.compareTo(obj.sessionExpireDt);
        }
        if (0 == comparison) {
            comparison = this.sessionId.compareTo(obj.sessionId);
        }
        if (0 == comparison) {
            comparison = this.passwordEnc.compareTo(obj.passwordEnc);
        }
        if (0 == comparison) {
            comparison = this.userId.compareTo(obj.userId);
        }
        if (0 == comparison) {
            comparison = this.clientId.compareTo(obj.clientId);
        }
        return comparison;
   }
}
