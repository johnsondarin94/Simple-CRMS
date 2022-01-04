package controller;

import Database.DatabaseCustomers;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customers;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import util.ErrorHandling;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {
    public Button signOff;
    public Button updateButton;
    public Button addButton;
    public Button reportsButton;
    public Button appointmentsButton;
    public TableView customerTable;
    public TableColumn customerId;
    public TableColumn customerName;
    public TableColumn address;
    public TableColumn zipCode;
    public TableColumn phoneNumber;
    public TableColumn createDate;
    public TableColumn createdBy;
    public TableColumn lastUpdate;
    public TableColumn divisionId;
    public TableColumn lastUpdatedBy;

    private static Customers customerHandOff = null;
    public Button deleteButton;

    public void onSignOff(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 275, 350);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public void onAdd(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 450, 500);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }

    public void onUpdate(ActionEvent actionEvent) throws IOException {
        try {
            customerHandOff = (Customers) customerTable.getSelectionModel().getSelectedItem();
            Parent root = FXMLLoader.load(getClass().getResource("/view/UpdateCustomer.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 450, 500);
            stage.setTitle("Update Customer");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e){
            ErrorHandling.displayError("No Customer selected.");
        }
    }

    public static Customers getCustomerHandOff(){
        return customerHandOff;
    }

    public void onReports(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Reports.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1050, 500);
        stage.setTitle("Reports");
        stage.setScene(scene);
        stage.show();
    }

    public void onAppointments(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 750, 550);
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<Customers> customerList = DatabaseCustomers.getAllCustomers();
        for(Customers C : customerList){
            customerTable.setItems(customerList);
            customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            customerName.setCellValueFactory(new PropertyValueFactory<>("name"));
            address.setCellValueFactory(new PropertyValueFactory<>("address"));
            zipCode.setCellValueFactory(new PropertyValueFactory<>("zipCode"));
            phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            createDate.setCellValueFactory(new PropertyValueFactory<>("createDate"));
            createdBy.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
            lastUpdate.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
            lastUpdatedBy.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
            divisionId.setCellValueFactory(new PropertyValueFactory<>("divisionId"));

        }

    }

    public void onDelete(ActionEvent actionEvent) {
        customerHandOff = (Customers) customerTable.getSelectionModel().getSelectedItem();
        int id = getCustomerHandOff().getCustomerId();

        DatabaseCustomers.deleteCustomer(id);
        customerTable.getSelectionModel().clearSelection();
    }
}
