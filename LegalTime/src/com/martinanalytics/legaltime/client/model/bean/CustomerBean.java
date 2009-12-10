

package com.martinanalytics.legaltime.client.model.bean;


import java.util.Map;
import java.util.HashMap;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * CustomerBean is a mapping of customer Table.
 * <br>Meta Data Information (in progress):
 * <ul>
 *    <li>comments: customer info table </li>
 * </ul>
 * @author sql2java
*/
public class CustomerBean implements IsSerializable {
    private static final long serialVersionUID = -6662277796317084399L;

    private String activeYn;

    private boolean activeYnIsModified = false;
    private boolean activeYnIsInitialized = false;

    private Double monthlyBillRate;

    private boolean monthlyBillRateIsModified = false;
    private boolean monthlyBillRateIsInitialized = false;

    private String billType;

    private boolean billTypeIsModified = false;
    private boolean billTypeIsInitialized = false;

    private String note;

    private boolean noteIsModified = false;
    private boolean noteIsInitialized = false;

    private java.util.Date clientSinceDt;

    private boolean clientSinceDtIsModified = false;
    private boolean clientSinceDtIsInitialized = false;

    private String email;

    private boolean emailIsModified = false;
    private boolean emailIsInitialized = false;

    private String fax;

    private boolean faxIsModified = false;
    private boolean faxIsInitialized = false;

    private String homePhone;

    private boolean homePhoneIsModified = false;
    private boolean homePhoneIsInitialized = false;

    private String workPhone;

    private boolean workPhoneIsModified = false;
    private boolean workPhoneIsInitialized = false;

    private String zip;

    private boolean zipIsModified = false;
    private boolean zipIsInitialized = false;

    private String state;

    private boolean stateIsModified = false;
    private boolean stateIsInitialized = false;

    private String city;

    private boolean cityIsModified = false;
    private boolean cityIsInitialized = false;

    private String address;

    private boolean addressIsModified = false;
    private boolean addressIsInitialized = false;

    private String lastName;

    private boolean lastNameIsModified = false;
    private boolean lastNameIsInitialized = false;

    private String firstName;

    private boolean firstNameIsModified = false;
    private boolean firstNameIsInitialized = false;

    private java.util.Date lastUpdate;

    private boolean lastUpdateIsModified = false;
    private boolean lastUpdateIsInitialized = false;

    private Integer clientId;

    private boolean clientIdIsModified = false;
    private boolean clientIdIsInitialized = false;

    private Integer customerId;

    private boolean customerIdIsModified = false;
    private boolean customerIdIsInitialized = false;

    private boolean _isNew = true;

    /**
     * Prefered method to create a Customer is via the createCustomer method in CustomerFetcher.
     */
    public CustomerBean() {
    }

    /**
     * Getter method for activeYn.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: customer.active_yn</li>
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
     * Getter method for monthlyBillRate.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: customer.monthly_bill_rate</li>
     * <li>column size: 17</li>
     * <li>jdbc type returned by the driver: Types.DOUBLE</li>
     * </ul>
     *
     * @return the value of monthlyBillRate
     */
    public Double getMonthlyBillRate() {
        return monthlyBillRate;
    }

    /**
     * Setter method for monthlyBillRate.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to monthlyBillRate
     */
    public void setMonthlyBillRate(Double newVal) {
        if ((newVal != null && monthlyBillRate != null && (newVal.compareTo(monthlyBillRate) == 0)) ||
            (newVal == null && monthlyBillRate == null && monthlyBillRateIsInitialized)) {
            return;
        }
        monthlyBillRate = newVal;
        monthlyBillRateIsModified = true;
        monthlyBillRateIsInitialized = true;
    }

    /**
     * Setter method for monthlyBillRate.
     * <br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to monthlyBillRate
     */
    public void setMonthlyBillRate(double newVal) {
        setMonthlyBillRate(new Double(newVal));
    }

