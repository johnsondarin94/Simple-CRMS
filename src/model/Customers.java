
package model;

import java.sql.Date;
import java.time.LocalDateTime;

public class Customers {
    private int customerId;
    private String name;
    private String address;
    private String zipCode;
    private String phoneNumber;
    private Countries country;
    private FirstLevelDivisions division;

    public Customers (int customerId, String name, String address, String zipCode, String phoneNumber,
                      Countries country, FirstLevelDivisions division){

        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
        this.country = country;
        this.division = division;

    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Countries getCountry() {
        return country;
    }

    public void setCountry(Countries country) {
        this.country = country;
    }

    public FirstLevelDivisions getDivision() {
        return division;
    }

    public void setDivision(FirstLevelDivisions division) {
        this.division = division;
    }

    @Override
    public String toString(){
        return(customerId + " - " + name);
    }

}
