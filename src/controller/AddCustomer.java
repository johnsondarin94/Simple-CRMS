package controller;

import Database.DatabaseCustomers;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Users;

import java.io.IOException;

public class AddCustomer {
    public Button cancelButton;
    public TextField addCustomerPhone;
    public TextField addCustomerName;
    public TextField addCustomerAddress;
    public TextField addCustomerZip;
    public TextField addCustomerId;
    public ComboBox addCustomerCountry;
    public Button addButton;

    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 750, 550);
        stage.setTitle("Customers");
        stage.setScene(scene);
        stage.show();
    }

    public void onAdd(ActionEvent actionEvent) {
        String customerName = addCustomerName.getText();
        String address = addCustomerAddress.getText();
        String zipCode = addCustomerZip.getText();
        String phone = addCustomerPhone.getText();
        DatabaseCustomers.addCustomer(customerName, address, zipCode, phone, "test", "test" );
    }
}
