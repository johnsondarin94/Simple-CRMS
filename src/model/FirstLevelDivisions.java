package model;

/**FirstLevelDivisions Class provides a model for a FirstLevelDivisions object*/
public class FirstLevelDivisions {
    private int divisionID;
    private String divisionName;

    /**Constructor for a FirstLevelDivisions object
     *
     * @param divisionID DivisionID (int) for a FirstLevelDivisions object
     * @param divisionName Division Name (String) for a FirstLevelDivisions object
     *
     * */
    public FirstLevelDivisions(int divisionID, String divisionName){
        this.divisionID = divisionID;
        this.divisionName = divisionName;

    }

    /**Getter for Division ID
     * @return Return Division ID*/
    public int getDivisionID() {
        return divisionID;
    }

    /**Setter for Division ID
     * @param divisionID Sets Division ID*/
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**Getter for Division Name
     * @return Return Division Name*/
    public String getDivisionName() {
        return divisionName;
    }

    /**Setter for Division Name
     * @param divisionName Sets Division Name*/
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**Overrides String Class to provide proper display of a FirstLevelDivisions object in the UI.*/
    @Override
    public String toString(){
        return(divisionID + " - " +divisionName);
    }
}
