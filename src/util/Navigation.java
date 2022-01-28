package util;

import javafx.event.ActionEvent;

import java.io.IOException;

public interface Navigation {
    void navigate(ActionEvent actionEvent, String path, String title, int x, int y) throws IOException;
}