    /**
     * Determines if the monthlyBillRate has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isMonthlyBillRateModified() {
        return monthlyBillRateIsModified;
    }

    /**
     * Determines if the monthlyBillRate has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isMonthlyBillRateInitialized() {
        return monthlyBillRateIsInitialized;
    }

    /**
     * Getter method for billType.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: customer.bill_type</li>
     * <li>column size: 25</li>
     * <li>jdbc type returned by the driver: Types.VARCHAR</li>
     * </ul>
     *
     * @return the value of billType
     */
    public String getBillType() {
        return billType;
    }

    /**
     * Setter method for billType.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to billType
     */
    public void setBillType(String newVal) {
        if ((newVal != null && billType != null && (newVal.compareTo(billType) == 0)) ||
            (newVal == null && billType == null && billTypeIsInitialized)) {
            return;
        }
        billType = newVal;
        billTypeIsModified = true;
        billTypeIsInitialized = true;
    }

    /**
     * Determines if the billType has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isBillTypeModified() {
        return billTypeIsModified;
    }

    /**
     * Determines if the billType has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isBillTypeInitialized() {
        return billTypeIsInitialized;
    }

    /**
     * Getter method for note.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: customer.note</li>
     * <li>column size: 2147483647</li>
     * <li>jdbc type returned by the driver: Types.VARCHAR</li>
     * </ul>
     *
     * @return the value of note
     */
    public String getNote() {
        return note;
    }

    /**
     * Setter method for note.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to note
     */
    public void setNote(String newVal) {
        if ((newVal != null && note != null && (newVal.compareTo(note) == 0)) ||
            (newVal == null && note == null && noteIsInitialized)) {
            return;
        }
        note = newVal;
        noteIsModified = true;
        noteIsInitialized = true;
    }

    /**
     * Determines if the note has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isNoteModified() {
        return noteIsModified;
    }

    /**
     * Determines if the note has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isNoteInitialized() {
        return noteIsInitialized;
    }

    /**
     * Getter method for clientSinceDt.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: customer.client_since_dt</li>
     * <li>column size: 13</li>
     * <li>jdbc type returned by the driver: Types.DATE</li>
     * </ul>
     *
     * @return the value of clientSinceDt
     */
    public java.util.Date getClientSinceDt() {
        return clientSinceDt;
    }

    /**
     * Setter method for clientSinceDt.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to clientSinceDt
     */
    public void setClientSinceDt(java.util.Date newVal) {
        if ((newVal != null && clientSinceDt != null && (newVal.compareTo(clientSinceDt) == 0)) ||
            (newVal == null && clientSinceDt == null && clientSinceDtIsInitialized)) {
            return;
        }
        clientSinceDt = newVal;
        clientSinceDtIsModified = true;
        clientSinceDtIsInitialized = true;
    }

    /**
     * Setter method for clientSinceDt.
     * <br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to clientSinceDt
     */
    public void setClientSinceDt(long newVal) {
        setClientSinceDt(new java.util.Date(newVal));
    }

    /**
     * Determines if the clientSinceDt has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isClientSinceDtModified() {
        return clientSinceDtIsModified;
    }

    /**
     * Determines if the clientSinceDt has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isClientSinceDtInitialized() {
        return clientSinceDtIsInitialized;
    }

    /**
     * Getter method for email.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: customer.email</li>
     * <li>column size: 100</li>
     * <li>jdbc type returned by the driver: Types.VARCHAR</li>
     * </ul>
     *
     * @return the value of email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter method for email.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to email
     */
    public void setEmail(String newVal) {
        if ((newVal != null && email != null && (newVal.compareTo(email) == 0)) ||
            (newVal == null && email == null && emailIsInitialized)) {
            return;
        }
        email = newVal;
        emailIsModified = true;
        emailIsInitialized = true;
    }

    /**
     * Determines if the email has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isEmailModified() {
        return emailIsModified;
    }

    /**
     * Determines if the email has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isEmailInitialized() {
        return emailIsInitialized;
    }

    /**
     * Getter method for fax.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: customer.fax</li>
     * <li>column size: 30</li>
     * <li>jdbc type returned by the driver: Types.VARCHAR</li>
     * </ul>
     *
     * @return the value of fax
     */
    public String getFax() {
        return fax;
    }

