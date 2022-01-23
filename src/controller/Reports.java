package controller;

import Database.DatabaseAppointments;
import Database.DatabaseReports;
import javafx.collections.FXCollections;
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

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.ResourceBundle;

public class Reports implements Initializable {
    public ComboBox reportCombo;
    public Button generateButton;
    public TextArea reportField;

    final String r1 = "Report - Total Apts by Type and Month";
    final String r2 = "Contact Schedule";
    final String r3 = "Report 3";

    public void onReturn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1100, 550);
        stage.setTitle("Customers");
        stage.setScene(scene);
        stage.show();
    }

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

    public void reportByContactSchedule(){
        ObservableList<Contacts> contacts = DatabaseReports.getContacts();

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

    public void reportByTotalHours(){
        ObservableList<Appointments> appointments = DatabaseAppointments.getAllAppointments();
        int totalHours = 0;

        for(Appointments a : appointments){
            totalHours += a.getEndDateTime().getHour() - a.getStartDateTime().getHour();

        }
        reportField.appendText("Total hours for upcoming appointments: " +totalHours);
    }

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
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reportCombo.getItems().addAll(r1, r2, r3);
    }
}
