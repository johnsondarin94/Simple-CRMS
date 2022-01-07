package model;

public class Countries {
    private int countryID;
    private String countryName;

    public Countries(int countryID, String countryName){
        this.countryID = countryID;
        this.countryName = countryName;
    }

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public String toString(){
        return(countryID + " - " + countryName);
    }

}