    /**
     * Setter method for fax.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to fax
     */
    public void setFax(String newVal) {
        if ((newVal != null && fax != null && (newVal.compareTo(fax) == 0)) ||
            (newVal == null && fax == null && faxIsInitialized)) {
            return;
        }
        fax = newVal;
        faxIsModified = true;
        faxIsInitialized = true;
    }

    /**
     * Determines if the fax has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isFaxModified() {
        return faxIsModified;
    }

    /**
     * Determines if the fax has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isFaxInitialized() {
        return faxIsInitialized;
    }

    /**
     * Getter method for homePhone.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: customer.home_phone</li>
     * <li>column size: 30</li>
     * <li>jdbc type returned by the driver: Types.VARCHAR</li>
     * </ul>
     *
     * @return the value of homePhone
     */
    public String getHomePhone() {
        return homePhone;
    }

    /**
     * Setter method for homePhone.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to homePhone
     */
    public void setHomePhone(String newVal) {
        if ((newVal != null && homePhone != null && (newVal.compareTo(homePhone) == 0)) ||
            (newVal == null && homePhone == null && homePhoneIsInitialized)) {
            return;
        }
        homePhone = newVal;
        homePhoneIsModified = true;
        homePhoneIsInitialized = true;
    }

    /**
     * Determines if the homePhone has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isHomePhoneModified() {
        return homePhoneIsModified;
    }

    /**
     * Determines if the homePhone has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isHomePhoneInitialized() {
        return homePhoneIsInitialized;
    }

    /**
     * Getter method for workPhone.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: customer.work_phone</li>
     * <li>column size: 30</li>
     * <li>jdbc type returned by the driver: Types.VARCHAR</li>
     * </ul>
     *
     * @return the value of workPhone
     */
    public String getWorkPhone() {
        return workPhone;
    }

    /**
     * Setter method for workPhone.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to workPhone
     */
    public void setWorkPhone(String newVal) {
        if ((newVal != null && workPhone != null && (newVal.compareTo(workPhone) == 0)) ||
            (newVal == null && workPhone == null && workPhoneIsInitialized)) {
            return;
        }
        workPhone = newVal;
        workPhoneIsModified = true;
        workPhoneIsInitialized = true;
    }

    /**
     * Determines if the workPhone has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isWorkPhoneModified() {
        return workPhoneIsModified;
    }

    /**
     * Determines if the workPhone has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isWorkPhoneInitialized() {
        return workPhoneIsInitialized;
    }

    /**
     * Getter method for zip.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: customer.zip</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.VARCHAR</li>
     * </ul>
     *
     * @return the value of zip
     */
    public String getZip() {
        return zip;
    }

    /**
     * Setter method for zip.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to zip
     */
    public void setZip(String newVal) {
        if ((newVal != null && zip != null && (newVal.compareTo(zip) == 0)) ||
            (newVal == null && zip == null && zipIsInitialized)) {
            return;
        }
        zip = newVal;
        zipIsModified = true;
        zipIsInitialized = true;
    }

    /**
     * Determines if the zip has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isZipModified() {
        return zipIsModified;
    }

    /**
     * Determines if the zip has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isZipInitialized() {
        return zipIsInitialized;
    }

    /**
     * Getter method for state.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: customer.state</li>
     * <li>column size: 2</li>
     * <li>jdbc type returned by the driver: Types.CHAR</li>
     * </ul>
     *
     * @return the value of state
     */
    public String getState() {
        return state;
    }

    /**
     * Setter method for state.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to state
     */
    public void setState(String newVal) {
        if ((newVal != null && state != null && (newVal.compareTo(state) == 0)) ||
            (newVal == null && state == null && stateIsInitialized)) {
            return;
        }
        state = newVal;
        stateIsModified = true;
        stateIsInitialized = true;
    }

    /**
     * Determines if the state has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isStateModified() {
        return stateIsModified;
    }

    /**
     * Determines if the state has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isStateInitialized() {
        return stateIsInitialized;
    }

    /**
     * Getter method for city.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: customer.city</li>
     * <li>column size: 50</li>
     * <li>jdbc type returned by the driver: Types.VARCHAR</li>
     * </ul>
     *
     * @return the value of city
     */
    public String getCity() {
        return city;
    }

