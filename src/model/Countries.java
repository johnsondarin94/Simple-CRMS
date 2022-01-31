package model;

/**Countries Class provides a model for a Countries object*/
public class Countries {
    private int countryID;
    private String countryName;

    /**Countries Constructor
     *
     * @param countryID Country ID (int) for a Countries object
     * @param countryName Country Name (String) for a Countries object
     *
     * */
    public Countries(int countryID, String countryName){
        this.countryID = countryID;
        this.countryName = countryName;
    }

    /**Getter for Country ID
     * @return Return Country ID*/
    public int getCountryID() {
        return countryID;
    }

    /**Setter for Country ID
     * @param countryID Sets Country ID*/
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**Getter for Country Name
     * @return Return Country Name*/
    public String getCountryName() {
        return countryName;
    }

    /**Setter for Country Name
     * @param countryName Sets Country Name*/
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**Overrides String Class to properly display a Countries object in the UI.*/
    @Override
    public String toString(){
        return(countryID +" - "+ countryName);
    }

}
