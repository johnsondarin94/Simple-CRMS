package model;

public class FirstLevelDivisions {
    private int divisionID;
    private String divisionName;

    public int getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public FirstLevelDivisions(int divisionID, String divisionName){
        this.divisionID = divisionID;
        this.divisionName = divisionName;
    }

    @Override
    public String toString(){
        return(divisionID + " - " + divisionName);
    }
}