    /**
     * Setter method for city.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to city
     */
    public void setCity(String newVal) {
        if ((newVal != null && city != null && (newVal.compareTo(city) == 0)) ||
            (newVal == null && city == null && cityIsInitialized)) {
            return;
        }
        city = newVal;
        cityIsModified = true;
        cityIsInitialized = true;
    }

    /**
     * Determines if the city has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isCityModified() {
        return cityIsModified;
    }

    /**
     * Determines if the city has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isCityInitialized() {
        return cityIsInitialized;
    }

    /**
     * Getter method for address.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: customer.address</li>
     * <li>column size: 2147483647</li>
     * <li>jdbc type returned by the driver: Types.VARCHAR</li>
     * </ul>
     *
     * @return the value of address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter method for address.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to address
     */
    public void setAddress(String newVal) {
        if ((newVal != null && address != null && (newVal.compareTo(address) == 0)) ||
            (newVal == null && address == null && addressIsInitialized)) {
            return;
        }
        address = newVal;
        addressIsModified = true;
        addressIsInitialized = true;
    }

    /**
     * Determines if the address has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isAddressModified() {
        return addressIsModified;
    }

    /**
     * Determines if the address has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isAddressInitialized() {
        return addressIsInitialized;
    }

    /**
     * Getter method for lastName.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: customer.last_name</li>
     * <li>column size: 50</li>
     * <li>jdbc type returned by the driver: Types.VARCHAR</li>
     * </ul>
     *
     * @return the value of lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter method for lastName.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to lastName
     */
    public void setLastName(String newVal) {
        if ((newVal != null && lastName != null && (newVal.compareTo(lastName) == 0)) ||
            (newVal == null && lastName == null && lastNameIsInitialized)) {
            return;
        }
        lastName = newVal;
        lastNameIsModified = true;
        lastNameIsInitialized = true;
    }

    /**
     * Determines if the lastName has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isLastNameModified() {
        return lastNameIsModified;
    }

    /**
     * Determines if the lastName has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isLastNameInitialized() {
        return lastNameIsInitialized;
    }

    /**
     * Getter method for firstName.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: customer.first_name</li>
     * <li>column size: 50</li>
     * <li>jdbc type returned by the driver: Types.VARCHAR</li>
     * </ul>
     *
     * @return the value of firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter method for firstName.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to firstName
     */
    public void setFirstName(String newVal) {
        if ((newVal != null && firstName != null && (newVal.compareTo(firstName) == 0)) ||
            (newVal == null && firstName == null && firstNameIsInitialized)) {
            return;
        }
        firstName = newVal;
        firstNameIsModified = true;
        firstNameIsInitialized = true;
    }

    /**
     * Determines if the firstName has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isFirstNameModified() {
        return firstNameIsModified;
    }

    /**
     * Determines if the firstName has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isFirstNameInitialized() {
        return firstNameIsInitialized;
    }

    /**
     * Getter method for lastUpdate.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: customer.last_update</li>
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
     * Getter method for clientId.
     * <br>
     * PRIMARY KEY.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: customer.client_id</li>
     * <li> foreign key: client_pref.client_id</li>
     * <li> imported key: customer_account_register.client_id</li>
     * <li> imported key: customer_bill_rate.client_id</li>
     * <li> imported key: expense_invoice_item.client_id</li>
     * <li> imported key: expense_register.client_id</li>
     * <li> imported key: followup.client_id</li>
     * <li> imported key: invoice.client_id</li>
     * <li> imported key: labor_invoice_item.client_id</li>
     * <li> imported key: labor_register.client_id</li>
     * <li> imported key: payment_log.client_id</li>
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
     * Getter method for customerId.
     * <br>
     * PRIMARY KEY.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: customer.customer_id</li>
     * <li> imported key: customer_account_register.customer_id</li>
     * <li> imported key: customer_bill_rate.customer_id</li>
     * <li> imported key: expense_invoice_item.customer_id</li>
     * <li> imported key: expense_register.customer_id</li>
     * <li> imported key: followup.customer_id</li>
     * <li> imported key: invoice.customer_id</li>
     * <li> imported key: labor_invoice_item.customer_id</li>
     * <li> imported key: labor_register.customer_id</li>
     * <li> imported key: payment_log.customer_id</li>
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
        return activeYnIsModified         || monthlyBillRateIsModified          || billTypeIsModified          || noteIsModified          || clientSinceDtIsModified          || emailIsModified          || faxIsModified          || homePhoneIsModified          || workPhoneIsModified          || zipIsModified          || stateIsModified          || cityIsModified          || addressIsModified          || lastNameIsModified          || firstNameIsModified          || lastUpdateIsModified          || clientIdIsModified          || customerIdIsModified  ;
    }

    /**
     * Resets the object modification status to 'not modified'.
     */
    public void resetIsModified() {
        activeYnIsModified = false;
        monthlyBillRateIsModified = false;
        billTypeIsModified = false;
        noteIsModified = false;
        clientSinceDtIsModified = false;
        emailIsModified = false;
        faxIsModified = false;
        homePhoneIsModified = false;
        workPhoneIsModified = false;
        zipIsModified = false;
        stateIsModified = false;
        cityIsModified = false;
        addressIsModified = false;
        lastNameIsModified = false;
        firstNameIsModified = false;
        lastUpdateIsModified = false;
        clientIdIsModified = false;
        customerIdIsModified = false;
    }

