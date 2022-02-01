package controller;

import Database.DatabaseAppointments;
import Database.DatabaseCustomers;
import javafx.collections.FXCollections;
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

/**Controller Class for the Customers page. Handles navigation to other pages and displays a current list of customers*/
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

    /**Returns user to Login Screen. LAMBDA 1 IS USED HERE.
     * @param actionEvent  Action Event for Sign Off button*/
    public void onSignOff(ActionEvent actionEvent) throws IOException {
        navigate.navigate(actionEvent, "/view/Login.fxml", "Login", 275, 350 );
    }

    /**Sends User to Add Customer Screen. LAMBDA 1 IS USED HERE.
     * @param actionEvent Action Event for Add Button*/
    public void onAdd(ActionEvent actionEvent) throws IOException {
        navigate.navigate(actionEvent, "/view/AddCustomer.fxml", "Add Customer", 450,500);
    }

    /**Retrieves selected customer from the table and navigates to Update Customer page. LAMBDA 1 IS USED HERE.
     * @param actionEvent Action Event for Update Button (Must have Customer selected)
     */
    public void onUpdate(ActionEvent actionEvent) throws IOException {
        try {
            customerHandOff = (Customers) customerTable.getSelectionModel().getSelectedItem();
            navigate.navigate(actionEvent, "/view/UpdateCustomer.fxml", "Update Customer", 450, 500 );
        } catch (Exception e){
            ErrorHandling.displayError("No Customer selected.");
        }
    }

    /**Retrieves Customers object. Used to pass Customers object to update page
     * @return Returns Customer object*/
    public static Customers getCustomerHandOff(){
        return customerHandOff;
    }

    /**Sends User to Reports Page. LAMBDA 1 IS USED HERE
     * @param actionEvent Action Event for Reports Button*/
    public void onReports(ActionEvent actionEvent) throws IOException {
        navigate.navigate(actionEvent, "/view/Reports.fxml", "Reports", 850, 650);
    }

    /**Sends User to Appointments Page. LAMBDA 1 IS USED HERE*/
    public void onAppointments(ActionEvent actionEvent) throws IOException {
        navigate.navigate(actionEvent, "/view/Appointments.fxml", "Appointments", 1000, 550);
    }

    /**Method displays alert at the initialize alerting User if there is an appointment within 15 minutes of User Login.*/
    public void checkForAppointments(){
        ObservableList<Appointments> apts = DatabaseAppointments.getAllAppointments();
        ObservableList<Appointments> upcomingApts = FXCollections.observableArrayList();
        LocalDateTime ldt = LocalDateTime.now().plusMinutes(15);
        for(Appointments a : apts){
            if((a.getStartDateTime().isBefore(ldt)) && (a.getStartDateTime().isAfter(ldt.minusMinutes(15)))){
                upcomingApts.add(a);
            }
        }
        if(upcomingApts.isEmpty()){
            ErrorHandling.displayInformation("There are no upcoming Appointments");
        }
        else{
            for(Appointments apt: upcomingApts){
                ErrorHandling.displayInformation("There is an appointment within 15 minutes.\n" +
                        "Title: " +apt.getTitle()+ "Type: " +apt.getType());
            }
        }
    }


    /**Initialize for Customers Page. Populates table view with list of all customers, and alerts user of any upcoming
     * appointments (within 15 minutes) by calling checkForAppointments*/
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

    /**Handles the deletion of a customer. Customer must have no appointments tied to it, Customer must also be
     * selected for the delete to take place. LAMBDA 1 IS USED HERE.
     * @param actionEvent Action Event for the Delete button*/
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
                    navigate.navigate(actionEvent, "/view/Customers.fxml", "Customers", 1100, 550);
                }

        } catch (Exception e) {
            ErrorHandling.displayError("Please select a Customer to delete");
        }
    }
}
