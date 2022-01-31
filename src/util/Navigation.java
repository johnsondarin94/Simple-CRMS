package util;

import javafx.event.ActionEvent;

import java.io.IOException;

/**Interface for a lambda expression. Used to clean up code used for navigating the application.*/
public interface Navigation {
    void navigate(ActionEvent actionEvent, String path, String title, int x, int y) throws IOException;
}
