package model;

/**Customers Class provides a model for a Customers object*/
public class Customers {
    private int customerId;
    private String name;
    private String address;
    private String zipCode;
    private String phoneNumber;
    private Countries country;
    private FirstLevelDivisions division;

    /**Constructor for a Customers Object
     *
     * @param customerId Customer ID (int) for Customers object
     * @param name Name (String) for Customers object
     * @param address Address (String) for Customers object
     * @param zipCode ZipCode (String) for Customers object
     * @param phoneNumber Phone Number (String) for Customers object
     * @param country Country (Countries object) for Customers object
     * @param division First Level Division (FirstLevelDivisions object) for a Customers object
     *
     * */
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

    /**Getter for Customer ID
     * @return Return Customer ID*/
    public int getCustomerId() {
        return customerId;
    }

    /**Setter for Customer ID
     * @param customerId Sets Customer ID*/
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**Getter for Name
     * @return Return Name*/
    public String getName() {
        return name;
    }

    /**Setter for Name
     * @param name Sets Name*/
    public void setName(String name) { this.name = name; }

    /**Getter for Address
     * @return Return Address*/
    public String getAddress() {return address; }

    /**Setter for Address
     * @param address Sets Address*/
    public void setAddress(String address) {this.address = address; }

    /**Getter for ZipCode
     * @return Return ZipCode*/
    public String getZipCode() { return zipCode; }

    /**Setter for ZipCode
     * @param zipCode Sets Zip Code*/
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }

    /**Getter for Phone Number
     * @return Return Phone Number*/
    public String getPhoneNumber() { return phoneNumber; }

    /**Setter for Phone Number
     * @param phoneNumber Sets Phone Number*/
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    /**Getter for Country
     * @return Return Country*/
    public Countries getCountry() { return country; }

    /**Setter for Country
     * @param country Sets Country*/
    public void setCountry(Countries country) {this.country = country; }

    /**Getter for First Level Division
     * @return Return Division*/
    public FirstLevelDivisions getDivision() { return division; }

    /**Setter for First Level Division
     * @param division Sets Division*/
    public void setDivision(FirstLevelDivisions division) { this.division = division; }

    /**Overrides the String Class to provide proper display of a Customers object in the UI.*/
    @Override
    public String toString(){ return(customerId + " - " + name); }

}
