package model;

/**Contacts class provides a model for a Contacts Object.*/
public class Contacts {
    private int contactID;
    private String contactName;
    private String contactEmail;

    /**Constructor for a Contacts object
     *
     * @param contactID ContactID (int) for Contacts object.
     * @param contactName Contact Name (String) for Contacts object.
     * @param contactEmail  Contact Email (String) for Contacts object.
     *
     * */
    public Contacts (int contactID, String contactName, String contactEmail){
        this.contactID = contactID;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /**Getter for Contact ID
     * @return Return Contact ID*/
    public int getContactID() {
        return contactID;
    }

    /**Setter for Contact ID
     * @param contactID Sets Contact ID*/
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**Getter for Contact Name
     * @return Return Contact Name*/
    public String getContactName() {
        return contactName;
    }

    /**Setter for Contact Name
     * @param contactName Sets Contact Name*/
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**Getter for Contact Email
     * @return Return Contact Email*/
    public String getContactEmail() {
        return contactEmail;
    }

    /**Setter for Contact Email
     * @param contactEmail Sets Contact Email*/
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    /**Override String Class to properly display Contacts Objects in the UI
     * @return Return Overridden String*/
    @Override
    public String toString(){
        return(contactID +" - "+ contactName + " - "+ contactEmail);
    }

}
