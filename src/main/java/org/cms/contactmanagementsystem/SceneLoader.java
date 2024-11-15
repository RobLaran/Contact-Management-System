package org.cms.contactmanagementsystem;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

public interface SceneLoader {

    void loadScene(String fxml, ActionEvent e);

    void loadScene(String fxml, MouseEvent e);

}
