package util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

/**ErrorHandling Class provides various display boxes for clean code. Handles error messages, confirmation messages, and general information.*/
public class ErrorHandling {

    /**Displays an Error message. Takes in a String for the message.
     *@param errorMessage Sets the contents of the message.
     * */
    public static void displayError(String errorMessage){
        Alert alert = new Alert(Alert.AlertType.ERROR, errorMessage);
        Optional<ButtonType> result = alert.showAndWait();
    }

    /**Displays a Confirmation message. Takes in a String as a message and returns a boolean dependant on user choice.
     * @param confirmationMessage Sets the content of the message.
     * @return Returns confirm(boolean) depending on what the user chose.
     * */
    public static boolean displayConfirmation(String confirmationMessage){
        boolean confirm;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, confirmationMessage);
        alert.setTitle("Confirmation Dialogue");
        alert.setHeaderText("Confirm");
        alert.setContentText(confirmationMessage);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            confirm = true;
        } else {
            confirm = false;
        }

        return confirm;
    }

    /**Displays an Information Message. Takes in a String for the message.
     * @param informationMessage Sets the contents of the message.
     * */
    public static void displayInformation(String informationMessage){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(informationMessage);

        Optional<ButtonType> result = alert.showAndWait();
    }
}
