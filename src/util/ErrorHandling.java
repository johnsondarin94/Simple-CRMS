package util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class ErrorHandling {
    public static void displayError(String errorMessage){
        Alert alert = new Alert(Alert.AlertType.ERROR, errorMessage);
        Optional<ButtonType> result = alert.showAndWait();
    }

    public static boolean displayConfirmation(String confirmationMessage){
        boolean confirm = false;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, confirmationMessage);
        alert.setTitle("Confirmation Dialogue");
        alert.setHeaderText("Delete?");
        alert.setContentText("Are you sure you want to Delete the selected item?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
        } else {
            // ... user chose CANCEL or closed the dialog
        }

        return confirm;
    }

    public static void displayInformation(String informationMessage){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(informationMessage);

        Optional<ButtonType> result = alert.showAndWait();
    }
}
