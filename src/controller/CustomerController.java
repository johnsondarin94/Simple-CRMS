package controller;

import Database.DatabaseAppointments;
import Database.DatabaseCustomers;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointments;
import model.Customers;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.ErrorHandling;
import controller.Login;
import util.Navigation;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;
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

    private static Customers customerHandOff = null;
    public Button deleteButton;
    public TableColumn country;
    public TableColumn division;

    Navigation navigate = (actionEvent, path, title, x, y) -> {
        Parent root = FXMLLoader.load(getClass().getResource(path));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, x, y);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    };


    public void onSignOff(ActionEvent actionEvent) throws IOException {
        navigate.navigate(actionEvent, "/view/Login.fxml", "Login", 275, 350 );
        /*
        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 275, 350);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
        */

    }

    public void onAdd(ActionEvent actionEvent) throws IOException {
        navigate.navigate(actionEvent, "/view/AddCustomer.fxml", "Add Customer", 450,500);
        /*
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 450, 500);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
        */

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
        Scene scene = new Scene(root, 850, 650);
        stage.setTitle("Reports");
        stage.setScene(scene);
        stage.show();
    }

    public void onAppointments(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 550);
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();
    }

    public void checkForAppointments(){
        ObservableList<Appointments> apts = DatabaseAppointments.getAllAppointments();
        LocalDateTime ldt = LocalDateTime.now().plusMinutes(15);
        for(Appointments a : apts){
            if((a.getStartDateTime().isBefore(ldt)) && (a.getStartDateTime().isAfter(ldt.minusMinutes(15)))){
                System.out.println("There is an appointment within 15 minutes");
            }
            else{
                System.out.println("There are no upcoming appointments");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        checkForAppointments();

        ObservableList<Customers> customerList = DatabaseCustomers.getAllCustomers();
        for(Customers c : customerList){
            customerTable.setItems(customerList);
            customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            customerName.setCellValueFactory(new PropertyValueFactory<>("name"));
            address.setCellValueFactory(new PropertyValueFactory<>("address"));
            zipCode.setCellValueFactory(new PropertyValueFactory<>("zipCode"));
            phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            division.setCellValueFactory(new PropertyValueFactory<>("division"));
            country.setCellValueFactory(new PropertyValueFactory<>("country"));
        }
    }

    public void onDelete(ActionEvent actionEvent) {
        try{
            Customers selectedCustomer = (Customers) customerTable.getSelectionModel().getSelectedItem();
            int id = selectedCustomer.getCustomerId();

            ObservableList<Appointments> associatedAppointments = DatabaseAppointments.getAssociatedAppointments(id);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete Selected Customer?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK)
                if (associatedAppointments.size() >= 1) {
                    ErrorHandling.displayError("Cannot delete Customer, has Associated Appointments");
                }
                else {
                    DatabaseCustomers.deleteCustomer(id);
                    ErrorHandling.displayInformation("Successfully deleted Customer: " + selectedCustomer.getName());
                    customerTable.getSelectionModel().clearSelection();
                    Parent root = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root, 1100, 550);
                    stage.setTitle("Customers");
                    stage.setScene(scene);
                    stage.show();
                }

        } catch (Exception e) {
            ErrorHandling.displayError("Please select a Customer to delete");
        }
    }
}
