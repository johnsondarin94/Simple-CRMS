package Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Countries;
import model.FirstLevelDivisions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseLocations {
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

    public static ObservableList<FirstLevelDivisions> getAllFirstLevelDivisions() {
        ObservableList<FirstLevelDivisions> firstleveldivisions = FXCollections.observableArrayList();
        try{
            String sql = "SELECT Division_ID, Division FROM first_level_divisions";

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