    /**
     * Copies the passed bean into the current bean.
     *
     * @param bean the bean to copy into the current bean
     */
    public void copy(CustomerBean bean) {
        setActiveYn(bean.getActiveYn());
        setMonthlyBillRate(bean.getMonthlyBillRate());
        setBillType(bean.getBillType());
        setNote(bean.getNote());
        setClientSinceDt(bean.getClientSinceDt());
        setEmail(bean.getEmail());
        setFax(bean.getFax());
        setHomePhone(bean.getHomePhone());
        setWorkPhone(bean.getWorkPhone());
        setZip(bean.getZip());
        setState(bean.getState());
        setCity(bean.getCity());
        setAddress(bean.getAddress());
        setLastName(bean.getLastName());
        setFirstName(bean.getFirstName());
        setLastUpdate(bean.getLastUpdate());
        setClientId(bean.getClientId());
        setCustomerId(bean.getCustomerId());
    }

    /**
     * Return a dictionnary of the object.
     *
     * @return a map of column name keys to column value string representations
     */
    public  java.util.Map<java.lang.String, java.lang.String> getDictionnary() {
        Map<java.lang.String, java.lang.String> dictionnary = new HashMap<java.lang.String, java.lang.String>();
        dictionnary.put("active_yn", getActiveYn() == null ? "" : String.valueOf(getActiveYn()));
        dictionnary.put("monthly_bill_rate", getMonthlyBillRate() == null ? "" : String.valueOf(getMonthlyBillRate()));
        dictionnary.put("bill_type", getBillType() == null ? "" : String.valueOf(getBillType()));
        dictionnary.put("note", getNote() == null ? "" : String.valueOf(getNote()));
        dictionnary.put("client_since_dt", getClientSinceDt() == null ? "" : String.valueOf(getClientSinceDt()));
        dictionnary.put("email", getEmail() == null ? "" : String.valueOf(getEmail()));
        dictionnary.put("fax", getFax() == null ? "" : String.valueOf(getFax()));
        dictionnary.put("home_phone", getHomePhone() == null ? "" : String.valueOf(getHomePhone()));
        dictionnary.put("work_phone", getWorkPhone() == null ? "" : String.valueOf(getWorkPhone()));
        dictionnary.put("zip", getZip() == null ? "" : String.valueOf(getZip()));
        dictionnary.put("state", getState() == null ? "" : String.valueOf(getState()));
        dictionnary.put("city", getCity() == null ? "" : String.valueOf(getCity()));
        dictionnary.put("address", getAddress() == null ? "" : String.valueOf(getAddress()));
        dictionnary.put("last_name", getLastName() == null ? "" : String.valueOf(getLastName()));
        dictionnary.put("first_name", getFirstName() == null ? "" : String.valueOf(getFirstName()));
        dictionnary.put("last_update", getLastUpdate() == null ? "" : String.valueOf(getLastUpdate()));
        dictionnary.put("client_id", getClientId() == null ? "" : String.valueOf(getClientId()));
        dictionnary.put("customer_id", getCustomerId() == null ? "" : String.valueOf(getCustomerId()));
        return dictionnary;
    }

