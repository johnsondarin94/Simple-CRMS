package controller;

import Database.DatabaseCustomers;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Customers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateCustomer implements Initializable{
    public TextField updateCustomerName;
    public TextField updateCustomerPhone;
    public TextField updateCustomerAddress;
    public TextField updateCustomerZip;
    public TextField updateCustomerId;
    public ComboBox updateCustomerState;
    public ComboBox updateCustomerCountry;

    public Button cancelButton;
    public Button updateButton;

    private Customers customerToModify = null;


    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 750, 550);
        stage.setTitle("Customers");
        stage.setScene(scene);
        stage.show();
    }

    public void onUpdate(ActionEvent actionEvent) {
        int customerId = Integer.parseInt(updateCustomerId.getText());
        String customerName = updateCustomerName.getText();
        String customerPhone = updateCustomerPhone.getText();
        String customerAddress = updateCustomerAddress.getText();
        String customerZip = updateCustomerZip.getText();

        DatabaseCustomers.updateCustomer(customerId, customerName, customerPhone, customerAddress, customerZip);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        customerToModify = CustomerController.getCustomerHandOff();

        updateCustomerId.setText(String.valueOf(customerToModify.getCustomerId()));
        updateCustomerName.setText(String.valueOf(customerToModify.getName()));
        updateCustomerAddress.setText(String.valueOf(customerToModify.getAddress()));
        updateCustomerPhone.setText(String.valueOf(customerToModify.getPhoneNumber()));
        updateCustomerZip.setText(String.valueOf(customerToModify.getZipCode()));

    }
}
