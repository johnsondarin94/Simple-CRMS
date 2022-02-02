package Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Countries;
import model.FirstLevelDivisions;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**DatabaseLocations handles queries related to Country data as well as First Level Divisions Data.*/
public class DatabaseLocations {

    /**Queries a list of all countries in database and adds them to an observable list
     * @return ObservableList Countries objects*/
    public static ObservableList<Countries> getAllCountries(){
        ObservableList<Countries> countries = FXCollections.observableArrayList();
        try{
            String sql = "SELECT Country_ID, Country FROM countries";

            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int countryID = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");

                Countries c = new Countries(countryID, countryName);

                countries.add(c);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return countries;
    }

    /**Queries a specified list of FirstLevelDivisions based on a countryID and adds them to a list
     * @param countryID Desired countries first level divisions
     * @return ObservableList FirstLevelDivisions objects*/
    public static ObservableList<FirstLevelDivisions> getSelectedFirstLevelDivisions(int countryID) {
        ObservableList<FirstLevelDivisions> firstleveldivisions = FXCollections.observableArrayList();
        try{
            String sql = "SELECT Division_ID, Division FROM first_level_divisions WHERE Country_ID='"+countryID+"'";

            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int divisionID = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");

                FirstLevelDivisions f = new FirstLevelDivisions(divisionID, divisionName);

                firstleveldivisions.add(f);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return firstleveldivisions;
    }
}