    /**
     * Return a dictionnary of the primary key columns.
     * 
     * @return a map of column name keys to column value string representations
     */
    public java.util.Map<java.lang.String, java.lang.String> getPkDictionnary() {
        Map<java.lang.String, java.lang.String> dictionnary = new HashMap<java.lang.String, java.lang.String>();
        dictionnary.put("customer_id", getCustomerId() == null ? "" : String.valueOf(getCustomerId()));
        dictionnary.put("client_id", getClientId() == null ? "" : String.valueOf(getClientId()));
        return dictionnary;
    }

    /**
     * return a the value string representation of the given field
     */
    public String getValue(String column) {
        if (null == column || "".equals(column)) {
            return "";
        } else if ("active_yn".equalsIgnoreCase(column) || "activeYn".equalsIgnoreCase(column)) {
            return getActiveYn() == null ? "" : String.valueOf(getActiveYn());
        } else if ("monthly_bill_rate".equalsIgnoreCase(column) || "monthlyBillRate".equalsIgnoreCase(column)) {
            return getMonthlyBillRate() == null ? "" : String.valueOf(getMonthlyBillRate());
        } else if ("bill_type".equalsIgnoreCase(column) || "billType".equalsIgnoreCase(column)) {
            return getBillType() == null ? "" : String.valueOf(getBillType());
        } else if ("note".equalsIgnoreCase(column) || "note".equalsIgnoreCase(column)) {
            return getNote() == null ? "" : String.valueOf(getNote());
        } else if ("client_since_dt".equalsIgnoreCase(column) || "clientSinceDt".equalsIgnoreCase(column)) {
            return getClientSinceDt() == null ? "" : String.valueOf(getClientSinceDt());
        } else if ("email".equalsIgnoreCase(column) || "email".equalsIgnoreCase(column)) {
            return getEmail() == null ? "" : String.valueOf(getEmail());
        } else if ("fax".equalsIgnoreCase(column) || "fax".equalsIgnoreCase(column)) {
            return getFax() == null ? "" : String.valueOf(getFax());
        } else if ("home_phone".equalsIgnoreCase(column) || "homePhone".equalsIgnoreCase(column)) {
            return getHomePhone() == null ? "" : String.valueOf(getHomePhone());
        } else if ("work_phone".equalsIgnoreCase(column) || "workPhone".equalsIgnoreCase(column)) {
            return getWorkPhone() == null ? "" : String.valueOf(getWorkPhone());
        } else if ("zip".equalsIgnoreCase(column) || "zip".equalsIgnoreCase(column)) {
            return getZip() == null ? "" : String.valueOf(getZip());
        } else if ("state".equalsIgnoreCase(column) || "state".equalsIgnoreCase(column)) {
            return getState() == null ? "" : String.valueOf(getState());
        } else if ("city".equalsIgnoreCase(column) || "city".equalsIgnoreCase(column)) {
            return getCity() == null ? "" : String.valueOf(getCity());
        } else if ("address".equalsIgnoreCase(column) || "address".equalsIgnoreCase(column)) {
            return getAddress() == null ? "" : String.valueOf(getAddress());
        } else if ("last_name".equalsIgnoreCase(column) || "lastName".equalsIgnoreCase(column)) {
            return getLastName() == null ? "" : String.valueOf(getLastName());
        } else if ("first_name".equalsIgnoreCase(column) || "firstName".equalsIgnoreCase(column)) {
            return getFirstName() == null ? "" : String.valueOf(getFirstName());
        } else if ("last_update".equalsIgnoreCase(column) || "lastUpdate".equalsIgnoreCase(column)) {
            return getLastUpdate() == null ? "" : String.valueOf(getLastUpdate());
        } else if ("client_id".equalsIgnoreCase(column) || "clientId".equalsIgnoreCase(column)) {
            return getClientId() == null ? "" : String.valueOf(getClientId());
        } else if ("customer_id".equalsIgnoreCase(column) || "customerId".equalsIgnoreCase(column)) {
            return getCustomerId() == null ? "" : String.valueOf(getCustomerId());
        }
        return "";
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(Object object) {
        if (!(object instanceof CustomerBean)) {
            return false;
        }

        CustomerBean obj = (CustomerBean) object;
        if (this.activeYn != obj.activeYn) {
                if (this.activeYn != null && obj.activeYn != null && (obj.activeYn.compareTo(activeYn) != 0)) {
                    return false;
                }
        }
        if (this.monthlyBillRate != obj.monthlyBillRate) {
                if (this.monthlyBillRate != null && obj.monthlyBillRate != null && (obj.monthlyBillRate.compareTo(monthlyBillRate) != 0)) {
                    return false;
                }
        }
        if (this.billType != obj.billType) {
                if (this.billType != null && obj.billType != null && (obj.billType.compareTo(billType) != 0)) {
                    return false;
                }
        }
        if (this.note != obj.note) {
                if (this.note != null && obj.note != null && (obj.note.compareTo(note) != 0)) {
                    return false;
                }
        }
        if (this.clientSinceDt != obj.clientSinceDt) {
                if (this.clientSinceDt != null && obj.clientSinceDt != null && (obj.clientSinceDt.compareTo(clientSinceDt) != 0)) {
                    return false;
                }
        }
        if (this.email != obj.email) {
                if (this.email != null && obj.email != null && (obj.email.compareTo(email) != 0)) {
                    return false;
                }
        }
        if (this.fax != obj.fax) {
                if (this.fax != null && obj.fax != null && (obj.fax.compareTo(fax) != 0)) {
                    return false;
                }
        }
        if (this.homePhone != obj.homePhone) {
                if (this.homePhone != null && obj.homePhone != null && (obj.homePhone.compareTo(homePhone) != 0)) {
                    return false;
                }
        }
        if (this.workPhone != obj.workPhone) {
                if (this.workPhone != null && obj.workPhone != null && (obj.workPhone.compareTo(workPhone) != 0)) {
                    return false;
                }
        }
        if (this.zip != obj.zip) {
                if (this.zip != null && obj.zip != null && (obj.zip.compareTo(zip) != 0)) {
                    return false;
                }
        }
        if (this.state != obj.state) {
                if (this.state != null && obj.state != null && (obj.state.compareTo(state) != 0)) {
                    return false;
                }
        }
        if (this.city != obj.city) {
                if (this.city != null && obj.city != null && (obj.city.compareTo(city) != 0)) {
                    return false;
                }
        }
        if (this.address != obj.address) {
                if (this.address != null && obj.address != null && (obj.address.compareTo(address) != 0)) {
                    return false;
                }
        }
        if (this.lastName != obj.lastName) {
                if (this.lastName != null && obj.lastName != null && (obj.lastName.compareTo(lastName) != 0)) {
                    return false;
                }
        }
        if (this.firstName != obj.firstName) {
                if (this.firstName != null && obj.firstName != null && (obj.firstName.compareTo(firstName) != 0)) {
                    return false;
                }
        }
        if (this.lastUpdate != obj.lastUpdate) {
                if (this.lastUpdate != null && obj.lastUpdate != null && (obj.lastUpdate.compareTo(lastUpdate) != 0)) {
                    return false;
                }
        }
        if (this.clientId != obj.clientId) {
                if (this.clientId != null && obj.clientId != null && (obj.clientId.compareTo(clientId) != 0)) {
                    return false;
                }
        }
        if (this.customerId != obj.customerId) {
                if (this.customerId != null && obj.customerId != null && (obj.customerId.compareTo(customerId) != 0)) {
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
        total = total * constant + this.activeYn.hashCode();
        total = total * constant + this.monthlyBillRate.hashCode();
        total = total * constant + this.billType.hashCode();
        total = total * constant + this.note.hashCode();
        total = total * constant + this.clientSinceDt.hashCode();
        total = total * constant + this.email.hashCode();
        total = total * constant + this.fax.hashCode();
        total = total * constant + this.homePhone.hashCode();
        total = total * constant + this.workPhone.hashCode();
        total = total * constant + this.zip.hashCode();
        total = total * constant + this.state.hashCode();
        total = total * constant + this.city.hashCode();
        total = total * constant + this.address.hashCode();
        total = total * constant + this.lastName.hashCode();
        total = total * constant + this.firstName.hashCode();
        total = total * constant + this.lastUpdate.hashCode();
        total = total * constant + this.clientId.hashCode();
        total = total * constant + this.customerId.hashCode();
        return total;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuffer sb = new StringBuffer(60);
            sb.append("active_yn = ");
            sb.append(this.getActiveYn());
            sb.append("\n");
            sb.append("monthly_bill_rate = ");
            sb.append(this.getMonthlyBillRate());
            sb.append("\n");
            sb.append("bill_type = ");
            sb.append(this.getBillType());
            sb.append("\n");
            sb.append("note = ");
            sb.append(this.getNote());
            sb.append("\n");
            sb.append("client_since_dt = ");
            sb.append(this.getClientSinceDt());
            sb.append("\n");
            sb.append("email = ");
            sb.append(this.getEmail());
            sb.append("\n");
            sb.append("fax = ");
            sb.append(this.getFax());
            sb.append("\n");
            sb.append("home_phone = ");
            sb.append(this.getHomePhone());
            sb.append("\n");
            sb.append("work_phone = ");
            sb.append(this.getWorkPhone());
            sb.append("\n");
            sb.append("zip = ");
            sb.append(this.getZip());
            sb.append("\n");
            sb.append("state = ");
            sb.append(this.getState());
            sb.append("\n");
            sb.append("city = ");
            sb.append(this.getCity());
            sb.append("\n");
            sb.append("address = ");
            sb.append(this.getAddress());
            sb.append("\n");
            sb.append("last_name = ");
            sb.append(this.getLastName());
            sb.append("\n");
            sb.append("first_name = ");
            sb.append(this.getFirstName());
            sb.append("\n");
            sb.append("last_update = ");
            sb.append(this.getLastUpdate());
            sb.append("\n");
            sb.append("client_id = ");
            sb.append(this.getClientId());
            sb.append("\n");
            sb.append("customer_id = ");
            sb.append(this.getCustomerId());
            sb.append("\n");
       return sb.toString();
    }


    public int compareTo(Object object) {
        CustomerBean obj = (CustomerBean) object;
        int comparison = 0;
        if (0 == comparison) {
            comparison = this.activeYn.compareTo(obj.activeYn);
        }
        if (0 == comparison) {
            comparison = this.monthlyBillRate.compareTo(obj.monthlyBillRate);
        }
        if (0 == comparison) {
            comparison = this.billType.compareTo(obj.billType);
        }
        if (0 == comparison) {
            comparison = this.note.compareTo(obj.note);
        }
        if (0 == comparison) {
            comparison = this.clientSinceDt.compareTo(obj.clientSinceDt);
        }
        if (0 == comparison) {
            comparison = this.email.compareTo(obj.email);
        }
        if (0 == comparison) {
            comparison = this.fax.compareTo(obj.fax);
        }
        if (0 == comparison) {
            comparison = this.homePhone.compareTo(obj.homePhone);
        }
        if (0 == comparison) {
            comparison = this.workPhone.compareTo(obj.workPhone);
        }
        if (0 == comparison) {
            comparison = this.zip.compareTo(obj.zip);
        }
        if (0 == comparison) {
            comparison = this.state.compareTo(obj.state);
        }
        if (0 == comparison) {
            comparison = this.city.compareTo(obj.city);
        }
        if (0 == comparison) {
            comparison = this.address.compareTo(obj.address);
        }
        if (0 == comparison) {
            comparison = this.lastName.compareTo(obj.lastName);
        }
        if (0 == comparison) {
            comparison = this.firstName.compareTo(obj.firstName);
        }
        if (0 == comparison) {
            comparison = this.lastUpdate.compareTo(obj.lastUpdate);
        }
        if (0 == comparison) {
            comparison = this.clientId.compareTo(obj.clientId);
        }
        if (0 == comparison) {
            comparison = this.customerId.compareTo(obj.customerId);
        }
        return comparison;
   }
}
