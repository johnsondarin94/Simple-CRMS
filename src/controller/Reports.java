package controller;

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

import java.io.IOException;
import java.net.URL;
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
        Scene scene = new Scene(root, 750, 550);
        stage.setTitle("Customers");
        stage.setScene(scene);
        stage.show();
    }

    public void onGenerate(ActionEvent actionEvent) {
        if(reportCombo.getSelectionModel().getSelectedItem() == r1){
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
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reportCombo.getItems().addAll(r1, r2, r3);
    }
}
