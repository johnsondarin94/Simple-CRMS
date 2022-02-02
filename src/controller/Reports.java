package controller;

import Database.DatabaseAppointments;
import Database.DatabaseReports;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.Appointments;
import model.Contacts;
import util.ErrorHandling;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**Controller Class for Reports*/
public class Reports implements Initializable {
    public ComboBox reportCombo;
    public Button generateButton;
    public TextArea reportField;

    final String r1 = "Total Apts by Type and Month";
    final String r2 = "Contact Schedule";
    final String r3 = "Grand Total Hours";

    /**Returns User to CustomerController
     * @param actionEvent Action Event for Return Button*/
    public void onReturn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1100, 550);
        stage.setTitle("Customers");
        stage.setScene(scene);
        stage.show();
    }

    /**Populates text field with reports by type and month generated by 2 database calls*/
    public void reportByTypeMonth(){
        ObservableList<String> typeList = DatabaseReports.getReportByType();
        ObservableList<String> monthList = DatabaseReports.getReportByMonth();

        reportField.appendText("Report: Total Customer Appointments by Type: \n");
        for(String s : typeList){
            reportField.appendText(s + "\n");
        }
        reportField.appendText("Report: Total Customer Appointments by Month: \n");
        for(String s : monthList){
            reportField.appendText(s + "\n");
        }
    }

    /**Populates text field with a list of all contacts paired with their respective upcoming appointments.*/
    public void reportByContactSchedule(){
        ObservableList<Contacts> contacts = DatabaseAppointments.getAllContacts();

        for(Contacts c : contacts){
            int id = c.getContactID();
            reportField.appendText(c.getContactName() + ": \n\n");
            ObservableList<Appointments> apts = DatabaseReports.getContactAppointments(id);
            for(Appointments a : apts) {
                reportField.appendText(a + "\n");

                if(apts.isEmpty()){
                    reportField.appendText("Contact has an open schedule.");
                }
            }
            reportField.appendText("\n");
        }
    }

    public interface CalculateTotalHours{
        int calculate(int hour1, int hour2);
    }

    /**LAMBDA 2 IS FOUND HERE AS WELL AS REPORT 3. Method populates text field with a grand total of hours needed
     * across all appointments. JUSTIFICATION: COULD BE USED TO PROVIDE ADDITIONAL FUNCTIONALITY TO THE APPLICATION
     * TO PROVIDE FAIR SCHEDULING FOR EACH CONTACT.*/
    public void reportByTotalHours(){
        ObservableList<Appointments> appointments = DatabaseAppointments.getAllAppointments();
        int totalHours = 0;

        for(Appointments a : appointments){
            CalculateTotalHours calculateTotalHours = (hour1, hour2) -> hour1 - hour2;
            totalHours += calculateTotalHours.calculate(a.getEndDateTime().getHour(), a.getStartDateTime().getHour());

        }
        reportField.appendText("Total hours for upcoming appointments: " +totalHours);
    }

    /**Generates a report based on which report is populated in the combo box
     * @param actionEvent Action Event for the Generate Button*/
    public void onGenerate(ActionEvent actionEvent) {
        if(reportCombo.getSelectionModel().getSelectedItem() == r1){
            reportField.setText(null);
            reportByTypeMonth();
        }
        if(reportCombo.getSelectionModel().getSelectedItem() == r2){
            reportField.setText(null);
            reportByContactSchedule();
        }
        if(reportCombo.getSelectionModel().getSelectedItem() == r3){
            reportField.setText(null);
            reportByTotalHours();
        }
        if(reportCombo.getSelectionModel().getSelectedItem() == null){
            ErrorHandling.displayError("Please select an option from the combo box above.");
        }
    }

    /**Initialize method populates the combo box with each report option for the user to select.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reportCombo.getItems().addAll(r1, r2, r3);
    }
}
